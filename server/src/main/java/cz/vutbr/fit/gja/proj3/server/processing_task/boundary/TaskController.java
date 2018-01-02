package cz.vutbr.fit.gja.proj3.server.processing_task.boundary;

import cz.vutbr.fit.gja.proj3.server.node.boundary.NodeRepository;
import cz.vutbr.fit.gja.proj3.server.node.entity.Node;
import cz.vutbr.fit.gja.proj3.server.processing_task.entity.ProcessingTask;
import cz.vutbr.fit.gja.proj3.server.processing_task.entity.ProcessingTaskUnit;
import cz.vutbr.fit.gja.proj3.server.project.boundary.ProjectRepository;
import cz.vutbr.fit.gja.proj3.server.project.entity.Project;
import cz.vutbr.fit.gja.proj3.server.utils.GuiUtils;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.java.Log;
import org.hibernate.SessionFactory;
import org.ocpsoft.rewrite.annotation.Join;
import org.ocpsoft.rewrite.annotation.RequestAction;
import org.ocpsoft.rewrite.el.ELBeanName;
import org.ocpsoft.rewrite.faces.annotation.Deferred;
import org.ocpsoft.rewrite.faces.annotation.IgnorePostback;
import org.primefaces.event.RowEditEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.persistence.EntityManagerFactory;
import java.util.ArrayList;
import java.util.List;

import static cz.vutbr.fit.gja.proj3.server.utils.GuiUtils.showInfo;
import java.util.ArrayList;
import lombok.Getter;
import lombok.Setter;
import org.primefaces.event.RowEditEvent;

@Log
@Scope(value = "session")
@Component(value = "taskController")
@ELBeanName(value = "taskController")
@Join(path = "/task", to = "/task-list.jsf")

@ManagedBean(name = "taskController")
@ViewScoped
public class TaskController {

    protected final TaskRepository taskRepository;
    protected final ProjectRepository projectRepository;
    protected final NodeRepository nodeRepository;
    protected final TaskRestController taskRestController;

    @Getter @Setter
    protected List<ProcessingTask> processingTasks;
    
    @Getter @Setter
    protected ProcessingTask selectedProcessingTask = new ProcessingTask();
    
    @Getter @Setter
    protected ProcessingTask newTask = new ProcessingTask();
    
    @Getter @Setter
    protected ProcessingTaskUnit newTaskUnit = new ProcessingTaskUnit();
    
    @Getter @Setter
    protected List<ProcessingTask> filteredTasks = new ArrayList<>();
    private Node selectedNode;

    @Autowired
    public TaskController(TaskRepository taskRepository, ProjectRepository projectRepository, NodeRepository nodeRepository, TaskRestController taskRestController) {
        this.taskRepository = taskRepository;
        this.projectRepository = projectRepository;
        this.nodeRepository = nodeRepository;
        this.taskRestController = taskRestController;
    }

    /**
     * Loads processing tasks on page load.
     */
    @Deferred
    @RequestAction
    @IgnorePostback
    public void loadData() {
        processingTasks = taskRepository.findAll();
    }
    
    public void onRowEditTaskUnit(RowEditEvent event) {
        this.taskRepository.save(selectedProcessingTask);
        this.loadData();
    }

    public void addCommand() {
        newTaskUnit.setProcessingTask(selectedProcessingTask);
        selectedProcessingTask.getProcessingTaskUnits().add(newTaskUnit);
        selectedProcessingTask = taskRepository.save(selectedProcessingTask);
        taskRepository.flush();
        showInfo("Command created.");
        newTaskUnit = new ProcessingTaskUnit();
    }

    public void addNewTask() {
        newTask.setUser(GuiUtils.getLoggedUser());
        taskRepository.save(newTask);
        this.loadData();
        showInfo("Task " + newTask.getName() + " created.");
        newTask = new ProcessingTask();
    }

    public void update() {
        taskRepository.save(selectedProcessingTask);
        this.loadData();
        showInfo("Task " + selectedProcessingTask.getName() + " updated.");
    }
    
    public void remove(ProcessingTask pt) {
        showInfo("Task " + pt.getName() + " removed.");
        if (pt == this.selectedProcessingTask) {
            this.selectedProcessingTask = null;
        }
        taskRepository.delete(pt);
        this.loadData();
    }
    
    public void removeTaskUnit(ProcessingTaskUnit ptu) {
        showInfo("Command was removed from task.");
        this.selectedProcessingTask.getProcessingTaskUnits().remove(ptu);
        selectedProcessingTask = taskRepository.save(this.selectedProcessingTask);
        this.loadData();
    }
    
    public List<Project> getProjects() {
        return projectRepository.findAll();
    }
    
    public void startTaskExecution() {
        // TODO call this from the GUI
        try {
            log.info("called");
            // TODO add real node selection
            List<Node> allNodes = nodeRepository.findAll();
            if (allNodes.isEmpty()) {
                log.severe("No nodes!!");
                return;
            }
            this.selectedNode = selectedProcessingTask.getNode();

            taskRestController.startTaskExecution(selectedProcessingTask, selectedNode);
        } catch (Exception ex) {
            log.severe("exception: " + ex.getMessage());
            ex.printStackTrace();
        }
    }


    /**
     * Autocomplete for projects drop-down menu.
     * @param query Searching query
     * @return List of all projects; filtered if query is not empty
     */
    public List<Project> projectList(String query) {
        List<Project> projects = this.projectRepository.findAll();
        List<Project> filtered = new ArrayList<>();
        
        for (Project project : projects) {
            if (project.getName().toLowerCase().startsWith(query)) {
                filtered.add(project);
            }
        }
        
        return filtered;
    }

    /**
     * Autocomplete for nodes drop-down menu.
     * @param query Searching query
     * @return List of all nodes; filtered if query is not empty
     */
    public List<Node> nodeList(String query) {
        List<Node> nodes = this.nodeRepository.findAll();
        List<Node> filtered = new ArrayList<>();
        
        for (Node node : nodes) {
            if (node.getName().toLowerCase().startsWith(query)) {
                filtered.add(node);
            }
        }
        
        return filtered;
    }
}

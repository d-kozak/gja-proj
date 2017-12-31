package cz.vutbr.fit.gja.proj3.server.processing_task.boundary;

import cz.vutbr.fit.gja.proj3.server.node.boundary.NodeRepository;
import cz.vutbr.fit.gja.proj3.server.node.entity.Node;
import cz.vutbr.fit.gja.proj3.server.processing_task.entity.ProcessingTask;
import cz.vutbr.fit.gja.proj3.server.processing_task.entity.ProcessingTaskUnit;
import cz.vutbr.fit.gja.proj3.server.project.boundary.ProjectRepository;
import cz.vutbr.fit.gja.proj3.server.project.entity.Project;
import cz.vutbr.fit.gja.proj3.server.utils.GuiUtils;
import lombok.extern.java.Log;
import org.ocpsoft.rewrite.annotation.Join;
import org.ocpsoft.rewrite.annotation.RequestAction;
import org.ocpsoft.rewrite.el.ELBeanName;
import org.ocpsoft.rewrite.faces.annotation.Deferred;
import org.ocpsoft.rewrite.faces.annotation.IgnorePostback;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import java.util.List;

import static cz.vutbr.fit.gja.proj3.server.utils.GuiUtils.showInfo;
import java.util.ArrayList;
import javafx.concurrent.Task;
import javax.persistence.EntityManagerFactory;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.SessionFactory;
import org.primefaces.event.RowEditEvent;
import org.primefaces.event.SelectEvent;

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
    protected final SessionFactory hibernateSession;

    @Getter
    @Setter
    protected List<ProcessingTask> processingTasks;
    
    @Getter
    @Setter
    protected ProcessingTask selectedProcessingTask = new ProcessingTask();
    
    @Getter
    @Setter
    protected ProcessingTask newTask = new ProcessingTask();
    
    @Getter
    @Setter
    protected ProcessingTaskUnit newTaskUnit = new ProcessingTaskUnit();

    @Autowired
    public TaskController(TaskRepository taskRepository, ProjectRepository projectRepository, NodeRepository nodeRepository, EntityManagerFactory factory) {
        this.taskRepository = taskRepository;
        this.projectRepository = projectRepository;
        this.nodeRepository = nodeRepository;
        
        if (factory.unwrap(SessionFactory.class) == null){
            throw new NullPointerException("factory is not a hibernate factory");
        }
        this.hibernateSession = factory.unwrap(SessionFactory.class);
    }

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
        this.selectedProcessingTask = taskRepository.save(this.selectedProcessingTask);
        this.loadData();
    }
    
    /**
     * Autocomplete method for projects 
     * @param query - searching query
     * @return - list of filtered projects (or all - without query)
     */
    public List<Project> projectList(String query) {
        List<Project> projects = this.projectRepository.findAll();
        List<Project> filtered = new ArrayList<>();
        for (int i = 0; i < projects.size(); i++) {
            Project project = projects.get(i);
            if (project.getName().toLowerCase().startsWith(query)) {
                filtered.add(project);
            }
        }
        return filtered;
    }
    
    /**
     * Autocomplete method for nodes 
     * @param query - searching query
     * @return - list of filtered nodes (or all - without query)
     */
    public List<Node> nodeList(String query) {
        List<Node> nodes = this.nodeRepository.findAll();
        List<Node> filtered = new ArrayList<>();
        for (int i = 0; i < nodes.size(); i++) {
            Node node = nodes.get(i);
            if (node.getName().toLowerCase().startsWith(query)) {
                filtered.add(node);
            }
        }
        return filtered;
    }
}

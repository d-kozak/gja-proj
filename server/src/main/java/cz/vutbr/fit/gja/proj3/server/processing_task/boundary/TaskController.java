package cz.vutbr.fit.gja.proj3.server.processing_task.boundary;

import cz.vutbr.fit.gja.proj3.common.processing_task.entity.OutputType;
import cz.vutbr.fit.gja.proj3.server.node.boundary.NodeRepository;
import cz.vutbr.fit.gja.proj3.server.node.entity.Node;
import cz.vutbr.fit.gja.proj3.server.processing_task.entity.OutputVerification;
import cz.vutbr.fit.gja.proj3.server.processing_task.entity.ProcessingTask;
import cz.vutbr.fit.gja.proj3.server.processing_task.entity.ProcessingTaskUnit;
import cz.vutbr.fit.gja.proj3.server.processing_task.entity.TaskState;
import cz.vutbr.fit.gja.proj3.server.project.boundary.ProjectRepository;
import cz.vutbr.fit.gja.proj3.server.project.entity.Project;
import cz.vutbr.fit.gja.proj3.server.utils.GuiUtils;
import static cz.vutbr.fit.gja.proj3.server.utils.GuiUtils.getParam;
import static cz.vutbr.fit.gja.proj3.server.utils.GuiUtils.showError;
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
import java.util.Arrays;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import javax.faces.event.AjaxBehaviorEvent;
import lombok.Getter;
import lombok.Setter;
import org.primefaces.event.ReorderEvent;
import org.primefaces.event.RowEditEvent;

/**
 * Task section backend controller.
 */
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
    protected final TaskService service;

    @Getter @Setter
    protected List<ProcessingTask> processingTasks;
    
    @Getter @Setter
    protected ProcessingTask selectedProcessingTask = new ProcessingTask();
    
    @Getter @Setter
    protected ProcessingTask newTask = new ProcessingTask();
    
    @Getter @Setter
    protected ProcessingTaskUnit newTaskUnit = new ProcessingTaskUnit();
    
    @Getter @Setter
    protected Project filterProject;
    
    @Getter @Setter
    protected Node filterNode;
    
    @Getter
    protected List<Project> projects;
    
    @Getter
    protected List<Node> nodes;
    
    @Getter @Setter
    protected String selectedId;
    
    @Getter @Setter
    protected String outputValue;
    
    private Node selectedNode;

    @Autowired
    public TaskController(TaskRepository taskRepository, ProjectRepository projectRepository, NodeRepository nodeRepository, TaskRestController taskRestController, TaskService service) {
        this.taskRepository = taskRepository;
        this.projectRepository = projectRepository;
        this.nodeRepository = nodeRepository;
        this.taskRestController = taskRestController;
        this.service = service;
    }

    /**
     * Loads processing tasks on page load.
     */
    @Deferred
    @RequestAction
    @IgnorePostback
    public void loadData() {
        projects = projectRepository.findAllEagerFetch();
        nodes = nodeRepository.findAllEagerFetch();

        processingTasks = taskRepository.findAllEagerFetch();
        
        if (filterProject != null) {
            processingTasks = processingTasks.stream().filter(task -> task.getProject() != null && task.getProject().equals(filterProject)).collect(Collectors.toList());
        }
        
        if (filterNode != null) {
            processingTasks = processingTasks.stream().filter(task -> task.getNode() != null && task.getNode().equals(filterNode)).collect(Collectors.toList());
        }
    }

    /**
     * Reloads database data on filter change.
     */
    public void filterChange() {
        this.loadData();
    }
    
    /**
     * Handles URL parameters.
     */
    public String onload() {
        if (getParam("id") == null) 
            selectedId = null;
        
        if (selectedId != null) {
            Long id;
            try {
                id = Long.parseLong(selectedId);
                selectedProcessingTask = processingTasks.stream()
                    .filter(item -> item.getId() == id)
                    .findAny()
                .get();
                return "task-list.xhtml?faces-redirect=true";
            }catch (NumberFormatException ex) {
                showError("Invalid task ID.");
                return "task-list.xhtml?faces-redirect=true";
            }catch (NoSuchElementException ex) {
                showError("Task with that ID does not exist.");
                return "task-list.xhtml?faces-redirect=true";
            }
        }
        
        return null;
    }
    
    /**
     * Updates task units.
     * @param event Edit event
     */
    public void onRowEditTaskUnit(RowEditEvent event) {
        updateSelectedTask();
        showInfo("Task command updated.");
    }
    
    /**
     * Updates task units ordering.
     * @param event Ordering event
     */
    public void onRowReorderTaskUnit(ReorderEvent event) {
        updateSelectedTask();
    }

    /**
     * Adds new command (task unit) to database based on filled information.
     */
    public void addCommand() {
        newTaskUnit.setProcessingTask(selectedProcessingTask);
        
        switch (newTaskUnit.getOutputVerification().getOutputType()) {
            case SINGLE_FILE:
            case REGEX:
                newTaskUnit.getOutputVerification().getExpectedOutput().add(outputValue);
                break;
            case ENUMERATED_LIST:
                newTaskUnit.getOutputVerification().setExpectedOutput(Arrays.asList(outputValue.split(",")));
                break;
            default:
                break;
        }
        
        selectedProcessingTask.getProcessingTaskUnits().add(newTaskUnit);
        selectedProcessingTask = service.save(selectedProcessingTask);
        showInfo("Command created.");
        newTaskUnit = new ProcessingTaskUnit();
        outputValue = "";
    }

    /**
     * Adds new task to database based on filled information.
     */
    public void addNewTask() {
        newTask.setUser(GuiUtils.getLoggedUser());
        taskRepository.save(newTask);
        this.loadData();
        showInfo("Task " + newTask.getName() + " created.");
        newTask = new ProcessingTask();
    }

    /**
     * Updates selected task.
     */
    public void update() {
        updateSelectedTask();
        showInfo("Task " + selectedProcessingTask.getName() + " updated.");
    }
    
    private void updateSelectedTask() {
        selectedProcessingTask = service.save(selectedProcessingTask);
        this.loadData();
    }
    
    /**
     * Removes specified task from database.
     * @param pt Task entity to be removed
     */
    public void remove(ProcessingTask pt) {
        showInfo("Task " + pt.getName() + " removed.");
        if (pt == this.selectedProcessingTask) {
            this.selectedProcessingTask = null;
        }
        taskRepository.delete(pt);
        this.loadData();
    }
    
    /**
     * Removes specified task command from database.
     * @param ptu Task command to be removed
     */
    public void removeTaskUnit(ProcessingTaskUnit ptu) {
        showInfo("Command was removed from task.");
        this.selectedProcessingTask.getProcessingTaskUnits().remove(ptu);
        selectedProcessingTask = service.save(this.selectedProcessingTask);
        this.loadData();
    }
    
    /**
     * Starts task command chain execution.
     */
    public void startTaskExecution() {
        try {
            log.info("called");
            List<Node> allNodes = nodeRepository.findAll();
            if (allNodes.isEmpty()) {
                log.severe("No nodes!!");
                return;
            }
            this.selectedNode = selectedProcessingTask.getNode();

            try {
                showInfo("Started task execution.");
                taskRestController.startTaskExecution(selectedProcessingTask, selectedNode);
            }catch (Exception e) {
                showError("Task execution failed.");
            }
            
            loadData();
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
        return projects.stream().filter((project) -> (project.getName().toLowerCase().startsWith(query.toLowerCase()))).collect(Collectors.toList());
    }

    /**
     * Autocomplete for nodes drop-down menu.
     * @param query Searching query
     * @return List of all nodes; filtered if query is not empty
     */
    public List<Node> nodeList(String query) {        
        return nodes.stream().filter((node) -> (node.getName().toLowerCase().startsWith(query.toLowerCase()))).collect(Collectors.toList());
    }
    
    /**
     * Retrieves Task states constant.
     * @return Map of constants and descriptions
     */
    public Map<TaskState, String> getConstStates() {
        return TaskRepository.STATES;
    }
    
    /**
     * Retrieves output verification types constant.
     * @return Map of constants and descriptions
     */
    public Map<OutputType, String> getConstOutputTypes() {
        return TaskRepository.OUTPUT_TYPES;
    }
}

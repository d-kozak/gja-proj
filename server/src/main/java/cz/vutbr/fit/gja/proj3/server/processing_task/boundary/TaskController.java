package cz.vutbr.fit.gja.proj3.server.processing_task.boundary;

import cz.vutbr.fit.gja.proj3.server.processing_task.entity.ProcessingTask;
import cz.vutbr.fit.gja.proj3.server.processing_task.entity.ProcessingTaskUnit;
import lombok.extern.java.Log;
import org.ocpsoft.rewrite.annotation.Join;
import org.ocpsoft.rewrite.annotation.RequestAction;
import org.ocpsoft.rewrite.el.ELBeanName;
import org.ocpsoft.rewrite.faces.annotation.Deferred;
import org.ocpsoft.rewrite.faces.annotation.IgnorePostback;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
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
    protected final TaskUnitRepository taskUnitRepository;

    protected List<ProcessingTask> processingTasks;

    protected ProcessingTask selectedProcessingTask = new ProcessingTask();
    protected ProcessingTaskUnit selectedProcessingTaskUnit = new ProcessingTaskUnit();
 
    @Getter
    @Setter
    protected ProcessingTask newTask = new ProcessingTask();
    @Getter
    @Setter
    protected ProcessingTaskUnit newTaskUnit = new ProcessingTaskUnit();

    @Autowired
    public TaskController(TaskRepository taskRepository, TaskUnitRepository taskUnitRepository) {
        this.taskRepository = taskRepository;
        this.taskUnitRepository = taskUnitRepository;
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
    
    public void onRowSelectTaskUnit(SelectEvent event) {
        showInfo("ProcessingTask selected", ((ProcessingTask) event.getObject()).getName());
    }

    public void onRowSelect(SelectEvent event) {
        showInfo("ProcessingTask selected", ((ProcessingTask) event.getObject()).getName());
    }
    
    public void addCommand() {
        /*if (selectedProcessingTask != null) {
            newTaskUnit.setProcessingTask(selectedProcessingTask);
            taskUnitRepository.save(newTaskUnit);
        }*/
        newTaskUnit.setProcessingTask(selectedProcessingTask);
        selectedProcessingTask.getProcessingTaskUnits().add(newTaskUnit);
        
        for (ProcessingTaskUnit ptu : selectedProcessingTask.getProcessingTaskUnits()) {
            log.info(ptu.toString()); 
        }
       
        taskRepository.save(selectedProcessingTask);
        this.loadData();
        showInfo("Command created.");
        newTaskUnit = new ProcessingTaskUnit();
    }

    public void addNewTask() {
        taskRepository.save(newTask);
        this.loadData();
        showInfo("Task " + newTask.getName() + " created.");
        newTask = new ProcessingTask();
    }

    public void update() {
        for (ProcessingTaskUnit ptu: selectedProcessingTask.getProcessingTaskUnits()) {
            log.info(ptu.getCommand());
        }
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
        this.taskUnitRepository.delete(ptu);
        this.selectedProcessingTask.getProcessingTaskUnits().remove(ptu);
        taskRepository.save(this.selectedProcessingTask);
        this.loadData();
    }

    public List<ProcessingTask> getProcessingTasks() {
        return processingTasks;
    }

    public void setProcessingTasks(List<ProcessingTask> processingTasks) {
        this.processingTasks = processingTasks;
    }

    public ProcessingTask getSelectedProcessingTask() {
        return selectedProcessingTask;
    }

    public void setSelectedProcessingTask(ProcessingTask selectedProcessingTask) {
        this.selectedProcessingTask = selectedProcessingTask;
    }

    public ProcessingTaskUnit getSelectedProcessingTaskUnit() {
        return selectedProcessingTaskUnit;
    }

    public void setSelectedProcessingTaskUnit(ProcessingTaskUnit selectedProcessingTaskUnit) {
        this.selectedProcessingTaskUnit = selectedProcessingTaskUnit;
    }
}

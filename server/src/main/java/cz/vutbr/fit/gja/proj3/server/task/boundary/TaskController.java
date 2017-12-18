package cz.vutbr.fit.gja.proj3.server.task.boundary;

import cz.vutbr.fit.gja.proj3.server.task.entity.Task;
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

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import java.util.List;

@Log
@Scope(value = "session")
@Component(value = "taskController")
@ELBeanName(value = "taskController")
@Join(path = "/task", to = "/task-list.jsf")

@ManagedBean(name = "taskController")
@ViewScoped
public class TaskController {

    private final TaskRepository taskRepository;

    private List<Task> tasks;

    private Task selectedTask = new Task();

    @Autowired
    public TaskController(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Deferred
    @RequestAction
    @IgnorePostback
    public void loadData() {
        tasks = taskRepository.findAll();
    }

    public void onRowSelect(SelectEvent event) {
        log.info("row select");
        FacesMessage msg = new FacesMessage("Task selected", ((Task) event.getObject()).getName());
        FacesContext.getCurrentInstance()
                    .addMessage(null, msg);
    }

    public void onRowUnselect(UnselectEvent event) {
        log.info("row unselect");
        FacesMessage msg = new FacesMessage("Task unselected", ((Task) event.getObject()).getName());
        FacesContext.getCurrentInstance()
                    .addMessage(null, msg);
    }

    public String save() {
        log.info("Saving");
        taskRepository.save(selectedTask);
        selectedTask = new Task();
        return "/task-list.xhtml?faces-redirect=true";
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }

    public Task getSelectedTask() {
        return selectedTask;
    }

    public void setSelectedTask(Task selectedTask) {
        this.selectedTask = selectedTask;
    }
}

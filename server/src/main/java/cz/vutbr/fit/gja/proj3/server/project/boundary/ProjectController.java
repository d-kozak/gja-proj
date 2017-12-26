package cz.vutbr.fit.gja.proj3.server.project.boundary;

import cz.vutbr.fit.gja.proj3.server.processing_task.entity.ProcessingTask;
import cz.vutbr.fit.gja.proj3.server.processing_task.entity.ProcessingTaskUnit;
import cz.vutbr.fit.gja.proj3.server.project.entity.Project;
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

import static cz.vutbr.fit.gja.proj3.server.utils.GuiUtils.showInfo;
import lombok.Getter;
import lombok.Setter;

@Log
@Scope(value = "session")
@Component(value = "projectController")
@ELBeanName(value = "projectController")
@Join(path = "/project", to = "/project-list.jsf")

@ManagedBean(name = "projectController")
@ViewScoped
public class ProjectController {

    private final ProjectRepository projectRepository;

    @Getter
    @Setter
    private List<Project> projects;

    @Getter
    @Setter
    private Project selectedProject = new Project();

    @Getter
    @Setter
    private Project newProject = new Project();

    @Autowired
    public ProjectController(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }
    
    @Deferred
    @RequestAction
    @IgnorePostback
    public void loadData() {
        projects = projectRepository.findAllEagerFetch();
    }


    public void onRowSelect(SelectEvent event) {
        log.info("row select");
        FacesMessage msg = new FacesMessage("Project selected", ((Project) event.getObject()).getName());
        FacesContext.getCurrentInstance()
                    .addMessage(null, msg);
    }

    public void onRowUnselect(UnselectEvent event) {
        log.info("row unselect");
        FacesMessage msg = new FacesMessage("Project unselected", ((ProcessingTask) event.getObject()).getName());
        FacesContext.getCurrentInstance()
                    .addMessage(null, msg);
    }

    public String save() {
        log.info("Saving");
        projectRepository.save(selectedProject);
        selectedProject = new Project();
        return "/project-list.xhtml?faces-redirect=true";
    }

    public void addNewProject() {
        projectRepository.save(newProject);
        String message = "Project " + newProject.getName() + " created";
        showInfo(message);
        log.info(message);
        newProject = new Project();
        this.loadData();
    }

    public void update() {
        projectRepository.save(selectedProject);
        String message = "Project " + selectedProject.getName() + " updated";
        log.info(message);
        showInfo(message);
    }
}

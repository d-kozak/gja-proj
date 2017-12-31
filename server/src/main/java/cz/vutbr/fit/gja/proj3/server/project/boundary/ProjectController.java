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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import java.util.List;

import static cz.vutbr.fit.gja.proj3.server.utils.GuiUtils.showInfo;
import static cz.vutbr.fit.gja.proj3.server.utils.GuiUtils.showError;
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

    public void addNewProject() {
        projectRepository.save(newProject);
        this.loadData();
        showInfo("Project " + newProject.getName() + " created."); 
        this.newProject = new Project();
    }

    public void update() {
        projectRepository.save(selectedProject);
        this.loadData();
        showInfo("Project updated.");
    }
    
    public void remove(Project p) {
        showInfo("Project " + p.getName() + " removed.");
        if (p == this.selectedProject) {
            this.selectedProject = null;
        }
        projectRepository.delete(p);
        this.loadData();
    }
}

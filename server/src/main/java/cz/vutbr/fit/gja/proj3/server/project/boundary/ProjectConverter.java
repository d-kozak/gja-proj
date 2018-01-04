package cz.vutbr.fit.gja.proj3.server.project.boundary;

import cz.vutbr.fit.gja.proj3.server.project.entity.Project;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;
import lombok.extern.java.Log;
import org.ocpsoft.rewrite.el.ELBeanName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Project UI section converter.
 */
@Log
@ManagedBean(name = "projectConverter")
@Component(value = "projectConverter")
@ELBeanName(value = "projectConverter")
@RequestScoped
@FacesConverter(value="projectConverter", forClass = Project.class)
public class ProjectConverter implements Converter {  
    @Autowired
    private ProjectRepository repository;
    
    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String string) {
        if (string != null && !string.isEmpty()) {
            Project project = this.repository.findOne(Long.parseLong(string));
            return project;  
        }else{
            return null;
        }
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object o) {
        if (o != null) {
            Project project = (Project)o;
            return Long.toString(project.getId());
        }else{
            return null;
        }
    }
}

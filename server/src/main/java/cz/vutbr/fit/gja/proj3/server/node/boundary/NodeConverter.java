package cz.vutbr.fit.gja.proj3.server.node.boundary;

import cz.vutbr.fit.gja.proj3.server.node.entity.Node;
import lombok.extern.java.Log;
import org.ocpsoft.rewrite.el.ELBeanName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@Log
@ManagedBean(name = "nodeConverter")
@Component(value = "nodeConverter")
@ELBeanName(value = "nodeConverter")
@RequestScoped
@FacesConverter(value = "nodeConverter", forClass = Node.class)
public class NodeConverter implements Converter {  
    @Autowired
    private NodeRepository repository;

    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String string) {
        if (string != null && !string.isEmpty()) {
            Node node = this.repository.findOne(Long.parseLong(string));
            return node;
        } else {
            return null;
        }
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object o) {        
        if (o != null) {
            Node node = (Node) o;
            return Long.toString(node.getId());
        } else {
            return "";
        }
    }
}

package cz.vutbr.fit.gja.proj3.server.dashboard;

import cz.vutbr.fit.gja.proj3.server.node.boundary.NodeRepository;
import cz.vutbr.fit.gja.proj3.server.node.entity.Node;
import java.util.List;
import javax.annotation.PostConstruct;
import lombok.extern.java.Log;
import org.ocpsoft.rewrite.annotation.Join;
import org.ocpsoft.rewrite.el.ELBeanName;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import lombok.Getter;
import lombok.Setter;
import org.ocpsoft.rewrite.annotation.RequestAction;
import org.ocpsoft.rewrite.faces.annotation.Deferred;
import org.ocpsoft.rewrite.faces.annotation.IgnorePostback;
import org.primefaces.model.DashboardColumn;
import org.primefaces.model.DashboardModel;
import org.primefaces.model.DefaultDashboardColumn;
import org.primefaces.model.DefaultDashboardModel;
import org.springframework.beans.factory.annotation.Autowired;

@Log
@Scope(value = "session")
@Component(value = "dashboardController")
@ELBeanName(value = "dashboardController")
@Join(path = "/", to = "/dashboard.jsf")
@ManagedBean(name = "dashboardController")
@ViewScoped
public class DashboardController {
    
    private final NodeRepository nodeRepository;
    
    @Getter @Setter
    private DashboardModel model;
    
    @Getter @Setter
    private List<Node> inactiveNodes;
    
    @Autowired
    public DashboardController(NodeRepository nodeRepository) {
        this.nodeRepository = nodeRepository;
    }
    
    @RequestAction
    @IgnorePostback
    @Deferred
    public void loadData() {
        inactiveNodes = nodeRepository.findAllByActiveIsFalse();
    }
    
    @PostConstruct
    public void init() {
        model = new DefaultDashboardModel();
        DashboardColumn column1 = new DefaultDashboardColumn();
        DashboardColumn column2 = new DefaultDashboardColumn();
         
        column1.addWidget("running_tasks");
        column2.addWidget("inactive_nodes");
 
        model.addColumn(column1);
        model.addColumn(column2);      
    }
}
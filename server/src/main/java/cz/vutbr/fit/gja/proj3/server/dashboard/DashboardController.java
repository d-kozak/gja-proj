package cz.vutbr.fit.gja.proj3.server.dashboard;

import cz.vutbr.fit.gja.proj3.server.node.boundary.NodeRepository;
import cz.vutbr.fit.gja.proj3.server.node.entity.Node;
import cz.vutbr.fit.gja.proj3.server.processing_task.boundary.ProcessingTaskResultRepository;
import cz.vutbr.fit.gja.proj3.server.processing_task.entity.ProcessingTask;
import cz.vutbr.fit.gja.proj3.server.processing_task.entity.ProcessingTaskResult;
import cz.vutbr.fit.gja.proj3.server.processing_task.entity.TaskState;
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

/**
 * Dashboard section backend controller.
 */
@Log
@Scope(value = "session")
@Component(value = "dashboardController")
@ELBeanName(value = "dashboardController")
@Join(path = "/", to = "/dashboard.jsf")
@ManagedBean(name = "dashboardController")
@ViewScoped
public class DashboardController {
    
    private final NodeRepository nodeRepository;
    private final ProcessingTaskResultRepository taskResultRepository;
    
    @Getter
    private DashboardModel model;
    
    @Getter
    private List<Node> inactiveNodes;
    
    @Getter
    private List<ProcessingTaskResult> runningTasks;
    
    @Autowired
    public DashboardController(NodeRepository nodeRepository, ProcessingTaskResultRepository taskResultRepository) {
        this.nodeRepository = nodeRepository;
        this.taskResultRepository = taskResultRepository;
    }
    
    /**
     * Loads database data on page load.
     */
    @RequestAction
    @IgnorePostback
    @Deferred
    public void loadData() {
        inactiveNodes = nodeRepository.findAllByActiveIsFalse();
        runningTasks = taskResultRepository.findAllByTaskState(TaskState.RUNNING);
    }
    
    /**
     * Post-construct initialization method.
     */
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
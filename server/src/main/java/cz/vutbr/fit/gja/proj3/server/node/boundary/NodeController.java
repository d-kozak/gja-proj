package cz.vutbr.fit.gja.proj3.server.node.boundary;

import cz.vutbr.fit.gja.proj3.server.node.control.NodeEchoService;
import cz.vutbr.fit.gja.proj3.server.node.entity.Node;
import cz.vutbr.fit.gja.proj3.server.processing_task.entity.ProcessingTask;
import static cz.vutbr.fit.gja.proj3.server.utils.GuiUtils.showError;
import static cz.vutbr.fit.gja.proj3.server.utils.GuiUtils.showInfo;
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
import lombok.Getter;
import lombok.Setter;

@Log
@Scope(value = "session")
@Component(value = "nodeController")
@ELBeanName(value = "nodeController")
@Join(path = "/node", to = "/node-list.jsf")
@ManagedBean(name = "nodeController")
@ViewScoped
public class NodeController {
    private final NodeRepository nodeRepository;
    private final NodeEchoService nodeEchoService;
    
    @Getter
    @Setter
    private List<Node> nodes;

    @Getter
    @Setter
    private Node selectedNode = new Node();

    @Getter
    @Setter
    private Node newNode = new Node();

    @Getter
    @Setter
    private ProcessingTask selectedProcessingTask;

    @Autowired
    public NodeController(NodeRepository nodeRepository, NodeEchoService nodeEchoService) {
        this.nodeRepository = nodeRepository;
        this.nodeEchoService = nodeEchoService;
    }

    @Deferred
    @RequestAction
    @IgnorePostback
    public void loadData() {
        nodes = nodeRepository.findAllEagerFetch();
    }
    
    public void checkAvailability() {
        boolean nodeRunning = nodeEchoService.isNodeRunning(selectedNode);
        if (!nodeRunning) {
            showError("Node is not running."); 
        } else {
            showInfo("Node " + selectedNode.getName() + " is running.");
        }
    }

    public void update() {
        log.info("called for node " + selectedNode);
        boolean nodeRunning = nodeEchoService.isNodeRunning(selectedNode);
        if (!nodeRunning) {
            log.severe("Node is not running.");
            showError("Node is not running."); 
        } else {
            nodeRepository.save(selectedNode);
            this.loadData();
            showInfo("Node " + selectedNode.getName() + " updated.");
        }
    }

    public void addNewNode() {
        showInfo("Sending echo to node. Please wait."); 
        log.info("creating: "+newNode);
        boolean nodeRunning = nodeEchoService.isNodeRunning(newNode);
        if (!nodeRunning) {
            log.severe("Node is not running.");
            showError("Node is not running."); 
        } else {
            log.info("node running, saving "+newNode);
            nodeRepository.save(newNode);
            log.info("node saved");
            this.loadData();
            showInfo("Node " + newNode.getName() + " created."); 
            this.newNode = new Node();
        }
    }
    
    public void remove(Node n) {
        showInfo("Node " + n.getName() + " removed.");
        if (n == this.selectedNode) {
            this.selectedNode = null;
        }
        nodeRepository.delete(n);
        this.loadData();
    }
}

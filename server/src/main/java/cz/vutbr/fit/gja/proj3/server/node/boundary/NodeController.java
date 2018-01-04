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

/**
 * Node section backend controller.
 */
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
    
    @Getter @Setter
    private List<Node> nodes;

    @Getter @Setter
    private Node selectedNode = new Node();

    @Getter @Setter
    private Node newNode = new Node();

    @Getter @Setter
    private ProcessingTask selectedProcessingTask;

    @Autowired
    public NodeController(NodeRepository nodeRepository, NodeEchoService nodeEchoService) {
        this.nodeRepository = nodeRepository;
        this.nodeEchoService = nodeEchoService;
    }

    /**
     * Loads database data on page load.
     */
    @Deferred
    @RequestAction
    @IgnorePostback
    public void loadData() {
        nodes = nodeRepository.findAllEagerFetch();
    }

    /**
     * Updates selected node data.
     */
    public void update() {
        if (!nodeEchoService.isNodeRunning(selectedNode)) {
            log.severe("Node is not running.");
            showError("Node is not running."); 
        }else{
            selectedNode = nodeRepository.save(selectedNode);
            this.loadData();
            showInfo("Node " + selectedNode.getName() + " updated.");
        }
    }

    /**
     * Creates node from filled information.
     */
    public void addNewNode() {
        showInfo("Sending echo to node. Please wait."); 
        
        if (!nodeEchoService.isNodeRunning(newNode)) {
            log.severe("Node is not running.");
            showError("Node is not running."); 
        }else{
            nodeRepository.save(newNode);
            this.loadData();
            showInfo("Node " + newNode.getName() + " created."); 
            this.newNode = new Node();
        }
    }
    
    /**
     * Removes Node from database.
     * @param n Node entity to be removed
     */
    public void remove(Node n) {
        showInfo("Node " + n.getName() + " removed.");
        if (n == this.selectedNode) {
            this.selectedNode = null;
        }
        nodeRepository.delete(n);
        this.loadData();
    }
}

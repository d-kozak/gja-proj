package cz.vutbr.fit.gja.proj3.server.node.boundary;

import cz.vutbr.fit.gja.proj3.server.node.control.NodeEchoService;
import cz.vutbr.fit.gja.proj3.server.node.entity.Node;
import cz.vutbr.fit.gja.proj3.server.processing_task.entity.ProcessingTask;
import lombok.extern.java.Log;
import org.ocpsoft.rewrite.annotation.Join;
import org.ocpsoft.rewrite.annotation.RequestAction;
import org.ocpsoft.rewrite.el.ELBeanName;
import org.ocpsoft.rewrite.faces.annotation.Deferred;
import org.ocpsoft.rewrite.faces.annotation.IgnorePostback;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;
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
@Component(value = "nodeController")
@ELBeanName(value = "nodeController")
@Join(path = "/node", to = "/node-list.jsf")
@ManagedBean(name = "nodeController")
@ViewScoped
public class NodeController {
    private final NodeRepository nodeRepository;
    private final NodeEchoService nodeEchoService;

    private List<Node> nodes;

    private Node selectedNode = new Node();

    private Node newNode = new Node();

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

    public String update() {
        nodeRepository.save(selectedNode);
        return "/node-list.xhtml?faces-redirect=true";
    }

    public void addNewNode() {
        log.info("called for node " + newNode);
        FacesMessage msg = new FacesMessage("Sending echo to the node", "");
        FacesContext.getCurrentInstance()
                    .addMessage(null, msg);
        boolean nodeRunning = nodeEchoService.isNodeRunning(newNode);
        if (!nodeRunning) {
            log.severe("error");
            FacesMessage errMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Could not add new node", "");
            FacesContext.getCurrentInstance()
                        .addMessage(null, errMsg);
            RequestContext.getCurrentInstance()
                          .execute("PF('addNode').show()");
        } else {
            log.info("success");
            nodeRepository.save(newNode);
            selectedNode = newNode;
            newNode = new Node();
            FacesMessage okMsg = new FacesMessage("New node added", newNode.getName());
            FacesContext.getCurrentInstance()
                        .addMessage(null, okMsg);
            log.info("Add new node finished");
        }
    }

    public void onRowSelect(SelectEvent event) {
        FacesMessage msg = new FacesMessage("Node selected", ((Node) event.getObject()).getName());
        FacesContext.getCurrentInstance()
                    .addMessage(null, msg);
    }


    public List<Node> getNodes() {
        return nodes;
    }

    public void setNodes(List<Node> nodes) {
        this.nodes = nodes;
    }

    public Node getSelectedNode() {
        return selectedNode;
    }

    public void setSelectedNode(Node selectedNode) {
        this.selectedNode = selectedNode;
    }

    public ProcessingTask getSelectedProcessingTask() {
        return selectedProcessingTask;
    }

    public void setSelectedProcessingTask(ProcessingTask selectedProcessingTask) {
        this.selectedProcessingTask = selectedProcessingTask;
    }

    public Node getNewNode() {
        return newNode;
    }

    public void setNewNode(Node newNode) {
        this.newNode = newNode;
    }
}

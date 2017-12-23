package cz.vutbr.fit.gja.proj3.server.node.boundary;

import cz.vutbr.fit.gja.proj3.common.node.entity.NodeReply;
import cz.vutbr.fit.gja.proj3.common.node.entity.NodeRequest;
import cz.vutbr.fit.gja.proj3.server.node.entity.Node;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Log
@RestController
@RequestMapping("/hello")
public class NodeHelloController {

    private final NodeRepository nodeRepository;

    @Autowired
    public NodeHelloController(NodeRepository nodeRepository) {
        this.nodeRepository = nodeRepository;
    }

    @PostMapping
    public NodeReply post(@RequestBody NodeRequest nodeRequest) {
        Node node = new Node();
        node.setName(nodeRequest.getDetails());
        node.setUrl(nodeRequest.getDetails());
        nodeRepository.save(node);
        log.info("saving node: " + node);
        return new NodeReply(true);
    }
}
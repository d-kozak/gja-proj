package cz.vutbr.fit.gja.proj3.server.node.control;

import cz.vutbr.fit.gja.proj3.server.node.boundary.NodeRepository;
import cz.vutbr.fit.gja.proj3.server.node.entity.Node;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;

/**
 * Periodically probes all nodes and marks them as active, if they reply, false otherwise
 */
@Log
@Service
public class NodeChecker {

    private final NodeRepository nodeRepository;
    private final NodeEchoService nodeEchoService;

    @Autowired
    public NodeChecker(NodeRepository nodeRepository, NodeEchoService nodeEchoService) {
        this.nodeRepository = nodeRepository;
        this.nodeEchoService = nodeEchoService;
    }

    @Transactional
    @Scheduled(fixedRate = 10000)
    public void probeAllNodes() {
        log.info("Probing all known nodes");
        List<Node> inactiveNodes = nodeRepository.findAllByActiveIsTrue()
                                                 .stream()
                                                 .filter(node -> !nodeEchoService.isNodeRunning(node))
                                                 .collect(toList());

        List<Node> activeNodes = nodeRepository.findAllByActiveIsFalse()
                                               .stream()
                                               .filter(node -> nodeEchoService.isNodeRunning(node))
                                               .collect(toList());

        if (!inactiveNodes.isEmpty()) {
            log.severe("\nFound inactive nodes: \n" + inactiveNodes.stream()
                                                                   .map(Node::toString)
                                                                   .collect(joining("\n")) + "\n");

            inactiveNodes.forEach(node -> {
                node.setActive(false);
                nodeRepository.save(node);
            });

            log.severe("Nodes marked as inactive");
        }

        if (!activeNodes.isEmpty()) {
            log.severe("\nFound active nodes: \n" + activeNodes.stream()
                                                               .map(Node::toString)
                                                               .collect(joining("\n")) + "\n");

            activeNodes.forEach(node -> {
                node.setActive(true);
                nodeRepository.save(node);
            });

            log.severe("Nodes marked as active");
        }

        log.info("finished");
    }
}

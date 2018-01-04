package cz.vutbr.fit.gja.proj3.node;

import cz.vutbr.fit.gja.proj3.common.node.entity.NodeReply;
import cz.vutbr.fit.gja.proj3.common.node.entity.NodeRequest;
import lombok.extern.java.Log;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * url /hello is periodically pinged by the server to check if the node is still active
 */
@RestController
@Log
public class HelloController {

    @PostMapping("/hello")
    public NodeReply hello(@RequestBody NodeRequest request) {
        log.info("Called with " + request);
        return new NodeReply(true);
    }
}

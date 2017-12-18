package cz.vutbr.fit.gja.proj3.server.node.boundary;

import cz.vutbr.fit.gja.proj3.common.node.entity.NodeReply;
import cz.vutbr.fit.gja.proj3.common.node.entity.NodeRequest;
import cz.vutbr.fit.gja.proj3.server.node.entity.Node;
import lombok.extern.java.Log;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Log
@Service
public class NodeEchoService {

    private RestTemplate restTemplate = new RestTemplate();

    public boolean isNodeRunning(Node node) {
        try {
            String url = prepareUrl(node);
            NodeRequest nodeRequest = prepareNodeRequest(node);
            log.info("Sending request " + nodeRequest + " to url " + url);
            NodeReply nodeReply = restTemplate.postForObject(url, nodeRequest, NodeReply.class);
            log.info("Received reply: " + nodeReply);
            return true;
        } catch (Exception ex) {
            log.severe("Error: " + ex.getMessage());
            return false;
        }
    }

    private NodeRequest prepareNodeRequest(Node node) {
        return new NodeRequest("details");
    }

    private String prepareUrl(Node node) {
        String url = node.getUrl();
        if (!url.startsWith("http://")) {
            url = "http://" + url;
        }
        if (!url.endsWith("/hello"))
            url += "/hello";
        return url;
    }
}

package cz.vutbr.fit.gja.proj3.node;

import cz.vutbr.fit.gja.proj3.common.node.entity.NodeReply;
import cz.vutbr.fit.gja.proj3.common.node.entity.NodeRequest;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
@Log
public class ServerNotifier {

    @Value("${cz.vutbr.fit.gja.proj3.node.ServerNotifier.server_url}")
    private String serverUrl;

    @Bean
    public CommandLineRunner promptServer() {
        return args -> {
            String fullUrl = serverUrl + "/hello";
            log.info("Probing url: " + fullUrl);
            RestTemplate restTemplate = new RestTemplate();
            NodeRequest nodeRequest = new NodeRequest();
            NodeReply nodeReply = restTemplate.postForObject(fullUrl, nodeRequest, NodeReply.class);
            log.info("received " + nodeReply);
        };
    }
}

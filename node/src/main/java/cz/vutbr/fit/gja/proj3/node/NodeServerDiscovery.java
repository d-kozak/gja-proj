package cz.vutbr.fit.gja.proj3.node;

import cz.vutbr.fit.gja.proj3.common.node.entity.NodeReply;
import cz.vutbr.fit.gja.proj3.common.node.entity.NodeRequest;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.context.embedded.EmbeddedServletContainerInitializedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import java.net.InetAddress;

@Configuration
@Log
public class NodeServerDiscovery implements ApplicationListener<EmbeddedServletContainerInitializedEvent> {

    @Value("${cz.vutbr.fit.gja.proj3.node.ServerNotifier.server_url}")
    private String serverUrl;

    private int serverPort = -1;

    @Override
    public void onApplicationEvent(EmbeddedServletContainerInitializedEvent event) {
        serverPort = event.getEmbeddedServletContainer()
                          .getPort();
    }

    @Bean
    public CommandLineRunner echoToServer() {
        return args -> {
            String nodeUrl = InetAddress.getLocalHost()
                                        .getHostAddress() + ":" + serverPort;
            log.info(nodeUrl);
            String fullUrl = serverUrl + "/hello";
            log.info("Probing url: " + fullUrl);
            RestTemplate restTemplate = new RestTemplate();
            NodeRequest nodeRequest = new NodeRequest(nodeUrl);
            NodeReply nodeReply = restTemplate.postForObject(fullUrl, nodeRequest, NodeReply.class);
            log.info("received " + nodeReply);
        };
    }
}

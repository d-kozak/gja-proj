package cz.vutbr.fit.gja.proj3.common.node.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NodeReply {
    private boolean isOk;
    private String description;

    public NodeReply(boolean isOk) {
        this.isOk = isOk;
        this.description = "";
    }
}

package cz.vutbr.fit.gja.proj3.server.node.entity;

import cz.vutbr.fit.gja.proj3.server.processing_task.entity.ProcessingTask;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Node implements Serializable {

    @Id
    @GeneratedValue
    private long id;

    @NotNull
    private String url;
    private String name;

    private boolean active = true;

    @OneToMany(mappedBy = "node")
    private List<ProcessingTask> processingTasks;

    @Override
    public boolean equals(Object o) {
        return o != null && o instanceof Node && ((Node) o).getId() == this.getId();
    }
}

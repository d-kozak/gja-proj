package cz.vutbr.fit.gja.proj3.server.node.entity;

import cz.vutbr.fit.gja.proj3.server.processing_task.entity.ProcessingTask;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.io.Serializable;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Node implements Serializable {

    @Id
    private String url;
    private String name;

    private boolean active = true;

    @OneToMany(mappedBy = "node")
    private List<ProcessingTask> processingTasks;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Node node = (Node) o;

        return url != null ? url.equals(node.url) : node.url == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (url != null ? url.hashCode() : 0);
        return result;
    }
}

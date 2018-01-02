package cz.vutbr.fit.gja.proj3.server.node.entity;

import cz.vutbr.fit.gja.proj3.server.processing_task.entity.ProcessingTask;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import java.util.List;
import javax.persistence.FetchType;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

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
}

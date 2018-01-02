package cz.vutbr.fit.gja.proj3.server.processing_task.entity;

import cz.vutbr.fit.gja.proj3.server.node.entity.Node;
import cz.vutbr.fit.gja.proj3.server.project.entity.Project;
import cz.vutbr.fit.gja.proj3.server.user.entity.User;
import java.io.Serializable;
import java.util.ArrayList;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.Fetch;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProcessingTask implements Serializable {

    @Id
    @GeneratedValue
    private long id;

    private String name;

    @OrderColumn
    @OneToMany(mappedBy = "processingTask", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    @Fetch(FetchMode.SELECT)
    private List<ProcessingTaskUnit> processingTaskUnits = new ArrayList<>();

    @OneToMany(mappedBy = "processingTask", cascade = CascadeType.DETACH)
    private List<ProcessingTaskResult> processingTaskResults;

    @ManyToOne
    private User user;

    @ManyToOne
    private Node node;
    
    @ManyToOne
    private Project project;

    @Override
    public String toString() {
        return "ProcessingTask{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", processingTaskUnits=" + processingTaskUnits +
                '}';
    }
}

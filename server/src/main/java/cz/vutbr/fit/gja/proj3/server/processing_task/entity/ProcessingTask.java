package cz.vutbr.fit.gja.proj3.server.processing_task.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import cz.vutbr.fit.gja.proj3.server.node.entity.Node;
import cz.vutbr.fit.gja.proj3.server.project.entity.Project;
import cz.vutbr.fit.gja.proj3.server.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;
import org.hibernate.annotations.Cascade;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProcessingTask {

    @Id
    @GeneratedValue
    private long id;

    private String name;

    @OrderColumn
    @OneToMany(mappedBy = "processingTask", cascade = CascadeType.ALL)
    private List<ProcessingTaskUnit> processingTaskUnits;

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

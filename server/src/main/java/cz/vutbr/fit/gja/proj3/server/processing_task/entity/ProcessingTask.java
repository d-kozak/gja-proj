package cz.vutbr.fit.gja.proj3.server.processing_task.entity;

import cz.vutbr.fit.gja.proj3.server.node.entity.Node;
import cz.vutbr.fit.gja.proj3.server.project.entity.Project;
import cz.vutbr.fit.gja.proj3.server.user.entity.User;
import java.io.Serializable;
import java.util.ArrayList;
import cz.vutbr.fit.gja.proj3.common.processing_task.entity.ProcessingTaskDTO;
import cz.vutbr.fit.gja.proj3.common.processing_task.entity.ProcessingTaskUnitDTO;
import cz.vutbr.fit.gja.proj3.server.node.entity.Node;
import cz.vutbr.fit.gja.proj3.server.project.entity.Project;
import cz.vutbr.fit.gja.proj3.server.user.entity.User;
import java.util.List;
import static java.util.stream.Collectors.toList;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderColumn;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

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
    @OneToMany(mappedBy = "processingTask", cascade = CascadeType.ALL, orphanRemoval = true)
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

    public ProcessingTaskDTO toDTO() {
        return ProcessingTaskDTO.builder()
                                .id(getId())
                                .processingTaskUnitDTOS(subtasksToDTOs())
                                .build();
    }

    private List<ProcessingTaskUnitDTO> subtasksToDTOs() {
        return processingTaskUnits.stream()
                                  .map(ProcessingTaskUnit::toDTO)
                                  .collect(toList());
    }

    @Override
    public String toString() {
        return "ProcessingTask{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", processingTaskUnits=" + processingTaskUnits +
                '}';
    }
}

package cz.vutbr.fit.gja.proj3.server.processing_task.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProcessingTaskResult {

    @Id
    @GeneratedValue
    private long id;

    private Date submittedAt = new Date();

    private TaskState taskState = TaskState.CREATED;

    @ManyToOne
    private ProcessingTask processingTask;

    @OneToMany(mappedBy = "processingTaskResult")
    private List<ProcessingTaskUnitResult> processingTaskUnitResultList;
}

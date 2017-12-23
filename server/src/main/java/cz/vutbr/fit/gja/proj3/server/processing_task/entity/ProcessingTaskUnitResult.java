package cz.vutbr.fit.gja.proj3.server.processing_task.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProcessingTaskUnitResult {

    @Id
    @GeneratedValue
    private long id;

    @OneToOne
    private ProcessingTaskUnit processingTaskUnit;

    @ManyToOne
    private ProcessingTaskResult processingTaskResult;

    private int successfulExecutions;
    private int totalExecutions;

}

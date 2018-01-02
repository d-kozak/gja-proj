package cz.vutbr.fit.gja.proj3.server.processing_task.entity;

import cz.vutbr.fit.gja.proj3.common.processing_task.entity.ProcessingTaskUnitResultDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProcessingTaskUnitResult {

    @Id
    @GeneratedValue
    private long id;

    private TaskState taskState = TaskState.CREATED;

    @OneToOne
    private ProcessingTaskUnit processingTaskUnit;

    @ManyToOne
    private ProcessingTaskResult processingTaskResult;

    private int successfulExecutions;
    private int totalExecutions;
    private long totalExecutionTime;
    private double averageExecutionTime;
    private Date startDate;

    public void update(ProcessingTaskUnitResultDTO processingTaskUnitResultDTO) {
        this.successfulExecutions = processingTaskUnitResultDTO.getSuccessfulExecutions();
        this.totalExecutions = processingTaskUnitResultDTO.getTotalExecutions();
        this.totalExecutionTime = processingTaskUnitResultDTO.getTotalExecutionTime();
        this.averageExecutionTime = processingTaskUnitResultDTO.getAverageExecutionTime();
        this.startDate = processingTaskUnitResultDTO.getStartDate();
    }
}

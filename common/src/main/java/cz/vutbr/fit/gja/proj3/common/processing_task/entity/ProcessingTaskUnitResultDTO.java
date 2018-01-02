package cz.vutbr.fit.gja.proj3.common.processing_task.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProcessingTaskUnitResultDTO {
    private long id;
    private int successfulExecutions;
    private int totalExecutions;
    private long totalExecutionTime;
    private double averageExecutionTime;
    private Date startDate;
}

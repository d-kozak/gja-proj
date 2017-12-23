package cz.vutbr.fit.gja.proj3.common.processing_task.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProcessingTaskUnitResultDTO {
    private long id;
    private int successfulExecutions;
    private int totalExecutions;
}

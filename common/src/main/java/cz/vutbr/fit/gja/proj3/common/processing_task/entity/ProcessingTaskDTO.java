package cz.vutbr.fit.gja.proj3.common.processing_task.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProcessingTaskDTO {
    private long id;
    private List<ProcessingTaskUnitDTO> processingTaskUnitDTOS;
}

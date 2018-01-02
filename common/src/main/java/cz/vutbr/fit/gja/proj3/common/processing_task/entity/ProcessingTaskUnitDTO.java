package cz.vutbr.fit.gja.proj3.common.processing_task.entity;

import cz.vutbr.fit.gja.proj3.common.processing_task.control.CommandConstraint;
import cz.vutbr.fit.gja.proj3.common.processing_task.control.DirectoryConstraint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProcessingTaskUnitDTO {
    private long id;

    @CommandConstraint
    private String command;

    @DirectoryConstraint
    private String directory;

    private String arguments;

    private OutputVerificationDTO outputVerificationDTO;
}

package cz.vutbr.fit.gja.proj3.server.processing_task.entity;

import cz.vutbr.fit.gja.proj3.common.processing_task.control.CommandConstraint;
import cz.vutbr.fit.gja.proj3.common.processing_task.entity.ProcessingTaskUnitDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProcessingTaskUnit {
    @Id
    @GeneratedValue
    private long id;

    @CommandConstraint
    private String command;

    private String directory;

    private String arguments;

    private OutputVerification outputVerification;

    @ManyToOne
    private ProcessingTask processingTask;

    public ProcessingTaskUnitDTO toDTO() {
        return ProcessingTaskUnitDTO.builder()
                                    .id(getId())
                                    .command(getCommand())
                                    .arguments(getArguments())
                                    .directory(getDirectory())
                                    .outputVerificationDTO(getOutputVerification().toDTO())
                                    .build();
    }

    @Override
    public String toString() {
        return "ProcessingTaskUnit{" +
                "id=" + id +
                ", command='" + command + '\'' +
                ", argument=" + arguments +
                '}';
    }
}

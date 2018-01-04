package cz.vutbr.fit.gja.proj3.server.processing_task.entity;

import cz.vutbr.fit.gja.proj3.common.processing_task.control.CommandConstraint;
import cz.vutbr.fit.gja.proj3.common.processing_task.entity.OutputType;
import cz.vutbr.fit.gja.proj3.common.processing_task.entity.ProcessingTaskUnitDTO;
import java.util.ArrayList;
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

    private String inputDirectory = ".";

    private String inputFileRegex = "\\w*.in";

    private OutputVerification outputVerification = new OutputVerification(){{
        setOutputType(OutputType.NO_CHECK);
        setExpectedOutput(new ArrayList<String>());
    }};

    @ManyToOne
    private ProcessingTask processingTask;

    public ProcessingTaskUnitDTO toDTO() {
        return ProcessingTaskUnitDTO.builder()
                                    .id(getId())
                                    .command(getCommand())
                                    .arguments(getArguments())
                                    .directory(getDirectory())
                                    .inputFileRegex(getInputFileRegex() != null ? getInputFileRegex() : "\\w*.in")
                                    .inputDirectory(getInputDirectory() != null ? getInputDirectory() : ".")
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

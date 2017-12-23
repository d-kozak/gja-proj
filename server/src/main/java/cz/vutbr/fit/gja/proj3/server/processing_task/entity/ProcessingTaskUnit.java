package cz.vutbr.fit.gja.proj3.server.processing_task.entity;

import cz.vutbr.fit.gja.proj3.common.processing_task.control.CommandConstraint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

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

    @ElementCollection
    private List<String> arguments;

    @ManyToOne
    private ProcessingTask processingTask;
}

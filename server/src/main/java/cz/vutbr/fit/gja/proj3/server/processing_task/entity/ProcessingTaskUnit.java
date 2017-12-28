package cz.vutbr.fit.gja.proj3.server.processing_task.entity;

import cz.vutbr.fit.gja.proj3.common.processing_task.control.CommandConstraint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


import javax.persistence.*;
import java.util.List;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

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
    
    @ManyToOne
    private ProcessingTask processingTask;
}

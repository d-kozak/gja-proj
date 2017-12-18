package cz.vutbr.fit.gja.proj3.server.task.entity;

import lombok.Builder;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@Builder
public class PartialTask {
    @Id
    @GeneratedValue
    private long id;

    //    @CommandConstraint
    private String command;

    //    @DirectoryConstraint
    private String directory;

    @ElementCollection
    private List<String> arguments;

    @ManyToOne
    private Task task;
}

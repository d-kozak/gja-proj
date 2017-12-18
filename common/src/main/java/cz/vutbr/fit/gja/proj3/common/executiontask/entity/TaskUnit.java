package cz.vutbr.fit.gja.proj3.common.executiontask.entity;

import cz.vutbr.fit.gja.proj3.common.executiontask.control.CommandConstraint;
import cz.vutbr.fit.gja.proj3.common.executiontask.control.DirectoryConstraint;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskUnit {
    @CommandConstraint
    private String command;

    @DirectoryConstraint
    private String directory;

    private List<String> arguments;
}

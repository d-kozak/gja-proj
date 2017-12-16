package cz.vutbr.fit.gja.proj3.executiontask.entity;

import cz.vutbr.fit.gja.proj3.executiontask.control.CommandConstraint;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExecutionTaskDto {
    @CommandConstraint
    private String command;
    private List<String> arguments;
}

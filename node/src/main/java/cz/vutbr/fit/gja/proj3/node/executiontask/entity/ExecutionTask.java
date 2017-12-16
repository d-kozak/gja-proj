package cz.vutbr.fit.gja.proj3.node.executiontask.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExecutionTask {
    private String command;
    private List<String> arguments;
}

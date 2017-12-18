package cz.vutbr.fit.gja.proj3.common.executiontask.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExecutionTask {
    private long id;
    private List<TaskUnit> taskUnits;
}

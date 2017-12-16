package cz.vutbr.fit.gja.proj3.node.executiontask.boundary;

import cz.vutbr.fit.gja.proj3.node.executiontask.control.TaskExecutor;
import cz.vutbr.fit.gja.proj3.node.executiontask.entity.ExecutionTask;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/task")
@Log
public class ExecutionTaskController {

    private final TaskExecutor taskExecutor;

    @Autowired
    public ExecutionTaskController(TaskExecutor taskExecutor) {
        this.taskExecutor = taskExecutor;
    }

    @PostMapping
    public void start(@RequestBody ExecutionTask executionTask) {
        log.info("Accepted task " + executionTask);
        try {
            taskExecutor.start(executionTask);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

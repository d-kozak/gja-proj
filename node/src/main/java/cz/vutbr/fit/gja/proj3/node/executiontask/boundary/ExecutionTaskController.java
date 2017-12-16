package cz.vutbr.fit.gja.proj3.node.executiontask.boundary;

import cz.vutbr.fit.gja.proj3.executiontask.entity.ExecutionTaskDto;
import cz.vutbr.fit.gja.proj3.node.executiontask.control.TaskExecutor;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
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
    public String start(@RequestBody @Valid ExecutionTaskDto executionTask, BindingResult binding) {
        if (binding.hasErrors()) {
            return "error: " + binding.getAllErrors();
        }
        log.info("Accepted task " + executionTask);
        try {
            taskExecutor.start(executionTask);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "ok";
    }
}

package cz.vutbr.fit.gja.proj3.node.executiontask.boundary;

import cz.vutbr.fit.gja.proj3.common.processing_task.entity.ProcessingTaskDTO;
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
    public String start(@RequestBody @Valid ProcessingTaskDTO processingTaskDTO, BindingResult binding) {
        if (binding.hasErrors()) {
            return "error: " + binding.getAllErrors();
        }
        log.info("Accepted processing_task " + processingTaskDTO);
        try {
            taskExecutor.start(processingTaskDTO);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "ok";
    }
}

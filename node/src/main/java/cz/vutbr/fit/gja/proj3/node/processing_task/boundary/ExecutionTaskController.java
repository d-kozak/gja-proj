package cz.vutbr.fit.gja.proj3.node.processing_task.boundary;

import cz.vutbr.fit.gja.proj3.common.node.entity.NodeReply;
import cz.vutbr.fit.gja.proj3.common.processing_task.entity.ProcessingTaskDTO;
import cz.vutbr.fit.gja.proj3.node.processing_task.control.TaskExecutor;
import lombok.extern.java.Log;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

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
    public NodeReply start(HttpServletRequest request, @RequestBody @Valid ProcessingTaskDTO processingTaskDTO, BindingResult binding) {
        String remoteHost = request.getRemoteHost();
        int remotePort = 8080;


        if (binding.hasErrors()) {
            return new NodeReply(false, binding.getAllErrors()
                                               .toString());
        }
        log.info("Accepted processing_task " + processingTaskDTO);

        taskExecutor.start(processingTaskDTO, remoteHost, remotePort);

        return new NodeReply(true);
    }
}

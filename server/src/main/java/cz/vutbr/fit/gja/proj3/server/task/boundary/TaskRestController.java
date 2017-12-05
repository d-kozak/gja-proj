package cz.vutbr.fit.gja.proj3.server.task.boundary;

import cz.vutbr.fit.gja.proj3.server.task.entity.Task;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
@Log
public class TaskRestController {

    private final TaskRepository taskRepository;

    @Autowired
    public TaskRestController(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @GetMapping
    public List<Task> get() {
        log.info("called");
        List<Task> all = taskRepository.findAll();
        all.forEach(task -> task.getCommands());
        return all;
    }
}

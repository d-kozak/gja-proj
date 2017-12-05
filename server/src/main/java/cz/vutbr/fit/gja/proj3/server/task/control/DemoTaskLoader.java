package cz.vutbr.fit.gja.proj3.server.task.control;

import cz.vutbr.fit.gja.proj3.server.task.boundary.CommandRepository;
import cz.vutbr.fit.gja.proj3.server.task.boundary.TaskRepository;
import cz.vutbr.fit.gja.proj3.server.task.entity.Command;
import cz.vutbr.fit.gja.proj3.server.task.entity.Task;
import lombok.extern.java.Log;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

@Configuration
@Log
public class DemoTaskLoader {

    @Bean
    CommandLineRunner addTasks(TaskRepository taskRepository, CommandRepository commandRepository) {
        return (param) -> {
            log.info("adding demo tasks");
            Task newTask = Task.builder()
                               .name("Cook")
                               .commands(Arrays.asList(Command.builder()
                                                              .commandText("go to kitchen")
                                                              .build(),
                                       Command.builder()
                                              .commandText("start cooking")
                                              .build(),
                                       Command.builder()
                                              .commandText("finish cooking")
                                              .build()))
                               .build();

            newTask.getCommands()
                   .forEach(command -> command.setTask(newTask));
            taskRepository.save(newTask);
        };
    }
}

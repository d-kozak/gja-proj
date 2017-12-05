package cz.vutbr.fit.gja.proj3.server.task.control;

import cz.vutbr.fit.gja.proj3.server.task.boundary.TaskRepository;
import cz.vutbr.fit.gja.proj3.server.task.entity.Command;
import cz.vutbr.fit.gja.proj3.server.task.entity.Task;
import cz.vutbr.fit.gja.proj3.server.user.boundary.UserRepository;
import cz.vutbr.fit.gja.proj3.server.user.entity.User;
import lombok.extern.java.Log;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.List;

@Configuration
@Log
@Transactional
public class DemoTaskLoader {

    @Bean
    CommandLineRunner addTasks(TaskRepository taskRepository, UserRepository userRepository) {
        return (param) -> {
            log.info("adding demo tasks");
            // could be simpler with constructors, just an example of the builder API
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
            List<User> users = userRepository.findAll();
            if (!users.isEmpty()) {
                User user = users.get(0);
                user.getTasks()
                    .add(newTask);
                userRepository.save(user);
            }
        };
    }
}

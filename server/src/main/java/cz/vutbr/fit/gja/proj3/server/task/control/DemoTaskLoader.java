package cz.vutbr.fit.gja.proj3.server.task.control;

import cz.vutbr.fit.gja.proj3.server.task.boundary.TaskRepository;
import cz.vutbr.fit.gja.proj3.server.task.entity.Command;
import cz.vutbr.fit.gja.proj3.server.task.entity.Task;
import cz.vutbr.fit.gja.proj3.server.user.boundary.UserRepository;
import cz.vutbr.fit.gja.proj3.server.user.entity.User;
import lombok.extern.java.Log;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.Arrays;
import java.util.List;

@Service
@Log
public class DemoTaskLoader {


    @Bean
    CommandLineRunner addTasks(TaskRepository taskRepository, UserRepository userRepository, PlatformTransactionManager transactionManager) {
        return (param) -> {
            log.info(DemoTaskLoader.class.getSimpleName() + ": adding demo tasks");
            // transaction template necessary to tun the code in a transaction
            TransactionTemplate transactionTemplate = new TransactionTemplate(transactionManager);
            transactionTemplate.execute((status) -> {
                Task task = createDummyTask();
                task.getCommands()
                    .forEach(command -> command.setTask(task));
                taskRepository.save(task);
                List<User> users = userRepository.findAll();
                if (!users.isEmpty()) {
                    User user = users.get(0);
                    user.getTasks()
                        .add(task);
                    userRepository.save(user);
                }
                return null;
            });
        };
    }

    private Task createDummyTask() {
        // could be simpler with constructors, just an example of the builder API
        return Task.builder()
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
    }
}


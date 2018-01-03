package cz.vutbr.fit.gja.proj3.server.processing_task.control;

import cz.vutbr.fit.gja.proj3.server.processing_task.boundary.TaskRepository;
import cz.vutbr.fit.gja.proj3.server.processing_task.entity.ProcessingTask;
import cz.vutbr.fit.gja.proj3.server.processing_task.entity.ProcessingTaskUnit;
import lombok.extern.java.Log;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Log
@Configuration
public class DummyProcessingTaskProducer {

    /*@Bean
    public CommandLineRunner produceDummyTasks(TaskRepository taskRepository) {
        return args -> {
            ProcessingTask processingTask = new ProcessingTask();
            ProcessingTaskUnit processingTaskUnit;
            processingTask.setName("dummy at " + System.currentTimeMillis());
            List<ProcessingTaskUnit> processingTaskUnitList = new ArrayList<>();

            for (String command : Arrays.asList("./clear.sh", "./run1.sh", "./run2.sh", "./run3.sh")) {
                processingTaskUnit = new ProcessingTaskUnit();
                processingTaskUnit.setProcessingTask(processingTask);
                processingTaskUnit.setDirectory("/home/david/tmp/gja");
                processingTaskUnit.setArguments("");
                processingTaskUnit.setCommand(command);
                processingTaskUnitList.add(processingTaskUnit);
            }

            processingTask.setProcessingTaskUnits(processingTaskUnitList);
            log.info("saving task " + processingTask);
            taskRepository.save(processingTask);
        };
    }*/
}

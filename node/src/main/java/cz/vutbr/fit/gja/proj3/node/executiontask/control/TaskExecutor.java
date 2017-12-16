package cz.vutbr.fit.gja.proj3.node.executiontask.control;

import cz.vutbr.fit.gja.proj3.executiontask.entity.ExecutionTaskDto;
import lombok.extern.java.Log;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

@Service
@Log
public class TaskExecutor {
    private ExecutionTaskDto executionTask;
    private ProcessBuilder processBuilder;

    private Process taskExecutor;
    private ProcessOutputReader processOutputReader;


    public void start(ExecutionTaskDto executionTask) throws IOException {
        this.executionTask = executionTask;
        ArrayList<String> command = new ArrayList<>(executionTask.getArguments());
        command.add(0, executionTask.getCommand());
        processBuilder = new ProcessBuilder(command);


        taskExecutor = processBuilder.start();
        processOutputReader = new ProcessOutputReader(taskExecutor.getInputStream(), taskExecutor.getErrorStream());
        processOutputReader.start();
    }

    public int waitFor() throws InterruptedException {
        return taskExecutor.waitFor();
    }

    public static class ProcessOutputReader extends Thread {
        private final BufferedReader inputReader;
        private final BufferedReader errorReader;

        public ProcessOutputReader(InputStream inputStream, InputStream errorStream) {
            inputReader = new BufferedReader(new InputStreamReader(inputStream));
            errorReader = new BufferedReader(new InputStreamReader(errorStream));
        }

        @Override
        public void run() {
            while (!isInterrupted()) {
                inputReader.lines()
                           .forEach(log::info);
                errorReader.lines()
                           .forEach(log::severe);

                try {
                    sleep(500);
                } catch (InterruptedException e) {
                    log.info("Interrupted");
                    interrupt();
                    break;
                }
            }
        }
    }
}

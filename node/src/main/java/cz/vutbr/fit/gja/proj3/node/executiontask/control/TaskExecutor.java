package cz.vutbr.fit.gja.proj3.node.executiontask.control;

import cz.vutbr.fit.gja.proj3.common.executiontask.entity.ExecutionTask;
import cz.vutbr.fit.gja.proj3.common.executiontask.entity.TaskUnit;
import lombok.extern.java.Log;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.ArrayList;

@Service
@Log
public class TaskExecutor {
    private ProcessBuilder processBuilder;

    private Process taskExecutor;
    private ProcessOutputReader processOutputReader;


    public void start(ExecutionTask executionTask) throws IOException {
        for (TaskUnit taskUnit : executionTask.getTaskUnits()) {
            ArrayList<String> command = new ArrayList<>(taskUnit.getArguments());
            command.add(0, taskUnit.getCommand());
            processBuilder = new ProcessBuilder(command);
            processBuilder.directory(new File(taskUnit.getDirectory()));


            taskExecutor = processBuilder.start();
            processOutputReader = new ProcessOutputReader(taskExecutor.getInputStream(), taskExecutor.getErrorStream());
            processOutputReader.start();

            try {
                taskExecutor.waitFor();
            } catch (InterruptedException ex) {
                log.severe("Interrupted");
                taskExecutor.destroy();
                break;
            } finally {
                processOutputReader.interrupt();
                processOutputReader.close();
            }
        }
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

        public void close() {
            try {
                inputReader.close();
                errorReader.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}

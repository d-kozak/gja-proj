package cz.vutbr.fit.gja.proj3.node.processing_task.control;

import cz.vutbr.fit.gja.proj3.common.node.entity.NodeReply;
import cz.vutbr.fit.gja.proj3.common.processing_task.OutputVerificationFactory;
import cz.vutbr.fit.gja.proj3.common.processing_task.control.OutputVerificationStrategy;
import cz.vutbr.fit.gja.proj3.common.processing_task.entity.ProcessingTaskDTO;
import cz.vutbr.fit.gja.proj3.common.processing_task.entity.ProcessingTaskUnitDTO;
import cz.vutbr.fit.gja.proj3.common.processing_task.entity.ProcessingTaskUnitResultDTO;
import cz.vutbr.fit.gja.proj3.node.NodeServerDiscovery;
import lombok.extern.java.Log;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

/**
 * Executes specified task and notify the server about the execution of the task
 */
@Service
@Log
public class TaskExecutor {

    private Process taskExecutor;

    public CompletableFuture<Void> start(ProcessingTaskDTO processingTaskDTO, String remoteHost, int remotePort) {
        log.info("called with " + processingTaskDTO + ", " + remoteHost + ", " + remotePort);
        return CompletableFuture.supplyAsync(() -> {
            try {

                log.info("Preparing to process task " + processingTaskDTO.getId());
                notifyTaskStarted(processingTaskDTO, remoteHost, remotePort);
                for (ProcessingTaskUnitDTO processingTaskUnitDTO : processingTaskDTO.getProcessingTaskUnitDTOS()) {
                    notifySubtaskStarted(processingTaskUnitDTO, remoteHost, remotePort);
                    File directory = new File(processingTaskUnitDTO.getInputDirectory());
                    File[] inputFiles = directory.listFiles(file -> file.getName()
                                                                        .matches(processingTaskUnitDTO.getInputFileRegex()));
                    if (inputFiles == null || inputFiles.length == 0) {
                        log.severe("no input files found, subtask will run only once without input");
                        inputFiles = new File[]{new File(".")};
                    }
                    OutputVerificationStrategy outputVerificationStrategy = OutputVerificationFactory.strategyFor(processingTaskUnitDTO.getOutputVerificationDTO());

                    long totalStartTime = System.currentTimeMillis();
                    List<Long> executionTimes = new ArrayList<>();
                    Date startDate = new Date();
                    int successfullExecutions = 0;

                    long millisSinceLastUpdate = 0;
                    for (int fileNum = 0; fileNum < inputFiles.length; fileNum++) {
                        try {
                            File inputFile = inputFiles[fileNum];
                            log.info("processing input file " + inputFile);
                            List<String> arguments = Stream.of(processingTaskUnitDTO.getArguments()
                                                                                    .split("\\W*"))
                                                           .collect(toList());
                            Optional<String> first = arguments.stream()
                                                              .filter(argument -> argument.contains("{input}"))
                                                              .findFirst();
                            if (!first.isPresent()) {
                                log.severe("Input file argument not found, assuming it is set in a different way");
                            }
                            first.ifPresent(argument -> {
                                int index = arguments.indexOf(argument);
                                arguments.remove(index);
                                arguments.add(index, argument.replace("{input}", inputFile.getName()));
                            });

                            ArrayList<String> command = new ArrayList<>(arguments);
                            command.add(0, processingTaskUnitDTO.getCommand());
                            ProcessBuilder processBuilder = new ProcessBuilder(command);
                            processBuilder.directory(new File(processingTaskUnitDTO.getDirectory()));

                            long startTime = System.currentTimeMillis();
                            taskExecutor = processBuilder.start();
                            int retVal = taskExecutor.waitFor();
                            long duration = System.currentTimeMillis() - startTime;
                            boolean isCorrect = outputVerificationStrategy.verify(fileNum + 1);
                            if (isCorrect)
                                successfullExecutions++;
                            executionTimes.add(duration);

                        } catch (InterruptedException ex) {
                            log.severe("Interrupted");
                            taskExecutor.destroy();
                            break;
                        } catch (IOException ex) {
                            throw new RuntimeException(ex);
                        }

                        millisSinceLastUpdate = System.currentTimeMillis() - millisSinceLastUpdate;
                        if (millisSinceLastUpdate > 5 * 1000) {
                            millisSinceLastUpdate = 0;
                            long totalTime = System.currentTimeMillis() - totalStartTime;
                            sendTaskUnitResult(processingTaskDTO, remoteHost, remotePort, processingTaskUnitDTO, inputFiles, executionTimes, startDate, successfullExecutions, totalTime);
                        }
                    }
                    long totalTime = System.currentTimeMillis() - totalStartTime;
                    sendTaskUnitResult(processingTaskDTO, remoteHost, remotePort, processingTaskUnitDTO, inputFiles, executionTimes, startDate, successfullExecutions, totalTime);
                    notifySubtaskFinished(processingTaskUnitDTO, remoteHost, remotePort);
                }
            } catch (Exception ex) {
                log.severe("Execution failed: " + ex);
                ex.printStackTrace();
                throw new RuntimeException(ex);
            }
            notifyTaskFinished(processingTaskDTO, remoteHost, remotePort);
            return null;
        });
    }

    private void notifyTaskStarted(ProcessingTaskDTO processingTaskDTO, String remoteHost, int remotePort) {
        String url = getServerUrl(remoteHost, remotePort) + "/api/task/started";
        sendInfo(processingTaskDTO.getId(), url);
    }

    private void notifyTaskFinished(ProcessingTaskDTO processingTaskDTO, String remoteHost, int remotePort) {
        String url = getServerUrl(remoteHost, remotePort) + "/api/task/finished";
        sendInfo(processingTaskDTO.getId(), url);

    }

    private void notifySubtaskStarted(ProcessingTaskUnitDTO processingTaskUnitDTO, String remoteHost, int remotePort) {
        String url = getServerUrl(remoteHost, remotePort) + "/api/task/subtask/started";
        sendInfo(processingTaskUnitDTO.getId(), url);
    }

    private void notifySubtaskFinished(ProcessingTaskUnitDTO processingTaskUnitDTO, String remoteHost, int remotePort) {
        String url = getServerUrl(remoteHost, remotePort) + "/api/task/subtask/finished";
        sendInfo(processingTaskUnitDTO.getId(), url);

    }

    private void sendInfo(Long id, String url) {
        log.info("sending info about subtask start to " + url);
        RestTemplate restTemplate = new RestTemplate();
        NodeReply nodeReply = restTemplate.postForObject(url, id, NodeReply.class);
        if (nodeReply.isOk()) {
            log.info("info sent successfully");
        } else {
            log.severe("failed to send the info");
        }
    }

    private void sendTaskUnitResult(ProcessingTaskDTO processingTaskDTO, String remoteHost, int remotePort, ProcessingTaskUnitDTO processingTaskUnitDTO, File[] inputFiles, List<Long> executionTimes, Date startDate, int successfullExecutions, long totalTime) {
        ProcessingTaskUnitResultDTO processingTaskUnitResultDTO = new ProcessingTaskUnitResultDTO();
        processingTaskUnitResultDTO.setId(processingTaskUnitDTO.getId());
        processingTaskUnitResultDTO.setSuccessfulExecutions(successfullExecutions);
        processingTaskUnitResultDTO.setTotalExecutions(inputFiles.length);
        processingTaskUnitResultDTO.setTotalExecutionTime(totalTime);
        processingTaskUnitResultDTO.setAverageExecutionTime(executionTimes.stream()
                                                                          .mapToLong(i -> i)
                                                                          .average()
                                                                          .orElse(0));
        processingTaskUnitResultDTO.setStartDate(startDate);

        String url = getServerUrl(remoteHost, remotePort) + "/api/task/subtask/result";
        log.info("sending reply to " + url);
        RestTemplate restTemplate = new RestTemplate();
        NodeReply nodeReply = restTemplate.postForObject(url, processingTaskUnitResultDTO, NodeReply.class);
        if (nodeReply.isOk()) {
            log.info("results sent successfully");
        } else {
            log.severe("failed to send the result");
        }
    }

    private String getServerUrl(String remoteHost, int remotePort) {
        return NodeServerDiscovery.serverUrl;
    }

}

package cz.vutbr.fit.gja.proj3.server.processing_task.boundary;

import cz.vutbr.fit.gja.proj3.common.node.entity.NodeReply;
import cz.vutbr.fit.gja.proj3.common.processing_task.entity.*;
import cz.vutbr.fit.gja.proj3.server.node.entity.Node;
import cz.vutbr.fit.gja.proj3.server.processing_task.control.ProcessingTaskResultService;
import cz.vutbr.fit.gja.proj3.server.processing_task.entity.ProcessingTask;
import cz.vutbr.fit.gja.proj3.server.processing_task.entity.ProcessingTaskResult;
import cz.vutbr.fit.gja.proj3.server.processing_task.entity.ProcessingTaskUnitResult;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping("/api/task")
@Log
public class TaskRestController {

    private final TaskRepository taskRepository;
    private final ProcessingTaskResultService taskResultService;
    private final ProcessingTaskResultRepository processingTaskResultRepository;

    @Autowired
    public TaskRestController(TaskRepository taskRepository, ProcessingTaskResultService taskResultService, ProcessingTaskResultRepository processingTaskResultRepository) {
        this.taskRepository = taskRepository;
        this.taskResultService = taskResultService;
        this.processingTaskResultRepository = processingTaskResultRepository;
    }

    @GetMapping
    public List<ProcessingTask> get() {
        log.info("called");
        List<ProcessingTask> all = taskRepository.findAll();
        return all;
    }

    @PostMapping
    @RequestMapping("/subtask/result")
    public NodeReply taskFinished(@RequestBody ProcessingTaskUnitResultDTO processingTaskUnitResultDTO) {
        log.info("called with " + processingTaskUnitResultDTO);

        taskResultService.saveResult(processingTaskUnitResultDTO);

        return new NodeReply(true);
    }

    @PostMapping
    @RequestMapping("/started")
    public NodeReply taskStarted(@RequestBody long id) {
        log.info("called with " + id);

        taskResultService.taskStarted(id);

        return new NodeReply(true);
    }

    @PostMapping
    @RequestMapping("/finished")
    public NodeReply taskFinished(@RequestBody long id) {
        log.info("called with " + id);

        taskResultService.taskFinished(id);

        return new NodeReply(true);
    }

    @PostMapping
    @RequestMapping("/subtask/started")
    public NodeReply subtaskStarted(@RequestBody long id) {
        log.info("called with " + id);

        taskResultService.subtaskStarted(id);

        return new NodeReply(true);
    }

    @PostMapping
    @RequestMapping("/subtask/finished")
    public NodeReply subtaskFinished(@RequestBody long id) {
        log.info("called with " + id);

        taskResultService.subtaskFinished(id);

        return new NodeReply(true);
    }

    public NodeReply startTaskExecution(ProcessingTask processingTask, Node node) {
        log.info("Starting execution of " + processingTask.getName() + " on node " + node.getName());
        RestTemplate restTemplate = new RestTemplate();
        ProcessingTaskDTO processingTaskDTO = processingTask.toDTO();

        // save the result objects, so that we know their id's
        ProcessingTaskResult processingTaskResult = taskResultService.saveEmptyResultObjectsFor(processingTask);
        setUpIds(processingTaskDTO, processingTaskResult);


        log.info("created processing task result" + processingTaskResult.getProcessingTaskUnitResultList()
                                                                        .stream()
                                                                        .map(ProcessingTaskUnitResult::getId)
                                                                        .collect(toList()));


        // TODO real output verification needs to be set from the view
        OutputVerificationDTO outputVerificationDTO = new OutputVerificationDTO();
        outputVerificationDTO.setOutputType(OutputType.NO_CHECK);
        processingTaskDTO.getProcessingTaskUnitDTOS()
                         .forEach(task -> task.setOutputVerificationDTO(outputVerificationDTO));
        NodeReply nodeReply = restTemplate.postForObject("http://" + node.getUrl() + "/task", processingTaskDTO, NodeReply.class);
        if (nodeReply.isOk()) {
            log.info("execution started successfully");
        } else {
            log.severe("error: " + nodeReply.getDescription());
        }
        return nodeReply;
    }

    private void setUpIds(ProcessingTaskDTO processingTaskDTO, ProcessingTaskResult processingTaskResult) {
        processingTaskDTO.setId(processingTaskResult.getId());

        List<ProcessingTaskUnitDTO> processingTaskUnitDTOS = processingTaskDTO.getProcessingTaskUnitDTOS();
        List<ProcessingTaskUnitResult> processingTaskUnitResultList = processingTaskResult.getProcessingTaskUnitResultList();

        if (processingTaskUnitDTOS.size() != processingTaskUnitResultList.size()) {
            throw new RuntimeException("DTO objects were not built correctly, invalid size of taskUnits");
        }

        for (int i = 0; i < processingTaskUnitDTOS.size(); i++) {
            ProcessingTaskUnitDTO processingTaskUnitDTO = processingTaskUnitDTOS.get(i);
            ProcessingTaskUnitResult processingTaskUnitResult = processingTaskUnitResultList.get(i);
            processingTaskUnitDTO.setId(processingTaskUnitResult.getId());
        }
    }
}

package cz.vutbr.fit.gja.proj3.server.processing_task.control;

import cz.vutbr.fit.gja.proj3.common.processing_task.entity.ProcessingTaskUnitResultDTO;
import cz.vutbr.fit.gja.proj3.server.processing_task.boundary.ProcessingTaskResultRepository;
import cz.vutbr.fit.gja.proj3.server.processing_task.boundary.ProcessingTaskUnitResultRepository;
import cz.vutbr.fit.gja.proj3.server.processing_task.boundary.TaskRepository;
import cz.vutbr.fit.gja.proj3.server.processing_task.entity.ProcessingTask;
import cz.vutbr.fit.gja.proj3.server.processing_task.entity.ProcessingTaskResult;
import cz.vutbr.fit.gja.proj3.server.processing_task.entity.ProcessingTaskUnitResult;
import cz.vutbr.fit.gja.proj3.server.processing_task.entity.TaskState;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

import static java.util.stream.Collectors.toList;

/**
 * ProcessingTaskResult entity service.
 */
@Log
@Service
@Transactional
public class ProcessingTaskResultService {

    private final TaskRepository taskRepository;
    private final ProcessingTaskResultRepository processingTaskResultRepository;
    private final ProcessingTaskUnitResultRepository processingTaskUnitResultRepository;

    @Autowired
    public ProcessingTaskResultService(TaskRepository taskRepository, ProcessingTaskResultRepository processingTaskResultRepository, ProcessingTaskUnitResultRepository processingTaskUnitResultRepository) {
        this.taskRepository = taskRepository;
        this.processingTaskResultRepository = processingTaskResultRepository;
        this.processingTaskUnitResultRepository = processingTaskUnitResultRepository;
    }

    public void saveResult(ProcessingTaskUnitResultDTO processingTaskUnitResultDTO) {
        log.info("saving result: " + processingTaskUnitResultDTO);

        ProcessingTaskUnitResult processingTaskUnitResult = processingTaskUnitResultRepository.findOne(processingTaskUnitResultDTO.getId());
        processingTaskUnitResult.update(processingTaskUnitResultDTO);


        processingTaskUnitResultRepository.save(processingTaskUnitResult);
    }


    public ProcessingTaskResult saveEmptyResultObjectsFor(ProcessingTask processingTask) {
        ProcessingTaskResult processingTaskResult = new ProcessingTaskResult();
        processingTaskResult.setProcessingTask(processingTask);
        processingTaskResult.setProcessingTaskUnitResultList(processingTask.getProcessingTaskUnits()
                                                                           .stream()
                                                                           .map(taskUnit -> {
                                                                               ProcessingTaskUnitResult processingTaskUnitResult = new ProcessingTaskUnitResult();
                                                                               processingTaskUnitResult.setProcessingTaskResult(processingTaskResult);
                                                                               processingTaskUnitResult.setProcessingTaskUnit(taskUnit);
                                                                               processingTaskUnitResultRepository.save(processingTaskUnitResult);
                                                                               return processingTaskUnitResult;
                                                                           })
                                                                           .collect(toList())
        );
        processingTaskResultRepository.save(processingTaskResult);
        return processingTaskResult;
    }

    public void taskStarted(long id) {
        ProcessingTaskResult one = processingTaskResultRepository.findOne(id);
        one.setTaskState(TaskState.RUNNING);
        processingTaskResultRepository.save(one);
    }

    public void subtaskStarted(long id) {
        ProcessingTaskUnitResult one = processingTaskUnitResultRepository.findOne(id);
        one.setTaskState(TaskState.RUNNING);
        processingTaskUnitResultRepository.save(one);
    }

    public void taskFinished(long id) {
        ProcessingTaskResult one = processingTaskResultRepository.findOne(id);
        one.setTaskState(TaskState.FINISHED);
        processingTaskResultRepository.save(one);
    }

    public void subtaskFinished(long id) {
        ProcessingTaskUnitResult one = processingTaskUnitResultRepository.findOne(id);
        one.setTaskState(TaskState.FINISHED);
        processingTaskUnitResultRepository.save(one);
    }
}

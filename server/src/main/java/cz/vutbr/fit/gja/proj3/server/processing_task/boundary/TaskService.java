/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.vutbr.fit.gja.proj3.server.processing_task.boundary;

import cz.vutbr.fit.gja.proj3.server.processing_task.entity.ProcessingTask;
import cz.vutbr.fit.gja.proj3.server.processing_task.entity.ProcessingTaskUnit;
import javax.transaction.Transactional;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Task entity service.
 */
@Service
@Transactional
public class TaskService {
    private final TaskRepository repository;
    
    @Autowired
    public TaskService(TaskRepository repository) {
        this.repository = repository;
    }
    
    public ProcessingTask save(ProcessingTask task) {
        ProcessingTask ret = repository.save(task);
        repository.flush();
        Hibernate.initialize(ret.getProcessingTaskResults());
        ret.getProcessingTaskResults().forEach(
            result -> Hibernate.initialize(result.getProcessingTaskUnitResultList())
        );
        return ret;
    }
    
    /*public ProcessingTask removeTaskUnit(ProcessingTask task, ProcessingTaskUnit taskUnit) {
        
    }*/
}

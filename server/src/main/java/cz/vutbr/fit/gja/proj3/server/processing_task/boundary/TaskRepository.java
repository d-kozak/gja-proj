package cz.vutbr.fit.gja.proj3.server.processing_task.boundary;

import cz.vutbr.fit.gja.proj3.server.processing_task.entity.ProcessingTask;
import org.hibernate.Hibernate;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;
import java.util.List;

public interface TaskRepository extends JpaRepository<ProcessingTask, Long> {

    @Transactional
    default List<ProcessingTask> findAllFetchTaskUnits() {
        List<ProcessingTask> all = findAll();
        // all.forEach(processingTask -> Hibernate.initialize(processingTask.getProcessingTaskUnits()));
        return all;
    }
}

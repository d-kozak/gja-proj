package cz.vutbr.fit.gja.proj3.server.processing_task.boundary;

import cz.vutbr.fit.gja.proj3.server.processing_task.entity.ProcessingTaskResult;
import cz.vutbr.fit.gja.proj3.server.processing_task.entity.TaskState;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface ProcessingTaskResultRepository extends JpaRepository<ProcessingTaskResult, Long> {
    public List<ProcessingTaskResult> findAllByTaskState(TaskState state);
}

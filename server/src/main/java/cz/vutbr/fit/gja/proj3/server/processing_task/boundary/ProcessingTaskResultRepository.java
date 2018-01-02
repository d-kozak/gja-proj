package cz.vutbr.fit.gja.proj3.server.processing_task.boundary;

import cz.vutbr.fit.gja.proj3.server.processing_task.entity.ProcessingTaskResult;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProcessingTaskResultRepository extends JpaRepository<ProcessingTaskResult, Long> {
}

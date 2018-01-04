package cz.vutbr.fit.gja.proj3.server.processing_task.boundary;

import cz.vutbr.fit.gja.proj3.server.processing_task.entity.ProcessingTaskUnitResult;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * ProcessingTaskUnitResult entity repository.
 */
public interface ProcessingTaskUnitResultRepository extends JpaRepository<ProcessingTaskUnitResult, Long> {
}

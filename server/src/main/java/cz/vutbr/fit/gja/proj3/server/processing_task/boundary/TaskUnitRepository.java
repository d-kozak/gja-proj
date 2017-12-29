package cz.vutbr.fit.gja.proj3.server.processing_task.boundary;

import cz.vutbr.fit.gja.proj3.server.processing_task.entity.ProcessingTaskUnit;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskUnitRepository extends JpaRepository<ProcessingTaskUnit, Long> {
    
}

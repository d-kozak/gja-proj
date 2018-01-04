package cz.vutbr.fit.gja.proj3.server.processing_task.boundary;

import cz.vutbr.fit.gja.proj3.common.processing_task.entity.OutputType;
import cz.vutbr.fit.gja.proj3.server.processing_task.entity.ProcessingTask;
import cz.vutbr.fit.gja.proj3.server.processing_task.entity.TaskState;
import java.util.HashMap;
import org.hibernate.Hibernate;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Repository;

@Repository
@Transactional
public interface TaskRepository extends JpaRepository<ProcessingTask, Long> {
    public static final Map<TaskState, String> STATES = new HashMap<TaskState, String>(){{
        put(TaskState.CREATED, "Created");
        put(TaskState.RUNNING, "Running");
        put(TaskState.FINISHED, "Finished");
    }};
    
    public static final Map<OutputType, String> OUTPUT_TYPES = new HashMap<OutputType, String>(){{
        put(OutputType.NO_CHECK, "No checking");
        put(OutputType.SINGLE_FILE, "Single file");
        put(OutputType.ENUMERATED_LIST, "File list");
        put(OutputType.REGEX, "Regex");
    }};
    
    default List<ProcessingTask> findAllEagerFetch() {
        List<ProcessingTask> all = findAll();
        all.forEach(
                task -> {
                    Hibernate.initialize(task.getProcessingTaskUnits());
                    Hibernate.initialize(task.getProcessingTaskResults());
                    task.getProcessingTaskResults().forEach(
                        result -> Hibernate.initialize(result.getProcessingTaskUnitResultList())
                    );
                }
        );
        return all;
    }
}
package cz.vutbr.fit.gja.proj3.server.processing_task.boundary;

import cz.vutbr.fit.gja.proj3.server.node.entity.Node;
import cz.vutbr.fit.gja.proj3.server.processing_task.entity.ProcessingTask;
import cz.vutbr.fit.gja.proj3.server.processing_task.entity.TaskState;
import cz.vutbr.fit.gja.proj3.server.project.entity.Project;
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
    public static final Map<TaskState, String> STATES = new HashMap<TaskState, String>(){{;
        put(TaskState.CREATED, "Created");
        put(TaskState.RUNNING, "Running");
        put(TaskState.FINISHED, "Finished");
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
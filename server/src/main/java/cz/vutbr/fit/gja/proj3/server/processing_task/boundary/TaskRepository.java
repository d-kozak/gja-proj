package cz.vutbr.fit.gja.proj3.server.processing_task.boundary;

import cz.vutbr.fit.gja.proj3.server.node.entity.Node;
import cz.vutbr.fit.gja.proj3.server.processing_task.entity.ProcessingTask;
import cz.vutbr.fit.gja.proj3.server.project.entity.Project;
import org.hibernate.Hibernate;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
@Transactional
public interface TaskRepository extends JpaRepository<ProcessingTask, Long> {
    
    List<ProcessingTask> findAllByProject(Project p);
    List<ProcessingTask> findAllByNode(Node n);
    
    default List<ProcessingTask> findAllEagerFetch() {
        List<ProcessingTask> all = findAll();
        all.forEach(task -> Hibernate.initialize(task.getProcessingTaskUnits()));
        return all;
    }
}
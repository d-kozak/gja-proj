package cz.vutbr.fit.gja.proj3.server.project.boundary;

import cz.vutbr.fit.gja.proj3.server.node.entity.Node;
import cz.vutbr.fit.gja.proj3.server.processing_task.entity.ProcessingTask;
import cz.vutbr.fit.gja.proj3.server.project.entity.Project;
import java.util.List;
import javax.transaction.Transactional;
import org.hibernate.Hibernate;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectRepository extends JpaRepository<Project, Long> {
    
    @Transactional
    default List<Project> findAllEagerFetch() {
        List<Project> all = findAll();
        all.forEach(project -> Hibernate.initialize(project.getProcessingTasks()));
        return all;
    }
}

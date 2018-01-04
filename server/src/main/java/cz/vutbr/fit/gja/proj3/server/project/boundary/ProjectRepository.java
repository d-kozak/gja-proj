package cz.vutbr.fit.gja.proj3.server.project.boundary;

import cz.vutbr.fit.gja.proj3.server.project.entity.Project;
import java.util.List;
import javax.transaction.Transactional;
import org.hibernate.Hibernate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Project entity repository.
 */
@Transactional
@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {
    default List<Project> findAllEagerFetch() {
        List<Project> all = findAll();
        all.forEach(project -> Hibernate.initialize(project.getProcessingTasks()));
        all.forEach(
            node -> node.getProcessingTasks().forEach(
                    task -> Hibernate.initialize(task.getProcessingTaskUnits())
            )
        );
        return all;
    }
}

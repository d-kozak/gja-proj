package cz.vutbr.fit.gja.proj3.server.project.boundary;

import cz.vutbr.fit.gja.proj3.server.project.entity.Project;
import java.util.List;
import javax.transaction.Transactional;
import org.hibernate.Hibernate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@Transactional
public interface ProjectRepository extends JpaRepository<Project, Long> {
    
    default List<Project> findAllEagerFetch() {
        List<Project> all = findAll();
        all.forEach(project -> Hibernate.initialize(project.getProcessingTasks()));
        return all;
    }
}

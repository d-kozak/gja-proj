package cz.vutbr.fit.gja.proj3.server.task.boundary;

import cz.vutbr.fit.gja.proj3.server.task.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Long> {
}

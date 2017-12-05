package cz.vutbr.fit.gja.proj3.server.task.boundary;

import cz.vutbr.fit.gja.proj3.server.task.entity.Command;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommandRepository extends JpaRepository<Command, Long> {
}

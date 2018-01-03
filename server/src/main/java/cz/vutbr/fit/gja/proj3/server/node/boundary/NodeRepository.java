package cz.vutbr.fit.gja.proj3.server.node.boundary;

import cz.vutbr.fit.gja.proj3.server.node.entity.Node;
import org.hibernate.Hibernate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public interface NodeRepository extends JpaRepository<Node, Long> {

    List<Node> findAllByActiveIsTrue();

    List<Node> findAllByActiveIsFalse();

    Node findByUrl(String url);

    default List<Node> findAllEagerFetch() {
        List<Node> all = findAll();
        all.forEach(node -> Hibernate.initialize(node.getProcessingTasks()));
        all.forEach(
                node -> node.getProcessingTasks()
                            .forEach(
                                    task -> Hibernate.initialize(task.getProcessingTaskUnits())
                            )
        );
        return all;
    }
}

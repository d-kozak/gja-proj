package cz.vutbr.fit.gja.proj3.server.node.boundary;

import cz.vutbr.fit.gja.proj3.server.node.entity.Node;
import org.hibernate.Hibernate;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;
import java.util.List;

public interface NodeRepository extends JpaRepository<Node, Long> {

    List<Node> findAllByActiveIsTrue();

    @Transactional
    default List<Node> findAllEagerFetch() {
        List<Node> all = findAll();
        all.forEach(node -> Hibernate.initialize(node.getProcessingTasks()));
        return all;
    }
}

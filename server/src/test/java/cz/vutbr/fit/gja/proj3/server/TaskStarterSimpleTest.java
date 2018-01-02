package cz.vutbr.fit.gja.proj3.server;

import cz.vutbr.fit.gja.proj3.server.node.boundary.NodeRepository;
import cz.vutbr.fit.gja.proj3.server.node.entity.Node;
import cz.vutbr.fit.gja.proj3.server.processing_task.boundary.TaskRepository;
import cz.vutbr.fit.gja.proj3.server.processing_task.boundary.TaskRestController;
import cz.vutbr.fit.gja.proj3.server.processing_task.entity.ProcessingTask;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Random;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class TaskStarterSimpleTest {

    @Autowired
    private TaskRestController taskRestController;

    @Autowired
    private NodeRepository nodeRepository;

    @Autowired
    private TaskRepository taskRepository;

    private Node selectedNode;

    private ProcessingTask selectedTask;

    private static <T> T randomFrom(List<T> list) {
        if (list.size() == 0) {
            throw new RuntimeException("list is empty");
        }
        Random random = new Random();
        return list.get(random.nextInt(list.size()));
    }

    @Before
    public void setup() {
        selectedNode = randomFrom(nodeRepository.findAllByActiveIsTrue());
        selectedTask = randomFrom(taskRepository.findAll());
    }

    @Test
    public void taskExecutes() {
        taskRestController.startTaskExecution(selectedTask, selectedNode);

        sleep(10000);
    }

    private void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}

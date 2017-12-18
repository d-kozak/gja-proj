package cz.vutbr.fit.gja.proj3.server.node.entity;

import cz.vutbr.fit.gja.proj3.server.task.entity.Task;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Node {
    @Id
    @GeneratedValue
    private long id;
    @NotNull
    private String url;
    private String name;

    @OneToMany(mappedBy = "node")
    private List<Task> tasks;
}

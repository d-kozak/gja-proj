package cz.vutbr.fit.gja.proj3.server.task.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Task {

    @Id
    @GeneratedValue
    private long id;
    private String name;

    @OrderColumn
    @OneToMany(mappedBy = "task", cascade = CascadeType.ALL)
    private List<Command> commands;

    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", commands=" + commands +
                '}';
    }
}

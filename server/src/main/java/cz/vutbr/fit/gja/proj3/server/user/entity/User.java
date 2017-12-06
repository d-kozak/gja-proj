package cz.vutbr.fit.gja.proj3.server.user.entity;

import cz.vutbr.fit.gja.proj3.server.task.entity.Task;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {
    @Id
    @GeneratedValue
    private long id;

    @Column(nullable = false, unique = true)
    private String login;
    private String password;
    private String firstName;
    private String lastName;

    @ManyToMany
    private List<Task> tasks;

}

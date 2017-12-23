package cz.vutbr.fit.gja.proj3.server.user.entity;

import cz.vutbr.fit.gja.proj3.server.processing_task.entity.ProcessingTask;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

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
    private List<ProcessingTask> processingTasks;

    @ElementCollection(fetch = FetchType.EAGER)
    private Set<String> roles;

}

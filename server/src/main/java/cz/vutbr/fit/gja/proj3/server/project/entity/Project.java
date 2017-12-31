package cz.vutbr.fit.gja.proj3.server.project.entity;

import cz.vutbr.fit.gja.proj3.server.processing_task.entity.ProcessingTask;
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
public class Project {
    @Id
    @GeneratedValue
    private long id;
    @NotNull
    private String name;

    @OneToMany(mappedBy = "project")
    private List<ProcessingTask> processingTasks;
    
    @Override
    public String toString() {
        return this.name;
    }
}

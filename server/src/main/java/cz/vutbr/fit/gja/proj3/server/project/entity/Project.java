package cz.vutbr.fit.gja.proj3.server.project.entity;

import cz.vutbr.fit.gja.proj3.server.processing_task.entity.ProcessingTask;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.FetchType;

/**
 * Project entity.
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Project implements Serializable {
    @Id
    @GeneratedValue
    private long id;
    @NotNull
    private String name;

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProcessingTask> processingTasks;
    
    @Override
    public boolean equals(Object o) {
        return o != null && o instanceof Project && ((Project)o).getId() == this.getId();
    }
}

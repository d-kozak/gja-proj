package cz.vutbr.fit.gja.proj3.server.task.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Map;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Command {

    @Id
    @GeneratedValue
    private long id;
    private String commandText;
    @ManyToOne(cascade = CascadeType.ALL)
    @JsonIgnore
    private Task task;
    @ElementCollection
    private Map<String, String> parameters;

    @Override
    public String toString() {
        return "Command{" +
                "id=" + id +
                ", commandText='" + commandText + '\'' +
                '}';
    }
}

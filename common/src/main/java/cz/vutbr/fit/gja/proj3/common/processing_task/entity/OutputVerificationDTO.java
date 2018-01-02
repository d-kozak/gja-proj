package cz.vutbr.fit.gja.proj3.common.processing_task.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OutputVerificationDTO {
    @NotNull
    private OutputType outputType;

    private String outputDirectory = ".";

    private List<String> expectedOutput;
}

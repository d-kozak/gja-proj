package cz.vutbr.fit.gja.proj3.server.processing_task.entity;

import cz.vutbr.fit.gja.proj3.common.processing_task.entity.OutputType;
import cz.vutbr.fit.gja.proj3.common.processing_task.entity.OutputVerificationDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.ElementCollection;
import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Task Output verification entity.
 */
@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OutputVerification {
    @NotNull
    private OutputType outputType;

    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> expectedOutput;

    public OutputVerificationDTO toDTO() {
        return OutputVerificationDTO.builder()
                                    .outputType(getOutputType())
                                    .expectedOutput(getExpectedOutput())
                                    .build();
    }

}

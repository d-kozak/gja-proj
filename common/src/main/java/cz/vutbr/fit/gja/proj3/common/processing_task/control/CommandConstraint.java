package cz.vutbr.fit.gja.proj3.common.processing_task.control;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = CommandValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface CommandConstraint {
    String message() default "This command is not valid";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}

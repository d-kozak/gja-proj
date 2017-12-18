package cz.vutbr.fit.gja.proj3.executiontask.control;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = DirectoryValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface DirectoryConstraint {
    String message() default "Invalid directory";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}

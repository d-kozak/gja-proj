package cz.vutbr.fit.gja.proj3.common.executiontask.control;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class CommandValidator implements ConstraintValidator<CommandConstraint, String> {

    public void initialize(CommandConstraint commandConstraint) {

    }

    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        // dummy implementation
        return !s.equals("rm");
    }
}

package cz.vutbr.fit.gja.proj3.common.executiontask.control;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.io.File;

public class DirectoryValidator implements ConstraintValidator<DirectoryConstraint, String> {
    public void initialize(DirectoryConstraint constraint) {
    }

    public boolean isValid(String url, ConstraintValidatorContext context) {
        File dir = new File(url);
        return dir.isDirectory() && dir.canWrite();
    }
}

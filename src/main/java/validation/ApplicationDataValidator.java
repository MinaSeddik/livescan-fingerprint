package validation;

import com.softxpert.livescanfingerprint.model.Application;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Objects;

public class ApplicationDataValidator implements ConstraintValidator<ValidApplicationData, Application> {

    @Override
    public void initialize(ValidApplicationData validApplicationData) {
    }

    @Override
    public boolean isValid(Application application, ConstraintValidatorContext cxt) {

        if (Objects.isNull(application) || Objects.isNull(application.getFingerprints())) {
            return false;
        }

        return application.getFingerprints().size() == 14;

    }
}

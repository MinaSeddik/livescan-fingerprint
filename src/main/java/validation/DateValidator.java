package validation;



import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Objects;

import static com.softxpert.livescanfingerprint.model.ApplicationConstraint.DATE_PATTERN;

public class DateValidator implements ConstraintValidator<ValidDate, String> {



    @Override
    public void initialize(ValidDate validDate) {
    }

    @Override
    public boolean isValid(String customDateField, ConstraintValidatorContext cxt) {

        if(Objects.isNull(customDateField))
            return false;

        SimpleDateFormat sdf = new SimpleDateFormat(DATE_PATTERN);
        try {
            sdf.setLenient(false);
            sdf.parse(customDateField);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }


}

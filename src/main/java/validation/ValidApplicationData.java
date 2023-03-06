package validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = ApplicationDataValidator.class)
@Target( { ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidApplicationData {

    String message() default "Invalid Application data";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}

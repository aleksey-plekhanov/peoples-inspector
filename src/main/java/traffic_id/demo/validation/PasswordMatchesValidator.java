package traffic_id.demo.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import traffic_id.demo.service.UserDto;
import traffic_id.demo.validation.annotation.PasswordMatches;

public class PasswordMatchesValidator implements ConstraintValidator<PasswordMatches, Object> {
    
    @Override
    public void initialize(PasswordMatches constraintAnnotation) {
    }
    @Override
    public boolean isValid(Object obj, ConstraintValidatorContext context){
        UserDto user = (UserDto) obj;
        boolean isValid = user.getPassword().equals(user.getMatchingPassword());
        
        if (!isValid) {
          context.disableDefaultConstraintViolation();
          context.buildConstraintViolationWithTemplate(context.getDefaultConstraintMessageTemplate()).addPropertyNode("password").addConstraintViolation();
        }

        return isValid;
    }
}

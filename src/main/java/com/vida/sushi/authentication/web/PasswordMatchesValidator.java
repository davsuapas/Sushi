package com.vida.sushi.authentication.web;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Password matches validator
 *
 * @author dav.sua.pas@gmail.com
 */
public class PasswordMatchesValidator implements ConstraintValidator<PasswordMatches, Object> {

    @Override
    public void initialize(final PasswordMatches constraintAnnotation) {
    }

    @Override
    public boolean isValid(final Object obj, final ConstraintValidatorContext context) {
        final RegistrationUser user = (RegistrationUser)obj;
        return user.getPassword().equals(user.getMatchingPassword());
    }

}
package com.emmariescurrena.all_blog.validators;

import java.util.Arrays;

import org.passay.CharacterRule;
import org.passay.EnglishCharacterData;
import org.passay.LengthRule;
import org.passay.PasswordData;
import org.passay.PasswordValidator;
import org.passay.RuleResult;
import org.passay.WhitespaceRule;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PasswordConstraintValidator implements ConstraintValidator<ValidPassword, String> {

    @Override
    public void initialize(ValidPassword arg0) {
    }

    @Override
    public boolean isValid(String password, ConstraintValidatorContext context) {

        PasswordValidator validator = new PasswordValidator(Arrays.asList(
                new LengthRule(8, 30),
                new CharacterRule(EnglishCharacterData.UpperCase, 1),
                new CharacterRule(EnglishCharacterData.LowerCase, 1),
                new CharacterRule(EnglishCharacterData.Digit, 1),
                new CharacterRule(EnglishCharacterData.Special, 1),
                new WhitespaceRule()));

        RuleResult result = validator.validate(new PasswordData(password));
        if (result.isValid()) {
            return true;
        }

        /*
         * List<String> messages = validator.getMessages(result);
         * 
         * String messageTemplate = messages.stream()
         * .collect(Collectors.joining(","));
         */

        context.buildConstraintViolationWithTemplate(
                String.join("",
                        "Password must have at least 1 lowercased letter, ",
                        "1 uppercased letter, ",
                        "1 number, ",
                        "and 1 special character, ",
                        "a min. of 8 characters ",
                        "and a max. of 30"))
                .addConstraintViolation()
                .disableDefaultConstraintViolation();
        return false;

    }
}

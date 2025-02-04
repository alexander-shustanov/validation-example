package com.shustanov.customvalidator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PasswordValidator.class)
public @interface ValidPassword {
    String message() default "Password must be at least 8 characters long and contain uppercase letters, lowercase letters, digits, and special characters";

    int minLength() default 8;

    String specialChars() default "!@#$%^&*()_+-=[]{}|;:',.<>?";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}

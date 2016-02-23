package com.hedges.jpalearning.advancedchapters.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Indicate that a number should be even.
 * May be applied on fields or properties of type Integer.
 */
@Constraint(validatedBy={EvenNumberValidator.class})
@Target({ ElementType.METHOD,  ElementType.FIELD})
@Retention( RetentionPolicy.RUNTIME )
public @interface Even
{
    String message() default "Number must be even";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
    boolean includeZero() default true;
}
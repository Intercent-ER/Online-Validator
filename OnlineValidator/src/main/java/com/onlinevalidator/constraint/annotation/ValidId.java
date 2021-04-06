package com.onlinevalidator.constraint.annotation;

import com.onlinevalidator.constraint.IdValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * @author Manuel Gozzi
 */
@Documented
@Constraint(validatedBy = IdValidator.class)
@Target(value = {METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER})
@Retention(value = RUNTIME)
public @interface ValidId {

	String message();

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
}
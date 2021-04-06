package com.onlinevalidator.constraint;

import com.onlinevalidator.constraint.annotation.ValidId;
import com.onlinevalidator.util.ApplicationLogger;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @author Manuel Gozzi
 */
public class IdValidator implements ConstraintValidator<ValidId, Integer>, ApplicationLogger {

	@Override
	public void initialize(ValidId constraintAnnotation) {
	}

	@Override
	public boolean isValid(Integer value, ConstraintValidatorContext context) {

		// Se il valore è null, la validazione fallisce automaticamente
		if (value == null) {

			logDebug("Valore null, impossibile proseguire con la validazione");
			return false;
		}

		// Id valido solamente se positivo
		return value > 0;
	}
}

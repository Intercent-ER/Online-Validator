package com.onlinevalidator.constraint;

import com.onlinevalidator.constraint.annotation.MultipartFileNotNull;
import com.onlinevalidator.util.ApplicationLogger;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @author Manuel Gozzi
 */
public class MultipartFileValidator implements ConstraintValidator<MultipartFileNotNull, MultipartFile>, ApplicationLogger {

	@Override
	public void initialize(MultipartFileNotNull constraintAnnotation) {
	}

	@Override
	public boolean isValid(MultipartFile value, ConstraintValidatorContext context) {

		// Se l'oggetto è null, la validazione fallisce
		if (value == null) {

			logDebug("{} null in input", MultipartFile.class.getName());
			return false;
		}

		// Se il file non ha dimensione, la validazione fallisce
		if (value.getSize() <= 0L) {

			logDebug("Il file ricevuto in ingresso è vuoto");
			return false;
		}

		// In tutti gli altri casi, la validazione va a buon fine
		return true;
	}
}

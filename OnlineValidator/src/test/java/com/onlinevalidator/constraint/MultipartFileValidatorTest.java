package com.onlinevalidator.constraint;

import org.junit.Test;
import org.springframework.mock.web.MockMultipartFile;

import java.nio.charset.StandardCharsets;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * @author Manuel Gozzi
 */
public class MultipartFileValidatorTest {

	@Test
	public void initialize() {
		new MultipartFileValidator().initialize(null);
	}

	@Test
	public void isValid() {

		MultipartFileValidator multipartFileValidator = new MultipartFileValidator();
		MockMultipartFile mockMultipartFile = new MockMultipartFile("fileName", "".getBytes());

		assertFalse(
				multipartFileValidator.isValid(null, null)
		);
		assertFalse(
				multipartFileValidator.isValid(mockMultipartFile, null)
		);

		mockMultipartFile = new MockMultipartFile("fileName", "payload".getBytes(StandardCharsets.UTF_8));
		assertTrue(
				multipartFileValidator.isValid(mockMultipartFile, null)
		);
	}
}
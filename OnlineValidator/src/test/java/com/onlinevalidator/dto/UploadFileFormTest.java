package com.onlinevalidator.dto;

import org.junit.Test;
import org.springframework.mock.web.MockMultipartFile;

import java.nio.charset.StandardCharsets;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

/**
 * @author Manuel Gozzi
 */
public class UploadFileFormTest {

	@Test
	public void getFileDocumento() {

		try {

			assertNull(new UploadFileForm().getFileDocumento());
		} catch (Exception e) {

			fail(e.getMessage());
		}
	}

	@Test
	public void setFileDocumento() {

		try {

			new UploadFileForm().setFileDocumento(new MockMultipartFile("fileName", "fileContent".getBytes(StandardCharsets.UTF_8)));
			new UploadFileForm().setFileDocumento(null);
		} catch (Exception e) {

			fail(e.getMessage());
		}
	}

	@Test
	public void getIdDocumento() {

		try {

			assertNull(new UploadFileForm().getIdDocumento());
		} catch (Exception e) {

			fail(e.getMessage());
		}
	}

	@Test
	public void setIdDocumento() {

		try {

			new UploadFileForm().setIdDocumento(100);
			new UploadFileForm().setIdDocumento(null);
		} catch (Exception e) {

			fail(e.getMessage());
		}
	}

	@Test
	public void getIdRappresentazioneDocumento() {

		try {

			assertNull(new UploadFileForm().getIdRappresentazioneDocumento());
		} catch (Exception e) {

			fail(e.getMessage());
		}
	}

	@Test
	public void setIdRappresentazioneDocumento() {

		try {

			new UploadFileForm().setIdRappresentazioneDocumento(105);
			new UploadFileForm().setIdRappresentazioneDocumento(null);
		} catch (Exception e) {

			fail(e.getMessage());
		}
	}

	@Test
	public void getCaptcha() {

		try {

			assertNull(new UploadFileForm().getCaptcha());
		} catch (Exception e) {

			fail(e.getMessage());
		}
	}

	@Test
	public void setCaptcha() {

		try {

			new UploadFileForm().setCaptcha("captcha");
			new UploadFileForm().setCaptcha(null);
		} catch (Exception e) {

			fail(e.getMessage());
		}
	}
}
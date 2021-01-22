package com.onlinevalidator.util;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletResponse;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * @author Manuel Gozzi
 */
public class FileUtilTest {

	@Test
	public void outputFile() {

		HttpServletResponse httpServletResponse = new MockHttpServletResponse();
		httpServletResponse.setContentType("text/xml");
		try {
			FileUtil.outputFile(httpServletResponse, new ByteArrayInputStream("prova".getBytes(StandardCharsets.UTF_8)), "filediprova.txt", "text/xml");
		} catch (IOException e) {
			Assert.fail();
		}

	}

}
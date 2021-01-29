package com.onlinevalidator.util;

import org.apache.commons.io.IOUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Importata da NoTI-ER.
 *
 * @author Manuel Gozzi
 */
public class FileUtil {

	private static final int BUFFER_SIZE = 2048;

	private static void outputFile(HttpServletResponse response, InputStream inputStream, boolean isAttachment, String nomeFile) throws IOException {
		outputFile(response, inputStream, isAttachment, nomeFile, "text/xml");
	}

	private static void outputFile(HttpServletResponse response, InputStream inputStream, boolean isAttachment, String nomeFile, String mimeType)
			throws IOException {
		response.setContentType(mimeType);
		if (isAttachment) {
			String headerKey = "Content-Disposition";
			String headerValue = String.format("attachment; filename=\"%s\"", nomeFile);
			response.setHeader(headerKey, headerValue);
		}
		OutputStream outStream = response.getOutputStream();
		byte[] buffer = new byte[BUFFER_SIZE];
		int bytesRead = -1;
		while ((bytesRead = inputStream.read(buffer)) != -1) {
			outStream.write(buffer, 0, bytesRead);
		}
		inputStream.close();
		outStream.close();
	}

	public static void outputFile(HttpServletResponse response, InputStream inputStream, String nomeFile, String mimeType) throws IOException {

		outputFile(response, inputStream, true, nomeFile, mimeType);
	}

	public static void outputFile(HttpServletResponse response, InputStream inputStream, String nomeFile) throws IOException {

		outputFile(response, inputStream, true, nomeFile);
	}

	public static void outputFile(HttpServletResponse response, InputStream inputStream) throws IOException {

		outputFile(response, inputStream, false, null);
	}

	public static void outputFile(HttpServletResponse response, ByteArrayOutputStream bos, String nomeFile) throws IOException {

		OutputStream output = null;
		String mimeType = "text/xml";
		response.setContentType(mimeType);
		String headerKey = "Content-Disposition";
		String headerValue = String.format("attachment; filename=\"%s\"", nomeFile);
		response.setHeader(headerKey, headerValue);
		try {

			output = response.getOutputStream();
			output.write(bos.toByteArray());

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			IOUtils.closeQuietly(bos);
			IOUtils.closeQuietly(output);
		}
	}

}

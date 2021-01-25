package com.onlinevalidator.service.impl;

import com.onlinevalidator.dto.Render;
import com.onlinevalidator.dto.ValidationAssert;
import com.onlinevalidator.dto.ValidationReport;
import com.onlinevalidator.exception.rendering.PdfRenderingException;
import com.onlinevalidator.exception.rendering.XmlRenderingException;
import com.onlinevalidator.pojo.TipoRenderingEnum;
import com.onlinevalidator.service.RenderingServiceInterface;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 * @author Manuel Gozzi
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:/bean-servlet-test.xml")
public class RenderingServiceTest {

	@Autowired
	private RenderingServiceInterface renderingService;

	@Test
	public void render() {

		RenderingServiceInterface renderingServiceSpy = spy(this.renderingService);
		ValidationReport validationReport = mockValidationReportFailed();
		ValidationReport validationReport1 = mockValidationReportSucceeded();

		try {

			renderingServiceSpy.render(validationReport, TipoRenderingEnum.PDF);
			verify(renderingServiceSpy, times(1)).renderToPdf(validationReport);
			verify(renderingServiceSpy, times(0)).renderToXml(validationReport);
		} catch (Exception e) {

			fail(e.getMessage());
		}

		try {

			renderingServiceSpy.render(null, TipoRenderingEnum.PDF);
		} catch (Exception e) {

			assertTrue(e instanceof PdfRenderingException);
			assertEquals("Impossibile eseguire il rendering PDF: null", e.getMessage());
		}

		try {

			renderingServiceSpy.render(validationReport, TipoRenderingEnum.XML);
			verify(renderingServiceSpy, times(1)).renderToPdf(validationReport);
			verify(renderingServiceSpy, times(1)).renderToXml(validationReport);
		} catch (Exception e) {

			fail(e.getMessage());
		}

		try {
			renderingServiceSpy.render(null, TipoRenderingEnum.XML);
		} catch (Exception e) {

			assertTrue(e instanceof XmlRenderingException);
			assertEquals("Impossibile eseguire il rendering XML: null", e.getMessage());
		}

		try {
			renderingServiceSpy.render(null, null);
		} catch (Exception e) {

			assertTrue(e instanceof NullPointerException);
			assertEquals("Impossibile eseguire il rendering senza specificare il tipo", e.getMessage());
		}

		try {

			renderingServiceSpy.render(validationReport1, TipoRenderingEnum.PDF);
			verify(renderingServiceSpy, times(1)).renderToPdf(validationReport1);
			verify(renderingServiceSpy, times(0)).renderToXml(validationReport1);
		} catch (Exception e) {

			fail(e.getMessage());
		}

		try {

			renderingServiceSpy.render(validationReport1, TipoRenderingEnum.XML);
			verify(renderingServiceSpy, times(1)).renderToPdf(validationReport1);
			verify(renderingServiceSpy, times(1)).renderToXml(validationReport1);
		} catch (Exception e) {

			fail(e.getMessage());
		}
	}

	@Test
	public void renderToPdf() {

		RenderingServiceInterface renderingServiceSpy = spy(this.renderingService);
		ValidationReport validationReport = mockValidationReportFailed();
		ValidationReport validationReport1 = mockValidationReportSucceeded();

		try {

			Render result = renderingServiceSpy.renderToPdf(validationReport);
			assertNotNull(result);
			assertNotNull(result.getFile());
			assertEquals(TipoRenderingEnum.PDF, result.getTipoEsportazioneEnum());
		} catch (Exception e) {

			fail(e.getMessage());
		}

		try {

			renderingServiceSpy.renderToPdf(null);
		} catch (Exception e) {

			assertTrue(e instanceof PdfRenderingException);
			assertEquals("Impossibile eseguire il rendering PDF: null", e.getMessage());
		}

		try {

			Render result = renderingServiceSpy.renderToPdf(validationReport1);
			assertNotNull(result);
			assertNotNull(result.getFile());
			assertEquals(TipoRenderingEnum.PDF, result.getTipoEsportazioneEnum());
		} catch (Exception e) {

			fail(e.getMessage());
		}
	}

	@Test
	public void renderToXml() {

		RenderingServiceInterface renderingServiceSpy = spy(this.renderingService);
		ValidationReport validationReport = mockValidationReportFailed();
		ValidationReport validationReport1 = mockValidationReportSucceeded();

		try {

			Render result = renderingServiceSpy.renderToXml(validationReport);
			assertNotNull(result);
			assertNotNull(result.getFile());
			assertEquals(TipoRenderingEnum.XML, result.getTipoEsportazioneEnum());
		} catch (Exception e) {

			fail(e.getMessage());
		}

		try {

			renderingServiceSpy.renderToXml(null);
		} catch (Exception e) {

			assertTrue(e instanceof XmlRenderingException);
			assertEquals("Impossibile eseguire il rendering XML: null", e.getMessage());
		}

		try {

			Render result = renderingServiceSpy.renderToXml(validationReport1);
			assertNotNull(result);
			assertNotNull(result.getFile());
			assertEquals(TipoRenderingEnum.XML, result.getTipoEsportazioneEnum());
		} catch (Exception e) {

			fail(e.getMessage());
		}
	}

	/**
	 * Restituisce un mock della classe {@link ValidationReport} per il caso di errore.
	 *
	 * @return il mock della classe interessata
	 */
	private ValidationReport mockValidationReportFailed() {

		ValidationAssert fatalAssert = mock(ValidationAssert.class);
		when(fatalAssert.getTest()).thenReturn("test");
		when(fatalAssert.getTesto()).thenReturn("descrizione errore");
		when(fatalAssert.isFatal()).thenReturn(true);
		when(fatalAssert.isWarning()).thenReturn(false);

		ValidationAssert warningAssert = mock(ValidationAssert.class);
		when(warningAssert.getTest()).thenReturn("test");
		when(warningAssert.getTesto()).thenReturn("descrizione errore");
		when(warningAssert.isFatal()).thenReturn(false);
		when(warningAssert.isWarning()).thenReturn(true);

		ValidationReport validationReport = mock(ValidationReport.class);
		List<ValidationAssert> asserts = new ArrayList<>();
		asserts.add(fatalAssert);
		asserts.add(warningAssert);
		when(validationReport.getErroriDiValidazione()).thenReturn(asserts);
		when(validationReport.getDataDiGenerazione()).thenReturn(new Date());
		when(validationReport.isValido()).thenReturn(false);

		return validationReport;
	}

	/**
	 * Restituisce un mock della classe {@link ValidationReport} per il caso di successo.
	 *
	 * @return il mock della classe interessata
	 */
	private ValidationReport mockValidationReportSucceeded() {

		ValidationReport validationReport = mock(ValidationReport.class);
		when(validationReport.getErroriDiValidazione()).thenReturn(Collections.emptyList());
		when(validationReport.getDataDiGenerazione()).thenReturn(new Date());
		when(validationReport.isValido()).thenReturn(true);

		return validationReport;
	}
}
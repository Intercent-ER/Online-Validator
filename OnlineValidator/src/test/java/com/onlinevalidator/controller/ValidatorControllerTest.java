package com.onlinevalidator.controller;

import com.onlinevalidator.dto.ValidationAssert;
import com.onlinevalidator.dto.ValidationReport;
import com.onlinevalidator.model.OvTipoDocumento;
import com.onlinevalidator.model.enumerator.NomeTipoDocumentoEnum;
import com.onlinevalidator.repository.OvTipoDocumentoJpaRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.servlet.http.HttpSession;
import java.io.InputStream;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.fileUpload;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author Manuel Gozzi
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:/bean-servlet-test.xml")
@WebAppConfiguration
public class ValidatorControllerTest extends AbstractControllerTest {

	@Autowired
	private ValidatorController validatorController;

	@Autowired
	private OvTipoDocumentoJpaRepository tipoDocumentoRepository;

	@Override
	protected ValidatorController getController() {
		return validatorController;
	}

	@Test
	public void validazione() {

		OvTipoDocumento documentoDiTrasporto = tipoDocumentoRepository.findByNmNome(NomeTipoDocumentoEnum.DOCUMENTO_DI_TRASPORTO);
		OvTipoDocumento ordine = tipoDocumentoRepository.findByNmNome(NomeTipoDocumentoEnum.ORDINE);

		try (InputStream ddtOk = ValidatorControllerTest.class.getClassLoader().getResourceAsStream("test-case/validator-service/ddt-ok.xml");
			 InputStream ddtFatal = ValidatorControllerTest.class.getClassLoader().getResourceAsStream("test-case/validator-service/ddt-fatal.xml");
			 InputStream ordineOk = ValidatorControllerTest.class.getClassLoader().getResourceAsStream("test-case/validator-service/ordine-ok.xml");
			 InputStream ordineFatal = ValidatorControllerTest.class.getClassLoader().getResourceAsStream("test-case/validator-service/ordine-fatal.xml")) {

			assertNotNull(ddtOk);
			assertNotNull(ddtFatal);
			assertNotNull(ordineOk);
			assertNotNull(ordineFatal);

			// Test di validazione da controller DDT OK
			MockMultipartFile multipartFile = new MockMultipartFile(
					"file",
					ddtOk
			);
			HttpSession httpSession = this.mockMvc.perform(
					fileUpload("/uploadFile")
							.file(multipartFile)
							.contentType(MediaType.MULTIPART_FORM_DATA)
							.param("id", documentoDiTrasporto.getIdTipoDocumento() + ""))
					.andExpect(status().isOk())
					.andExpect(model().hasNoErrors())
					.andExpect(model().attributeExists("risultatoValidazione"))
					.andExpect(model().attributeExists("dataValidazione"))
					.andReturn()
					.getRequest()
					.getSession();
			assertNotNull(httpSession);

			ValidationReport risultatoValidazione = (ValidationReport) httpSession.getAttribute("risultatoValidazione");
			assertNotNull(risultatoValidazione);
			assertTrue(risultatoValidazione.isValido());

			// Test di validazione da controller DDT FATAL
			multipartFile = new MockMultipartFile(
					"file",
					ddtFatal
			);
			httpSession = this.mockMvc.perform(
					fileUpload("/uploadFile")
							.file(multipartFile)
							.contentType(MediaType.MULTIPART_FORM_DATA)
							.param("id", documentoDiTrasporto.getIdTipoDocumento() + ""))
					.andExpect(status().isOk())
					.andExpect(model().hasNoErrors())
					.andExpect(model().attributeExists("risultatoValidazione"))
					.andExpect(model().attributeExists("dataValidazione"))
					.andReturn()
					.getRequest()
					.getSession();
			assertNotNull(httpSession);

			risultatoValidazione = (ValidationReport) httpSession.getAttribute("risultatoValidazione");
			assertNotNull(risultatoValidazione);
			assertFalse(risultatoValidazione.isValido());

			// Test di validazione da controller Ordine OK
			multipartFile = new MockMultipartFile(
					"file",
					ordineOk
			);
			httpSession = this.mockMvc.perform(
					fileUpload("/uploadFile")
							.file(multipartFile)
							.contentType(MediaType.MULTIPART_FORM_DATA)
							.param("id", ordine.getIdTipoDocumento() + ""))
					.andExpect(status().isOk())
					.andExpect(model().hasNoErrors())
					.andExpect(model().attributeExists("risultatoValidazione"))
					.andExpect(model().attributeExists("dataValidazione"))
					.andReturn()
					.getRequest()
					.getSession();
			assertNotNull(httpSession);

			risultatoValidazione = (ValidationReport) httpSession.getAttribute("risultatoValidazione");
			assertNotNull(risultatoValidazione);
			assertTrue(risultatoValidazione.isValido());

			// Test di validazione da controller Ordine FATAL
			multipartFile = new MockMultipartFile(
					"file",
					ordineFatal
			);
			httpSession = this.mockMvc.perform(
					fileUpload("/uploadFile")
							.file(multipartFile)
							.contentType(MediaType.MULTIPART_FORM_DATA)
							.param("id", ordine.getIdTipoDocumento() + ""))
					.andExpect(status().isOk())
					.andExpect(model().hasNoErrors())
					.andExpect(model().attributeExists("risultatoValidazione"))
					.andExpect(model().attributeExists("dataValidazione"))
					.andReturn()
					.getRequest()
					.getSession();
			assertNotNull(httpSession);

			risultatoValidazione = (ValidationReport) httpSession.getAttribute("risultatoValidazione");
			assertNotNull(risultatoValidazione);
			assertFalse(risultatoValidazione.isValido());
		} catch (Exception e) {

			fail(e.getMessage());
		}
	}

	@Test
	public void esportaRisultato() {

		ValidationReport fatalReport = new ValidationReport();
		fatalReport.setDescrizioneErroreXsd(null);

		ValidationAssert fatalAssert = new ValidationAssert("test", "location", "testo", "fatal");
		ValidationAssert warningAssert = new ValidationAssert("test", "location", "testo", "warning");
		fatalReport.aggiungiDettaglio(fatalAssert);
		fatalReport.aggiungiDettaglio(warningAssert);

		try {

			MockHttpSession mockHttpSession = new MockHttpSession();
			mockHttpSession.setAttribute("risultatoValidazione", fatalReport);

			this.mockMvc.perform(
					get("/esportaRisultato")
							.session(mockHttpSession)
							.param("tipoRendering", "PDF")
			).andExpect(status().isOk());

			mockHttpSession = new MockHttpSession();
			mockHttpSession.setAttribute("risultatoValidazione", "XML");

			this.mockMvc.perform(
					get("/esportaRisultato")
							.session(mockHttpSession)
							.param("tipoRendering", "XML")
			).andExpect(status().isOk());


			ValidationReport validationReport = new ValidationReport();

			mockHttpSession = new MockHttpSession();
			mockHttpSession.setAttribute("risultatoValidazione", validationReport);

			this.mockMvc.perform(
					get("/esportaRisultato")
							.session(mockHttpSession)
							.param("tipoRendering", "XML")
			).andExpect(status().isOk());

			mockHttpSession = new MockHttpSession();
			mockHttpSession.setAttribute("risultatoValidazione", validationReport);

			this.mockMvc.perform(
					get("/esportaRisultato")
							.session(mockHttpSession)
							.param("tipoRendering", "XML")
			).andExpect(status().isOk());
		} catch (Exception e) {

			fail(e.getMessage());
		}


	}
}
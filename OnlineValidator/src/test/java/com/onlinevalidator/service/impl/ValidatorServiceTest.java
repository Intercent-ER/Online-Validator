package com.onlinevalidator.service.impl;

import com.onlinevalidator.dto.ValidationReport;
import com.onlinevalidator.model.OvRappresentazione;
import com.onlinevalidator.model.OvTipoDocumento;
import com.onlinevalidator.model.OvValidatore;
import com.onlinevalidator.model.enumerator.NomeTipoDocumentoEnum;
import com.onlinevalidator.model.enumerator.TipoFileEnum;
import com.onlinevalidator.repository.OvRappresentazioneJpaRepository;
import com.onlinevalidator.repository.OvTipoDocumentoJpaRepository;
import com.onlinevalidator.service.ValidatorServiceInterface;
import org.apache.commons.io.IOUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.spy;

/**
 * @author Manuel Gozzi
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:/bean-servlet-test.xml")
public class ValidatorServiceTest {

	@Autowired
	private ValidatorServiceInterface validatorService;

	@Autowired
	private OvTipoDocumentoJpaRepository tipoDocumentoJpaRepository;

	@Autowired
	private OvRappresentazioneJpaRepository rappresentazioneJpaRepository;

	@Test
	public void filtraTuttiITipiDocumento() {

		List<OvTipoDocumento> ovTipoDocumentoList = validatorService.filtraTuttiITipiDocumento();
		assertNotNull(ovTipoDocumentoList);
		assertEquals(2, ovTipoDocumentoList.size());
		assertTrue(
				ovTipoDocumentoList.stream()
						.anyMatch(ov -> ov.getName().equals(NomeTipoDocumentoEnum.DOCUMENTO_DI_TRASPORTO))
		);
		assertTrue(
				ovTipoDocumentoList.stream()
						.anyMatch(ov -> ov.getName().equals(NomeTipoDocumentoEnum.ORDINE))
		);
	}

	@Test
	public void getOvTipoDocumentoById() {

		OvTipoDocumento ordine = tipoDocumentoJpaRepository.findByNmNome(NomeTipoDocumentoEnum.ORDINE);
		OvTipoDocumento ordine_1 = validatorService.getOvTipoDocumentoById(ordine.getIdTipoDocumento());
		assertNotNull(ordine);
		assertNotNull(ordine_1);
		assertEquals(ordine.getIdTipoDocumento(), ordine_1.getIdTipoDocumento());
		assertEquals(ordine.getName(), ordine_1.getName());
		assertNotNull(ordine.getRappresentazione());
		assertNotNull(ordine_1.getRappresentazione());
//		assertEquals(ordine.getRappresentazione().size(), ordine_1.getRappresentazione().size());

		OvTipoDocumento ddt = tipoDocumentoJpaRepository.findByNmNome(NomeTipoDocumentoEnum.DOCUMENTO_DI_TRASPORTO);
		OvTipoDocumento ddt_1 = validatorService.getOvTipoDocumentoById(ddt.getIdTipoDocumento());
		assertNotNull(ddt);
		assertNotNull(ddt_1);
		assertEquals(ddt.getIdTipoDocumento(), ddt_1.getIdTipoDocumento());
		assertEquals(ddt.getName(), ddt_1.getName());
		assertNotNull(ddt.getRappresentazione());
		assertNotNull(ddt_1.getRappresentazione());
//		assertEquals(ddt.getRappresentazione().size(), ddt_1.getRappresentazione().size());
	}

	@Test
	public void filtraValidatore() {

		OvTipoDocumento ordine = tipoDocumentoJpaRepository.findByNmNome(NomeTipoDocumentoEnum.ORDINE);
		List<OvRappresentazione> rappresentazioneOrdine = rappresentazioneJpaRepository.findByOvTipoDocumento(ordine);
		OvValidatore validatoreOrdineXsd = validatorService.filtraValidatore(rappresentazioneOrdine.get(0), TipoFileEnum.XSD);
		assertNotNull(validatoreOrdineXsd);
		assertNotNull(validatoreOrdineXsd.getBlFile());

		OvValidatore validatoreOrdineXsl = validatorService.filtraValidatore(rappresentazioneOrdine.get(0), TipoFileEnum.SCHEMATRON);
		assertNotNull(validatoreOrdineXsl);
		assertNotNull(validatoreOrdineXsl.getBlFile());

		OvTipoDocumento ddt = tipoDocumentoJpaRepository.findByNmNome(NomeTipoDocumentoEnum.DOCUMENTO_DI_TRASPORTO);
		List<OvRappresentazione> rappresentazioneDdt = rappresentazioneJpaRepository.findByOvTipoDocumento(ordine);
		OvValidatore validatoreDdtXsd = validatorService.filtraValidatore(rappresentazioneDdt.get(0), TipoFileEnum.XSD);
		assertNotNull(validatoreDdtXsd);
		assertNotNull(validatoreDdtXsd.getBlFile());

		OvValidatore validatoreDdtXsl = validatorService.filtraValidatore(rappresentazioneDdt.get(0), TipoFileEnum.SCHEMATRON);
		assertNotNull(validatoreDdtXsl);
		assertNotNull(validatoreDdtXsl.getBlFile());

		try {

			validatorService.filtraValidatore(rappresentazioneDdt.get(0), null);
		} catch (Exception e) {

			assertTrue(e instanceof NullPointerException);
			assertEquals("Tipo file enum null", e.getMessage());
		}

		try {

			validatorService.filtraValidatore(null, TipoFileEnum.SCHEMATRON);
		} catch (Exception e) {

			assertTrue(e instanceof NullPointerException);
			assertEquals("Tipo documento null", e.getMessage());
		}

		try {

			validatorService.filtraValidatore(null, null);
		} catch (Exception e) {

			assertTrue(e instanceof NullPointerException);
			assertEquals("Tipo documento null", e.getMessage());
		}

		OvRappresentazione ovRappresentazione = new OvRappresentazione();
		OvValidatore validatore = validatorService.filtraValidatore(ovRappresentazione, TipoFileEnum.SCHEMATRON);
		assertNull(validatore);
	}

	@Test
	public void effettuaValidazione() {

		// TODO: aggiornare metodo
		ValidatorServiceInterface validatorServiceSpy = spy(this.validatorService);

		try {
			validatorServiceSpy.effettuaValidazione(null, null);
		} catch (Exception e) {

			assertTrue(e instanceof NullPointerException);
			assertEquals("Impossibile validare un documento null", e.getMessage());
		}

		try {
			validatorServiceSpy.effettuaValidazione("erroreSimulato".getBytes(StandardCharsets.UTF_8), null);
		} catch (Exception e) {

			assertTrue(e instanceof NullPointerException);
			assertEquals("Impossibile validare un documento senza averne specificato la sua rappresentazione", e.getMessage());
		}

		try (InputStream inputStream = ValidatorServiceTest.class.getClassLoader().getResourceAsStream("test-case/validator-service/ddt-ok.xml")) {

			assertNotNull(inputStream);
			byte[] byteArray = IOUtils.toByteArray(inputStream);
			ValidationReport validationReport = validatorServiceSpy.effettuaValidazione(byteArray, tipoDocumentoJpaRepository.findByNmNome(NomeTipoDocumentoEnum.DOCUMENTO_DI_TRASPORTO).getRappresentazione().get(0));
			assertNotNull(validationReport);
			assertTrue(validationReport.isValido());
			assertNotNull(validationReport.getErroriDiValidazione());
			assertTrue(validationReport.getErroriDiValidazione().isEmpty());

		} catch (IOException e) {

			fail(
					String.format(
							"Errore di configurazione del test unitario: %s",
							e.getMessage()
					)
			);
		} catch (Exception e) {

			fail(e.getMessage());
		}

		try (InputStream inputStream = ValidatorServiceTest.class.getClassLoader().getResourceAsStream("test-case/validator-service/ddt-fatal.xml")) {

			assertNotNull(inputStream);
			byte[] byteArray = IOUtils.toByteArray(inputStream);
			ValidationReport validationReport = validatorServiceSpy.effettuaValidazione(byteArray, tipoDocumentoJpaRepository.findByNmNome(NomeTipoDocumentoEnum.DOCUMENTO_DI_TRASPORTO).getRappresentazione().get(0));
			assertNotNull(validationReport);
			assertFalse(validationReport.isValido());
			assertNotNull(validationReport.getErroriDiValidazione());
			assertFalse(validationReport.getErroriDiValidazione().isEmpty());
			assertEquals(1, validationReport.getErroriDiValidazione().size());
		} catch (IOException e) {

			fail(
					String.format(
							"Errore di configurazione del test unitario: %s",
							e.getMessage()
					)
			);
		} catch (Exception e) {

			fail(e.getMessage());
		}

		try (InputStream inputStream = ValidatorServiceTest.class.getClassLoader().getResourceAsStream("test-case/validator-service/ordine-ok.xml")) {

			assertNotNull(inputStream);
			byte[] byteArray = IOUtils.toByteArray(inputStream);
			ValidationReport validationReport = validatorServiceSpy.effettuaValidazione(byteArray, tipoDocumentoJpaRepository.findByNmNome(NomeTipoDocumentoEnum.ORDINE).getRappresentazione().get(0));
			assertNotNull(validationReport);
			assertTrue(validationReport.isValido());
			assertNotNull(validationReport.getErroriDiValidazione());
			assertTrue(validationReport.getErroriDiValidazione().isEmpty());
		} catch (IOException e) {

			fail(
					String.format(
							"Errore di configurazione del test unitario: %s",
							e.getMessage()
					)
			);
		} catch (Exception e) {

			fail(e.getMessage());
		}

		try (InputStream inputStream = ValidatorServiceTest.class.getClassLoader().getResourceAsStream("test-case/validator-service/ordine-fatal.xml")) {

			assertNotNull(inputStream);
			byte[] byteArray = IOUtils.toByteArray(inputStream);
			ValidationReport validationReport = validatorServiceSpy.effettuaValidazione(byteArray, tipoDocumentoJpaRepository.findByNmNome(NomeTipoDocumentoEnum.ORDINE).getRappresentazione().get(0));
			assertNotNull(validationReport);
			assertFalse(validationReport.isValido());
			assertNotNull(validationReport.getErroriDiValidazione());
			assertFalse(validationReport.getErroriDiValidazione().isEmpty());
			assertEquals(2, validationReport.getErroriDiValidazione().size());
		} catch (IOException e) {

			fail(
					String.format(
							"Errore di configurazione del test unitario: %s",
							e.getMessage()
					)
			);
		} catch (Exception e) {

			fail(e.getMessage());
		}
	}

}

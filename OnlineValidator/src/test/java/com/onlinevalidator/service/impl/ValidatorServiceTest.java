package com.onlinevalidator.service.impl;

import com.onlinevalidator.model.OvTipoDocumento;
import com.onlinevalidator.model.enumerator.NomeTipoDocumentoEnum;
import com.onlinevalidator.repository.OvTipoDocumentoJpaRepository;
import com.onlinevalidator.service.ValidatorServiceInterface;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.junit.Assert.*;

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
		assertEquals(ordine.getRappresentazione().size(), ordine_1.getRappresentazione().size());

		OvTipoDocumento ddt = tipoDocumentoJpaRepository.findByNmNome(NomeTipoDocumentoEnum.DOCUMENTO_DI_TRASPORTO);
		OvTipoDocumento ddt_1 = validatorService.getOvTipoDocumentoById(ddt.getIdTipoDocumento());
		assertNotNull(ddt);
		assertNotNull(ddt_1);
		assertEquals(ddt.getIdTipoDocumento(), ddt_1.getIdTipoDocumento());
		assertEquals(ddt.getName(), ddt_1.getName());
		assertNotNull(ddt.getRappresentazione());
		assertNotNull(ddt_1.getRappresentazione());
		assertEquals(ddt.getRappresentazione().size(), ddt_1.getRappresentazione().size());
	}

	@Test
	public void filtraValidatore() {

		// TODO: riscrivere metodo
//		OvTipoDocumento ordine = tipoDocumentoJpaRepository.findByNmNome(NomeTipoDocumentoEnum.ORDINE);
//		OvValidatore validatoreOrdineXsd = validatorService.filtraValidatore(ordine, TipoFileEnum.XSD);
//		assertNotNull(validatoreOrdineXsd);
//		assertNotNull(validatoreOrdineXsd.getBlFile());
//
//		OvValidatore validatoreOrdineXsl = validatorService.filtraValidatore(ordine, TipoFileEnum.SCHEMATRON);
//		assertNotNull(validatoreOrdineXsl);
//		assertNotNull(validatoreOrdineXsl.getBlFile());
//
//		OvTipoDocumento ddt = tipoDocumentoJpaRepository.findByNmNome(NomeTipoDocumentoEnum.DOCUMENTO_DI_TRASPORTO);
//		OvValidatore validatoreDdtXsd = validatorService.filtraValidatore(ddt, TipoFileEnum.XSD);
//		assertNotNull(validatoreDdtXsd);
//		assertNotNull(validatoreDdtXsd.getBlFile());
//
//		OvValidatore validatoreDdtXsl = validatorService.filtraValidatore(ddt, TipoFileEnum.SCHEMATRON);
//		assertNotNull(validatoreDdtXsl);
//		assertNotNull(validatoreDdtXsl.getBlFile());
//
//		try {
//
//			validatorService.filtraValidatore(ddt, null);
//		} catch (Exception e) {
//
//			assertTrue(e instanceof NullPointerException);
//			assertEquals("Tipo file enum null", e.getMessage());
//		}
//
//		try {
//
//			validatorService.filtraValidatore(null, TipoFileEnum.SCHEMATRON);
//		} catch (Exception e) {
//
//			assertTrue(e instanceof NullPointerException);
//			assertEquals("Tipo documento null", e.getMessage());
//		}
//
//		try {
//
//			validatorService.filtraValidatore(null, null);
//		} catch (Exception e) {
//
//			assertTrue(e instanceof NullPointerException);
//			assertEquals("Tipo documento null", e.getMessage());
//		}
//
//		OvTipoDocumento tipoDocumento = new OvTipoDocumento();
//		tipoDocumento.setValidatori(Collections.emptyList());
//		OvValidatore validatore = validatorService.filtraValidatore(tipoDocumento, TipoFileEnum.SCHEMATRON);
//		assertNull(validatore);
	}

	@Test
	public void effettuaValidazione() {

		// TODO: aggiornare metodo
//		ValidatorServiceInterface validatorServiceSpy = spy(this.validatorService);
//
//		try {
//			validatorServiceSpy.effettuaValidazione(null, null);
//		} catch (Exception e) {
//
//			assertTrue(e instanceof NullPointerException);
//			assertEquals("Impossibile validare un documento null", e.getMessage());
//		}
//
//		try {
//			validatorServiceSpy.effettuaValidazione("erroreSimulato".getBytes(StandardCharsets.UTF_8), null);
//		} catch (Exception e) {
//
//			assertTrue(e instanceof NullPointerException);
//			assertEquals("Impossibile validare un documento senza averne specificato il tipo", e.getMessage());
//		}
//
//		try (InputStream inputStream = ValidatorServiceTest.class.getClassLoader().getResourceAsStream("test-case/validator-service/ddt-ok.xml")) {
//
//			assertNotNull(inputStream);
//			byte[] byteArray = IOUtils.toByteArray(inputStream);
//			ValidationReport validationReport = validatorServiceSpy.effettuaValidazione(byteArray, tipoDocumentoJpaRepository.findByNmNome(NomeTipoDocumentoEnum.DOCUMENTO_DI_TRASPORTO));
//			assertNotNull(validationReport);
//			assertTrue(validationReport.isValido());
//			assertNotNull(validationReport.getErroriDiValidazione());
//			assertTrue(validationReport.getErroriDiValidazione().isEmpty());
//
//		} catch (IOException e) {
//
//			fail(
//					String.format(
//							"Errore di configurazione del test unitario: %s",
//							e.getMessage()
//					)
//			);
//		} catch (Exception e) {
//
//			fail(e.getMessage());
//		}
//
//		try (InputStream inputStream = ValidatorServiceTest.class.getClassLoader().getResourceAsStream("test-case/validator-service/ddt-fatal.xml")) {
//
//			assertNotNull(inputStream);
//			byte[] byteArray = IOUtils.toByteArray(inputStream);
//			ValidationReport validationReport = validatorServiceSpy.effettuaValidazione(byteArray, tipoDocumentoJpaRepository.findByNmNome(NomeTipoDocumentoEnum.DOCUMENTO_DI_TRASPORTO));
//			assertNotNull(validationReport);
//			assertFalse(validationReport.isValido());
//			assertNotNull(validationReport.getErroriDiValidazione());
//			assertFalse(validationReport.getErroriDiValidazione().isEmpty());
//			assertEquals(1, validationReport.getErroriDiValidazione().size());
//		} catch (IOException e) {
//
//			fail(
//					String.format(
//							"Errore di configurazione del test unitario: %s",
//							e.getMessage()
//					)
//			);
//		} catch (Exception e) {
//
//			fail(e.getMessage());
//		}
//
//		try (InputStream inputStream = ValidatorServiceTest.class.getClassLoader().getResourceAsStream("test-case/validator-service/ordine-ok.xml")) {
//
//			assertNotNull(inputStream);
//			byte[] byteArray = IOUtils.toByteArray(inputStream);
//			ValidationReport validationReport = validatorServiceSpy.effettuaValidazione(byteArray, tipoDocumentoJpaRepository.findByNmNome(NomeTipoDocumentoEnum.ORDINE));
//			assertNotNull(validationReport);
//			assertTrue(validationReport.isValido());
//			assertNotNull(validationReport.getErroriDiValidazione());
//			assertTrue(validationReport.getErroriDiValidazione().isEmpty());
//		} catch (IOException e) {
//
//			fail(
//					String.format(
//							"Errore di configurazione del test unitario: %s",
//							e.getMessage()
//					)
//			);
//		} catch (Exception e) {
//
//			fail(e.getMessage());
//		}
//
//		try (InputStream inputStream = ValidatorServiceTest.class.getClassLoader().getResourceAsStream("test-case/validator-service/ordine-fatal.xml")) {
//
//			assertNotNull(inputStream);
//			byte[] byteArray = IOUtils.toByteArray(inputStream);
//			ValidationReport validationReport = validatorServiceSpy.effettuaValidazione(byteArray, tipoDocumentoJpaRepository.findByNmNome(NomeTipoDocumentoEnum.ORDINE));
//			assertNotNull(validationReport);
//			assertFalse(validationReport.isValido());
//			assertNotNull(validationReport.getErroriDiValidazione());
//			assertFalse(validationReport.getErroriDiValidazione().isEmpty());
//			assertEquals(2, validationReport.getErroriDiValidazione().size());
//		} catch (IOException e) {
//
//			fail(
//					String.format(
//							"Errore di configurazione del test unitario: %s",
//							e.getMessage()
//					)
//			);
//		} catch (Exception e) {
//
//			fail(e.getMessage());
//		}
	}

}

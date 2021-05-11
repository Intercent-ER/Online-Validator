package com.onlinevalidator.model.enumerator;

import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * @author Manuel Gozzi
 */
public class NomeTipoDocumentoEnumTest {

	@Test
	public void testToString() {

		Arrays.stream(NomeTipoDocumentoEnum.values())
				.forEach(
						nomeTipoDocumentoEnum -> assertEquals(nomeTipoDocumentoEnum.name(), nomeTipoDocumentoEnum.toString())
				);
	}

	@Test
	public void getReadableValue() {

		assertEquals("Ordine", NomeTipoDocumentoEnum.ORDINE.getReadableValue());
		assertEquals("Documento di trasporto", NomeTipoDocumentoEnum.DOCUMENTO_DI_TRASPORTO.getReadableValue());
		assertEquals("Fattura", NomeTipoDocumentoEnum.FATTURA.getReadableValue());
		assertEquals("Ordine Preconcordato", NomeTipoDocumentoEnum.ORDINE_PRECONCORDATO.getReadableValue());
		assertEquals("Risposta all'Ordine", NomeTipoDocumentoEnum.RISPOSTA_ALL_ORDINE.getReadableValue());
	}

	@Test
	public void values() {

		NomeTipoDocumentoEnum[] values = NomeTipoDocumentoEnum.values();

		assertNotNull(values);
		assertEquals(5, values.length);
	}
}
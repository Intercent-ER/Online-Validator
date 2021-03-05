package com.onlinevalidator.model.enumerator;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * @author Manuel Gozzi
 */
public class NomeTipoDocumentoEnumTest {

	@Test
	public void testToString() {

		NomeTipoDocumentoEnum testCase = NomeTipoDocumentoEnum.ORDINE;

		String name = testCase.name();
		String toString = testCase.toString();

		assertEquals(name, toString);

	}

	@Test
	public void getReadableValue() {

		assertEquals("Ordine", NomeTipoDocumentoEnum.ORDINE.getReadableValue());
		assertEquals("Documento di trasporto", NomeTipoDocumentoEnum.DOCUMENTO_DI_TRASPORTO.getReadableValue());

	}

	@Test
	public void values() {

		NomeTipoDocumentoEnum[] values = NomeTipoDocumentoEnum.values();

		assertNotNull(values);
		assertEquals(3, values.length);

	}
}
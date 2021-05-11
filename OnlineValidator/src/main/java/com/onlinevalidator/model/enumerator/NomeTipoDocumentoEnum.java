package com.onlinevalidator.model.enumerator;

/**
 * @author Manuel Gozzi
 */
public enum NomeTipoDocumentoEnum {

	ORDINE("Ordine"), DOCUMENTO_DI_TRASPORTO("Documento di trasporto"), FATTURA("Fattura"),
	ORDINE_PRECONCORDATO("Ordine Preconcordato"), RISPOSTA_ALL_ORDINE("Risposta all'Ordine");

	private final String readableValue;

	NomeTipoDocumentoEnum(String readableValue) {
		this.readableValue = readableValue;
	}

	@Override
	public String toString() {
		return name();
	}

	public String getReadableValue() {
		return readableValue;
	}
}

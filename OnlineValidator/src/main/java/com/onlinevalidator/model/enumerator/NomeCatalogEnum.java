package com.onlinevalidator.model.enumerator;

/**
 * @author Manuel Gozzi
 */
public enum NomeCatalogEnum {

	UnitOfMeasureCode, PaymentMeansCode, FormatoAttachment, CategoriaImposte, TipoDocumento, VATSchemes, ProvinceItaliane,
	TipoFattura, TipoParcella, OrderTypeCode, HandlingCode;

	@Override
	public String toString() {
		return name();
	}

}

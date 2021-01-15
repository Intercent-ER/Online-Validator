package com.onlinevalidator.model.enumerator;

/**
 * @author Manuel Gozzi
 */
public enum TipoFileEnum {

	XSD, SCHEMATRON;

	@Override
	public String toString() {
		return name();
	}

}

package com.onlinevalidator.util;

/**
 * @author Manuel Gozzi
 */
public interface ApplicationConstant {

	/**
	 * Costanti utilizzate nella logica di business.
	 */
	String G_RECAPTCHA_RESPONSE_HTTP_HEADER_KEY = "g-recaptcha-response";
	String PATTERN_SIMPLE_DATE_FORMAT = "dd/MM/yyyy HH:mm:ss";

	/**
	 * Costanti utilizzate nel contesto MVC (controller e .jsp).
	 */
	String INDEX_JSP_AJAX_ID_TIPO_DOCUMENTO = "idTipoDocumento";
	String INDEX_JSP_ELENCO_TIPI_DOCUMENTO = "tipoDocumento";
	String INDEX_JSP_RECAPTCHA_SITE_KEY = "gRecaptchaSiteKey";
	String INDEX_JSP_UPLOAD_FILE_FORM = "uploadFileForm";
	String RESULT_JSP_TIPO_RENDERING = "tipoRendering";
	String RESULT_JSP_DATA_VALIDAZIONE = "dataValidazione";
	String RESULT_JSP_RISULTATO_VALIDAZIONE = "risultatoValidazione";
}

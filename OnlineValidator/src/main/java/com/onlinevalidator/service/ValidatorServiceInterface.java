package com.onlinevalidator.service;

import com.onlinevalidator.dto.ValidationReport;
import com.onlinevalidator.model.OvRappresentazione;
import com.onlinevalidator.model.OvTipoDocumento;
import com.onlinevalidator.model.OvValidatore;
import com.onlinevalidator.model.enumerator.TipoFileEnum;
import com.onlinevalidator.util.ApplicationLogger;

import java.util.List;

/**
 * @author Manuel Gozzi
 */
public interface ValidatorServiceInterface extends ApplicationLogger {

	String NET_SF_SAXON_TRANSFORMER_FACTORY_IMPL = "net.sf.saxon.TransformerFactoryImpl";
	String OWASP_FEATURES_TO_DISALLOW_DOCTYPE = "http://apache.org/xml/features/disallow-doctype-decl";
	String OWASP_FEATURES_TO_DISALLOW_XXE_ENT = "http://xml.org/sax/features/external-general-entities";
	String OWASP_FEATURES_TO_DISALLOW_XXE_PAR = "http://xml.org/sax/features/external-parameter-entities";

	/**
	 * Recupera tutti i tipi documento.
	 *
	 * @return tutti i tipi documento presenti a sistema
	 */
	List<OvTipoDocumento> filtraTuttiITipiDocumento();
        
        List<OvRappresentazione> filtraRappresentazione();

	/**
	 * Recupera un tipo documento partendo dal suo id.
	 *
	 * @param idTipoDocumento è l'identificativo del tipo documento da recuperare
	 * @return l'istanza del tipo documento
	 */
	OvTipoDocumento getOvTipoDocumentoById(int idTipoDocumento);
        
        OvRappresentazione getOvRappresentazioneById(int idRappresentazione);

	/**
	 * Dato un tipo documento e un tipo di file, recupera il corrispondente validatore.
	 *
	 * @param rappresentazione è il tipo del documento
	 * @param tipoFileEnum  è il tipo di file {@link TipoFileEnum}
	 * @return il validatore corrispondente
	 */
	OvValidatore filtraValidatore(OvRappresentazione rappresentazione, TipoFileEnum tipoFileEnum);

	/**
	 * Dato uno specifico documento, se ne effettua la validazione, incapsulando il risultato all'interno di un'istanza
	 * dell'oggetto ValidationReport.
	 *
	 * @param documento     è il documento su cui occorre applicare la validazione XSLT (in byte[])
	 * @param rappresentazione è il tipo del documento che occorre validare
	 * @return il risultato di validazione
	 * @author Manuel Gozzi
	 */
	ValidationReport effettuaValidazione(byte[] documento, OvRappresentazione rappresentazione);

}

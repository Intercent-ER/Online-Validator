package com.onlinevalidator.service;

import com.onlinevalidator.model.TipoFileEnum;
import com.onlinevalidator.model.Tipodocumento;
import com.onlinevalidator.model.Validatore;
import com.onlinevalidator.pojo.ValidationReport;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

@Service
public interface ValidatorServiceInterface {

	String OWASP_FEATURES_TO_DISALLOW_XXE_ENT = "http://xml.org/sax/features/external-general-entities";
	String OWASP_FEATURES_TO_DISALLOW_XXE_PAR = "http://xml.org/sax/features/external-parameter-entities";
	String OWASP_FEATURES_TO_DISALLOW_DOCTYPE = "http://apache.org/xml/features/disallow-doctype-decl";

	List<Tipodocumento> getAllEntity() throws SQLException;

	Tipodocumento getEntity(int id);

	Tipodocumento getValidatoreByTipoDocumento(int idTipoDocumento);

	Tipodocumento getTipodocumentoById(int idTipoDocumento);

	Validatore filtraValidatore(Tipodocumento tipodocumento, TipoFileEnum tipoFileEnum);

	/**
	 * Dato uno specifico documento, se ne effettua la validazione, incapsulando il risultato all'interno di un'istanza
	 * dell'oggetto ValidationReport.
	 *
	 * @param documento     è il documento su cui occorre applicare la validazione XSLT (in byte[])
	 * @param tipodocumento è il tipo del documento che occorre validare
	 * @return il risultato di validazione
	 * @author Manuel Gozzi
	 */
	ValidationReport effettuaValidazione(byte[] documento, Tipodocumento tipodocumento);

}

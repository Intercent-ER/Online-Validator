package com.onlinevalidator.services;

import com.onlinevalidator.model.TipoFileEnum;
import com.onlinevalidator.model.Tipodocumento;
import com.onlinevalidator.model.Validatore;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

@Service
public interface ValidatorServiceInterface {

	public List<Tipodocumento> getAllEntity() throws SQLException;

	public Tipodocumento getEntity(int id);

	public Tipodocumento getTipodocumentoById(int idTipoDocumento);

	public Validatore filtraValidatore(Tipodocumento tipodocumento, TipoFileEnum tipoFileEnum);
}

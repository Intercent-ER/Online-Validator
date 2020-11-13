package com.onlinevalidator.utils;

import java.sql.SQLException;
import java.util.List;

import org.springframework.stereotype.Service;

import com.onlinevalidator.model.Tipodocumento;
import com.onlinevalidator.model.Validatore;

@Service
public interface ValidatorServiceInterface{
	
	public List<Tipodocumento> getAllEntity() throws SQLException;
	
	public Tipodocumento getEntity(int id);
	
	public Tipodocumento getValidatoreByTipoDocumento(int idTipoDocumento);

}

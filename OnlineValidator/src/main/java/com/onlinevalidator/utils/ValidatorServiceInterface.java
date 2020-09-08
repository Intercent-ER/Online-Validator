package com.onlinevalidator.utils;

import java.sql.SQLException;
import java.util.List;

import org.springframework.stereotype.Service;

import com.onlinevalidator.model.Tipodocumento;
import com.onlinevalidator.model.ValidatorEntity;
import com.onlinevalidator.model.Validatore;

@Service
public interface ValidatorServiceInterface{
	
	public List<Validatore> getAllEntity() throws SQLException;
	
	public Validatore getEntity(Long id);
	
	public Validatore getValidatoreByTipoDocumento(Long idTipoDocumento);

}

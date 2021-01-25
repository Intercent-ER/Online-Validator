package com.onlinevalidator.repository;

import com.onlinevalidator.model.OvTipoDocumento;
import com.onlinevalidator.model.enumerator.NomeTipoDocumentoEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OvTipoDocumentoJpaRepository extends JpaRepository<OvTipoDocumento, Integer> {

	OvTipoDocumento findByNmNome(NomeTipoDocumentoEnum nmNome);

}

package com.onlinevalidator.repository;

import com.onlinevalidator.model.OvCatalog;
import com.onlinevalidator.model.enumerator.NomeCatalogEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OvCatalogJpaRepository extends JpaRepository<OvCatalog, Integer> {

	OvCatalog findByNmNomeAndCdVersione(NomeCatalogEnum nmNome, String cdVersione);
}

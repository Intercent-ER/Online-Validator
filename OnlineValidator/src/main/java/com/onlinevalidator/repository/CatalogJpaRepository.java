package com.onlinevalidator.repository;

import com.onlinevalidator.model.OvCatalog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CatalogJpaRepository extends JpaRepository<OvCatalog, Integer> {

	@Query("select cat from OvCatalog cat where cat.nmNome = :nomeCatalog and cat.cdVersione = :versione")
	OvCatalog getOneBy(@Param("nomeCatalog") String nomeCatalog, @Param("versione") String versione);
}

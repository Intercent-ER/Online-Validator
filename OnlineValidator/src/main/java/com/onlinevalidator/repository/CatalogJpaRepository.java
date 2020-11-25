package com.onlinevalidator.repository;

import com.onlinevalidator.model.Catalog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CatalogJpaRepository extends JpaRepository<Catalog, Integer> {

	@Query("select cat from Catalog cat where cat.nome = :nomeCatalog and cat.versione = :versione")
	Catalog getOneBy(@Param("nomeCatalog") String nomeCatalog, @Param("versione") String versione);
}

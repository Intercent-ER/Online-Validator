package com.onlinevalidator.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.onlinevalidator.model.Catalog;

@Repository
public interface CatalogJpaRepository extends JpaRepository<Catalog, Integer> {

}

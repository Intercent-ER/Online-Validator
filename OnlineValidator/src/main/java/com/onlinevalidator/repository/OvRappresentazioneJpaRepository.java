/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.onlinevalidator.repository;

import com.onlinevalidator.model.OvRappresentazione;
import com.onlinevalidator.model.OvTipoDocumento;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author luca.bergonzoni
 */
public interface OvRappresentazioneJpaRepository extends JpaRepository<OvRappresentazione, Integer> {

	List<OvRappresentazione> findByOvTipoDocumento(OvTipoDocumento ovTipoDocumento);

}

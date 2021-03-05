package com.onlinevalidator.service.impl;

import com.onlinevalidator.dto.RappresentazioneViewer;
import com.onlinevalidator.model.OvRappresentazione;
import com.onlinevalidator.repository.OvRappresentazioneJpaRepository;
import com.onlinevalidator.service.RappresentazioneServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Manuel Gozzi
 */
@Service
public class RappresentazioneService implements RappresentazioneServiceInterface {

	@Autowired
	private OvRappresentazioneJpaRepository rappresentazioneJpaRepository;

	private List<OvRappresentazione> rappresentazioni;

	@PostConstruct
	public void init() {

		logInfo("Inizializzazione cache rappresentazioni");
		this.rappresentazioni = rappresentazioneJpaRepository.findAll();
	}

	@Override
	public List<RappresentazioneViewer> filtraRappresentazioniPerTipoDocumento(int idTipoDocumento) {

		if (CollectionUtils.isEmpty(rappresentazioni)) {
			return Collections.emptyList();
		}

		// Recupero dalla cache le rappresentazioni selezionate
		List<OvRappresentazione> ovRappresentazione = rappresentazioni.stream()
				.filter(r -> r.getOvTipoDocumento().getIdTipoDocumento() == idTipoDocumento)
				.collect(Collectors.toList());

		// Se non trovo corrispondenze, restituisco una lista vuota
		if (CollectionUtils.isEmpty(ovRappresentazione)) {
			logWarn("Nessuna rappresentazione trovata in relazione all'id tipo documento \"{}\"", idTipoDocumento);
			return Collections.emptyList();
		}

		// Converto il risultato della query in DTO preposti
		return getRappresentazioneViewers(ovRappresentazione);
	}

	/**
	 * Data una lista di {@link OvRappresentazione} creo la corrispondente lista di {@link RappresentazioneViewer}.
	 *
	 * @param listaRappresentazioni è la lista di rappresentazioni da convertire
	 * @return la lista risultante dalla conversione
	 */
	private List<RappresentazioneViewer> getRappresentazioneViewers(List<OvRappresentazione> listaRappresentazioni) {

		if (CollectionUtils.isEmpty(listaRappresentazioni)) {
			return Collections.emptyList();
		}

		List<RappresentazioneViewer> rappresentazioneViewers = new ArrayList<>();
		for (OvRappresentazione rappresentazione : listaRappresentazioni) {
			rappresentazioneViewers.add(convertIntoRappresentazioneViewer(rappresentazione));
		}
		return rappresentazioneViewers;
	}

	/**
	 * Converte un oggetto {@link OvRappresentazione} in oggetto {@link RappresentazioneViewer}.
	 *
	 * @param ovRappresentazione è la entity da convertire
	 * @return il DTO preposto
	 */
	private RappresentazioneViewer convertIntoRappresentazioneViewer(OvRappresentazione ovRappresentazione) {
		return new RappresentazioneViewer(ovRappresentazione.getIdRappresentazione(), ovRappresentazione.getDsNomeRappresentazione());
	}

}

package com.onlinevalidator.service.impl;

import com.onlinevalidator.model.OvCatalog;
import com.onlinevalidator.model.enumerator.NomeCatalogEnum;
import com.onlinevalidator.repository.OvCatalogJpaRepository;
import com.onlinevalidator.service.LocalServiceUriResolverInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.transform.Source;
import javax.xml.transform.TransformerException;
import javax.xml.transform.stream.StreamSource;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author Manuel Gozzi
 */
@Service
public class LocalServiceURIResolverService implements LocalServiceUriResolverInterface {

	private Map<String, OvCatalog> catalogMap = new HashMap<>();
	private Date refreshDate = new Date();

	@Autowired
	private OvCatalogJpaRepository catalogRepository;

	public static Map<String, String> splitQuery(String query) throws UnsupportedEncodingException {
		Map<String, String> query_pairs = new LinkedHashMap<>();
		String[] firstSplit = query.split("\\?");
		String[] pairs = firstSplit[1].split("&");
		for (String pair : pairs) {
			int idx = pair.indexOf("=");
			query_pairs.put(URLDecoder.decode(pair.substring(0, idx), "UTF-8"), URLDecoder.decode(pair.substring(idx + 1), "UTF-8"));
		}
		return query_pairs;
	}

	@Override
	public Source resolve(String href, String base) throws TransformerException {
		try {
			checkForRefresh();
			Map<String, String> parameters = splitQuery(href);
			OvCatalog catalog = getCatalog(parameters);
			InputStream is = new ByteArrayInputStream(
					catalog.getBlFileCatalog()
			);
			return new StreamSource(is);
		} catch (UnsupportedEncodingException e) {
			throw new TransformerException(e);
		}
	}

	private OvCatalog getCatalog(Map<String, String> parameters) {
		NomeCatalogEnum nomeCatalog = NomeCatalogEnum.valueOf(parameters.get("nomeCatalog"));
		String versione = parameters.get("versione");
		String index = nomeCatalog + "_" + versione;
		OvCatalog catalog = catalogMap.get(index);
		if (catalog == null) {
			catalog = catalogRepository.findByNmNomeAndCdVersione(nomeCatalog, versione);
			catalogMap.put(index, catalog);
		}
		return catalog;
	}

	private void checkForRefresh() {
		if (new Date().after(refreshDate)) {
			catalogMap = new HashMap<>();
			refreshDate = new Date();
		}
	}

}
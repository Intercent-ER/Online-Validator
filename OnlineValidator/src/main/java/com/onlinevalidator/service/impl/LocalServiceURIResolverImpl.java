package com.onlinevalidator.service.impl;

import com.onlinevalidator.service.LocalServiceUriResolverInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.xml.transform.Source;
import javax.xml.transform.TransformerException;
import javax.xml.transform.stream.StreamSource;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLDecoder;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author Manuel Gozzi
 */
@Service
public class LocalServiceURIResolverImpl implements LocalServiceUriResolverInterface {

	private Map<String, CdiCatalog> catalogMap = new HashMap<>();
	private Date refreshDate = new Date();
	private Long REFRESH_INTERVAL;

	@Autowired
	CdiCatalogRepository catalogRepository;

	@Autowired
	ConfigNotierService configNotierService;

	@PostConstruct
	private void init() {
	}

	public static Map<String, String> splitQuery(String query) throws UnsupportedEncodingException {
		Map<String, String> query_pairs = new LinkedHashMap<String, String>();
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
			CdiCatalog catalog = getCatalog(parameters);
			InputStream is = new ByteArrayInputStream(catalog.getBlFile().getBytes("UTF-8"));
			return new StreamSource(is);
		} catch (UnsupportedEncodingException e) {
			throw new TransformerException(e);
		}
	}

	private CdiCatalog getCatalog(Map<String, String> parameters) {
		String nomeCatalog = parameters.get("nomeCatalog");
		String versione = parameters.get("versione");
		String index = nomeCatalog + "_" + versione;
		CdiCatalog catalog = catalogMap.get(index);
		if (catalog == null) {
			catalog = catalogRepository.getOneBy(nomeCatalog, versione);
			catalogMap.put(index, catalog);
		}
		return catalog;
	}

	private void checkForRefresh() {
		if (new Date().after(refreshDate)) {
			catalogMap = new HashedMap<>();
			refreshDate = new Date(new Date().getTime() + REFRESH_INTERVAL);
		}
	}

	@Override
	public URI getUnsupportedURI() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getReason() {
		// TODO Auto-generated method stub
		return 0;
	}

}
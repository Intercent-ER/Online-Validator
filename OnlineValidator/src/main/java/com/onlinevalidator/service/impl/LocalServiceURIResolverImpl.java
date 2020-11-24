package com.onlinevalidator.service.impl;

import com.onlinevalidator.service.LocalServiceUriResolverInterface;
import org.springframework.stereotype.Service;

import javax.xml.transform.Source;
import javax.xml.transform.TransformerException;
import javax.xml.transform.stream.StreamSource;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author Manuel Gozzi
 */
@Service
public class LocalServiceURIResolverImpl implements LocalServiceUriResolverInterface {

	private Map<String, Object> catalogMap = new HashMap<>(); // TODO inserire mappa String, Catalogo (entity)
	private Date refreshDate = new Date();

	// @Autowired
	Object catalogRepository; // TODO inserire repository dei cataloghi

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
			Object catalog = getCatalog(parameters); // TODO: sostituire il tipo dato "Object" con l'effettivo Model che mappa il catalogo
			InputStream is = new ByteArrayInputStream(
					// catalog.getBlFile().getBytes(StandardCharsets.UTF_8.name())
					new String("questo è il file del catalogo").getBytes(StandardCharsets.UTF_8.name())
			);
			return new StreamSource(is);
		} catch (UnsupportedEncodingException e) {
			throw new TransformerException(e);
		}
	}

	// TODO: sostituire il tipo dato "Object" con l'effettivo Model che mappa il catalogo
	private Object getCatalog(Map<String, String> parameters) {
		String nomeCatalog = parameters.get("nomeCatalog");
		String versione = parameters.get("versione");
		String index = nomeCatalog + "_" + versione;
		Object catalog = catalogMap.get(index); // TODO: sostituire il tipo dato "Object" con l'effettivo Model che mappa il catalogo
		if (catalog == null) {
			// TODO: sostituire il "new Object()" con la query di recupero del catalogo Model che mappa il catalogo (esempio sotto)
			catalog = new Object();
			// catalogRepository.getOneBy(nomeCatalog, versione);
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
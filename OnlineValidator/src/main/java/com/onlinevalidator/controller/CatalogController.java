package com.onlinevalidator.controller;

import com.onlinevalidator.model.OvCatalog;
import com.onlinevalidator.model.enumerator.NomeCatalogEnum;
import com.onlinevalidator.repository.OvCatalogJpaRepository;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import static com.onlinevalidator.util.FileUtil.outputFile;

/**
 * @author Manuel Gozzi
 */
@Controller
public class CatalogController {

	public static final String CATALOG_BASE_URL = "catalog";

	@Autowired
	private OvCatalogJpaRepository catalogJpaRepository;

	@RequestMapping(value = "/" + CATALOG_BASE_URL, method = RequestMethod.GET)
	public void getCatalog(HttpServletResponse response, @RequestParam(value = "nomeCatalog") NomeCatalogEnum nomeCatalog,
						   @RequestParam(value = "versione") String versione) throws IOException {
		OvCatalog catalog = catalogJpaRepository.findByNmNomeAndCdVersione(nomeCatalog, versione);
		outputFile(
				response,
				IOUtils.toInputStream(
						new String(
								catalog.getBlFileCatalog(), StandardCharsets.UTF_8)
				)
		);
	}

}

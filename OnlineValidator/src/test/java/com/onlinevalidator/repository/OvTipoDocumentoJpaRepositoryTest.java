package com.onlinevalidator.repository;

import com.onlinevalidator.model.OvTipoDocumento;
import com.onlinevalidator.model.enumerator.NomeTipoDocumentoEnum;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * @author Manuel Gozzi
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:/bean-servlet-test.xml")
public class OvTipoDocumentoJpaRepositoryTest {

	@Autowired
	private OvTipoDocumentoJpaRepository tipoDocumentoJpaRepository;

	@Test
	public void findByNmNome() {

		assertNotNull(this.tipoDocumentoJpaRepository);

		OvTipoDocumento tipoDocumento = tipoDocumentoJpaRepository.findByNmNome(NomeTipoDocumentoEnum.DOCUMENTO_DI_TRASPORTO);
		assertNotNull(tipoDocumento);
		assertEquals(NomeTipoDocumentoEnum.DOCUMENTO_DI_TRASPORTO, tipoDocumento.getName());

		tipoDocumento = tipoDocumentoJpaRepository.findByNmNome(NomeTipoDocumentoEnum.ORDINE);
		assertNotNull(tipoDocumento);
		assertEquals(NomeTipoDocumentoEnum.ORDINE, tipoDocumento.getName());
	}
}
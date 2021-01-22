package com.onlinevalidator.dto;

import com.onlinevalidator.pojo.TipoRenderingEnum;
import org.junit.Test;

import static org.junit.Assert.assertNull;

/**
 * @author Manuel Gozzi
 */
public class RenderTest {

	@Test
	public void getFile() {

		Render render = new Render(null, null, null);
		assertNull(render.getFile());
	}

	@Test
	public void setFile() {

		Render render = new Render(null, null, null);
		render.setFile(new byte[]{0});
	}

	@Test
	public void getTipoEsportazioneEnum() {

		Render render = new Render(null, null, null);
		assertNull(render.getTipoEsportazioneEnum());
	}

	@Test
	public void setTipoEsportazioneEnum() {

		Render render = new Render(null, null, null);
		render.setTipoEsportazioneEnum(TipoRenderingEnum.XML);
	}

	@Test
	public void getFileName() {

		Render render = new Render(null, null, null);
		assertNull(render.getFileName());
	}

	@Test
	public void setFileName() {

		Render render = new Render(null, null, null);
		render.setFileName("filename");
	}
}
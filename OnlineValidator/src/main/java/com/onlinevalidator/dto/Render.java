package com.onlinevalidator.dto;

import com.onlinevalidator.pojo.TipoRenderingEnum;

/**
 * @author Manuel Gozzi
 */
public class Render {

	private byte[] file;

	private TipoRenderingEnum tipoRenderingEnum;

	private String fileName;

	public Render(byte[] file, TipoRenderingEnum tipoRenderingEnum, String fileName) {
		this.file = file;
		this.tipoRenderingEnum = tipoRenderingEnum;
		this.fileName = fileName;
	}

	public byte[] getFile() {
		return file;
	}

	public void setFile(byte[] file) {
		this.file = file;
	}

	public TipoRenderingEnum getTipoEsportazioneEnum() {
		return tipoRenderingEnum;
	}

	public void setTipoEsportazioneEnum(TipoRenderingEnum tipoRenderingEnum) {
		this.tipoRenderingEnum = tipoRenderingEnum;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
}

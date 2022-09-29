package com.udec.autoreslibrosjpa.dto;

import java.io.Serializable;

public class AutorLectorDto implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private AutorDto autor;
	
	private LectorDto lector;
	 
	private String infoAdicional;

	public AutorDto getAutor() {
		return autor;
	}

	public LectorDto getLector() {
		return lector;
	}

	public String getInfoAdicional() {
		return infoAdicional;
	}

	public void setAutor(AutorDto autor) {
		this.autor = autor;
	}

	public void setLector(LectorDto lector) {
		this.lector = lector;
	}

	public void setInfoAdicional(String infoAdicional) {
		this.infoAdicional = infoAdicional;
	}
	
}

package com.udec.autoreslibrosjpa.dto;

import java.io.Serializable;

public class LectorDto implements Serializable{
	
	private static final long serialVersionUID = 1L;

	private Integer Id;
	
	private String cedula;	
	
	private String nombre;
	
	private String apellido;
	
	public Integer getId() {
		return Id;
	}

	public String getCedula() {
		return cedula;
	}

	public String getNombre() {
		return nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public void setId(Integer id) {
		Id = id;
	}

	public void setCedula(String cedula) {
		this.cedula = cedula;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

}

package com.udec.autoreslibrosjpa.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name ="lector")
public class Lector {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@NotNull
	@Size(min = 7,  max = 10,  message = "Cédula entre 7 y 10 números")		
	@Column(name = "cedula", nullable = false, length = 10)
	private String cedula;
	
	@NotNull
	@Size(min = 3,  max = 25,  message = "Nombre entre 3 y 25 carácteres")	
	@Column(name = "nombre", nullable = false, length = 25)
	private String nombre;
		
	@NotNull
	@Size(min = 3,  max = 25,  message = "Apellido entre 3 y 25 carácteres")
	@Column(name = "apellido", nullable = false, length = 25)
	private String apellido;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCedula() {
		return cedula;
	}

	public void setCedula(String cedula) {
		this.cedula = cedula;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}
}

package com.udec.autoreslibrosjpa.view;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "autor_direccion_libro_view")
public class AutorDireccionLibro{

	@Id
	private Integer id;
	
	private String nombre;
	
	private String apellido;
	
	private String cedula;
	
	private Integer edad;
	
	private String barrio;
	
	private String direccion;
	
	@Column(name = "numero_libros")
	private Integer numeroLibros;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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

	public String getCedula() {
		return cedula;
	}

	public void setCedula(String cedula) {
		this.cedula = cedula;
	}

	public Integer getEdad() {
		return edad;
	}

	public void setEdad(Integer edad) {
		this.edad = edad;
	}

	public String getBarrio() {
		return barrio;
	}

	public void setBarrio(String barrio) {
		this.barrio = barrio;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public Integer getNumeroLibros() {
		return numeroLibros;
	}

	public void setNumeroLibros(Integer numeroLibros) {
		this.numeroLibros = numeroLibros;
	}
}

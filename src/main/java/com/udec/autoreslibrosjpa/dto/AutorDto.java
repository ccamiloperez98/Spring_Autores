package com.udec.autoreslibrosjpa.dto;

import java.time.LocalDate;
import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import com.udec.autoreslibrosjpa.entity.Libro;

public class AutorDto {

	private Integer Id;
	
	@NotNull
	@Size(min = 7,max = 11, message = "La cédula debe ser entre 7 y 11 carácteres")	
	private String cedula;	
	
	@NotNull
	@Size(min = 5,max = 25, message = "El nombre debe ser entre 5 y 25 carácteres")	
	private String nombre;
	
	@NotNull
	@Size(min = 5,max = 25, message = "El apellido debe ser entre 5 y 25 carácteres")		
	private String apellido;
	
	@NotNull(message = "fechaNacimiento es requerido")
	@Past(message = "La fecha debe ser menor a la fecha actual")
	@DateTimeFormat(iso = ISO.DATE)
	private LocalDate fechaNacimiento;
	
	private List<Libro> libro;

	
	public Integer getId() {
		return Id;
	}

	public String getNombre() {
		return nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public LocalDate getFechaNacimiento() {
		return fechaNacimiento;
	}

	public void setId(Integer id) {
		Id = id;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public void setFechaNacimiento(LocalDate fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}

	public List<Libro> getLibro() {
		return libro;
	}

	public void setLibro(List<Libro> libro) {
		this.libro = libro;
	}

	public String getCedula() {
		return cedula;
	}

	public void setCedula(String cedula) {
		this.cedula = cedula;
	}		
		
}

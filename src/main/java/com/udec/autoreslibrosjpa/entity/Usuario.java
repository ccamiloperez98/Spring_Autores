package com.udec.autoreslibrosjpa.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "usuario")
public class Usuario {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer idUsuario;
		
	@NotNull(message = "Número del documento es requerido")
	@Size(min = 7, max = 29, message = "Longitud número del documento fuera de desfase")	
	@Column(name = "documento", nullable = false, length = 30, unique = true)
	private String documento;

	@NotNull(message = "Nombre es requerido")
	@Size(min = 3, max = 24, message = "Longitud nombre fuera de desfase")	
	@Column(name = "nombre", nullable = false, length = 25)
	private String nombre;

	@NotNull(message = "Apellido es requerido")
	@Size(min = 3, max = 24, message = "Longitud apellido fuera de desfase")		
	@Column(name = "apellido", nullable = false, length = 25)
	private String apellido;

	@NotNull(message = "Nick es requerido")
	@Size(min = 3, max = 30, message = "Longitud nick fuera de desfase")		
	@Column(name = "nick", nullable = false, unique = true)
	private String nick;

	@NotNull(message = "Clave es requerido")		
	@Column(columnDefinition="TEXT", name = "clave", nullable = false)
	private String clave;

	@Column(name = "estado", nullable = false)
	private boolean estado;
	
	@ManyToOne
	@JoinColumn(name = "idRol", foreignKey = @ForeignKey(name = "FK_rol"))
	private Rol rol;

	public Integer getIdUsuario() {
		return idUsuario;
	}

	public String getDocumento() {
		return documento;
	}

	public String getNombre() {
		return nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public String getNick() {
		return nick;
	}

	public String getClave() {
		return clave;
	}

	public boolean isEstado() {
		return estado;
	}

	public Rol getRol() {
		return rol;
	}

	public void setIdUsuario(Integer idUsuario) {
		this.idUsuario = idUsuario;
	}

	public void setDocumento(String documento) {
		this.documento = documento;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public void setNick(String nick) {
		this.nick = nick;
	}

	public void setClave(String clave) {
		this.clave = clave;
	}

	public void setEstado(boolean estado) {
		this.estado = estado;
	}

	public void setRol(Rol rol) {
		this.rol = rol;
	}	

}

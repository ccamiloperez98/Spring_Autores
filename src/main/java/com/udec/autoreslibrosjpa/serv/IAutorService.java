package com.udec.autoreslibrosjpa.serv;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import com.udec.autoreslibrosjpa.dto.AutorLectorDto;
import com.udec.autoreslibrosjpa.entity.Autor;
import com.udec.autoreslibrosjpa.entity.AutorLector;
import com.udec.autoreslibrosjpa.entity.Direccion;
import com.udec.autoreslibrosjpa.view.AutorDireccionLibro;

@Component
public interface IAutorService extends AbstractFacade<Autor, Integer>{
	
	public Page<Autor> listarPorNombre(int page, int size, String nombre);
	
	public List<Autor> listarPorApellido(String apellido);
	
	public Autor buscarPorLibro(String nombreLibro);
	
	public Autor buscarPorLibroII(String nombreLibro);
	
	public Page<Autor> buscarPorNombreApellido(int page, int size, String nombre, String apellido);
	
	public void borrarCascada(Integer idAutor);

	public void editarDireccion(Direccion direccion);
	
	public void editarDireccionII(Direccion direccion);
	
	public Page<AutorDireccionLibro> ListarAutorView(int page, int size);
	
	public void guardarAutorLector(AutorLectorDto dto);
	
	public void guardarAutorLector(List<AutorLectorDto> dto);
	
	public List<AutorLector> listarAutorLector(Integer idAutor);
	
	public void borrarAutorLector(AutorLectorDto dto);
}

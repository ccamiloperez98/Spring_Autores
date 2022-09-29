package com.udec.autoreslibrosjpa.service.imp;

import java.math.BigInteger;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.udec.autoreslibrosjpa.entity.Autor;
import com.udec.autoreslibrosjpa.entity.Libro;
import com.udec.autoreslibrosjpa.exception.ArgumentRequiredException;
import com.udec.autoreslibrosjpa.exception.ModelNotFoundException;

import com.udec.autoreslibrosjpa.repository.IAutorRepo;
import com.udec.autoreslibrosjpa.repository.ILibroRepo;
import com.udec.autoreslibrosjpa.serv.ILibroService;


@Service
public class LibroServiceImp implements ILibroService{

	@Autowired
	private ILibroRepo repo;
	
	@Autowired
	private IAutorRepo repoAutor;
	
	@Override
	public List<Libro> listar() {
		return repo.findAll();
		
	}
	
	@Override
	public Page<Libro> listarPaginado(boolean lazy, Integer page, Integer size) {
		Page<Libro> listaPaginaLibro = repo.findAll(PageRequest.of(page, size, Sort.by("nombre").ascending()));
		return listaPaginaLibro;
	}

	@Override
	public Libro listarPorId(Integer id) {
		return repo.findById(id).orElseThrow(() 
				-> new ModelNotFoundException("Libro no encontrado"));	
	}

	@Override
	public void guardar(Libro libro) {
		if(libro.getAutor() != null && libro.getAutor().getId() != null) {
			BigInteger contador = repoAutor.validarExistenciaPorId(libro.getAutor().getId());
			if(contador.intValue() > 0) {
			    
				Autor autor = new Autor();
				autor.setId(libro.getAutor().getId());
	
				libro.setAutor(autor);
				repo.save(libro);
				
			} else {
				throw new ModelNotFoundException("Autor no encontrado");
			}
		} else {
			throw new ArgumentRequiredException("Id Autor es requerido");
		}
		
	}

	@Override
	public void editar(Libro libro) {
		if(libro.getId() == null) {
			throw new ArgumentRequiredException("Id Libro es requerido");
		}
		Libro auxLibro=listarPorId(libro.getId());
		auxLibro.setNombre(libro.getNombre());
		auxLibro.setNumeroPaginas(libro.getNumeroPaginas());
		
		repo.save(auxLibro);
	}

	@Override
	public void eliminar(Integer idLibro) {
		 
		  boolean flag = repo.existsById(idLibro);
		  if(flag==false) {
			  throw new ModelNotFoundException("Libro no encontrado");
		  }
		  repo.deleteById(idLibro);		  
	}
	
}

package com.udec.autoreslibrosjpa.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.udec.autoreslibrosjpa.entity.Autor;
import com.udec.autoreslibrosjpa.entity.Libro;
import com.udec.autoreslibrosjpa.serv.ILibroService;


@RestController
@RequestMapping("/libros")
public class LibroController {
	

	@Autowired
	private ILibroService service;
	
	@GetMapping(path = "/listar")
	public ResponseEntity<List<Libro>> listar() {
		List<Libro> libros = service.listar();
		return new ResponseEntity<List<Libro>>(libros, HttpStatus.OK);
	}
	
	@GetMapping("/listarPageable/{page}/{size}")
	public  ResponseEntity<Page<Libro>> rentorPageable(@PathVariable int page, @PathVariable int size) {		
		Page<Libro> listarLibro = service.listarPaginado(true,page, size);
		return new ResponseEntity<Page<Libro>>(listarLibro, HttpStatus.OK);
	}
	
	
	@GetMapping("/buscar/{id}")
	public ResponseEntity<Libro> listarPorId(@PathVariable int id)  {
		Libro libro = service.listarPorId(id);
			return new ResponseEntity<Libro>(libro, HttpStatus.OK);					
	}
	
	@PutMapping(path = "/editar")
	public ResponseEntity<Libro> editar(@Valid @RequestBody Libro libro){
		service.editar(libro);
		return new ResponseEntity<Libro>(HttpStatus.OK);
	}
	
	@PostMapping("/guardar")
	public ResponseEntity<Object> guardar(@Valid @RequestBody Libro libro) {
		service.guardar(libro);
		return new ResponseEntity<Object>(HttpStatus.CREATED);
	}
	
	@DeleteMapping("/eliminar/{idLibro}")
	public ResponseEntity<Autor> eliminar(@PathVariable int idLibro)  {
			service.eliminar(idLibro);
			return new ResponseEntity<Autor>(HttpStatus.NO_CONTENT);					
	}
	
}

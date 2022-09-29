package com.udec.autoreslibrosjpa.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.udec.autoreslibrosjpa.dto.AutorLectorDto;
import com.udec.autoreslibrosjpa.entity.Autor;
import com.udec.autoreslibrosjpa.entity.AutorLector;
import com.udec.autoreslibrosjpa.entity.Direccion;
import com.udec.autoreslibrosjpa.serv.IAutorService;
import com.udec.autoreslibrosjpa.view.AutorDireccionLibro;


@PreAuthorize("hasAuthority('Administrador')")
@RestController
@RequestMapping("/autores")
public class AutorController {
	
	@Autowired
	IAutorService service;
	
	@GetMapping(path = "/listar")
	public ResponseEntity<List<Autor>> listar() {
		List<Autor> autores = service.listar();
		System.out.print("prueba");
		return new ResponseEntity<List<Autor>>(autores, HttpStatus.OK);
	}
	
	@GetMapping(path = "/listarPageable/{lazy}/{page}/{size}")
	public ResponseEntity<Page<Autor>> listarPageable(@PathVariable boolean lazy, @PathVariable int page,@PathVariable int size) {
		Page<Autor> autores = service.listarPaginado(lazy,page, size);
		return new ResponseEntity<Page<Autor>>(autores, HttpStatus.OK);
	}
	
	@GetMapping(path = "/listarPageableView/{page}/{size}")
	public ResponseEntity<Page<AutorDireccionLibro>> listarPageable(@PathVariable int page,@PathVariable int size) {
		Page<AutorDireccionLibro> autores = service.ListarAutorView(page, size);
		return new ResponseEntity<Page<AutorDireccionLibro>>(autores, HttpStatus.OK);
	}
	
	@GetMapping(path = "/buscar")
	public ResponseEntity<Autor> buscar(@RequestParam(value ="id", required = true) int id){
		Autor autor = service.listarPorId(id);
		return new ResponseEntity<Autor>(autor, HttpStatus.OK);
	}

	@PostMapping(path = "/guardar")
	public ResponseEntity<Autor> guardar(@Valid @RequestBody Autor autor) throws Exception {
		service.guardar(autor);
		return new ResponseEntity<Autor>(HttpStatus.CREATED);
	}

	@PutMapping(path = "/editar")
	public ResponseEntity<Autor> editar(@Valid @RequestBody Autor autor){
		service.editar(autor);
		return new ResponseEntity<Autor>(HttpStatus.OK);
	}
	
	@DeleteMapping(path = "/eliminar")
	public ResponseEntity<Object> borrar(@RequestParam(value ="id", required = true)int id){
		service.eliminar(id);
		return new ResponseEntity<Object>(HttpStatus.NO_CONTENT);
	}
	
	//---------------------------------------------------------------------------------------------------------------------------------
	@PutMapping(path = "/editarDireccion")
	public ResponseEntity<Direccion> editar(@Valid @RequestBody Direccion direccion){
		service.editarDireccion(direccion);
		return new ResponseEntity<Direccion>(HttpStatus.OK);
	}
	
	@PutMapping(path = "/editarDireccionII")
	public ResponseEntity<Direccion> editarII(@Valid @RequestBody Direccion direccion){
		service.editarDireccionII(direccion);
		return new ResponseEntity<Direccion>(HttpStatus.OK);
	}
	
	
	//-----------------------------------------------------------------------------------------------------------------------------------
	
	@GetMapping("/listarPorNombre/{page}/{size}/{nombre}")
	public  ResponseEntity<Page<Autor>> listarPorNombre(@PathVariable int page, @PathVariable int size, @PathVariable String nombre) {		
		Page<Autor> listaAutor = service.listarPorNombre(page, size, nombre);
		return new ResponseEntity<Page<Autor>>(listaAutor, HttpStatus.OK);
	}
	
	@GetMapping("/listarPorApellido/{apellido}")
	public  ResponseEntity<List<Autor>> listarPorApellido(@PathVariable String apellido) {		
		List<Autor> listaAutor = service.listarPorApellido(apellido);
		return new ResponseEntity<List<Autor>>(listaAutor, HttpStatus.OK);
	}
	
	@GetMapping("/listarPorLibro/{libro}")
	public  ResponseEntity<Autor> listarPorLibro(@PathVariable String libro) {		
		Autor autor = service.buscarPorLibro(libro);
		return new ResponseEntity<Autor>(autor, HttpStatus.OK);
	}
	
	@GetMapping("/listarPorLibroII/{libro}")
	public  ResponseEntity<Autor> listarPorLibroII(@PathVariable String libro) {		
		Autor autor = service.buscarPorLibroII(libro);
		return new ResponseEntity<Autor>(autor, HttpStatus.OK);
	}

	@GetMapping("/listarPorNombreApellido/{page}/{size}/{nombre}/{apellido}")
	public  ResponseEntity<Page<Autor>> listarPorNombreApellido(@PathVariable int page, @PathVariable int size,
			@PathVariable String nombre, @PathVariable String apellido) {		
		Page<Autor> listaAutor = service.buscarPorNombreApellido(page, size, nombre, apellido);
		return new ResponseEntity<Page<Autor>>(listaAutor, HttpStatus.OK);
	}
	
	@DeleteMapping(path = "/borrarCascada")
	public ResponseEntity<Object> borrarCascada(@RequestParam(value ="id", required = true)int id){
		service.borrarCascada(id);
		return new ResponseEntity<Object>(HttpStatus.NO_CONTENT);
	}
	
	// AutorLector-----------------------------------------------------------------------------------------------------------------
	
	@GetMapping("/listarAutorLector/{idAutor}")
	public ResponseEntity<List<AutorLector>> listarAutorLector(@PathVariable int idAutor)  {
			List<AutorLector> listaAutorLector = service.listarAutorLector(idAutor);
			return new ResponseEntity<List<AutorLector>>(listaAutorLector, HttpStatus.OK);					
	}
	
	@PostMapping("/asociarLector")
	public ResponseEntity<Object> guardarAutorLector(@RequestBody AutorLectorDto obj) {
		service.guardarAutorLector(obj);
		return new ResponseEntity<Object>(HttpStatus.CREATED);
	}
	
	@PostMapping("/asociarLectorLista")
	public ResponseEntity<Object> guardarAutorLectorLista(@RequestBody List<AutorLectorDto> obj) {
		service.guardarAutorLector(obj);
		return new ResponseEntity<Object>(HttpStatus.CREATED);
	}
	
	@DeleteMapping(path = "/borrarAutorLector")
	public ResponseEntity<Object> borrarAutorLector(@RequestBody AutorLectorDto obj){
		service.borrarAutorLector(obj);
		return new ResponseEntity<Object>(HttpStatus.NO_CONTENT);
	}
}

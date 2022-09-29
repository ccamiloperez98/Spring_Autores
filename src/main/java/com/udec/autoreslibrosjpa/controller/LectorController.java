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
import com.udec.autoreslibrosjpa.entity.Lector;
import com.udec.autoreslibrosjpa.serv.ILectorService;

@PreAuthorize("hasAuthority('Invitado')")
@RestController
@RequestMapping("/lectores")
public class LectorController {

	@Autowired
	ILectorService service;
	
	@GetMapping(path = "/listar")
	public ResponseEntity<List<Lector>> listar() {
		List<Lector> lectores = service.listar();
		return new ResponseEntity<List<Lector>>(lectores, HttpStatus.OK);
	}
	
	@GetMapping(path = "/listarPageable/{lazy}/{page}/{size}")
	public ResponseEntity<Page<Lector>> listarPageable(@PathVariable boolean lazy, @PathVariable int page,@PathVariable int size) {
		Page<Lector> lectores = service.listarPaginado(lazy,page, size);
		return new ResponseEntity<Page<Lector>>(lectores, HttpStatus.OK);
	}
	
	
	@GetMapping(path = "/buscar")
	public ResponseEntity<Lector> buscar(@RequestParam(value ="id", required = true) int id){
		Lector lector = service.listarPorId(id);
		return new ResponseEntity<Lector>(lector, HttpStatus.OK);
	}

	@PostMapping(path = "/guardar")
	public ResponseEntity<Lector> guardar(@Valid @RequestBody Lector lector) throws Exception {
		service.guardar(lector);
		return new ResponseEntity<Lector>(HttpStatus.CREATED);
	}

	@PutMapping(path = "/editar")
	public ResponseEntity<Lector> editar(@Valid @RequestBody Lector lector){
		service.editar(lector);
		return new ResponseEntity<Lector>(HttpStatus.OK);
	}
	
	@DeleteMapping(path = "/eliminar")
	public ResponseEntity<Object> borrar(@RequestParam(value ="id", required = true)int id){
		service.eliminar(id);
		return new ResponseEntity<Object>(HttpStatus.NO_CONTENT);
	}
}

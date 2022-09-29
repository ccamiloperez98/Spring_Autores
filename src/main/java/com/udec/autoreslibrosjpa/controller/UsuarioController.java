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

import com.udec.autoreslibrosjpa.entity.Usuario;
import com.udec.autoreslibrosjpa.serv.IUsuarioService;


@RestController
@RequestMapping("/usuarios")
public class UsuarioController {
	
	@Autowired
	private IUsuarioService service;
	
	@PreAuthorize("hasAuthority('Administrador')")
	@GetMapping(path = "/listar")
	public ResponseEntity<List<Usuario>> listar() {
		List<Usuario> usuarios= service.listar();
		return new ResponseEntity<List<Usuario>>(usuarios, HttpStatus.OK);
	}
	
	@PreAuthorize("hasAuthority('Administrador')")
	@GetMapping(path = "/listarPageable/{lazy}/{page}/{size}")
	public ResponseEntity<Page<Usuario>> listarPageable(@PathVariable boolean lazy, @PathVariable int page,@PathVariable int size) {
		Page<Usuario> usuarios = service.listarPaginado(lazy,page, size);
		return new ResponseEntity<Page<Usuario>>(usuarios, HttpStatus.OK);
	}

	@PreAuthorize("hasAuthority('Administrador')")
	@GetMapping(path = "/buscar")
	public ResponseEntity<Usuario> buscar(@RequestParam(value ="id", required = true) int id){
		Usuario usuario = service.listarPorId(id);
		return new ResponseEntity<Usuario>(usuario, HttpStatus.OK);
	}

	@PostMapping(path = "/guardar")
	public ResponseEntity<Usuario> guardar(@Valid @RequestBody Usuario usuario) throws Exception {
		service.guardar(usuario);
		return new ResponseEntity<Usuario>(HttpStatus.CREATED);
	}
	
	@PreAuthorize("hasAuthority('Invitado')")
	@PutMapping(path = "/editar")
	public ResponseEntity<Usuario> editar(@Valid @RequestBody Usuario usuario){
		service.editar(usuario);
		return new ResponseEntity<Usuario>(HttpStatus.OK);
	}
	
	@PreAuthorize("hasAuthority('Administrador')")
	@PutMapping(path = "/adminEditar")
	public ResponseEntity<Usuario> adminEditar(@Valid @RequestBody Usuario usuario){
		service.editarParaAdmin(usuario);
		return new ResponseEntity<Usuario>(HttpStatus.OK);
	}
	
	@PreAuthorize("hasAuthority('Administrador')")
	@DeleteMapping(path = "/eliminar")
	public ResponseEntity<Object> borrar(@RequestParam(value ="id", required = true)int id){
		service.eliminar(id);
		return new ResponseEntity<Object>(HttpStatus.NO_CONTENT);
	}

}

package com.udec.autoreslibrosjpa.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.token.ConsumerTokenServices;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cerrarSesion")
public class CerrarSesionController {

	@Autowired
	private ConsumerTokenServices service;
	
	@DeleteMapping("/anular/{token:.*}")
	public void anularToken(@PathVariable("token") String token) {
		service.revokeToken(token);		
	}
}

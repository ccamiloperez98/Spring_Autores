package com.udec.autoreslibrosjpa;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;

import com.udec.autoreslibrosjpa.entity.Autor;
import com.udec.autoreslibrosjpa.exception.BusinessLogicException;
import com.udec.autoreslibrosjpa.serv.IAutorService;
import com.udec.autoreslibrosjpa.service.imp.AutorServiceImp;
import static java.time.Duration.ofMillis;

@SpringBootTest
class AutorServiceTests {

	@Autowired
	private IAutorService serv;

	private String nombreLibro;

	@BeforeEach
	public void before() {
		System.out.println("before()");
		nombreLibro = "pruebalibro222";
	}

	@Test
	void testListarPaginado() {
		Page<Autor> listaPaginaAutor = serv.listarPaginado(true, 1, 3);
		System.out.println("testListarPaginado()");
		assertNotEquals(listaPaginaAutor, null);
	}

	@Test
	void testBuscarPorLibro() {
		System.out.println("testBuscarPorLibro()");
		assertThrows(BusinessLogicException.class, () -> {
			serv.buscarPorLibro(nombreLibro);
		});
	}
	
	@Test
	void algortmoOptimo() {
		System.out.println("algoritmoOptimo()");
		AutorServiceImp a= new AutorServiceImp();
		assertTimeout(ofMillis(3000), ()-> {
			a.operacionOptima();
		});
	}

}

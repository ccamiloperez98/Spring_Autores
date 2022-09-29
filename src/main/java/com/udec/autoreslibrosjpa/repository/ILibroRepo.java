package com.udec.autoreslibrosjpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.udec.autoreslibrosjpa.entity.Libro;

@Repository
public interface ILibroRepo extends JpaRepository<Libro, Integer> {

	public Libro findByNombre(String nombre);
	
}

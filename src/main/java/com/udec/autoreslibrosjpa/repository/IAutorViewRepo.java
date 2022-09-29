package com.udec.autoreslibrosjpa.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.udec.autoreslibrosjpa.view.AutorDireccionLibro;

@Repository
public interface IAutorViewRepo extends JpaRepository<AutorDireccionLibro, Integer> {

	@Query(value = "SELECT * FROM autor_direccion_libro_view ", nativeQuery = true)
	public Page<AutorDireccionLibro> listarAutorView(Pageable pageable);
}

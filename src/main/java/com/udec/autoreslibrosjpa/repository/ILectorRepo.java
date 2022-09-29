package com.udec.autoreslibrosjpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.udec.autoreslibrosjpa.entity.Lector;

@Repository
public interface ILectorRepo extends JpaRepository<Lector, Integer> {

	public Lector findByCedula(String cedula);
	
	@Query(value = "select count(*) from public.lector where cedula = :cedula and id != :id", nativeQuery = true)				
	Object validarExistenciaCedulaEditar(@Param("id") Integer id, @Param("cedula") String cedula);
	
	
}

package com.udec.autoreslibrosjpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


import com.udec.autoreslibrosjpa.entity.Direccion;

@Repository
public interface IDireccionRepo extends JpaRepository<Direccion, Integer>{

	@Transactional
	@Modifying
	@Query(value = "UPDATE public.direccion a SET barrio = :barrio, descripcion = :descripcion "
			+ "where a.autor_id= :idAutor", nativeQuery = true)
	public void editarDireccion(@Param("idAutor") Integer idAutor, @Param("barrio") String barrio,@Param("descripcion") String descripcion);
	
	Direccion findByAutor_id(int idAutor);
}

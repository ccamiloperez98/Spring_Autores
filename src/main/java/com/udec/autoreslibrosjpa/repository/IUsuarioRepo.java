package com.udec.autoreslibrosjpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.udec.autoreslibrosjpa.entity.Usuario;

@Repository
public interface IUsuarioRepo extends JpaRepository<Usuario, Integer>{

	public Usuario findByNick(String nick);
	public Usuario findByDocumento(String cedula);
	public Usuario findByIdUsuario(Integer idUsuario);
	
	@Query(value = "select count(*) from public.usuario where documento = :cedula and id_usuario != :id", nativeQuery = true)				
	Object validarExistenciaCedulaEditar(@Param("id") Integer id, @Param("cedula") String cedula);
	
}

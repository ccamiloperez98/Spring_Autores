package com.udec.autoreslibrosjpa.repository;

import java.math.BigInteger;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.udec.autoreslibrosjpa.entity.Autor;
import com.udec.autoreslibrosjpa.entity.Libro;


@Repository
public interface IAutorRepo extends JpaRepository<Autor, Integer>{

	
	public Autor findByCedula(String cedula);

	public Autor findByCorreo(String correo);
	
	public Page<Autor> findByNombreIgnoreCase(String nombre, Pageable pageable);
	
	public List<Autor> findByApellidoIgnoreCase(String apellido, Sort sort);
	
	@Query(value = "SELECT p FROM Autor p WHERE p.nombre = :nombre AND p.apellido = :apellido")
	public Page<Autor> buscarPorNombreApellido(String nombre, String apellido, Pageable pageable);
	
	@Query(value = "SELECT p FROM Autor p, Libro l WHERE p.id = l.autor.id AND l.nombre= :nombre ")
	public Autor buscarPorNombreLibro(String nombre);
	
	@Query(value = "SELECT count(a) FROM Autor a WHERE a.id = :id")
	BigInteger validarExistenciaPorId(@Param("id") Integer id);
	
	@Query(value = "select count(*) from public.autor where cedula = :cedula and id != :id", nativeQuery = true)				
	Object validarExistenciaCedulaEditar(@Param("id") Integer id, @Param("cedula") String cedula);
	
	Autor findByLibro_NombreIgnoreCase(String nombre);
	
	public Autor findByLibro(Libro libro);
	
	
	@Transactional
	@Modifying
	@Query(value = "DELETE from public.autor_lector a where a.id_autor= :idAutor and a.id_lector= :idLector", nativeQuery = true)
	public void eliminarAutorLector(@Param("idAutor") Integer idAutor, @Param("idLector") Integer idLector);

}

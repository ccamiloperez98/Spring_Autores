package com.udec.autoreslibrosjpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.udec.autoreslibrosjpa.entity.Rol;



public interface IRolRepo extends JpaRepository<Rol, Integer>{

	public Rol findByIdRol(Integer idRol);
	
}

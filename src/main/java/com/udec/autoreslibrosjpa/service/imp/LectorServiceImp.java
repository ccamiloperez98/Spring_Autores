package com.udec.autoreslibrosjpa.service.imp;

import java.math.BigInteger;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.udec.autoreslibrosjpa.entity.Lector;
import com.udec.autoreslibrosjpa.exception.ArgumentRequiredException;
import com.udec.autoreslibrosjpa.exception.BusinessLogicException;
import com.udec.autoreslibrosjpa.exception.ModelNotFoundException;
import com.udec.autoreslibrosjpa.repository.ILectorRepo;
import com.udec.autoreslibrosjpa.serv.ILectorService;

@Service
public class LectorServiceImp implements ILectorService{
	
	@Autowired
	private ILectorRepo repoLector;
	
	@Override
	public Page<Lector> listarPaginado(boolean lazy, Integer page, Integer size) {
		Page<Lector> listaPaginaLector = repoLector.findAll(PageRequest.of(page, size, Sort.by("nombre").ascending()));
		return listaPaginaLector;
	}

	@Override
	public List<Lector> listar() {
		return repoLector.findAll();
	}

	@Override
	public Lector listarPorId(Integer id) {
		return repoLector.findById(id).orElseThrow(() -> new ModelNotFoundException("Lector no encontrado"));
	}

	@Override
	public void guardar(Lector obj) {
		Lector lector = repoLector.findByCedula(obj.getCedula());
		if (lector != null)
			throw new BusinessLogicException("La cedula ya se encuentra registrada");
		repoLector.save(obj);
	}

	@Override
	public void editar(Lector obj) {
		if (obj.getId() == null) {
			throw new ArgumentRequiredException("Id lector es requerido");
		}
	
		Lector lector = repoLector.findById(obj.getId()).orElseThrow(() -> new ModelNotFoundException("Lector no encontrado"));

		BigInteger contador = (BigInteger) repoLector.validarExistenciaCedulaEditar(obj.getId(), obj.getCedula());

		if (contador.intValue() > 0)
			throw new BusinessLogicException("Ya existe un Autor con esta cédula");
		
		lector.setNombre(obj.getNombre());
		lector.setApellido(obj.getApellido());
		lector.setCedula(obj.getCedula());
		
		repoLector.save(lector);
	}

	@Override
	public void eliminar(Integer id) {
		
		Lector lector = repoLector.findById(id).orElseThrow(() -> new ModelNotFoundException("Lector no encontrado"));
		repoLector.delete(lector);
		
	}

}

package com.udec.autoreslibrosjpa.service.imp;

import java.math.BigInteger;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.udec.autoreslibrosjpa.dto.AutorLectorDto;
import com.udec.autoreslibrosjpa.entity.Autor;
import com.udec.autoreslibrosjpa.entity.AutorLector;
import com.udec.autoreslibrosjpa.entity.Direccion;
import com.udec.autoreslibrosjpa.entity.Libro;
import com.udec.autoreslibrosjpa.exception.ArgumentRequiredException;
import com.udec.autoreslibrosjpa.exception.BusinessLogicException;
import com.udec.autoreslibrosjpa.exception.ModelNotFoundException;
import com.udec.autoreslibrosjpa.repository.IAutorLectorRepo;
import com.udec.autoreslibrosjpa.repository.IAutorRepo;
import com.udec.autoreslibrosjpa.repository.IAutorViewRepo;
import com.udec.autoreslibrosjpa.repository.IDireccionRepo;
import com.udec.autoreslibrosjpa.repository.ILectorRepo;
import com.udec.autoreslibrosjpa.repository.ILibroRepo;
import com.udec.autoreslibrosjpa.serv.IAutorService;
import com.udec.autoreslibrosjpa.view.AutorDireccionLibro;

@Service
public class AutorServiceImp implements IAutorService {

	@Autowired
	private IAutorRepo repo;

	@Autowired
	private IDireccionRepo repoDireccion;

	@Autowired
	private ILibroRepo repoLibro;

	@Autowired
	private IAutorViewRepo repoAutorView;

	@Autowired
	private IAutorLectorRepo repoAutorLector;
	
	@Autowired
	private ILectorRepo repoLector;

	@Override
	public List<Autor> listar() {
		return repo.findAll();
	}

	@Override
	public Page<Autor> listarPaginado(boolean lazy, Integer page, Integer size) {
		Page<Autor> listaPaginaAutor = repo.findAll(PageRequest.of(page, size, Sort.by("nombre").ascending()));
		if (lazy) {
			for (Autor autor : listaPaginaAutor.getContent()) {
				autor.setLibro(null);
			}
		}
		return listaPaginaAutor;
	}

	@Override
	public Page<Autor> listarPorNombre(int page, int size, String nombre) {
		return repo.findByNombreIgnoreCase(nombre, PageRequest.of(page, size, Sort.by("apellido").ascending()));
	}

	@Override
	public List<Autor> listarPorApellido(String apellido) {
		return repo.findByApellidoIgnoreCase(apellido, Sort.by(Sort.Direction.DESC, "id"));
	}

	@Override
	public Page<Autor> buscarPorNombreApellido(int page, int size, String nombre, String apellido) {
		return repo.buscarPorNombreApellido(nombre, apellido,
				PageRequest.of(page, size, Sort.by("cedula").ascending()));
	}

	@Override
	public Autor buscarPorLibro(String nombreLibro) {
		Autor autor = repo.buscarPorNombreLibro(nombreLibro);
		if (autor == null) {
			throw new BusinessLogicException("El libro no se encuentra registrado");
		}
		autor.setLibro(null);
		return autor;
	}

	@Override
	public Autor buscarPorLibroII(String nombreLibro) {
		Libro libro = repoLibro.findByNombre(nombreLibro);
		if (libro == null) {
			throw new BusinessLogicException("El libro no se encuentra registrado");
		}
		Autor autor = repo.findByLibro(libro);
		autor.setLibro(null);
		return autor;
	}

	@Override
	public Autor listarPorId(Integer id) {
		return repo.findById(id).orElseThrow(() -> new ModelNotFoundException("Autor no encontrado"));

	}

	@Override
	public void guardar(Autor autor) {
		Autor aut = repo.findByCedula(autor.getCedula());
		if (aut != null)
			throw new BusinessLogicException("La cedula ya se encuentra registrada");
		aut = repo.findByCorreo(autor.getCorreo());
		if (aut != null)
			throw new BusinessLogicException("El correo ingresado ya esta registrado");

		if (autor.getLibro() != null) {
			for (Libro libro : autor.getLibro()) {
				libro.setAutor(autor);
			}
		}

		if (autor.getDireccion() != null) {
			autor.getDireccion().setAutor(autor);
		}
		repo.save(autor);
	}

	@Override
	public void editar(Autor autor) {
		if (autor.getId() == null) {
			throw new ArgumentRequiredException("Id Autor es requerido");
		}
		Autor aut = repo.findById(autor.getId()).orElseThrow(() -> new ModelNotFoundException("Autor no encontrado"));

		BigInteger contador = (BigInteger) repo.validarExistenciaCedulaEditar(autor.getId(), autor.getCedula());

		if (contador.intValue() > 0)
			throw new BusinessLogicException("Ya existe un Autor con esta cédula");

		aut.setCedula(autor.getCedula());
		aut.setApellido(autor.getApellido());
		aut.setNombre(autor.getNombre());
		aut.setFechaNacimiento(autor.getFechaNacimiento());

		aut.getDireccion().setDescripcion(autor.getDireccion().getDescripcion());
		aut.getDireccion().setBarrio(autor.getDireccion().getBarrio());

		repo.save(aut);
	}

	@Override
	public void eliminar(Integer idAutor) {
		Autor autor = repo.findById(idAutor).orElseThrow(() -> new ModelNotFoundException("Autor no encontrado"));
		if (autor.getLibro().size() > 0) {
			throw new BusinessLogicException("Se debe eliminar primero todos los libros asociados al Autor");
		}

		repo.delete(autor);
	}

	@Override
	public void borrarCascada(Integer idAutor) {
		Autor autor = repo.findById(idAutor).orElseThrow(() -> new ModelNotFoundException("Autor no encontrado"));
		repo.delete(autor);
	}

	@Override
	public void editarDireccion(Direccion direccion) {
		boolean flag = repo.existsById(direccion.getAutor().getId());
		if (!flag)
			throw new ModelNotFoundException("Autor no encontrado");
		repoDireccion.editarDireccion(direccion.getAutor().getId(), direccion.getBarrio(), direccion.getDescripcion());

	}

	@Override
	public void editarDireccionII(Direccion direccion) {
		boolean flag = repo.existsById(direccion.getAutor().getId());
		if (!flag)
			throw new ModelNotFoundException("Autor no encontrado");
		Direccion enDireccion = repoDireccion.findByAutor_id(direccion.getAutor().getId());
		enDireccion.setBarrio(direccion.getBarrio());
		enDireccion.setDescripcion(direccion.getDescripcion());
		repoDireccion.save(enDireccion);
	}

	@Override
	public Page<AutorDireccionLibro> ListarAutorView(int page, int size) {
		return repoAutorView.listarAutorView(PageRequest.of(page, size));

	}
	
	//-----------------------------------------------------------------------------------------------------------------------------
	
	@Override
	public void guardarAutorLector(AutorLectorDto dto) {
		
		boolean flag = repo.existsById(dto.getAutor().getId());
		if (!flag) throw new ModelNotFoundException("Autor no encontrado");
		
		boolean flag2 = repoLector.existsById( dto.getLector().getId());
		if (!flag2) throw new ModelNotFoundException("Lector no encontrado");
		
		repoAutorLector.guardar(
				dto.getAutor().getId(), dto.getLector().getId(), dto.getInfoAdicional());
	}

	@Transactional
	@Override
	public void guardarAutorLector(List<AutorLectorDto> dto) {
		
		for (AutorLectorDto obj : dto) {
			boolean flag = repo.existsById(obj.getAutor().getId());
			if (!flag) throw new ModelNotFoundException("Autor no encontrado");
			
			boolean flag2 = repoLector.existsById( obj.getLector().getId());
			if (!flag2) throw new ModelNotFoundException("Lector no encontrado");
		}
		
		for (AutorLectorDto obj : dto) {
			repoAutorLector.guardar(obj.getAutor().getId(), obj.getLector().getId(), obj.getInfoAdicional());
		}
	}

	@Override
	public List<AutorLector> listarAutorLector(Integer idAutor) {
		List<AutorLector> lista = repoAutorLector.listarAutorLector(idAutor);
		for (AutorLector autorLector : lista) {
			autorLector.setAutor(null);
		}
		return lista;
	}

	@Override
	public void borrarAutorLector(AutorLectorDto dto) {
		boolean flag = repo.existsById(dto.getAutor().getId());
		if (!flag) throw new ModelNotFoundException("Autor no encontrado");
		
		boolean flag2 = repoLector.existsById( dto.getLector().getId());
		if (!flag2) throw new ModelNotFoundException("Lector no encontrado");
		
		repo.eliminarAutorLector(dto.getAutor().getId(), dto.getLector().getId());
	}
	
	public void operacionOptima() {
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO: handle exception
		}
	}
	

}

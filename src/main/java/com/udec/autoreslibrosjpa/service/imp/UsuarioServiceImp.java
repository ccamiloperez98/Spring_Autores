package com.udec.autoreslibrosjpa.service.imp;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.udec.autoreslibrosjpa.entity.Rol;
import com.udec.autoreslibrosjpa.entity.Usuario;
import com.udec.autoreslibrosjpa.exception.ArgumentRequiredException;
import com.udec.autoreslibrosjpa.exception.BusinessLogicException;
import com.udec.autoreslibrosjpa.exception.ModelNotFoundException;
import com.udec.autoreslibrosjpa.repository.IRolRepo;
import com.udec.autoreslibrosjpa.repository.IUsuarioRepo;
import com.udec.autoreslibrosjpa.serv.IUsuarioService;

@Service
public class UsuarioServiceImp implements IUsuarioService, UserDetailsService{

	@Autowired
	private IUsuarioRepo repo;
	
	@Autowired
	private IRolRepo repoRol;
	
	@Autowired
	private BCryptPasswordEncoder bcrypt;
	
	@Override
	public Page<Usuario> listarPaginado(boolean lazy, Integer page, Integer size) {
		return repo.findAll(PageRequest.of(page, size, Sort.by("nombre").ascending()));
	}

	@Override
	public List<Usuario> listar() {
		return repo.findAll();
	}

	@Override
	public Usuario listarPorId(Integer id) {
		return repo.findById(id).orElseThrow(() -> new ModelNotFoundException("Usuario no encontrado"));
	}

	@Override
	public void guardar(Usuario user) {
		Usuario usuario = repo.findByDocumento(user.getDocumento());
		if (usuario != null) throw new BusinessLogicException("La cedula ya se encuentra registrada");
		
		usuario = repo.findByNick(user.getNick());
		if (usuario != null) throw new BusinessLogicException("El nombre de usuario ya se encuentra registrado");
		
		String claveEncrip= bcrypt.encode(user.getClave());
		
		Rol rol= new Rol();
		rol.setIdRol(2);
		
		user.setClave(claveEncrip);
		user.setRol(rol);
		user.setEstado(true);
		
		repo.save(user);
	}

	@Override
	public void editar(Usuario user) {
		if (user.getIdUsuario() == null) {
			throw new ArgumentRequiredException("Id Usuario es requerido");
		}
		
		Usuario usuario = repo.findById(user.getIdUsuario()).orElseThrow(() -> new ModelNotFoundException("Usuario no encontrado"));
		
		BigInteger contador = (BigInteger) repo.validarExistenciaCedulaEditar(user.getIdUsuario(), user.getDocumento());

		if (contador.intValue() > 0) throw new BusinessLogicException("Ya existe un usuario con esta cédula");
		
		usuario.setNombre(user.getNombre());
		usuario.setApellido(user.getApellido());
		usuario.setDocumento(user.getDocumento());
		usuario.setClave(bcrypt.encode(user.getClave()));
		usuario.setNick(user.getNick());
		
		repo.save(usuario);
	}
	
	
	@Override
	public void editarParaAdmin(Usuario user) {
		if (user.getIdUsuario() == null) {
			throw new ArgumentRequiredException("Id Usuario es requerido");
		}
		
		Rol rol= repoRol.findByIdRol(user.getRol().getIdRol());
		if(rol == null) throw new ModelNotFoundException("Id rol no encontrado");
		
		Usuario usuario = repo.findById(user.getIdUsuario()).orElseThrow(() -> new ModelNotFoundException("Usuario no encontrado"));

		BigInteger contador = (BigInteger) repo.validarExistenciaCedulaEditar(user.getIdUsuario(), user.getDocumento());
		if (contador.intValue() > 0) throw new BusinessLogicException("Ya existe un usuario con esta cédula");
		
		usuario.setEstado(user.isEstado());
		usuario.setRol(user.getRol());
		
		repo.save(usuario);
	}
	

	@Override
	public void eliminar(Integer id) {
		Usuario usuario = repo.findById(id).orElseThrow(() -> new ModelNotFoundException("Usuario no encontrado"));
	    repo.delete(usuario);
	}
	
	@Override
	public UserDetails loadUserByUsername(String nick) throws UsernameNotFoundException {
		
		Usuario usuario = repo.findByNick(nick);
		if(usuario == null)
			throw new ModelNotFoundException("----Nick o password incorrecto");
		if(usuario.isEstado() == false)
			throw new BusinessLogicException("----Usuario deshabilitado");
		
		List<GrantedAuthority> roles = new ArrayList<>();
		roles.add(new SimpleGrantedAuthority(usuario.getRol().getNombre()));
		
		UserDetails ud = new User(usuario.getNick(), usuario.getClave(), roles);	
		return ud;
	}

}

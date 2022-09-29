package com.udec.autoreslibrosjpa.serv;

import java.util.List;

import org.springframework.data.domain.Page;


public abstract interface AbstractFacade <T,V> {
	
		public Page<T> listarPaginado(boolean lazy,V page, V size);
	
		public List<T> listar();
	
		public T listarPorId(V id);
		
		public void guardar( T obj);
		
		public void editar( T obj);
		
		public void eliminar(V obj);
	    
}

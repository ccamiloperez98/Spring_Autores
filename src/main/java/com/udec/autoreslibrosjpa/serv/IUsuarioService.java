package com.udec.autoreslibrosjpa.serv;

import com.udec.autoreslibrosjpa.entity.Usuario;

public interface IUsuarioService extends AbstractFacade<Usuario, Integer>{

	public void editarParaAdmin(Usuario user);
}

package com.pga.builders;

import com.pga.entity.UsersEntity;

public class UsuarioBuilder {

	private UsersEntity usuario;
	
	private UsuarioBuilder() {
		
	}
	
	public static UsuarioBuilder newUser() {
		UsuarioBuilder builder = new UsuarioBuilder();
		builder.usuario = new UsersEntity();
		builder.usuario.setName("Michael Cardoso");
		builder.usuario.setStatus('G');
		return builder;
	}
	
	public UsersEntity agora() {
		return usuario;
	}
	
	public UsuarioBuilder setStatus(char status) {
		this.usuario.setStatus(status);
		return this;
	}
	
	public UsersEntity build(){
		
		return usuario;
	}
	
}

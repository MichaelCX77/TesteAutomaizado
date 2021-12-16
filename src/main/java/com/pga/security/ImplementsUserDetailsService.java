package com.pga.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import com.pga.entity.UsersEntity;
import com.pga.repository.UserRepository;

@Service
public class ImplementsUserDetailsService implements UserDetailsService{


	@Autowired
	private UserRepository crudUsuario;
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		
		UsersEntity usuario = crudUsuario.findByEmail(email.toLowerCase());
		
		if(usuario == null){
			throw new UsernameNotFoundException("Usuario não encontrado!");
		}
		
		return usuario;
	}	
	
}

package com.pga.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.pga.entity.AccessCodesEntity;
import com.pga.entity.UserPreferencesEntity;
import com.pga.entity.UsersEntity;
import com.pga.repository.CodigosAcessoRepository;
import com.pga.repository.UserPreferencesRepository;
import com.pga.repository.UserRepository;

@RestController
public class DefinePasswordController {

	@Autowired
	private CodigosAcessoRepository crudCodigo;
	
	@Autowired
	private UserRepository crudUsuario;
	
	@Autowired
	private UserPreferencesRepository crudPreferences;
	
	@RequestMapping(method = RequestMethod.GET, path = "/verify-data")
	public ResponseEntity<String> verifyData(@RequestParam(value="id", required=true) int id_usuario, @RequestParam(value="code", required=true) String codigo) { 

		if(validate(id_usuario,codigo) == null) {
			return ResponseEntity.status(HttpStatus.OK).body("Permitido");
		}
		return validate(id_usuario,codigo);
	}
	
	@RequestMapping(method = RequestMethod.POST, path = "/definir-senha")
	public ResponseEntity<String> redefinirSenha(@RequestBody AccessCodesEntity codigos) { 

		ResponseEntity<String> response = validate(codigos.getUser().getId(),codigos.getCode());
		
		if(response == null) {
			UsersEntity usuario = crudUsuario.findById(codigos.getUser().getId());
			
//			Inativando código
			AccessCodesEntity codigo = crudCodigo.findByUserAndCodeAndStatus(usuario, codigos.getCode(), 'A');
			codigo.setStatus('I');
			crudCodigo.save(codigo);
			
			usuario.setPassword(new BCryptPasswordEncoder().encode(codigos.getUser().getPassword()));
			
//			Ativando usuário e criando preferências
			if(codigo.getType() == 'C') {
				usuario.setStatus('A');
				usuario.setDate(codigos.getUser().getDate());
				crudPreferences.save(new UserPreferencesEntity(usuario));
			}

			crudUsuario.save(usuario);
			
			return ResponseEntity.status(HttpStatus.OK).body("Senha alterada com sucesso");
		}
		
		return response;
	}
	
	public ResponseEntity<String> validate(int id_usuario, String codigo) {
		
		if(!isUser(id_usuario)) {
			ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Usuário Inexistente");
		}
		if(!haveCode(id_usuario, codigo)) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Código incorreto ou antigo");
		}
		
		return null;
	}
	
	public boolean isUser(int id) {
		
		UsersEntity usuario = crudUsuario.findById(id);
		if(usuario == null) {
			return false;
		}
		
		return true;
	}
	
	public boolean haveCode(int id_usuario, String codigo) {
		
		UsersEntity usuario = crudUsuario.findById(id_usuario);
		AccessCodesEntity codigos = crudCodigo.findByUserAndCodeAndStatus(usuario, codigo,'A');
		
		if(codigos == null) {
			return false;
		}
		return true;
	}
	
}

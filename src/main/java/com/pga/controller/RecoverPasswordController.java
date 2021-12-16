package com.pga.controller;

import java.io.IOException;

import org.apache.commons.mail.EmailException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.pga.entity.AccessCodesEntity;
import com.pga.entity.UsersEntity;
import com.pga.repository.CodigosAcessoRepository;
import com.pga.repository.ConfigDataRepository;
import com.pga.repository.UserRepository;
import com.pga.utils.GenerateCodes;
import com.pga.utils.ModelEmail;
import com.pga.utils.SendMail;

@RestController
public class RecoverPasswordController {

	@Autowired
	private UserRepository crudUsuario;
	
	@Autowired
	private CodigosAcessoRepository crudCodigos;
	
	@Autowired
	private ConfigDataRepository crudConfig;
	
	@RequestMapping(method = RequestMethod.POST, path = "/send-recover-password")
	public ResponseEntity<String> recuperaSenha(@RequestBody UsersEntity form) throws IOException, EmailException{
		
		UsersEntity usuario = crudUsuario.findByEmail(form.getEmail());
		
		if(usuario.getStatus() == 'P') {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Conclua seu cadastro primeiro");
		}
		
		AccessCodesEntity codigo = crudCodigos.findByUserAndType(usuario, 'S');
		String pass = GenerateCodes.generate();
		
		if(codigo != null) {
			codigo.setCode(pass);
			codigo.setStatus('A');
			codigo.updateDate();
		} else {
			codigo = new AccessCodesEntity(0, usuario, GenerateCodes.generate(), 'A', null, 'S');
		}
		
		crudCodigos.save(codigo);
		
		ModelEmail new_email = new ModelEmail("model-email.html");
		new_email.setTitle("Recuperação de Senha");
		
		if(usuario.getName().contains(" ")) {
			new_email.setMsg1("Olá " + usuario.getName().substring(0,usuario.getName().indexOf(" ")) + "!");
		} else {
			new_email.setMsg1("Olá " + usuario.getName() + "!");
		}
		
		new_email.setMsg2("Acesse o link abaixo para redefinir sua Senha:");
		new_email.setBtn("Redefinir Senha");
		new_email.setLink("/definir-senha?id=" + usuario.getId() + "&code=" + pass);
		
		SendMail.send("Recuperação de Senha", new_email.generateMail(), form.getEmail(), crudConfig.findAllByType("EMAIL"));

		return ResponseEntity.status(HttpStatus.OK).body("Verifique sua caixa de e-mail");
	}
	
}

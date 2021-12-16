package com.pga.controller;

import java.io.IOException;

import org.apache.commons.mail.EmailException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
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
public class CadastroController {
	
	@Autowired
	private UserRepository crudUsuario;
	
	@Autowired
	private CodigosAcessoRepository crudCodigos;

	@Autowired
	private ConfigDataRepository crudConfig;
	
	@PostMapping
	@RequestMapping(method = RequestMethod.POST, path = "/cadastrar")
	public ResponseEntity<String> cadastrar(@RequestBody UsersEntity form) throws IOException, EmailException{
		
		UsersEntity userTemp = crudUsuario.findByEmail(form.getEmail());
		
		if(userTemp != null && userTemp.getStatus() == 'A') {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Já existe um usuário cadastrado com este e-mail."); 
		} else {
			userTemp = crudUsuario.findByRegistry(form.getRegistry());
			
			if(userTemp != null && userTemp.getStatus() == 'A') {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Já existe um usuário cadastrado com este RE."); 
			} else if(form.getRegistry().length() > 6) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("O RE deve conter até 6 dígitos");
			}
		}


		UsersEntity usuario = null;
		
		if(userTemp == null) {
			usuario = new UsersEntity(0, form.getName(), form.getRegistry(), form.getEmail(),"", form.getDate(), 'P');
			crudUsuario.save(usuario);
		} else {
			usuario = userTemp;
		}

		String pass = GenerateCodes.generate();
		
		AccessCodesEntity codigoExists = crudCodigos.findByUserAndType(usuario,'C');
		AccessCodesEntity newCodigo = null;
		
		if(codigoExists != null) {
			codigoExists.setCode(pass);
			codigoExists.setStatus('A');
			codigoExists.updateDate();
			crudCodigos.save(codigoExists);
		} else {
			newCodigo = new AccessCodesEntity(0, usuario, pass, 'A', null, 'C');
			crudCodigos.save(newCodigo);
		}
		
		ModelEmail email = new ModelEmail("model-email.html");
		email.setTitle("Conclusão de Cadastro");
		
		if(usuario.getName().contains(" ")) {
			email.setMsg1(usuario.getName().substring(0,usuario.getName().indexOf(" ")) + ", Bem-Vindo(a) ao PGA!");
		} else {
			email.setMsg1(usuario.getName() + ", Bem-Vindo(a) ao PGA!");
		}

		email.setMsg2("Acesse o link abaixo para Concluir o cadastro e definir uma senha:");
		email.setBtn("Concluir Cadastro");
		email.setLink("/definir-senha?id=" + usuario.getId() + "&code=" + pass);
		
		String status = SendMail.send(email.getTitle(), email.generateMail(), form.getEmail(),crudConfig.findAllByType("EMAIL"));
		
		if(status.contains("#999")) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(status); 
		}
		
		return ResponseEntity.status(HttpStatus.OK).body("Verifique sua caixa de e-mail para concluir o cadastro."); 
		
	}
	
}
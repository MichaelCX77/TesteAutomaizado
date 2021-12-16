package com.pga.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.pga.entity.UsersEntity;

@RestController
public class LoginController {
	
	@RequestMapping(method = RequestMethod.POST, path = "/logar")
	public ResponseEntity<String> logar(@RequestBody UsersEntity te){
		
		System.out.println(te.getEmail() + te.getPassword());
		
		return ResponseEntity.status(HttpStatus.OK).body("Login Efetuado com Sucesso");
	}
	
}
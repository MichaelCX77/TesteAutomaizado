package com.pga.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.pga.entity.UserPreferencesEntity;
import com.pga.entity.UsersEntity;
import com.pga.repository.UserPreferencesRepository;
import com.pga.repository.UserRepository;

@RestController
public class PreferencesController {

	@Autowired
	private UserRepository crudUsuario;
	
	@Autowired
	private UserPreferencesRepository crudPreferences;
	

	@RequestMapping(method = RequestMethod.GET, path = "/preferences")
	public UserPreferencesEntity getPreferences(){
		
		UsersEntity user = crudUsuario.findByEmail(getUserSession().getUsername());
		return crudPreferences.findByUser(user);
	}
	
	@RequestMapping(method = RequestMethod.POST, path = "/update-preferences")
	public ResponseEntity<UserPreferencesEntity> getPreferences(@RequestBody UserPreferencesEntity form){
		
		UserPreferencesEntity preferences = crudPreferences.findByUser(crudUsuario.findByEmail(getUserSession().getUsername()));
		
		if (form.getAutoRefresh() != 'A' && form.getAutoRefresh() != 'I') {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(form);
		} else 	if(preferences == null) {
			return ResponseEntity.status(HttpStatus.NON_AUTHORITATIVE_INFORMATION).body(form);
		}

		preferences.setAutoRefresh(form.getAutoRefresh());
		crudPreferences.save(preferences);
		
		return ResponseEntity.status(HttpStatus.OK).body(preferences);
	}
	
	public UserDetails getUserSession() {
		return (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	}
	
}

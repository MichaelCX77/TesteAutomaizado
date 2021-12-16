package com.pga.auxiliar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.pga.entity.UsersEntity;
import com.pga.repository.UserRepository;

@RestController
public class UsersTestController {

	@Autowired
	private UserRepository ur;
	
	
	public void createUser(UsersEntity user) {
		
		UsersEntity usercopy = new UsersEntity();
		usercopy.setName("Michael");
		
		ur.save(usercopy);
		
	}
}

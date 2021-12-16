package com.pga.repository;

import org.springframework.data.repository.CrudRepository;

import com.pga.entity.UsersEntity;

public interface UserRepository  extends CrudRepository<UsersEntity, String>{

	UsersEntity findByEmail(String email);

	UsersEntity findByRegistry(String registry);

	UsersEntity findById(int id);
	
}

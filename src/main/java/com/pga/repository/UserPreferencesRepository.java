package com.pga.repository;

import org.springframework.data.repository.CrudRepository;

import com.pga.entity.UserPreferencesEntity;
import com.pga.entity.UsersEntity;

public interface UserPreferencesRepository extends CrudRepository<UserPreferencesEntity, String> {

	UserPreferencesEntity findByUser(UsersEntity user);

}

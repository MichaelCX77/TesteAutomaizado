package com.pga.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.pga.entity.CardsEntity;
import com.pga.entity.UsersEntity;

public interface CardRepository extends CrudRepository<CardsEntity, String>{

	List<CardsEntity> findAllByUser(UsersEntity usuario);
	
	List<CardsEntity> findAllByUserAndStatus(UsersEntity usuario, char status);
	
	CardsEntity findById(int id);
}
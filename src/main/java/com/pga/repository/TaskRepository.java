package com.pga.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.pga.entity.CardsEntity;
import com.pga.entity.TasksEntity;

public interface TaskRepository extends CrudRepository<TasksEntity, String>{

	List<TasksEntity> findAllByCard(CardsEntity card);
	
	List<TasksEntity> findAllByCardAndStatus(CardsEntity card, char status);

	List<TasksEntity> findAllByStatusAndCardIn(char status, List<CardsEntity> findAllByUser);
	
	TasksEntity findById(int id);
	
}
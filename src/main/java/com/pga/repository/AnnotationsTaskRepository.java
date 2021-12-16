package com.pga.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.pga.entity.TaskAnnotationsEntity;
import com.pga.entity.TasksEntity;

public interface AnnotationsTaskRepository extends CrudRepository<TaskAnnotationsEntity, String> {

	TaskAnnotationsEntity findById(int id);

	List<TaskAnnotationsEntity> findAllByTaskOrderByDateDesc(TasksEntity findById);
	
}

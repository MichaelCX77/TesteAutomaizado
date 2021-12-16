package com.pga.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.pga.entity.TasksEntity;
import com.pga.repository.CardRepository;
import com.pga.repository.TaskRepository;
import com.pga.repository.UserRepository;

@RestController
public class HistoricTasksController {

	@Autowired
	UserRepository crudUser;
	
	@Autowired
	TaskRepository crudTasks;
	
	@Autowired
	CardRepository crudCard;
	
	@RequestMapping(method = RequestMethod.GET, path = "/get-TasksHistoryList")
	public List<TasksEntity> getTaskHistoryList(){
	
		List<TasksEntity> listTasksHistoric = new ArrayList<TasksEntity>();
		
		listTasksHistoric = crudTasks.findAllByStatusAndCardIn('H',crudCard.findAllByUser(crudUser.findByEmail(getUserSession().getUsername())));
		
		return listTasksHistoric;
	}
	
	public UserDetails getUserSession() {
		return (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	}
	
}

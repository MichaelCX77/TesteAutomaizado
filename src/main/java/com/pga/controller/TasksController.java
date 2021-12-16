package com.pga.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.pga.entity.CardsEntity;
import com.pga.entity.TasksEntity;
import com.pga.repository.CardRepository;
import com.pga.repository.TaskRepository;
import com.pga.repository.UserRepository;

@RestController
public class TasksController {
	
	@Autowired
	private TaskRepository crudTask;
	
	@Autowired
	private CardRepository crudCard;
	
	@Autowired
	private UserRepository crudUser;
	
	@RequestMapping(method = RequestMethod.GET, path = "/view-task-detail")
	public ResponseEntity<TasksEntity> getDetailTask(@RequestParam(value="id", required=true) int id){
		
		TasksEntity tarefa = crudTask.findById(id);
		
		if(tarefa != null) {
			if(!tarefa.getCard().getUser().getEmail().equals(getUserSession().getUsername())) {
				return ResponseEntity.status(HttpStatus.NON_AUTHORITATIVE_INFORMATION).body(new TasksEntity());
			}
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(tarefa);
		}

		return ResponseEntity.status(HttpStatus.OK).body(tarefa);
	}
	
	@RequestMapping(method = RequestMethod.POST, path = "/update-task")
	public ResponseEntity<TasksEntity> updateTask(@RequestBody TasksEntity form){
		
		TasksEntity tarefa = crudTask.findById(form.getId());
		
		if(tarefa != null) {
			if(!verifyIdentity(tarefa.getCard().getUser().getEmail())) {
				return ResponseEntity.status(HttpStatus.NON_AUTHORITATIVE_INFORMATION).body(form);
			}
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(form);
		}
		
		if(tarefa.getStatus() == 'H') {
			return ResponseEntity.status(HttpStatus.NOT_MODIFIED).body(form);
		}
		
		if(form.getName() != null) {
			tarefa.setName(form.getName());
		} else if(form.getDescription() != null) {
			tarefa.setDescription(form.getDescription());
		} else if (form.getDateEnd() != null || form.getDateStart() != null) {
			if(form.getDateStart() != null) {
				tarefa.setDateStart(form.getDateStart());
			}
			if(form.getDateEnd() != null) {
				tarefa.setDateEnd(form.getDateEnd());
			}
		} else if(form.getLinkJira() != null) {
			tarefa.setLinkJira(form.getLinkJira());
		} else if(form.getFlagAlert() == 'A' || form.getFlagAlert() == 'I') {
			tarefa.setFlagAlert(form.getFlagAlert());
		} else if(form.getFlagAlertYellow() == 'A' || form.getFlagAlertYellow() == 'I'){
			tarefa.setFlagAlertYellow(form.getFlagAlertYellow());
			tarefa.setAlertYellowDate(form.getAlertYellowDate());
		} else if(form.getFlagAlertRed() == 'A' || form.getFlagAlertRed() == 'I'){
			tarefa.setFlagAlertRed(form.getFlagAlertRed());
			tarefa.setAlertRedDate(form.getAlertRedDate());
		} else if(form.getDescAlert() != null) {
			tarefa.setDescAlert(form.getDescAlert());
		}
		
		return ResponseEntity.status(HttpStatus.OK).body(crudTask.save(tarefa));
	}
	
	@RequestMapping(method = RequestMethod.GET, path = "/get-listTasks")
	public List<TasksEntity> getListTasks(){
		
		List<CardsEntity> listCards = crudCard.findAllByUser(crudUser.findByEmail(getUserSession().getUsername()));
		List<TasksEntity> listTasksEntities = new ArrayList<TasksEntity>();
		
		for (int i = 0; i < listCards.size(); i++) {
			listTasksEntities.addAll(crudTask.findAllByCard(listCards.get(i)));
		}
		
		return listTasksEntities;
	}
	
	public boolean verifyIdentity(String email) {
		
		UserDetails userSession = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if(email.equals(userSession.getUsername())) {
			return true;
		}
		
		return false;
	}
	
	public UserDetails getUserSession() {
		return (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	}
	
}



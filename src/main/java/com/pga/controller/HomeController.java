package com.pga.controller;

import java.sql.Timestamp;
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
import com.pga.entity.UsersEntity;
import com.pga.repository.CardRepository;
import com.pga.repository.TaskRepository;
import com.pga.repository.UserRepository;

@RestController
public class HomeController {
	
	@Autowired
	private UserRepository crudUsuario;
	
	@Autowired
	private CardRepository crudCard;
	
	@Autowired
	private TaskRepository crudTask;

	@RequestMapping(method = RequestMethod.POST, path = "/create-card")
	public CardsEntity createNewCard(@RequestBody CardsEntity form){
		CardsEntity card = new CardsEntity(crudUsuario.findByEmail(getUserSession().getUsername()), form.getName(), new Timestamp(System.currentTimeMillis()));
		return crudCard.save(card);
	}
	
	@RequestMapping(method = RequestMethod.POST, path = "/update-card")
	public ResponseEntity<TasksEntity> updateCard(@RequestBody TasksEntity form){
		
		CardsEntity card = crudCard.findById(form.getCard().getId());
		
		if(card == null || !card.getUser().getEmail().equals(getUserSession().getUsername())) {
			return ResponseEntity.status(HttpStatus.NON_AUTHORITATIVE_INFORMATION).body(new TasksEntity());
		}
		
		TasksEntity taskDb = crudTask.findById(form.getId());
		taskDb.setCard(card);
		
		return ResponseEntity.status(HttpStatus.OK).body(crudTask.save(taskDb));
		
	}
	
	@RequestMapping(method = RequestMethod.POST, path = "/update-card2")
	public ResponseEntity<CardsEntity> updateCard2(@RequestBody CardsEntity form){
		
		CardsEntity card = crudCard.findById(form.getId());
		
		if(card == null || !card.getUser().getEmail().equals(getUserSession().getUsername())) {
			return ResponseEntity.status(HttpStatus.NON_AUTHORITATIVE_INFORMATION).body(new CardsEntity());
		}
		card.setName(form.getName());
		return ResponseEntity.status(HttpStatus.OK).body(crudCard.save(card));
		
	}
	
	@RequestMapping(method = RequestMethod.GET, path = "/get-cards")
	public List<CardsEntity> getCards(){
		return crudCard.findAllByUserAndStatus(crudUsuario.findByEmail(getUserSession().getUsername()),'A');
	}
	
	@RequestMapping(method = RequestMethod.GET, path = "/get-tasks")
	public List<TasksEntity> getTasks(@RequestParam(value="id", required=true) int id){
		
		return crudTask.findAllByCardAndStatus(crudCard.findById(id), 'A');
	}
	
	@RequestMapping(method = RequestMethod.POST, path = "/create-task")
	public TasksEntity createNewTarefa(@RequestBody TasksEntity form){
		
		TasksEntity tarefa = new TasksEntity(crudCard.findById(form.getCard().getId()), form.getName());
		
		return crudTask.save(tarefa);
	}
	
	@RequestMapping(method = RequestMethod.DELETE, path = "/remove-task")
	public ResponseEntity<String> deleteTask(@RequestParam(value="id", required=true) int id){
		
		TasksEntity task = crudTask.findById(id);
		task.setStatus('H');
		crudTask.save(task);
		
		return ResponseEntity.status(HttpStatus.OK).body("Removido com Sucesso");
	}	
	
	@RequestMapping(method = RequestMethod.DELETE, path = "/remove-card")
	public ResponseEntity<String> deleteCard(@RequestParam(value="id", required=true) int id){
		
		List<TasksEntity> tarefas = crudTask.findAllByCard(crudCard.findById(id));
		
		for (int i = 0; i < tarefas.size(); i++) {
			tarefas.get(i).setStatus('H');
			crudTask.save(tarefas.get(i));
		}
		
		CardsEntity card = crudCard.findById(id);
		card.setStatus('H');
		crudCard.save(card);
		
		return ResponseEntity.status(HttpStatus.OK).body("Removido com Sucesso");
	}
	
	@RequestMapping(method = RequestMethod.GET, path = "/get-nameUser")
	public String getNameUser(){
		
		UsersEntity usuario = crudUsuario.findByEmail(getUserSession().getUsername());
		return usuario.getName();
	}
	
	public UserDetails getUserSession() {
		return (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	}
}

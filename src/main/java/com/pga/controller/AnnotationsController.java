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

import com.pga.entity.TaskAnnotationsEntity;
import com.pga.entity.TasksEntity;
import com.pga.repository.AnnotationsTaskRepository;
import com.pga.repository.TaskRepository;
import com.pga.repository.UserRepository;

@RestController
public class AnnotationsController {

	@Autowired
	private AnnotationsTaskRepository crudAnnotTasks;
	
	@Autowired
	private UserRepository crudUsuario;
	
	@Autowired
	private TaskRepository crudTask;
	
	@RequestMapping(method = RequestMethod.POST, path = "/create-annotation")
	public ResponseEntity<TaskAnnotationsEntity> createAnnotation(@RequestBody TaskAnnotationsEntity form){
		
		if(!verifyIdentity(crudTask.findById(form.getTask().getId()).getCard().getUser().getEmail())) {
			return ResponseEntity.status(HttpStatus.NON_AUTHORITATIVE_INFORMATION).body(new TaskAnnotationsEntity());
		}
		
		TasksEntity task = crudTask.findById(form.getTask().getId());
		if(task.getStatus() == 'H') {
			return ResponseEntity.status(HttpStatus.NOT_MODIFIED).body(form);
		}
		
		TaskAnnotationsEntity annotation = new TaskAnnotationsEntity(
				form.getDescription(), new Timestamp(System.currentTimeMillis()), 
				crudUsuario.findByEmail(getUserSession().getUsername()), 
				task);
		try {
			crudAnnotTasks.save(annotation);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new TaskAnnotationsEntity());
		}
		
		return ResponseEntity.status(HttpStatus.CREATED).body(annotation);
	}
	
	@RequestMapping(method = RequestMethod.GET, path = "/get-annotations")
	public List<TaskAnnotationsEntity> getAnnotations(@RequestParam(value="id", required=true) int idTask){
		return crudAnnotTasks.findAllByTaskOrderByDateDesc(crudTask.findById(idTask));
	}
	
	@RequestMapping(method = RequestMethod.DELETE, path = "/delete-annotation")
	public ResponseEntity<String> deleteAnnotation(@RequestParam(value="id", required=true) int idAnnotation){
		
		if(!verifyIdentity(crudAnnotTasks.findById(idAnnotation).getTask().getCard().getUser().getEmail())) {
			return ResponseEntity.status(HttpStatus.NON_AUTHORITATIVE_INFORMATION).body("Ação não permitida");
		}
		
		TasksEntity task = crudTask.findById(crudAnnotTasks.findById(idAnnotation).getTask().getId());
		if(task.getStatus() == 'H') {
			return ResponseEntity.status(HttpStatus.NOT_MODIFIED).body("Tarefa faz parte do histórico, não pode ser modificada");
		}
		
		try {
			crudAnnotTasks.delete(crudAnnotTasks.findById(idAnnotation));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: " + e);
		}
		
		return ResponseEntity.status(HttpStatus.OK).body("Deletado com Sucesso");
	}
	
	@RequestMapping(method = RequestMethod.POST, path = "/update-annotation")
	public ResponseEntity<TaskAnnotationsEntity> updateAnnotation(@RequestBody TaskAnnotationsEntity form){
		
		if(!verifyIdentity(crudAnnotTasks.findById(form.getId()).getTask().getCard().getUser().getEmail())) {
			return ResponseEntity.status(HttpStatus.NON_AUTHORITATIVE_INFORMATION).body(new TaskAnnotationsEntity());
		}
		
		TasksEntity task = crudTask.findById(crudAnnotTasks.findById(form.getId()).getTask().getId());
		if(task.getStatus() == 'H') {
			return ResponseEntity.status(HttpStatus.NOT_MODIFIED).body(form);
		}
		
		 TaskAnnotationsEntity annotation = crudAnnotTasks.findById(form.getId());
		 annotation.setDescription(form.getDescription());

		return ResponseEntity.status(HttpStatus.OK).body(crudAnnotTasks.save(annotation));
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

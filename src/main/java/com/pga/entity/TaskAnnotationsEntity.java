package com.pga.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table (name = "TASK_ANNOTATIONS")
public class TaskAnnotationsEntity {

	@Id
	@GeneratedValue(strategy =  GenerationType.IDENTITY)
	@Column (name = "TASK_ANNOT_ID")
	private int id;
	
	@Column (name = "TASK_ANNOT_DESC")
	private String description;
	
	@Column (name = "TASK_ANNOT_DATE")
	private Timestamp date;
	
	@ManyToOne (fetch = FetchType.EAGER)
	@JoinColumn (name = "USER_ID", referencedColumnName = "USER_ID")
	private UsersEntity user;
	
	@ManyToOne (fetch = FetchType.EAGER)
	@JoinColumn (name = "TASK_ID", referencedColumnName = "TASK_ID")
	private TasksEntity task;
	
	public TaskAnnotationsEntity() {
		super();
	}

	public TaskAnnotationsEntity(String description, Timestamp date, UsersEntity user, TasksEntity task) {
		super();
		this.description = description;
		this.date = date;
		this.user = user;
		this.task = task;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public TasksEntity getTask() {
		return task;
	}

	public void setTask(TasksEntity task) {
		this.task = task;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Timestamp getDate() {
		return date;
	}

	public void setDate(Timestamp date) {
		this.date = date;
	}

	public UsersEntity getUser() {
		return user;
	}

	public void setUser(UsersEntity user) {
		this.user = user;
	}
}

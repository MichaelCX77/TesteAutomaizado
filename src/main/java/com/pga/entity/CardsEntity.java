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
@Table (name = "CARDS")
public class CardsEntity {

	@Id
	@GeneratedValue(strategy =  GenerationType.IDENTITY)
	@Column (name ="CARD_ID")
	private int id;
	
	@ManyToOne (fetch = FetchType.EAGER)
	@JoinColumn (name = "USER_ID", referencedColumnName  = "USER_ID")
	private UsersEntity user;
	
	@Column (name = "CARD_NAME")
	private String name;
	
	@Column (name = "CARD_DATE")
	private Timestamp date;
	
	@Column (name = "CARD_STATUS")
	private char status;
	
	public CardsEntity() {
		
	}
	
	public CardsEntity(UsersEntity user, String name, Timestamp date) {
		super();
		this.user = user;
		this.name = name;
		this.date = date;
		this.status = 'A';
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public UsersEntity getUser() {
		return user;
	}

	public void setUser(UsersEntity user) {
		this.user = user;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Timestamp getDate() {
		return date;
	}

	public void setDate(Timestamp date) {
		this.date = date;
	}

	public char getStatus() {
		return status;
	}

	public void setStatus(char status) {
		this.status = status;
	}
	
}

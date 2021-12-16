package com.pga.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "user_preferences")
public class UserPreferencesEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "pref_id")
	private int id;
	
	@OneToOne (fetch = FetchType.EAGER)
	@JoinColumn (name = "user_id", referencedColumnName = "user_id")
	private UsersEntity user;
	
	@Column(name = "auto_refresh")
	private char autoRefresh;

	public UserPreferencesEntity() {
		super();
	}

	public UserPreferencesEntity(UsersEntity user) {
		this.user = user;
		this.autoRefresh = 'I';
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

	public char getAutoRefresh() {
		return autoRefresh;
	}

	public void setAutoRefresh(char autoRefresh) {
		this.autoRefresh = autoRefresh;
	}
	
}

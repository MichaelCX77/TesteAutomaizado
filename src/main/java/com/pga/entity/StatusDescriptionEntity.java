package com.pga.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table (name="STATUS_DESC")
public class StatusDescriptionEntity {

	@Id
	@Column (name = "STATUS")
	private char status;
	
	@Column (name = "DESCRIPTION")
	private String description;

	public char getStatus() {
		return status;
	}

	public void setStatus(char status) {
		this.status = status;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
}

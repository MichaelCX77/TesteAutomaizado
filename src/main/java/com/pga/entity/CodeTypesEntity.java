package com.pga.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table (name="CODE_TYPES")
public class CodeTypesEntity {
	
	@Id
	@Column (name = "CODE_TYPE")
	private char type;
	
	@Column (name = "CODE_DESC")
	private String description;
	
	public char getType() {
		return type;
	}

	public void setType(char type) {
		this.type = type;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
}

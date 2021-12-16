package com.pga.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table (name="CONFIG_DATA")
public class ConfigDataEntity {

	@Id
	@GeneratedValue(strategy =  GenerationType.IDENTITY)
	@Column (name ="ID_CONFIG")
	private int id;
	
	@Column (name = "CONFIG_TYPE")
	private String type;
	
	@Column (name = "CONFIG_NAME")
	private String name;
	
	@Column (name = "CONFIG_VALUE")
	private String value;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
}

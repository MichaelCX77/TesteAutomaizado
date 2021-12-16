package com.pga.entity;

import java.sql.Date;
import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.pga.utils.DatasUtil;

@Entity
@Table (name="ACCESS_CODES")
public class AccessCodesEntity {

	@Id
	@GeneratedValue(strategy =  GenerationType.IDENTITY)
	@Column (name = "ID_CODE")
	private int id;
	
	@ManyToOne (fetch = FetchType.EAGER)
	@JoinColumn (name = "USER_ID", referencedColumnName = "USER_ID")
	private UsersEntity user;
	
	@Column (name = "CODE")
	private String code;
	
	@Column (name = "CODE_STATUS")
	private char status;
	
	@Column (name = "CODE_DATE")
	private Date date;
	
	@Column (name = "CODE_TYPE")
	private char type;

	public AccessCodesEntity() {
		super();
	}

	public AccessCodesEntity(int id, UsersEntity user, String code, char status, Date date, char type) {
		super();
		this.user = user;
		this.code = code;
		this.status = status;
		this.type = type;
		
		Calendar cal = DatasUtil.getCalendarDate();
		this.date = new java.sql.Date(cal.getTimeInMillis());;
	}

	public UsersEntity getUser() {
		return user;
	}

	public void setUser(UsersEntity user) {
		this.user = user;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public char getStatus() {
		return status;
	}

	public void setStatus(char status) {
		this.status = status;
	}

	public Date getDate() {
		return date;
	}

	public void updateDate() {
		Calendar cal = DatasUtil.getCalendarDate();
		this.date = new java.sql.Date(cal.getTimeInMillis());
	}

	public char getType() {
		return type;
	}

	public void setType(char type) {
		this.type = type;
	}
	
}

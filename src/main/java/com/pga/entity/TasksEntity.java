package com.pga.entity;

import java.sql.Date;
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
@Table (name = "TASKS")
public class TasksEntity {

	@Id
	@GeneratedValue(strategy =  GenerationType.IDENTITY)
	@Column (name = "TASK_ID")
	private int id;
	
	@ManyToOne (fetch = FetchType.EAGER)
	@JoinColumn (name = "CARD_ID", referencedColumnName = "CARD_ID")
	private CardsEntity card;
	
	@Column (name = "TASK_NAME")
	private String name;
	
	@Column (name = "TASK_DESC")
	private String description;
	
	@Column (name = "TASK_DATE_START")
	private Date dateStart;
	
	@Column (name = "TASK_DATE_END")
	private Date dateEnd;
	
	@Column (name = "TASK_ALERT")
	private char flagAlert;
	
	@Column (name = "TASK_ALERT_YELLOW")
	private char flagAlertYellow;
	
	@Column (name = "TASK_ALERT_RED")
	private char flagAlertRed;
	
	@Column (name = "TASK_ALERT_DESC")
	private String descAlert;
	
	@Column (name = "TASK_ALERT_YELLOW_DATE")
	private Timestamp alertYellowDate;
	
	@Column (name = "TASK_ALERT_RED_DATE")
	private Timestamp alertRedDate;
	
	@Column (name = "TASK_LINK_JIRA")
	private String linkJira;
	
	@Column (name = "TASK_STATUS")
	private char status;
	
	public TasksEntity() {
		super();
	}

	public TasksEntity(CardsEntity card, String name) {
		super();
		this.card = card;
		this.name = name;
		this.description = null;
		this.descAlert = null;
		this.alertYellowDate = null;
		this.alertRedDate = null;
		this.flagAlert = 'I';
		this.flagAlertYellow = 'I';
		this.flagAlertRed = 'I';
		this.status = 'A';
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public CardsEntity getCard() {
		return card;
	}

	public void setCard(CardsEntity card) {
		this.card = card;
	}

	public String getName() {
		return name;
	}

	public void setName(String nome) {
		this.name = nome;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public char getFlagAlert() {
		return flagAlert;
	}

	public void setFlagAlert(char flagAlert) {
		this.flagAlert = flagAlert;
	}

	public String getDescAlert() {
		return descAlert;
	}

	public void setDescAlert(String descAlert) {
		this.descAlert = descAlert;
	}

	public Timestamp getAlertYellowDate() {
		return alertYellowDate;
	}

	public void setAlertYellowDate(Timestamp alertYellowDate) {
		this.alertYellowDate = alertYellowDate;
	}

	public Timestamp getAlertRedDate() {
		return alertRedDate;
	}

	public void setAlertRedDate(Timestamp alertRedDate) {
		this.alertRedDate = alertRedDate;
	}

	public Date getDateStart() {
		return dateStart;
	}

	public void setDateStart(Date dataStart) {
		this.dateStart = dataStart;
	}

	public Date getDateEnd() {
		return dateEnd;
	}

	public void setDateEnd(Date dateEnd) {
		this.dateEnd = dateEnd;
	}

	public String getLinkJira() {
		return linkJira;
	}

	public void setLinkJira(String linkJira) {
		this.linkJira = linkJira;
	}

	public char getFlagAlertYellow() {
		return flagAlertYellow;
	}

	public void setFlagAlertYellow(char flagAlertYellow) {
		this.flagAlertYellow = flagAlertYellow;
	}

	public char getFlagAlertRed() {
		return flagAlertRed;
	}

	public void setFlagAlertRed(char flagAlertRed) {
		this.flagAlertRed = flagAlertRed;
	}

	public char getStatus() {
		return status;
	}

	public void setStatus(char status) {
		this.status = status;
	}	
	
}

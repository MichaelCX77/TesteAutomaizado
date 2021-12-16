package com.pga.utils;

import java.io.IOException;
import java.util.Date;

public class ModelEmail {

	private String model;
	
	private String title;
	
	private String date;
	
	private String msg1;
	
	private String msg2;
	
	private String link;
	
	private String btn;
	
	private String PREFIX_LINK = "https://pga-oss.herokuapp.com";
	
	public ModelEmail(String model) throws IOException {
		this.date = DatasUtil.convertAndFormatToString(new Date(), "dd/MM/YYYY");
		this.model = ReadFiles.read("others/modelMail/" + model);
	}
	public String getModel() {
		return model;
	}
	public void setModel(String model) {
		this.model = model;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getMsg1() {
		return msg1;
	}
	public void setMsg1(String msg1) {
		this.msg1 = msg1;
	}
	public String getMsg2() {
		return msg2;
	}
	public void setMsg2(String msg2) {
		this.msg2 = msg2;
	}
	public String getLink() {
		return link;
	}
	public void setLink(String link) throws IOException {
		
//		String file_config = ReadFiles.read("application.properties");
//		String port = file_config.substring(12, 16);
		this.link = this.PREFIX_LINK + link;
	}
	public String getBtn() {
		return btn;
	}
	public void setBtn(String btn) {
		this.btn = btn;
	}
	
	public String generateMail() {
		
		if(this.model == null) {
			throw new NullPointerException("O modelo não pode ser null");
		} 
		this.model = this.model.replace(EmailEnum.TITLE.toString(), this.title == null ? "" : this.title);
		this.model = this.model.replace(EmailEnum.DATE.toString(), this.date == null ? "" : this.date);
		this.model = this.model.replace(EmailEnum.MSG1.toString(), this.msg1 == null ? "" : this.msg1);
		this.model = this.model.replace(EmailEnum.MSG2.toString(), this.msg2 == null ? "" : this.msg2);
		this.model = this.model.replace(EmailEnum.LINK.toString(), this.link == null ? "" : this.link);
		this.model = this.model.replace(EmailEnum.BTN.toString(), this.btn == null ? "" : this.btn);
		
		return this.model;
	}
	public void resetEmail() {
		this.model = null;
		this.title = null;
		this.date = null;
		this.msg1 = null;
		this.msg2 = null;
		this.link = null;
		this.btn = null;
	}
}

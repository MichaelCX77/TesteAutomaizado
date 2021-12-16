package com.pga.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.pga.PgaApplication;
import com.pga.entity.ConfigDataEntity;
import com.pga.repository.ConfigDataRepository;

@Controller
public class TemplatesController {
	
	@Autowired
	ConfigDataRepository crudConfig;

	@RequestMapping(method = RequestMethod.GET, path = "/index")
	public ModelAndView goIndex(){
		
		ConfigDataEntity version = crudConfig.findByTypeAndName("APPLICATION","VERSION");
		
		float actual_version = Float.parseFloat(version.getValue());
		return (PgaApplication.getApplVersion() < actual_version) ? criaMV("index").addObject("version","minorVersion") : criaMV("index").addObject("version",PgaApplication.getApplVersion());
	}
	
	@RequestMapping(method = RequestMethod.GET, path = "/redirect-to-update-page")
	public void rediretToUpdatePage() throws IOException{
		
		ConfigDataEntity updatePage = crudConfig.findByTypeAndName("APPLICATION", "UPDATE_PAGE");
		Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler " + updatePage.getValue());
		System.exit(0);
	}
	
	@RequestMapping(method = RequestMethod.GET, path = "/home")
	public ModelAndView goHome1(){return criaMV("home");}
	
	@RequestMapping (method = RequestMethod.GET, path = "/")
	public ModelAndView goHome2() {return criaMV("home");}
	
	@RequestMapping(method = RequestMethod.GET, path = "detail-task")
	public ModelAndView goDetailTasks() { return criaMV("detail-task");}

	@RequestMapping(method = RequestMethod.GET, path = "fragments/header")
	public ModelAndView getHeader() { return criaMV("fragments/header");}
	
	@RequestMapping(method = RequestMethod.GET, path = "fragments/itens-menu")
	public ModelAndView getMenu() { return criaMV("fragments/itens-menu");}
	
	@RequestMapping(method = RequestMethod.GET, path = "/cadastro")
	public ModelAndView getCadastro() { return criaMV("cadastro");}
	
	@RequestMapping(method = RequestMethod.GET, path = "/recuperar-senha")
	public ModelAndView getRecSenha() { return criaMV("recuperar-senha");}
	
	@RequestMapping(method = RequestMethod.GET, path = "/definir-senha")
	public ModelAndView getRedSenha() { return criaMV("definir-senha");}
	
	@RequestMapping(method = RequestMethod.GET, path = "/historic-tasks")
	public ModelAndView getHistoricTasks() { return criaMV("historic-tasks");}
	
	public ModelAndView criaMV(String name) {
		
		ModelAndView mv = new ModelAndView(name);
		mv.setViewName(name);
		return mv;
	}
}

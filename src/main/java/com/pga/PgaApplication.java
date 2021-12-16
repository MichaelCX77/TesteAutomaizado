package com.pga;

import java.awt.AWTException;
import java.io.IOException;
import java.net.URISyntaxException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PgaApplication {
	
//	Versão da aplicação - utilizada pra forçar update para novas versões
	public static final float APPL_VERSION = (float) 1.4;
	
	public static void main(String[] args) throws IOException, URISyntaxException, AWTException {
		
//		try {
//			new LoadView();
			SpringApplication.run(PgaApplication.class, args);
//		} catch (PortInUseException e) {
//			System.out.println("Porta em uso: 8090");
//		}
		
//		LoadView.getLoadFrame().setVisible(false);
//		TrayIcon.create();
//		System.out.println("ABRINDO NAVEGADOR");
//	    Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler " + "http://127.0.0.1:8090");
	}

	public static float getApplVersion() {
		return APPL_VERSION;
	}

}
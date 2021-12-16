package com.pga.utils;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;

import com.pga.entity.ConfigDataEntity;

// Classe padr�o que abre as conex�es que permitem o envio do e-mail
	public class SendMail {
		
		public static String send(String titulo, String conteudo, String destinatarios, List<ConfigDataEntity> configs) throws EmailException, IOException {
			
			String status = "";
			String[] list_recipients = destinatarios.split(";");
			
			HtmlEmail email = configure(configs);
			String logo = email.embed(ReadFiles.getFile("others/img/logoPGAabr-mini.png"));
		    conteudo = conteudo.replace("LOGO", logo);
		    
			try {
				
	            email.setDebug(true);
				for (String to : list_recipients) {
					email.addTo(to);
				}
	            email.setSubject(titulo);
	            email.setCharset("UTF-8");
	            email.setHtmlMsg(conteudo);
	            
				try {
					email.send();
					status = "#001 Email Enviado!";
					System.out.println("Email Enviado!");
				} catch (Exception e) {
					status = "#999 Problema ao enviar email: " + e.toString();
					e.printStackTrace();
				}

	        } catch (Exception e) {
	        	status = "#999 Problema ao enviar email: " + e.toString();
	            e.printStackTrace();
	        }
			
			return status;
			
		}
		
		public static HtmlEmail configure(List<ConfigDataEntity> configs) throws EmailException {
		
			HtmlEmail email = new HtmlEmail();
			
			Map<String, String> mapconfigs = new HashMap<String, String>();
			
			for (ConfigDataEntity config : configs) {
				
				mapconfigs.put(config.getName(), config.getValue());
			}
			
            
            email.setAuthenticator(new DefaultAuthenticator(mapconfigs.get("USER"), mapconfigs.get("PASSWORD")));
            email.setHostName(mapconfigs.get("HOST"));
            email.setFrom(mapconfigs.get("USER"), "PGA");
            email.setSmtpPort(Integer.parseInt(mapconfigs.get("PORT")));
            
            if(mapconfigs.get("CRIPTO").equals("TLS")) {
            	email.setSSLOnConnect(true);
            } else if(mapconfigs.get("CRIPTO").equals("STARTTLS")) {
            	email.setStartTLSEnabled(true);
            }
            
//            email.setSSL(true);
			
			return email;
		}

}

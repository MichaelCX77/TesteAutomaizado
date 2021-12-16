package com.pga.data;

import java.net.URI;
import java.net.URISyntaxException;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataConfiguration {

//	PostGress Nuvem
//	@Bean
//    public BasicDataSource dataSource() throws URISyntaxException {
//        URI dbUri = new URI(System.getenv("DATABASE_URL"));
//
//        String username = dbUri.getUserInfo().split(":")[0];
//        String password = dbUri.getUserInfo().split(":")[1];
//        String dbUrl = "jdbc:postgresql://" + dbUri.getHost() + ':' + dbUri.getPort() + dbUri.getPath();
//
//        BasicDataSource basicDataSource = new BasicDataSource();
//        basicDataSource.setUrl(dbUrl);
//        basicDataSource.setUsername(username);
//        basicDataSource.setPassword(password);
//
//        return basicDataSource;
//    }	
	
// Conexão com Produção
	
//	@Bean
//	public BasicDataSource dataSource() throws URISyntaxException {
//		BasicDataSource basicDataSource = new BasicDataSource();
//		basicDataSource.setUrl("jdbc:postgresql://ec2-23-23-88-216.compute-1.amazonaws.com:5432/d8tqgth9b3ega8");
//		basicDataSource.setUsername("gjvynekcpgdwtw");
//		basicDataSource.setPassword("61a6062ef8a5db4a3f3f7063c1f0a46901130d994a9b9ad712f7e86369843156");
//		return basicDataSource;
//	}
	
	
// Conexão com Homologação
	
//		@Bean
//		public BasicDataSource dataSource() throws URISyntaxException {
//			BasicDataSource basicDataSource = new BasicDataSource();
//			basicDataSource.setUrl("jdbc:postgresql://ec2-54-158-247-97.compute-1.amazonaws.com:5432/dc6hu1kpmp20bd");
//			basicDataSource.setUsername("lirgirjozxlyst");
//			basicDataSource.setPassword("61a6062ef8a5db4a3f3f7063c1f0a46901130d994a9b9ad712f7e86369843156");
//			return basicDataSource;
//		}
	
}

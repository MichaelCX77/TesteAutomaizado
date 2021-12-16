package com.pga.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter{
	
	
	@Autowired
	private ImplementsUserDetailsService userDetailsService;
	
	@Override
	protected void configure(HttpSecurity http) throws Exception{
		http.csrf().disable().authorizeRequests()
		.antMatchers("/css/**", "/js/**","/img/**","/gif/**").permitAll()
		.antMatchers("/index","/cadastro","/recuperar-senha","/definir-senha","/verify-data","/redirect-to-update-page").permitAll()
		.anyRequest().authenticated()
		.and().formLogin().loginPage("/index").permitAll()
		.and().logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout"));
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception{
		auth.userDetailsService(userDetailsService)
		.passwordEncoder(new BCryptPasswordEncoder());
	}

	@Override
	public void configure(WebSecurity web) throws Exception{
		web.ignoring().antMatchers("/cadastrar","/send-recover-password","/redefinir-senha","/verify-data","/redirect-to-update-page");
	}
	
//	@Override
//	protected void configure(HttpSecurity http) throws Exception{
//		http.csrf().disable().authorizeRequests()
//		.antMatchers("/css/**", "/js/**","/imagens/**","/assets/**","/materialize/**").permitAll()
//		.antMatchers(HttpMethod.GET, "/").permitAll()
//		.antMatchers("/index").permitAll()  
//		.antMatchers("/logout").permitAll()
//		.anyRequest().authenticated()
//		.and()
//		.formLogin().loginPage("/index").permitAll()
//		.and().logout()
//        .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
//        .logoutSuccessUrl("/index").and().exceptionHandling()
//        .accessDeniedPage("/access-denied")
//		.and().formLogin().loginPage("/index").permitAll()
//		;
//		
//	}
//	
//	@Override
//	protected void configure(AuthenticationManagerBuilder auth) throws Exception{
//		auth.userDetailsService(userDetailsService)
//		.passwordEncoder(new BCryptPasswordEncoder());
//	}
//	
//	@Override
//	public void configure(WebSecurity web) throws Exception{
//		web.ignoring().antMatchers("/assets/**", "/css/**","/imagens/**", "/materialize/**",
//				"/js/**","/cadastro-aluno","/recuperar-senha","/libera-cadastro**",
//				"/altera-senha","/cadastro-professor","/cadastro-escola",
//				"/confirma-codigo","/criar-conta","/for-schools",
//				"/for-schools","/mensagem","/recuperar-senha","fragments/footer");
//	}

}

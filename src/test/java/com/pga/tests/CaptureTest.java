package com.pga.tests;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.pga.auxiliar.UsersTestController;
import com.pga.entity.UsersEntity;
import com.pga.repository.UserRepository;

public class CaptureTest {

	@InjectMocks
	private UsersTestController service;
	
	@Mock
	private UserRepository ur;
	
	@Before
	public void initialize(){
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void testPersistence() {
		
		UsersEntity usuario = new UsersEntity(1,"Joana Dark",null,null,null,null,'A');
		
		service.createUser(usuario);
		
		ArgumentCaptor<UsersEntity> captUser = ArgumentCaptor.forClass(UsersEntity.class);
		verify(ur).save(captUser.capture());
		
		assertThat("Michael", is(captUser.getValue().getName()));
		
	}
	
}

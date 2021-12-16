package com.pga.tests;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;
import org.junit.rules.ExpectedException;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.pga.controller.DefinePasswordController;
import com.pga.entity.UsersEntity;
import com.pga.repository.UserRepository;

public class TestMocks {

	@InjectMocks
	DefinePasswordController dpc;
	
	@Mock
	UserRepository crudUser;
	
	@Rule
	public ErrorCollector ec = new ErrorCollector();
	
	@Rule
	public ExpectedException ee = ExpectedException.none();
	
	@Before
	public void executeAntes() {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void testMocks() {

		UsersEntity user = new UsersEntity(1,"Michael",null,null,null,null,'A');
		UsersEntity user2 = new UsersEntity(1,"Michael2",null,null,null,null,'A');
		
		List<UsersEntity> listUser = new ArrayList<UsersEntity>();
		listUser.add(user);listUser.add(user2);
		
		
		when(crudUser.findById(1)).thenReturn(null);
		ec.checkThat(dpc.isUser(1),is(true));
		
//		ee.expect(ClassCastException.class);
//		when(crudUser.findById(1)).thenReturn((UsersEntity) listUser);
		
		//acao
//		dpc.isUser(0);
	}
	
	
}

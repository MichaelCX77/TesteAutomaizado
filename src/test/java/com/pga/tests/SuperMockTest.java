package com.pga.tests;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.anyInt;

import java.util.Calendar;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import com.pga.controller.TestSuperMock;
import com.pga.entity.UsersEntity;
import com.pga.utils.DatasUtil;

@RunWith(PowerMockRunner.class)
@PrepareForTest({
	TestSuperMock.class,
	DatasUtil.class})
public class SuperMockTest {

	@InjectMocks
	public TestSuperMock tester;
	
	@Before
	public void inicialize() {
//		MockitoAnnotations.initMocks(this);
		tester = PowerMockito.spy(tester);
	}
	
	@Test
	public void testSuper () throws Exception {
		
		
//		Construtores
		
		UsersEntity userEntityMod = new UsersEntity(2,"Michael",null,null,null,null,'A');
		
		PowerMockito.whenNew(UsersEntity.class).withNoArguments().thenReturn(userEntityMod);
		
		TestSuperMock.constutor();

//		Metodos estáticos
		
		Calendar call = Calendar.getInstance();
		call.setTime(new Date());
		
		PowerMockito.mock(Calendar.class);
		PowerMockito.when(Calendar.getInstance()).thenReturn(DatasUtil.alterDate(call, 10, 5, 5, 5, 5));
		
		TestSuperMock.estatico();

	}
	
//	Simular retorno de metodo privado secundario
	
	@Test
	public void testprivado () throws Exception {
		
		//cenario

		PowerMockito.doReturn(15).when(tester,"calcula", anyInt());
		
		//acao
		
		tester.testprivado();
		
		//verificacao
		
		assertThat(20, is(tester.testprivado()));
	}
	
//	Simular retorno de metodo privado primario - chamada direta
	
	@Test
	public void testprivado2 () throws Exception {
		
		//acao

		Integer retorno = (Integer) Whitebox.invokeMethod(tester, "calcula",10);
		
		//verificacao
		
		assertThat(15, is(retorno));
	}
	
}

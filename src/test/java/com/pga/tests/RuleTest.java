package com.pga.tests;

import static com.pga.auxiliar.Auxiliar.getDayOfWeek;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;

import static com.pga.matchers.MatchersProprios.*;

public class RuleTest {

	public static int contador;
	
	@Rule
	public ErrorCollector error = new ErrorCollector();
	
	@BeforeClass
	public static void facaAntes() {
		contador = 0;
	}
	
	@AfterClass
	public static void facaDepois() {
		System.out.println("Resultado:" + contador);
	}
	
	@Test
	public void testRule() {
		
//		error.checkThat(getDayOfWeek(new Date()), is(Calendar.MONDAY));
//		error.checkThat(getDayOfWeek(new Date()), is(Calendar.THURSDAY));
		
		error.checkThat(getDayOfWeek(), ehDomingo());
	}

	
	@Test
	public void inc1() {
		contador++;
	}
	
	@Test
	public void inc2() {
		contador++;
	}
	
	@Test
	public void inc3() {
		contador++;
	}
	
	@Test
	public void inc4() {
		contador++;
	}
}

package com.pga.tests;

import static com.pga.matchers.MatchersProprios.*;
import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.*;

@RunWith(Parameterized.class)
public class ParametrizerTest {

	public int idadeCorte = 40;
	
	@Parameter
	public String nome;

	@Parameter(value=1)
	public int idade;
	
	@Parameters(name="idade - {1}")
	public static Collection<Object[]> getNames(){
		
		return Arrays.asList(new Object[][] {
			{"Joana",41},
			{"Michael",50},
			{"Maria",56}
		});
	}
	
	@Test
	public void maiorQue() {
		assertThat(idade,verificaMaior(idadeCorte));
	}
	
//	@Test
//	public void print() {
//		System.out.println(nome+ "-" +idade);
//	}

}

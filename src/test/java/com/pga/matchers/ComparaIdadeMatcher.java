package com.pga.matchers;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

public class ComparaIdadeMatcher extends TypeSafeMatcher<Integer> {

	private Integer idade;
	
//	Segundo parâmetro do That - valor que deve ser
	public ComparaIdadeMatcher(Integer idadecorte) {
		this.idade = idadecorte;
	}

	@Override
	public void describeTo(Description description) {
		
		description.appendText("maior que " + idade);
		
	}

//	Primeiro parâmetro do That - valor a verificar
	@Override
	protected boolean matchesSafely(Integer idade) {
		return idade > this.idade;
	}

}

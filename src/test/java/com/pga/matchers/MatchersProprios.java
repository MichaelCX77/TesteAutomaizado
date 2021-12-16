package com.pga.matchers;

import java.util.Calendar;

public class MatchersProprios {

	public static ComparaIdadeMatcher verificaMaior(int idade) {
		return new ComparaIdadeMatcher(idade);
	}
	
	public static MatcherDomingo ehDomingo() {
		return new MatcherDomingo(Calendar.SUNDAY);
	}
	
}

package com.pga.matchers;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

public class MatcherDomingo extends TypeSafeMatcher<Integer> {

	public int dayOfWeek;
	
	public MatcherDomingo(int day) {
		this.dayOfWeek = day;
	}

	@Override
	public void describeTo(Description description) {
		description.appendText("" + dayOfWeek + " - Domingo");

	}

	@Override
	protected boolean matchesSafely(Integer day) {
		
		return day != dayOfWeek;
	}

}

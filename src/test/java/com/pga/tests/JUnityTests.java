package com.pga.tests;

import static com.pga.auxiliar.Auxiliar.getDayOfWeek;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.Calendar;

import org.junit.Assume;
import org.junit.Test;


public class JUnityTests {

	@Test
	public void testmethod() {
		
		Assume.assumeThat(getDayOfWeek(), is(Calendar.THURSDAY));
		assertThat("Os dias não correspondem",getDayOfWeek(), is(Calendar.THURSDAY));

	}
	
	@Test
	public void testmethod2() {
		
		Assume.assumeThat(getDayOfWeek(), is(Calendar.SATURDAY));
		assertThat("Os dias não correspondem",getDayOfWeek(), is(Calendar.SATURDAY));

	}
	
}

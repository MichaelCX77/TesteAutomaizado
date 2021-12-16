package com.pga.tests;

import static com.pga.builders.UsuarioBuilder.newUser;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class TestUser {
	
	@Test
	public void testUser() {
		
		
		
	}
	
	@Test
	public void testUser2() {
		
		assertEquals(newUser().setStatus('B').build().getStatus(), 'B');
	}
}
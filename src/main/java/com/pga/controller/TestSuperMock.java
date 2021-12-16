package com.pga.controller;

import java.util.Calendar;

import com.pga.entity.UsersEntity;

public class TestSuperMock {

	public static void constutor() {
		
		UsersEntity userEntity = new UsersEntity();
		System.out.println(userEntity.getName());
			
	}
	
	public static void estatico() {
		
		Calendar call = Calendar.getInstance();
		
		System.out.println(call.getTime());
	}
	
	public int  testprivado() {
		
		return (calcula(1)+5);
	}
	
	private int calcula(int number) {
		
		return number+5;
	}
	
}

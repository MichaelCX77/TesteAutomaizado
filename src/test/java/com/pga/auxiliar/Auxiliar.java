package com.pga.auxiliar;

import java.util.Calendar;
import java.util.Date;

public class Auxiliar {

	public static int getDayOfWeek() {
		
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		return  calendar.get(Calendar.DAY_OF_WEEK) ;
	}
	
}
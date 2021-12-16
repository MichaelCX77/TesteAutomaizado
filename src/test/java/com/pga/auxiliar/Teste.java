package com.pga.auxiliar;

import java.util.Calendar;
import java.util.Date;

import com.pga.utils.DatasUtil;

public class Teste {

	public static void main(String[] args) {
		
		Calendar call = Calendar.getInstance();
		call.setTime(new Date());
		
		System.out.println(DatasUtil.alterDate(call, 5, 5, 5, 5, 5).getTime());
	}
	
}

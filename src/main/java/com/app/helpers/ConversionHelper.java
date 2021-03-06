package com.app.helpers;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.app.constants.TableNames;

public class ConversionHelper {

	
	public static Date convertToDate(String date) {
		Date result;
		
		if(date.equals("")) {
			return null;
		}
		
		DateFormat format = new SimpleDateFormat("dd.MM.yyyy");
		try {
			result = format.parse(date);
		} catch (ParseException e) {			
			e.printStackTrace();
			System.out.println("Datum nije prosao parsiranje: " + date);
			return null;
		}
		
		return result;
	}
	
	public static String convertDateToSrRsFormat(Date date) {
		SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
		String result = formatter.format(date);
		return result;
	}
	
	public static Date convertInternationalToDate(String date) {
		Date result;
		
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		try {
			result = format.parse(date);
		} catch (ParseException e) {			
			e.printStackTrace();
			System.out.println("Datum nije prosao parsiranje: " + date);
			return null;
		}
		
		return result;
	}
	
	public static String getTableCode(String name) {
		String code;
		
		code = name.replace(" ", "_")
					.replace("ć", "c")
					.replace("ž", "z")
					.replace("š", "s");
		
		return code;
	}

	public static String getTableName(String tableCode) {
		for (String name : TableNames.getAllTableNames()) {
			if(tableCode.equals(ConversionHelper.getTableCode(name))) {
				return name;
			}
		}
		return null;
	}
}

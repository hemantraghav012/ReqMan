package com.reqman.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Dateconverter {

	 public static String convertDateToStringDDMMDDYYYY(Date date) {
		// DateFormat dateTimeInstance = SimpleDateFormat.getDateTimeInstance();
		 
	        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
	        String newdate = null;
	        try {
	            newdate = df.format(date);
	            //

	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	        return newdate;

	    }
	
	
}

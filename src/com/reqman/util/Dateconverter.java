package com.reqman.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Dateconverter {

	 
	
	 public static String convertTimeToStringhhmmss(Date date) {
			// DateFormat dateTimeInstance = SimpleDateFormat.getDateTimeInstance();
			 
		        SimpleDateFormat df = new SimpleDateFormat("dd/MMM/yyyy H:mm ");
		        String newtime = null;
		        try {
		            newtime = df.format(date);
		            //

		        } catch (Exception e) {
		            e.printStackTrace();
		        }
		        return newtime;

		    }
	 
	 
	 public static String convertDateToStringDDMMDDYYYY(Date date) {
					 
		        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
		        String newdate = null;
		        try {
		            newdate = df.format(date);
		          
		        } catch (Exception e) {
		            e.printStackTrace();
		        }
		        return newdate;

		    }
	 
	 
	 //for createrequestbean class one month before used
	 public static Date getPreToPreMonthDate(Date date){
		    SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");

		    Calendar cal = Calendar.getInstance();  
		    cal.setTime(date);  
		     
		    cal.add(Calendar.YEAR, -1);

		    Date preMonthDate = cal.getTime();
			return preMonthDate;  
		   
		}
	 
	 //this two methods use in newrequestimpl class for date range on request grid
	 public static Date getMaxTimeByDate(Date date) {
	        Date startDate = null;
	        try {
	            Calendar calender = Calendar.getInstance();
	            calender.setTime(date);
	            calender.set(Calendar.HOUR_OF_DAY, 23);
	            calender.set(Calendar.MINUTE, 59);
	            calender.set(Calendar.SECOND, 59);
	            calender.set(Calendar.MILLISECOND, 999);
	            startDate = calender.getTime();
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	        return startDate;
	    }

	    /**
	     * Gets the min time by date.
	     *
	     * @param date
	     *            the date
	     * @return the min time by date
	     */
	    public static Date getMinTimeByDate(Date date) {
	        Date startDate = null;
	        try {
	            Calendar calender = Calendar.getInstance();
	            calender.setTime(date);
	            calender.set(Calendar.HOUR_OF_DAY, 0);
	            calender.set(Calendar.MINUTE, 0);
	            calender.set(Calendar.SECOND, 0);
	            calender.set(Calendar.MILLISECOND, 000);
	            startDate = calender.getTime();
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	        return startDate;
	    }
	    
	    public static Date covertStringToDateYYYYMMDD(String dateInString) {
	        try {
	            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	            sdf.setLenient(false);
	            Date date = sdf.parse(dateInString);
	            return date;
	        } catch (Exception e) {
	            e.printStackTrace();
	            return null;
	        }
	    }

	 
	 
	 
}

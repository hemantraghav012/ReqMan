package com.reqman.common;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ReadConfigurations 
{
	
public static String ZOHO_HOSTPAGE_URL = null;

public static String hostpageid = null;

public static String Authorization = null;

public static String paypagecreateurl = null;

public static String siteurl = null;

public static String returnurl = null;

public static String securitykey = null;
	
static 
{
	Properties myResources = new Properties();
	InputStream propertiesStream;
	try 
	{
		StringBuilder tmp = new StringBuilder();
		Thread currentThread = Thread.currentThread();
		ClassLoader contextClassLoader = currentThread.getContextClassLoader();
		propertiesStream = contextClassLoader.getResourceAsStream("ReqManConfig.properties");
		if (propertiesStream != null) 
		{
			myResources.load(propertiesStream);
		} else 
		{
		  // Properties file not found!
		}			
		if(propertiesStream != null)
		{
			myResources.load(propertiesStream);
			ZOHO_HOSTPAGE_URL = myResources.getProperty("ZOHO_HOSTPAGE");
			hostpageid = myResources.getProperty("hostpageid");
			Authorization = myResources.getProperty("Authorization");
			paypagecreateurl = myResources.getProperty("PAYPAGECREATEURL");
			siteurl = myResources.getProperty("SITEURL");
			returnurl = myResources.getProperty("RETURNURL");
			securitykey = myResources.getProperty("SECRETKEY");

		}
	}
	catch (IOException e) 
	{
		e.printStackTrace();
	}
	 catch (Throwable e) 
	{
		e.printStackTrace();
	}
}

}

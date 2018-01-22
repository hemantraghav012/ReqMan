package com.reqman.common;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ReadConfigurations 
{
	
public static String ZOHO_HOSTPAGE_URL = null;

public static String hostpageid = null;

public static String Authorization = null;
	
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

package com.reqman.dao;




public interface UserDetailsInterface 
{
	public int validate(String userName, String password)  throws Exception;
	
	public int saveUser(String emailid, String password, String firstname, String lastname, String shortname) throws Exception;


	
}

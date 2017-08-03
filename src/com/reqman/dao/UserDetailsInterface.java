package com.reqman.dao;

public interface UserDetailsInterface 
{
	public int validate(String userName, String password)  throws Exception;
}

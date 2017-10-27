package com.reqman.dao;

import com.reqman.vo.UserupdateVo;




public interface UserDetailsInterface 
{
	public int validate(String userName, String password)  throws Exception;
	
	public int saveUser(String emailid, String password, String firstname, String lastname, String shortname) throws Exception;

	public int updateUsers(String emailid,String firstname, String lastname, String shortname, String password) throws Exception;

	public UserupdateVo getUseremailid(String userName)throws Exception;



	

	
	
}

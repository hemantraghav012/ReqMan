package com.reqman.dao;

import org.primefaces.model.UploadedFile;

import com.reqman.vo.UserupdateVo;




public interface UserDetailsInterface 
{
	public int validate(String userName, String password, String userrole)  throws Exception;
	
	public int saveUser(String emailid, String password, String firstname, String lastname, String shortname, String hashkey,UploadedFile photo, String organizationkey) throws Exception;

	public int updateUsers(String emailid,String firstname, String lastname, String shortname, String password, UploadedFile photo, String organizationkey) throws Exception;

	public UserupdateVo getUseremailid(String userName)throws Exception;

	public int updatepasswordByHashkey(String hash, String emailid,	String password)throws Exception;

	public int updateinformationByHashkey(String hash, String emailid,
			String password, String firstname, String lastname, String shortname)throws Exception;

	public int savesocialUser(String emailid, String password,
			String firstname, String lastname, String shortname, String hashkey)throws Exception;
	
	public byte[] getImageDetails(String userName)  throws Exception;

	public int forgotpassword(String emailid, String hashkey)throws Exception;

	public int forgotpasswordwithemail(String hash, String emailid,
			String password)throws Exception;

	

	public int saveUserthrowgoogle(String googleemail) throws Exception;

	public int saveAccountadminUser(String emailid, String password, String firstname, String lastname, String shortname,
			String hashkey, String organizationname)throws Exception;

	





	

	
	
}

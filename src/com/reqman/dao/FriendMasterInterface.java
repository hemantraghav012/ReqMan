package com.reqman.dao;

import java.util.List;

import com.reqman.pojo.Users;



public interface FriendMasterInterface {

	public int saveFriend(String emailid, String password, String firstname,	String lastname, String shortname)throws Exception;

	public List < Users > AllUsers()  throws Exception;
	
}

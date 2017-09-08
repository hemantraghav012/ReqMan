package com.reqman.dao;

import java.util.List;

import com.reqman.pojo.Users;
import com.reqman.vo.FriendVo;
import com.reqman.vo.ProjectVo;



public interface FriendMasterInterface {

	
	public int savefriend(String firstname, String lastname, String emailid, String shortname) throws Exception;
	
	
	public int savefriend(String frienduser, Boolean status, String userName,String friendfirstname,String friendlastname,String friendshortname)throws Exception;

	public	List<FriendVo>getUsersDetails(String userName)throws Exception;
	
	
	
public FriendVo getUserFriendById(String friendId) throws Exception;
	
	public int updateUserFriendById(String friendId, boolean status) throws Exception;
	
}

package com.reqman.dao;

import java.util.List;
import com.reqman.vo.FriendVo;
import com.reqman.vo.UserVo;

public interface FriendMasterInterface {
	// for save
	public int savefriend(String firstname, String lastname, String emailid,
			String password, String shortname, String hashkey) throws Exception;

	// for save
	public int savefriend(String frienduser, Boolean status, String userName,
			String friendfirstname, String friendlastname, String password,
			String friendshortname, String haskey) throws Exception;

	// for display data in grid
	public List<FriendVo> getUsersDetails(String userName) throws Exception;

	// for pie graph true status
	public List<FriendVo> getUsersStatus(String userName) throws Exception;

	// for pie graph false status
	public List<FriendVo> getUsersfasleStatus(String userName) throws Exception;

	// for update status through grid
	public int updateFriend(String oldValue, String newValue,
			Integer updatefriendId) throws Exception;

	// for show name in create request teammember field
	public List<UserVo> AllUsers(String userName) throws Exception;

	public List<FriendVo> getaccountUsers(String userName) throws Exception;



	//public byte[] getImageDetails(String userName)throws Exception;

	public byte[] getuserImage(Integer userfriendid)throws Exception;

}

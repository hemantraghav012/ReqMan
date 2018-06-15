package com.reqman.dao;

import java.util.List;

import com.reqman.vo.AccountuserVo;

public interface AccountuserInterface {

 public	List<AccountuserVo> getaccountuserDetails(String userName) throws Exception;

 public int updateimagestatus(String userName, Boolean imagestatus) throws Exception;

public AccountuserVo getAccountuserById(String userName) throws Exception;

}

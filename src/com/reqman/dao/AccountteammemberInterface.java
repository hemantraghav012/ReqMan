package com.reqman.dao;

import java.util.List;

import com.reqman.vo.AccountteammemberVo;

public interface AccountteammemberInterface {

	List<AccountteammemberVo> getaccountteammemberDetails(String userName) throws Exception;

	int getuseraccountById(Integer id) throws Exception;

}

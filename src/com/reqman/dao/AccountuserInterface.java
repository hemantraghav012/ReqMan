package com.reqman.dao;

import java.util.List;

import com.reqman.vo.AccountuserVo;

public interface AccountuserInterface {

	List<AccountuserVo> getaccountuserDetails(String userName) throws Exception;

}

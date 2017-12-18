package com.reqman.dao;

import java.util.List;

import com.reqman.vo.AccountVo;

public interface AccountInterface {

public	List<AccountVo> getaccountDetails(String userName) throws Exception;

}

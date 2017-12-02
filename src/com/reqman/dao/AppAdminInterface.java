package com.reqman.dao;

import java.util.List;

import com.reqman.vo.AdminVo;

public interface AppAdminInterface {

	List<AdminVo> getappadminDetails(String userName) throws Exception;

}

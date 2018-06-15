package com.reqman.dao;

import java.util.List;

import com.reqman.vo.HomeVo;

public interface HomeInterface {

	List<HomeVo> getrequestDetailsinhomepage(String userName) throws Exception;

}

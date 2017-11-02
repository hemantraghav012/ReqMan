package com.reqman.dao;

import java.util.Date;
import java.util.List;

import com.reqman.vo.ResponseVo;

public interface responseInterface {

	List<ResponseVo> getresponseDetails(String userName) throws Exception;

	ResponseVo getResponseById(String requestId)throws Exception;

	int updateResponsetById(String requestId, Integer stage,Date completiondate, String userName)throws Exception;

}

package com.reqman.dao;

import java.util.Date;
import java.util.List;

import com.reqman.vo.UpdatestatusVo;

public interface UpdatestatusInterface {

	List<UpdatestatusVo> getupdatestatusDetails(String userName)throws Exception;

	UpdatestatusVo getRequestById(String requestId) throws Exception;

	int updateRequestById(String requestId, Date completiondate,Float completionpercentage) throws Exception;

}

package com.reqman.dao;

import java.util.Date;
import java.util.List;

import com.reqman.vo.ResponseVo;

public interface responseInterface {

	public 	List<ResponseVo> getresponseDetails(String userName) throws Exception;

	public 	ResponseVo getResponseById(String requestId)throws Exception;

	public int updateResponsetById(String requestId, Integer stage,Date completiondate, String userName, String message, String actualeffort)throws Exception;

	public List<ResponseVo> getresponseDetailsforemail(String emailid, String title,
			String description, String userproject, String usercategory,
			String userrequesttype, String createdby, String changedate)throws Exception;
	public void getRequestStatusUpdateByrequestId(String requestId)throws Exception ;

	public int getupdateongrid(Date completiondate_on_row, String stage_on_row, Integer updateResponseId,String userName)throws Exception;
}

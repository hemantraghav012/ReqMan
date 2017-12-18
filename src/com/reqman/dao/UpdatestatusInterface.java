package com.reqman.dao;

import java.util.Date;
import java.util.List;




import com.reqman.vo.UpdatestatusVo;

public interface UpdatestatusInterface {

	List<UpdatestatusVo> getupdatestatusDetails(String userName)throws Exception;

	UpdatestatusVo getRequestById(String requestId) throws Exception;

	int updateRequestById(String requestId, Date completiondate,int completionpercentage, Integer stage,String message, String userName) throws Exception;

	List<UpdatestatusVo> getallProject(String userName)throws Exception;

	List<UpdatestatusVo> getupdatestatusDetailsforemail(String userName,
			String title, String description, String userproject,
			String usercategory, String userrequesttype, String createdby,
			String changedate, Float completionpercentage, Integer stage) throws Exception;

	List<UpdatestatusVo> getcompletedtaskDetails(String userName) throws Exception;

}

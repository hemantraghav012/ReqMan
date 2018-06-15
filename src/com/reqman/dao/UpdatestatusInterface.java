package com.reqman.dao;

import java.util.Date;
import java.util.List;





import com.reqman.vo.UpdatestatusVo;
import com.reqman.vo.dailyDuedatewisesendRequestVo;

public interface UpdatestatusInterface {

	public List<UpdatestatusVo> getupdatestatusDetails(String userName)throws Exception;

	public UpdatestatusVo getRequestById(String requestId) throws Exception;

	public 	int updateRequestById(String requestId, Date completiondate,int completionpercentage, Integer stage,String message, String userName, String actualeffort) throws Exception;

	public List<UpdatestatusVo> getallProject(String userName)throws Exception;

	public List<UpdatestatusVo> getupdatestatusDetailsforemail(String userName,
			String title, String description, String userproject,
			String usercategory, String userrequesttype, String createdby,
			String changedate, Float completionpercentage, Integer stage) throws Exception;

	public 	List<UpdatestatusVo> getcompletedtaskDetails(String userName) throws Exception;

	public List<dailyDuedatewisesendRequestVo> getduedatesendrequestonteammember(String emailid) throws Exception;

	public void getRequestStatusUpdateByrequestId(String requestId)throws Exception ;

	public int getUpdateRequestById(Integer oldValue, Integer newValue, Integer updateRequestId, String userName) throws Exception;

}

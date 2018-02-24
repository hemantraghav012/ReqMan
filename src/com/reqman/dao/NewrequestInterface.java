package com.reqman.dao;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.primefaces.model.UploadedFile;

import com.reqman.pojo.Request;
import com.reqman.vo.AdminRequestVo;
import com.reqman.vo.NewrequestVo;

public interface NewrequestInterface {

	// int savefile(String title,UploadedFile attachment) throws IOException;

	// for save
	public int save(String title, String description, Integer usercategory,
			Integer userproject, Integer userrequesttype,
			UploadedFile attachment, String userName, Date completiondate,
			Integer[] userfriendlist, String estimatedeffort, Integer weightage, String priority) throws IOException, Exception;

	// for display data in the grid
	public List<NewrequestVo> getNewrequestDetails(String userName,
			Date startDate, Date endDate) throws Exception;

	// for show value in modify page
	public NewrequestVo getRequestById(String requestId) throws Exception;

	// for update value
	public int updateRequestById(String requestId, Boolean status,
			String description, Date completiondate, UploadedFile attachment,
			Float completionpercentage, Integer stage, String message,
			String userName, Integer userproject, Integer usercategory,
			Integer userrequesttype, Integer userfriend, Integer rating, String feedback, String estimatedeffort, Integer weightage, String priority) throws Exception;

	// public List<NewrequestVo> getallproject(String userName)throws Exception;

	// get data for send email
	public List<NewrequestVo> getNewrequestDetailsforemail(String userName,
			String title, String description, String userproject,
			String usercategory, String userrequesttype, String friendname,
			String changedate, Float completionpercentage, Integer stage)
			throws Exception;

	// for List of close request
	public List<NewrequestVo> getColserequestDetails(String userName)
			throws Exception;

	// for save feedback and rating
	public int savefeedbackratingById(String requestId, String userName,
			Integer rating, String feedback, Integer stage) throws Exception;

	public List<AdminRequestVo> getadminrequestDetails(String userName) throws Exception;

}

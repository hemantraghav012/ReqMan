package com.reqman.dao;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.primefaces.model.UploadedFile;

import com.reqman.pojo.Request;
import com.reqman.vo.NewrequestVo;


public interface NewrequestInterface {

	// int savefile(String title,UploadedFile attachment) throws IOException;

	public int save(String title, String description, Integer usercategory,
			Integer userproject, Integer userrequesttype,
			UploadedFile attachment, String userName, Date completiondate,
			Integer[] userfriendlist) throws IOException, Exception;

	public List<NewrequestVo> getNewrequestDetails(String userName,	Date startDate, Date endDate)
			throws Exception;

	public NewrequestVo getRequestById(String requestId)throws Exception;

	public int updateRequestById(String requestId, Boolean status,
			String description, Date completiondate, UploadedFile attachment, Float completionpercentage, Integer stage,String message, String userName, Integer userproject, Integer usercategory, Integer userrequesttype, Integer userfriend)throws Exception;

	public Map barchart(String userName)throws Exception;

	public List<NewrequestVo> getallproject(String userName)throws Exception;

	

	public List<NewrequestVo> getNewrequestDetailsforemail(String userName,
			String title, String description, String userproject,
			String usercategory, String userrequesttype, String friendname,
			String changedate, Float completionpercentage,Integer stage)throws Exception;

	public List<NewrequestVo> getColserequestDetails(String userName) throws Exception;

	

	

	
	




	

	

	

	

	
}

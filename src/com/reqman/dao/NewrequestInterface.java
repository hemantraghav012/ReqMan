package com.reqman.dao;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import org.primefaces.model.UploadedFile;

import com.reqman.pojo.Request;
import com.reqman.vo.NewrequestVo;

public interface NewrequestInterface {

	// int savefile(String title,UploadedFile attachment) throws IOException;

	public int save(String title, String description, Integer usercategory,
			Integer userproject, Integer userrequesttype,
			UploadedFile attachment, String userName, Date completiondate,
			Integer[] userfriendlist) throws IOException, Exception;

	public List<NewrequestVo> getNewrequestDetails(String userName)
			throws Exception;

	public NewrequestVo getRequestById(String requestId)throws Exception;

	public int updateRequestById(String requestId, Boolean status,
			String description)throws Exception;

}

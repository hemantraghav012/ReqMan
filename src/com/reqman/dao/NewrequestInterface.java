package com.reqman.dao;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.primefaces.model.UploadedFile;

import com.reqman.pojo.Request;
import com.reqman.vo.AdminRequestVo;
import com.reqman.vo.DatasummaryVo;
import com.reqman.vo.MonthlysummeryemailVo;
import com.reqman.vo.NewrequestVo;
import com.reqman.vo.QuickcreaterequestVo;
import com.reqman.vo.UserVo;
import com.reqman.vo.dailyDuedatewisesendRequestVo;

public interface NewrequestInterface {

	// int savefile(String title,UploadedFile attachment) throws IOException;

	// for save
	public int save(String title, String description, Integer usercategory,
			byte[] bFile, String filedata1, Integer userproject, Integer userrequesttype,
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
			int completionpercentage, Integer stage, String message,
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

	public List<AdminRequestVo> getadminrequestDetails(String userName, Date startDate, Date endDate) throws Exception;

	public	List<MonthlysummeryemailVo> getTotalaveragebyrequester(String emailid)
			throws Exception;

	public List<MonthlysummeryemailVo> getAdminsummarybyrequester(String emailid) throws Exception;

	public List<dailyDuedatewisesendRequestVo> getduedatesendrequest(String emailid) throws Exception;

	public List<DatasummaryVo> getdatasummary(String userName, Date startDate, Date endDate) throws Exception;

	public void getRequestStatusUpdateByrequestId(String requestId) throws Exception;

	public int quickrequestsave(List<QuickcreaterequestVo> quickrequestList, String userName)throws Exception;

	public int quickrequestcheckval(String titlelist, List<UserVo> selectedUsers, String userName)throws Exception;

	public int soundrecoder(byte[] data, String filedata1)throws Exception;

	public int updateRequestonGrid(String oldValue, String newValue, Integer updaterequestId, String userName) throws Exception;




	

}

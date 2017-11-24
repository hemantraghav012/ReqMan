package com.reqman.dao;

import java.util.List;








import com.reqman.vo.ProjectVo;

public interface ProjectMasterInterface {



     public int saveproject(String name) throws Exception;
	
	public int saveproject(String projectName, Boolean status, String emailId, Boolean projectaccess) throws Exception;
	
	public List<ProjectVo> getProjectDetails(String emailId) throws Exception;
	
	//public ProjectVo getUserProjectById(String projectId) throws Exception;
	
//	public int updateUserprojectById(String projectId, boolean status) throws Exception;

	public List<ProjectVo> getProjectStatus(String userName)throws Exception;

	public List<ProjectVo> getProjectfalseStatus(String userName)throws Exception;

	public int updateProject(String oldValue, String newValue,
			Integer updateprojectId)throws Exception;

}

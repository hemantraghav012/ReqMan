package com.reqman.dao;

import java.util.List;


import com.reqman.vo.ProjectVo;

public interface ProjectMasterInterface {



     public int saveproject(String name) throws Exception;
	
	public int saveproject(String projectName, Boolean status, String emailId) throws Exception;
	
	public List<ProjectVo> getProjectDetails(String emailId) throws Exception;
	
	public ProjectVo getUserProjectById(String projectId) throws Exception;
	
	public int updateUserprojectById(String projectId, boolean status) throws Exception;

}

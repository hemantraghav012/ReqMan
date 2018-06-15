package com.reqman.dao;

import java.util.List;

import com.reqman.vo.ProjectVo;

public interface ProjectMasterInterface {
	// for save name
	public int saveproject(String name) throws Exception;

	// for save name status emailid
	public int saveproject(String projectName, Boolean status, String emailId,
			Boolean projectaccess) throws Exception;

	// for display data in grid
	public List<ProjectVo> getProjectDetails(String emailId) throws Exception;

	// for pie graph true status
	public List<ProjectVo> getProjectStatus(String userName) throws Exception;

	// for pie graph false status
	public List<ProjectVo> getProjectfalseStatus(String userName)
			throws Exception;

	// for update status through grid
	public int updateProject(String oldValue, String newValue,
			Integer updateprojectId) throws Exception;

	public List<ProjectVo> getallaccountproject(String userName) throws Exception;

}

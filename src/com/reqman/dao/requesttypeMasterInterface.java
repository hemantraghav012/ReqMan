package com.reqman.dao;

import java.util.List;

import com.reqman.vo.RequesttypeVo;

public interface requesttypeMasterInterface {
	// for save name
	public int saverequesttype(String name) throws Exception;

	// for save name status emailid
	public int saverequesttype(String requesttypeName, Boolean status,
			String emailId) throws Exception;

	// for display data in grid
	public List<RequesttypeVo> getRequesttypeDetails(String emailId)
			throws Exception;

	// for pie graph true status
	public List<RequesttypeVo> getRequesttypeStatus(String userName)
			throws Exception;

	// for pie graph false status
	public List<RequesttypeVo> getRequesttypefalseStatus(String userName)
			throws Exception;

	// for update status through grid
	public int updateRequesttype(String oldValue, String newValue,
			Integer updaterequesttypeId) throws Exception;
}

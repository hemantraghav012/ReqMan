package com.reqman.dao;

import java.util.List;



public interface GetrolequeryInterface {	
	
	public List<Integer> getRequestListByRole(String roleName, String loginId) throws Exception;
}

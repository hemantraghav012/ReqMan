package com.reqman.dao;

import java.util.List;
import java.util.Map;

import com.reqman.vo.UserroleVo;

public interface UserroleInterface {

	public List<UserroleVo> getuserroleDetails(String userName) throws Exception;

	public int updateUserrole(String oldValue, String newValue,
			Integer updateUserroleid) throws Exception;

	public Map<String, Double> linechart(String userName)throws Exception;

}

package com.reqman.dao;

import java.util.List;

import com.reqman.vo.CategoryVo;

public interface CategoryMasterInterface {

	// for save name
	public int savecategory(String name) throws Exception;

	// for save name status emailid
	public int savecategory(String categoryName, Boolean status, String emailId)
			throws Exception;

	// for display data in grid
	public List<CategoryVo> getCategoryDetails(String emailId) throws Exception;

	// for pie graph true status
	public List<CategoryVo> getCategoryStatus(String userName) throws Exception;

	// for pie graph false status
	public List<CategoryVo> getCategoryStatusfalse(String userName)
			throws Exception;

	// for update status through grid
	public int updateCategory(String oldValue, String newValue,
			Integer updatecategoryId) throws Exception;

	public List<CategoryVo> getAccountwiseCategory(String userName) throws Exception;

}

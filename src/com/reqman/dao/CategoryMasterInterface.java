package com.reqman.dao;

import java.util.List;

import org.primefaces.model.chart.PieChartModel;

import com.reqman.vo.CategoryVo;



public interface CategoryMasterInterface {

	



	public int savecategory(String name) throws Exception;
	
	public int savecategory(String categoryName, Boolean status, String emailId) throws Exception;
	
	public List<CategoryVo> getCategoryDetails(String emailId) throws Exception;
	
	public CategoryVo getUserCategoryById(String categoryId) throws Exception;
	
	public int updateUserCategoryById(String categoryId, boolean status) throws Exception;

	public List<CategoryVo> getCategoryStatus(String userName)throws Exception;

	public List<CategoryVo> getCategoryStatusfalse(String userName)throws Exception;
	 
	
}

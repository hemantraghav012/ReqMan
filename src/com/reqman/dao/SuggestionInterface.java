package com.reqman.dao;

import java.util.List;

import com.reqman.vo.SuggestionVo;

public interface SuggestionInterface {

 public	int savesuggestion(String message, Boolean status,String messagetype, String userName)throws Exception;

public List<SuggestionVo> getsuggestionDetails(String userName)throws Exception;

public SuggestionVo getsuggestionById(Integer id)throws Exception;

public List<SuggestionVo> getallsuggestionDetails(String userName) throws Exception;

public int updateSuggestionById(Integer id, String requeststatus, String actionowner, String adminremarks) throws Exception;

}

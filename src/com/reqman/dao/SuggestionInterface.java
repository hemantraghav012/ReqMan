package com.reqman.dao;

import java.util.List;

import com.reqman.vo.SuggestionVo;

public interface SuggestionInterface {

 public	int savesuggestion(String message, Boolean status, String userName)throws Exception;

public List<SuggestionVo> getsuggestionDetails(String userName)throws Exception;

}

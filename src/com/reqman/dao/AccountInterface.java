package com.reqman.dao;

import java.util.List;

import org.primefaces.model.UploadedFile;

import com.reqman.vo.AccountVo;

public interface AccountInterface {

public	List<AccountVo> getaccountDetails(String userName) throws Exception;

public int updatelogo( String accountname, boolean status, Integer id, String userName, UploadedFile logo, String imagename) throws Exception;

public AccountVo getAccountById(Integer id, String userName)throws Exception;

public byte[] getImageDetails(String userName) throws Exception;

}

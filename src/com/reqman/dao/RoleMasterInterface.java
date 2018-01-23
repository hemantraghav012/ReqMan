package com.reqman.dao;

import java.util.List;

import com.reqman.vo.RoleVo;

public interface RoleMasterInterface {

	public	List<RoleVo> getroleDetails(String userName)throws Exception;

public int getsaverole(Integer id, String name) throws Exception;

}

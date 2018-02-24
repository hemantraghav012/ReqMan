package com.reqman.dao;

import java.util.List;

import com.reqman.vo.SchedulejobVo;

public interface SchedulejobInterface {

	int saveschedulejob(String jobname, Boolean status, String userName,
			String day, String hour, String minute, String description) throws Exception;

	SchedulejobVo getscheduleById(Integer schedulejobid) throws Exception;

	List<SchedulejobVo> getschedulejobDetails(String userName) throws Exception;

	int updatescheduleById(Integer schedulejobid, Boolean status,
			String description, String jobname, String day, String hour,
			String minute, String userName)throws Exception;

}

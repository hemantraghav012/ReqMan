package com.reqman.scheduler;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class PendingRequestJob implements Job 
{
	public void execute(JobExecutionContext context)
		throws JobExecutionException 
	{

		System.out.println("PendingRequestJob ");

	}
}

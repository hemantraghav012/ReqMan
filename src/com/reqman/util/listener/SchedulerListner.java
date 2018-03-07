package com.reqman.util.listener;

import java.util.Timer;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;

import com.reqman.scheduler.PendingRequestJob;

public class SchedulerListner implements ServletContextListener{
	
	
			 Timer timer = null;
			 @Override
			 public void contextDestroyed(ServletContextEvent arg0) {
			  // TODO Auto-generated method stub
			  
			 }

			 @Override 
			 public void contextInitialized(ServletContextEvent sce) {
			   try { 
				   
				 //Set job details.
		   
				   JobDetail job = JobBuilder.newJob(HelloJob.class)
			    			.withIdentity("helloJob", "group1").build();
				 //  JobDetail job1 = JobBuilder.newJob(monthjob.class)
			    	//		.withIdentity("monthjob", "group1").build();
			 
			     //configure the scheduler time
			 				   
				   Trigger trigger = TriggerBuilder.newTrigger()
				    		  .withIdentity("simpleTrigger", "group1")
				    		  .withSchedule(SimpleScheduleBuilder.simpleSchedule()
				    		  .withIntervalInMinutes(1).repeatForever()).build();


				 //Execute the job.

				   Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
		        	scheduler.start();
		        	scheduler.scheduleJob(job, trigger);
		        	//scheduler.scheduleJob(job1, trigger);
			     
			     
			   } catch (Exception e) {    
			    e.printStackTrace();
			   } 
			 }

}

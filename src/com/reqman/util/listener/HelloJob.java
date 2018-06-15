package com.reqman.util.listener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.quartz.Job;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.Scheduler;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;

import com.reqman.pojo.Schdulejobs;
import com.reqman.scheduler.AdminMonthlyRequest;
import com.reqman.scheduler.DailyRequestJob;
import com.reqman.scheduler.MonthlyRequestJob;
import com.reqman.scheduler.PendingRequestJob;
import com.reqman.util.CommonConstants;

/**
 * This class defines a quartz job.
 * 
 * @author javawithease
 */
public class HelloJob implements Job {

	Map<String, String> mapGlobal = new HashMap<String, String>();

	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		System.out.println("Hello World12.");

		String Day = "";
		Integer Hour = null;
		Integer Minute = null;
		Integer Date = null;

		try {
			List<Schdulejobs> listSchedule = new SchedulerDAO().findAll();

			org.quartz.Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
			if (scheduler.isStarted() == false) {
				scheduler.start();
			}

			org.quartz.Scheduler scheduler2 = StdSchedulerFactory.getDefaultScheduler();
			if (scheduler2.isStarted() == false) {
				scheduler2.start();
			}

			org.quartz.Scheduler scheduler3 = StdSchedulerFactory.getDefaultScheduler();
			if (scheduler3.isStarted() == false) {
				scheduler3.start();
			}

			org.quartz.Scheduler scheduler4 = StdSchedulerFactory.getDefaultScheduler();
			if (scheduler4.isStarted() == false) {
				scheduler4.start();
			}

			for (int i = 0; i < listSchedule.size(); i++) {
				Schdulejobs sch = (Schdulejobs) listSchedule.get(i);

				if (sch.getJobname() != null && sch.getStatus() != null) {

					if (sch.getJobname().trim().equals(CommonConstants.SCHEDULE_1)) {
						Day = sch.getDay().trim();
						Hour = sch.getHour();
						Minute = sch.getMinute();
						Date = sch.getDate();

						Map<String, Object> mapSchedule1 = new HashMap<String, Object>();
						mapSchedule1.put(CommonConstants.SCHEDULER, scheduler);
						mapSchedule1.put(CommonConstants.SCHEDULE_DAY, Day);
						mapSchedule1.put(CommonConstants.SCHEDULE_HOUR, Hour);
						mapSchedule1.put(CommonConstants.SCHEDULE_MINUTE, Minute);
						mapSchedule1.put(CommonConstants.SCHEDULE_DATE, Date);
						mapSchedule1.put(CommonConstants.JOB_NAME, sch.getJobname().trim());
						mapSchedule1.put(CommonConstants.JOB_DESC, sch.getDescription().trim());

						schedule1Job(mapSchedule1);

					}
				}

				if (sch.getJobname().trim().equals(CommonConstants.SCHEDULE_2)) {

					Day = sch.getDay().trim();
					Date = sch.getDate();
					Hour = sch.getHour();
					Minute = sch.getMinute();

					Map<String, Object> mapSchedule2 = new HashMap<String, Object>();
					mapSchedule2.put(CommonConstants.SCHEDULER, scheduler2);
					mapSchedule2.put(CommonConstants.SCHEDULE_DAY, Day);
					mapSchedule2.put(CommonConstants.SCHEDULE_HOUR, Hour);
					mapSchedule2.put(CommonConstants.SCHEDULE_MINUTE, Minute);
					mapSchedule2.put(CommonConstants.SCHEDULE_DATE, Date);
					mapSchedule2.put(CommonConstants.JOB_NAME, sch.getJobname().trim());
					mapSchedule2.put(CommonConstants.JOB_DESC, sch.getDescription().trim());

					schedule2Job(mapSchedule2);

				}

				if (sch.getJobname().trim().equals(CommonConstants.SCHEDULE_3)) {

					Day = sch.getDay().trim();
					Date = sch.getDate();
					Hour = sch.getHour();
					Minute = sch.getMinute();

					Map<String, Object> mapSchedule3 = new HashMap<String, Object>();
					mapSchedule3.put(CommonConstants.SCHEDULER, scheduler3);
					mapSchedule3.put(CommonConstants.SCHEDULE_DAY, Day);
					mapSchedule3.put(CommonConstants.SCHEDULE_HOUR, Hour);
					mapSchedule3.put(CommonConstants.SCHEDULE_MINUTE, Minute);
					mapSchedule3.put(CommonConstants.SCHEDULE_DATE, Date);
					mapSchedule3.put(CommonConstants.JOB_NAME, sch.getJobname().trim());
					mapSchedule3.put(CommonConstants.JOB_DESC, sch.getDescription().trim());

					schedule3Job(mapSchedule3);

				}

				if (sch.getJobname() != null && sch.getStatus() != null) {

					if (sch.getJobname().trim().equals(CommonConstants.SCHEDULE_4)) {
						Day = sch.getDay().trim();
						Hour = sch.getHour();
						Minute = sch.getMinute();
						Date = sch.getDate();

						Map<String, Object> mapSchedule4 = new HashMap<String, Object>();
						mapSchedule4.put(CommonConstants.SCHEDULER, scheduler);
						mapSchedule4.put(CommonConstants.SCHEDULE_DAY, Day);
						mapSchedule4.put(CommonConstants.SCHEDULE_HOUR, Hour);
						mapSchedule4.put(CommonConstants.SCHEDULE_MINUTE, Minute);
						mapSchedule4.put(CommonConstants.SCHEDULE_DATE, Date);
						mapSchedule4.put(CommonConstants.JOB_NAME, sch.getJobname().trim());
						mapSchedule4.put(CommonConstants.JOB_DESC, sch.getDescription().trim());

						schedule4Job(mapSchedule4);

					}
				}

			}

			// scheduler.shutdown(true);
		} catch (Exception e) {

			e.printStackTrace();
		}

	}

	private void schedule1Job(Map<String, Object> map) {

		try {

			String Day = (String) map.get(CommonConstants.SCHEDULE_DAY);
			Integer Hour = (Integer) map.get(CommonConstants.SCHEDULE_HOUR);
			Integer Minute = (Integer) map.get(CommonConstants.SCHEDULE_MINUTE);
			Integer Date = (Integer) map.get(CommonConstants.SCHEDULE_DATE);
			String Des = (String) map.get(CommonConstants.JOB_DESC);
			String jobname = (String) map.get(CommonConstants.JOB_NAME);

			// get current Date
			Date now = new Date();
			// get Week_name Using Date

			SimpleDateFormat simpleDateformat = new SimpleDateFormat("EEEE"); // the
																				// day
																				// of
																				// the
																				// week
																				// spelled
																				// out
																				// completely
			String current_day = simpleDateformat.format(now);
			System.out.println(simpleDateformat.format(now));

			// Get Hour and minutes using date
			Calendar calendar = Calendar.getInstance();

			calendar.setTime(now);
			int current_hours = calendar.get(Calendar.HOUR_OF_DAY);
			int current_minutes = calendar.get(Calendar.MINUTE);
			int current_seconds = calendar.get(Calendar.SECOND);
			int current_date = calendar.get(Calendar.DAY_OF_MONTH);

			if ((current_day.equalsIgnoreCase(Day) || current_date == Date) && current_hours == Hour
					&& current_minutes == Minute) {

				System.out.println("hi... running..first scheduler");
				PendingRequestJob pendingrequest = new PendingRequestJob();
				pendingrequest.request();
			} else {
				System.out.println("hi...not... running..first scheduler");

			}

		} catch (Exception e) {

		}
	}

	private void schedule2Job(Map<String, Object> map2) {

		try {

			String Day = (String) map2.get(CommonConstants.SCHEDULE_DAY);
			Integer Hour = (Integer) map2.get(CommonConstants.SCHEDULE_HOUR);
			Integer Minute = (Integer) map2.get(CommonConstants.SCHEDULE_MINUTE);
			Integer Date = (Integer) map2.get(CommonConstants.SCHEDULE_DATE);
			String Des = (String) map2.get(CommonConstants.JOB_DESC);
			String jobname = (String) map2.get(CommonConstants.JOB_NAME);

			// get current Date
			Date now = new Date();
			// get Week_name Using Date

			SimpleDateFormat simpleDateformat = new SimpleDateFormat("EEEE"); // the
																				// day
																				// of
																				// the
																				// week
																				// spelled
																				// out
																				// completely
			String current_day = simpleDateformat.format(now);
			System.out.println("second Scheduler" + simpleDateformat.format(now));

			// Get Hour and minutes using date
			Calendar calendar = Calendar.getInstance();

			calendar.setTime(now);
			int current_hours = calendar.get(Calendar.HOUR_OF_DAY);
			int current_minutes = calendar.get(Calendar.MINUTE);
			int current_seconds = calendar.get(Calendar.SECOND);
			int current_date = calendar.get(Calendar.DAY_OF_MONTH);

			if ((current_day.equalsIgnoreCase(Day) || current_date == Date) && current_hours == Hour
					&& current_minutes == Minute) {

				System.out.println("hi... running ...second Scheduler");
				MonthlyRequestJob monthlyRequestJob = new MonthlyRequestJob();
				monthlyRequestJob.request();
			} else {
				System.out.println("hi...not... running...second Scheduler");

			}

		} catch (Exception e) {

		}
	}

	private void schedule3Job(Map<String, Object> map3) {
		// TODO Auto-generated method stub

		try {

			String Day = (String) map3.get(CommonConstants.SCHEDULE_DAY);
			Integer Hour = (Integer) map3.get(CommonConstants.SCHEDULE_HOUR);
			Integer Minute = (Integer) map3.get(CommonConstants.SCHEDULE_MINUTE);
			Integer Date = (Integer) map3.get(CommonConstants.SCHEDULE_DATE);
			String Des = (String) map3.get(CommonConstants.JOB_DESC);
			String jobname = (String) map3.get(CommonConstants.JOB_NAME);

			// get current Date
			Date now = new Date();
			// get Week_name Using Date

			SimpleDateFormat simpleDateformat = new SimpleDateFormat("EEEE");
			
			String current_day = simpleDateformat.format(now);
			System.out.println("Third Scheduler" + simpleDateformat.format(now));

			// Get Hour and minutes using date
			Calendar calendar = Calendar.getInstance();

			calendar.setTime(now);
			int current_hours = calendar.get(Calendar.HOUR_OF_DAY);
			int current_minutes = calendar.get(Calendar.MINUTE);
			int current_seconds = calendar.get(Calendar.SECOND);
			int current_date = calendar.get(Calendar.DAY_OF_MONTH);

			if ((current_day.equalsIgnoreCase(Day) || current_date == Date) && current_hours == Hour
					&& current_minutes == Minute) {

				System.out.println("hi... running ...Third Scheduler");
				AdminMonthlyRequest adminMonthlyRequest = new AdminMonthlyRequest();
				adminMonthlyRequest.request();
			} else {
				System.out.println("hi...not... running...Third Scheduler");

			}

		} catch (Exception e) {

		}
	}

	private void schedule4Job(Map<String, Object> map4) {
		// TODO Auto-generated method stub

		try {

			// String Day = (String) map4.get(CommonConstants.SCHEDULE_DAY);
			Integer Hour = (Integer) map4.get(CommonConstants.SCHEDULE_HOUR);
			Integer Minute = (Integer) map4.get(CommonConstants.SCHEDULE_MINUTE);
			// Integer Date = (Integer) map4.get(CommonConstants.SCHEDULE_DATE);
			String Des = (String) map4.get(CommonConstants.JOB_DESC);
			String jobname = (String) map4.get(CommonConstants.JOB_NAME);

			// get current Date
			Date now = new Date();
			// get Week_name Using Date

			// SimpleDateFormat simpleDateformat = new SimpleDateFormat("EEEE");
			// // the day of the week spelled out completely
			// String current_day=simpleDateformat.format(now);
			// System.out.println("Third
			// Scheduler"+simpleDateformat.format(now));

			// Get Hour and minutes using date
			Calendar calendar = Calendar.getInstance();

			calendar.setTime(now);
			int current_hours = calendar.get(Calendar.HOUR_OF_DAY);
			int current_minutes = calendar.get(Calendar.MINUTE);
			// int current_seconds = calendar.get(Calendar.SECOND);
			// int current_date = calendar.get(Calendar.DAY_OF_MONTH);

			if (current_hours == Hour && current_minutes == Minute) {

				System.out.println("hi... running ...fourth Scheduler");
				DailyRequestJob dailyrequestJob = new DailyRequestJob();
				dailyrequestJob.request();
			} else {
				System.out.println("hi...not... running...fourth Scheduler");

			}

		} catch (Exception e) {

		}
	}

}
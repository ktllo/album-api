package org.leolo.album.jobs;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.annotation.WebListener;

import org.leolo.album.ConfigManager;
import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.ee.servlet.QuartzInitializerListener;
import org.quartz.impl.StdSchedulerFactory;


@WebListener
public class JobScheduler extends QuartzInitializerListener {

	private org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(JobScheduler.class);
	
	@Override
    public void contextInitialized(ServletContextEvent sce) {
		ConfigManager.getInstance();
		try {
			Thread.sleep(250);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			logger.error(e1.getMessage(),e1);
		}
        super.contextInitialized(sce);
        ServletContext ctx = sce.getServletContext();
        StdSchedulerFactory factory = (StdSchedulerFactory) ctx.getAttribute(QUARTZ_FACTORY_KEY);
        try {
            Scheduler scheduler = factory.getScheduler();
            scheduler.clear();
            JobDetail cleanerJob = JobBuilder.newJob(SessionCleaner.class).build();
            Trigger cleanerTrigger = TriggerBuilder.newTrigger()
            		.withIdentity("simple")
            		.withSchedule(
            				SimpleScheduleBuilder.repeatMinutelyForever(30)
                    )
            		.startNow().build();
            scheduler.scheduleJob(cleanerJob, cleanerTrigger);
            scheduler.start();
        } catch (Exception e) {
            ctx.log("There was an error scheduling the job.", e);
        }
    }
}

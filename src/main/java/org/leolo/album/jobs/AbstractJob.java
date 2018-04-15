package org.leolo.album.jobs;

import org.leolo.album.ConfigManager;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public abstract class AbstractJob implements Job {

	protected org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(AbstractJob.class);

	@Override
	public final void execute(JobExecutionContext context) throws JobExecutionException {
		logger.info(this.getClass().getSimpleName()+" started.");
		if(ConfigManager.getInstance()==null){
			logger.warn("System not ready yet!");
			return;
		}
		try{
			_execute(context);
			logger.info(this.getClass().getSimpleName()+" finished.");
		}catch(Throwable t){
			logger.warn(this.getClass().getSimpleName()+" started with error.");
			logger.error(t.getMessage(), t);
		}
	}
	
	public abstract void _execute(JobExecutionContext context) throws Exception;
}

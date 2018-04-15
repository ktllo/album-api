package org.leolo.album.jobs;

import java.util.List;

import org.leolo.album.SessionStatus;
import org.leolo.album.dao.SessionDao;
import org.quartz.JobExecutionContext;

public class SessionCleaner extends AbstractJob{

	
	@Override
	public void _execute(JobExecutionContext context) {
		SessionDao sDao = new SessionDao();
		List<String> sessionList = sDao.listSessions();
		int count = 0;
		for(String session:sessionList){
			SessionStatus status = sDao.checkSession(session);
			if(status == SessionStatus.EXPIRIED){
				sDao.invalidate(session);
				logger.info("Expried session {} is removed");
				count++;
			}
		}
		logger.info("{} sessions is removed", count);
	}
}

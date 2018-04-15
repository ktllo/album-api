package org.leolo.album;

public class SessionManager  implements Reloadable{

	private org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(SessionManager.class);
	private static SessionManager instance;
	private SessionManager(){
		ConfigManager.getInstance().registerReload(this);
	}
	public static synchronized SessionManager getInstance(){
		if(instance==null){
			instance=new SessionManager();
		}
		return instance;
	}
	private static long sessionLength = -1;
	
	public static synchronized long getSessionLength(){
		if(sessionLength==-1){
			sessionLength = ConfigManager.getInstance().getInt("security.session_length", 1200)*1000;
		}else if(sessionLength==0){
			return Long.MAX_VALUE;
		}
		return sessionLength;
	}
	@Override
	public void _reload() {
		sessionLength = -1;
	}
}

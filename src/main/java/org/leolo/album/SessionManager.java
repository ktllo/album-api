package org.leolo.album;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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
	public SessionStatus checkSession(String session){
		Connection conn=null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		final String SQL1 = "SELECT `lastaccess` FROM `authtoken` WHERE `token` = ?";
		SessionStatus status = SessionStatus.NOT_FOUND;
		try{
			conn = DatabaseManager.getInstance().getConnection();
			pstmt = conn.prepareStatement(SQL1);
			pstmt.setString(1, session);
			rs = pstmt.executeQuery();
			if(rs.next()){
				long lastAccessTime = rs.getTimestamp(1).getTime();
				logger.info("Session last access is {}, current time is {}", 
						Utils.getISO8601Time(new java.util.Date(lastAccessTime)),
						Utils.getISO8601Time(new java.util.Date(System.currentTimeMillis()))
						);
				logger.info("Session {} is last used {}ms ago. Limit is {} ms",session, System.currentTimeMillis()-lastAccessTime,getSessionLength());
				if(System.currentTimeMillis()-lastAccessTime < getSessionLength()){
					status = SessionStatus.VALID;
				}else{
					status = SessionStatus.EXPIRIED;
				}
			}
		}catch(SQLException e){
			logger.error(e.getMessage(),e);
		}finally{
			try {
				if(rs!=null){
					rs.close();
					rs = null;
				}
				if(pstmt!=null){
					pstmt.close();
					pstmt = null;
				}
				if(conn!=null){
					conn.close();
					conn = null;
				}
				
			} catch (SQLException e) {
				logger.error(e.getMessage(),e);
			}
		}
		return status;
	}
	
	private long sessionLength = -1;
	
	private synchronized long getSessionLength(){
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
	
	public enum SessionStatus{
		NOT_FOUND,
		EXPIRIED,
		VALID;
	}
	
	public boolean renewSession(String session){
		if(checkSession(session)==SessionStatus.VALID){
			Connection conn=null;
			PreparedStatement pstmt = null;
			final String SQL1 = "UPDATE `authtoken` SET `lastaccess` = NOW() WHERE `token` = ?";
			try{
				conn = DatabaseManager.getInstance().getConnection();
				pstmt = conn.prepareStatement(SQL1);
				pstmt.setString(1, session);
				pstmt.executeLargeUpdate();
			}catch(SQLException e){
				logger.error(e.getMessage(),e);
			}finally{
				try {
					if(pstmt!=null){
						pstmt.close();
						pstmt = null;
					}
					if(conn!=null){
						conn.close();
						conn = null;
					}
					
				} catch (SQLException e) {
					logger.error(e.getMessage(),e);
				}
			}
			return true;
		}
		return false;
	}
}

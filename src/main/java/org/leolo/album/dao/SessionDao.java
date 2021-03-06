package org.leolo.album.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.leolo.album.DatabaseManager;
import org.leolo.album.SessionManager;
import org.leolo.album.SessionStatus;
import org.leolo.album.Utils;

public class SessionDao {

	private org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(SessionDao.class);

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
				logger.info("Session {} is last used {}ms ago. Limit is {} ms",session, System.currentTimeMillis()-lastAccessTime,SessionManager.getSessionLength());
				if(System.currentTimeMillis()-lastAccessTime < SessionManager.getSessionLength()){
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

	public void invalidate(String sessionId){
		Connection conn = null;
		PreparedStatement pstmt = null;
		final String SQL = "DELETE FROM `authtoken` WHERE `token` = ?";
		try{
			conn = DatabaseManager.getInstance().getConnection();
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, sessionId);
			pstmt.executeUpdate();
			conn.commit();
		}catch(SQLException e){
			logger.error(e.getMessage(), e);
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
				pstmt.executeUpdate();
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
	
	public List<String> listSessions(){
		ArrayList<String> list = new ArrayList<>();
		Connection conn=null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		final String SQL = "SELECT `token` FROM `authtoken`";
		try{
			conn = DatabaseManager.getInstance().getConnection();
			pstmt = conn.prepareStatement(SQL);
			rs  = pstmt.executeQuery();
			while(rs.next()){
				list.add(rs.getString(1));
			}
		}catch(SQLException e){
			logger.error(e.getMessage(), e);
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
				return null;
			}
		}
		return list;
	}
}

package org.leolo.album.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.leolo.album.DatabaseManager;
import org.leolo.album.Utils;
import org.leolo.album.model.Album;

public class UserDao {

	private org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(UserDao.class);
	
	/**
	 * login the user
	 * @param user
	 * @param password
	 * @return the auth token. <i>null</i> if login fail
	 */
	public String login(String user, String password){
		Connection conn=null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		final String SQL1 = "SELECT `uid`,`password` FROM `user` WHERE `uname`=?";
		final String SQL2 = "INSERT INTO `authtoken` VALUES (?,?, NOW(), NOW())";
		boolean ok = false;
		String token = null;
		try{
			conn = DatabaseManager.getInstance().getConnection();
			pstmt = conn.prepareStatement(SQL1);
			pstmt.setString(1, user);
			rs = pstmt.executeQuery();
			int uid = -1;
			if(rs.next()){
				logger.info("Having user found. {}:{}","*******", rs.getString(2).trim());
				if(Utils.verifyPassword(password, rs.getString(2))){
					ok = true;
					uid = rs.getInt(1);
					token = Utils.getAuthToken();
					logger.info("Auth token is {}", token);
				}
			}
			if(rs!=null){
				rs.close();
				rs = null;
			}
			if(pstmt!=null){
				pstmt.close();
				pstmt = null;
			}
			if(ok){
				pstmt = conn.prepareStatement(SQL2);
				pstmt.setString(1, token);
				pstmt.setInt(2, uid);
				logger.error("Going to instert record");
				pstmt.executeUpdate();
				conn.commit();
				logger.error("Committed");
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
		return token;
	}
}

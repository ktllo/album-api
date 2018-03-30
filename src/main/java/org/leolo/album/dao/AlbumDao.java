package org.leolo.album.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.leolo.album.DatabaseManager;
import org.leolo.album.model.Album;

public class AlbumDao {

	private org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(AlbumDao.class);
	
	public List<Album> getAlbumList(){
		List<Album> list = new ArrayList<>();
		Connection conn=null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		final String SQL = "SELECT `albumId`,`fullname`,`name`,`description`,`thumbnail` "
				+ "FROM `album` JOIN `user` ON `album`.`owner` = `user`.`uid`";
		try{
			conn = DatabaseManager.getInstance().getConnection();
			pstmt = conn.prepareStatement(SQL);
			rs = pstmt.executeQuery();
			while(rs.next()){
				Album album = new Album(rs.getString(1));
				album.setOwnerName(rs.getString(2));
				album.setName(rs.getString(3));
				album.setDescription(rs.getString(4));
				album.setThumbnail(rs.getString(5));
				list.add(album);
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
		return list;
	}
	
}

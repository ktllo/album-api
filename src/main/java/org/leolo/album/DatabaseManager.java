package org.leolo.album;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;



public class DatabaseManager {

	private org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(DatabaseManager.class);
	
	private DataSource ds = null;
	
	private static DatabaseManager instance;
	
	public static DatabaseManager getInstance(){
		if(instance==null){
			instance = new DatabaseManager();
		}
		return instance;
	}
	
	private DatabaseManager(){
		try {
			Class.forName("com.mysql.jdbc.Driver");
			init();
		} catch (ClassNotFoundException e) {
			logger.error(e.getMessage(),e);
		}
	}
	
	private void init(){
		BasicDataSource bds = new BasicDataSource();
		bds.setDriverClassName("com.mysql.jdbc.Driver");
		bds.setUrl("jdbc:mysql://"+ConfigManager.getInstance().getString("database.host", "localhost")+
				"/"+ConfigManager.getInstance().getString("database.name", "album")+"?useSSL=false");
		bds.setUsername(ConfigManager.getInstance().getString("database.user", "root"));
		bds.setPassword(ConfigManager.getInstance().getString("database.password", ""));
		bds.setDefaultAutoCommit(false);
		bds.setMaxTotal(1024);
		bds.setMaxIdle(300);
		ds = bds;
	}
	
	public Connection getConnection(){
		try {
			return ds.getConnection();
		} catch (SQLException e) {
			logger.error(e.getMessage(),e);
			return null;
		}
	}
}

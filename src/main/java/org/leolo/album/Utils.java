package org.leolo.album;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Random;
import java.util.TimeZone;

import org.apache.commons.codec.binary.Base64;
import org.mindrot.jbcrypt.BCrypt;

public class Utils {
//	private static Calendar calendar = Calendar.getInstance();
	private static SimpleDateFormat dateFormatISO8601 = new SimpleDateFormat(
        "yyyy-MM-dd'T'HH:mm:ssZ", Locale.US);
	static{
		dateFormatISO8601.setTimeZone(TimeZone.getTimeZone("GMT"));
	}
	private static final Random requestIdRandomProvider;
	static{
		requestIdRandomProvider = new Random();
	}
	
	public static String getISO8601Time(){
		return getISO8601Time(new Date());
	}
	
	public static String getISO8601Time(Date time){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(time);
		return dateFormatISO8601.format(calendar.getTime());
	}
	
	public static String getNextRequestId(){
		byte [] randomId = new byte[12];
		requestIdRandomProvider.nextBytes(randomId);
		return Base64.encodeBase64URLSafeString(randomId);
	}
	
	public static String hashPassword(String password){
		return BCrypt.hashpw(password, BCrypt.gensalt(12));
	}
	
	public static boolean verifyPassword(String password, String hash){
		return BCrypt.checkpw(password, hash);
	}
	
	public static Map<String, Object> getVersionMap(){
		Map<String, Object> map = new HashMap<>();
		map.put("version", "0.0.1");
		map.put("min-api-level", "0");
		map.put("max-api-level", "0");
		map.put("extension", new ArrayList<String>());
		return map;
	}
}

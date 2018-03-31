package org.leolo.album;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Random;
import java.util.StringTokenizer;
import java.util.TimeZone;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.codec.binary.Base64;
import org.mindrot.jbcrypt.BCrypt;

public class Utils {
//	private static Calendar calendar = Calendar.getInstance();
	private static SimpleDateFormat dateFormatISO8601 = new SimpleDateFormat(
	        "yyyy-MM-dd'T'HH:mm:ssZ", Locale.US);
	private static SimpleDateFormat dateFormatISO8601ms = new SimpleDateFormat(
	        "yyyy-MM-dd'T'HH:mm:ss.SSSZ", Locale.US);
	private static org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(Utils.class);
	static{
		dateFormatISO8601.setTimeZone(TimeZone.getTimeZone("GMT"));
	}
	private static final Random requestIdRandomProvider;
	private static Random secureRandom;
	static{
		requestIdRandomProvider = new Random();
		try {
			secureRandom = SecureRandom.getInstanceStrong();
		} catch (NoSuchAlgorithmException e) {
			logger.error(e.getMessage(), e);
			secureRandom = new Random();
		}
	}
	
	public static String getISO8601Time(){
		return getISO8601Time(new Date());
	}
	
	public static String getISO8601Time(Date time){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(time);
		return dateFormatISO8601.format(calendar.getTime());
	}
	
	
	public static String getISO8601TimeWithMilliSecond(){
		return getISO8601TimeWithMilliSecond(new Date());
	}
	
	public static String getISO8601TimeWithMilliSecond(Date time){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(time);
		return dateFormatISO8601ms.format(calendar.getTime());
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
		ArrayList<String> ext = new ArrayList<String>();
		ext.add("album.db");
		map.put("extension", ext);
		return map;
	}
	
	public static String [] getURLPart(HttpServletRequest request){
        String requestURL = request.getRequestURI().substring(request.getContextPath().length());
        ArrayList<String> tokensList = new ArrayList<>();
        StringTokenizer st = new StringTokenizer(requestURL,"/");
        while(st.hasMoreTokens()){
            tokensList.add(st.nextToken());
        }
        String [] tokenArray = new String[tokensList.size()];
        tokensList.toArray(tokenArray);
        return tokenArray;
	}
	
	public static String getAuthToken(){
		byte [] array = new byte[18];
		secureRandom.nextBytes(array);
		return Base64.encodeBase64URLSafeString(array);
	}
}

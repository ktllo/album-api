package org.leolo.album;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
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
import org.json.JSONObject;
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
			secureRandom = SecureRandom.getInstance("NativePRNGNonBlocking");
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
		return BCrypt.hashpw(password.trim(), BCrypt.gensalt()).trim();
	}
	
	public static boolean verifyPassword(String password, String hash){
		logger.info("Strat checking password");
		password = password.trim();
		hash = hash.trim();
		logger.info("trimmed");
		boolean result =  BCrypt.checkpw(password, hash);
		logger.info("checked");
		return result;
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
	
	public static String getSourceAddress(HttpServletRequest request){
		if(request.getHeader("x-forwarded-for")!=null){
			String [] a = tokenize(request.getHeader("x-forwarded-for"), ",");
			return a[a.length-1];
		}
		return request.getRemoteAddr();
	}
	
	public static String[] tokenize(String str, String delim){
		StringTokenizer st = new StringTokenizer(str, delim);
		ArrayList<String> al = new ArrayList<>();
		while(st.hasMoreTokens()){
			al.add(st.nextToken());
		}
		String [] array = new String[al.size()];
		al.toArray(array);
		return array;
	}
	
	public static Map<String, Object> getPostMap(HttpServletRequest request){
		logger.debug("Start reading data");
		StringBuilder sb = new StringBuilder();
		try {
			BufferedReader br = request.getReader();
			while(true){
				String line = br.readLine();
				if(line==null){
					break;
				}
				sb.append(line);
			}
		} catch (IOException e) {
			logger.error(e.getMessage(),e);
		}
		logger.debug("Start parsing data");
		JSONObject obj = null;
		try{
			obj = new JSONObject(sb.toString());
		}catch(org.json.JSONException e){
			logger.debug("Fail parsing data");
			return null;
		}
		logger.debug("Done parsing data");
		return obj.toMap();
	}
}

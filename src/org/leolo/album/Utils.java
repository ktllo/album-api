package org.leolo.album;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class Utils {
	private static Calendar calendar = Calendar.getInstance();
	private static SimpleDateFormat dateFormatISO8601 = new SimpleDateFormat(
        "yyyy-MM-dd'T'HH:mm:ssZ", Locale.US);
	static{
		dateFormatISO8601.setTimeZone(TimeZone.getTimeZone("GMT"));
	}
	public static String getISO8601Time(Date time){
		return dateFormatISO8601.format(calendar.getTime());
	}
	public static String getISO8601Time(){
		return getISO8601Time(new Date());
	}
}

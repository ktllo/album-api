package org.leolo.album;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;

public class LogFormat extends Formatter{

	private static DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
	@Override
	public String format(LogRecord record) {
		StringBuilder sb = new StringBuilder();
		sb.append(df.format(new java.util.Date(record.getMillis()))).append(" ");
		sb.append(record.getLevel()).append(" ");
		sb.append(record.getSourceClassName()).append(" ").append(record.getMessage());
		String throwable = "";
        if (record.getThrown() != null) {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            pw.println();
            record.getThrown().printStackTrace(pw);
            pw.close();
            throwable = sw.toString();
            sb.append(" ").append(throwable);
        }
        
		return sb.toString();
	}
}

package org.leolo.album;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class LoginDelayManager implements Reloadable{

	private org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(LoginDelayManager.class);
	
	private static LoginDelayManager instance;
	
	private HashMap<String, Entry> map;
	class Entry{
		int attempt;
		long lastFailure;
		long lastDelayTo;
	}
	private long a,b,c,d;
	private int e;
	
	private long f_e, ddx_fe;
	
	
	private LoginDelayManager(){
		map = new HashMap<>();
		a=b=c=d=e=0;
		_reload();
	}
	
	public static LoginDelayManager getInstance(){
		if(instance==null){
			instance = new LoginDelayManager();
		}
		return instance;
	}
	
	private long calculate(int attempt){
		return (attempt<=e)?_calcBlackoutTimeLower(attempt):_calcBlackoutTimeUpper(attempt);
	}
	
	public void remove(String key){
		map.remove(key);
	}
	
	public long getLockUntil(String key){
		Entry e = map.get(key);
		if(e==null) return 0;
		return e.lastDelayTo;
	}
	
	public long setLock(String key){
		Entry e = map.get(key);
		if(e == null){
			e = new Entry();
			e.attempt = 1;
			e.lastFailure = System.currentTimeMillis();
			e.lastDelayTo = System.currentTimeMillis() + calculate(1);
			logger.info("Lock {}ms till {}", calculate(1), Utils.getISO8601Time(new Date(e.lastDelayTo)));
			map.put(key, e);
			return e.lastDelayTo;
		}
		int attempt = e.attempt + 1;
		e.attempt = attempt;
		e.lastFailure = System.currentTimeMillis();
		if(e.lastDelayTo>System.currentTimeMillis()){
			e.lastDelayTo = e.lastDelayTo + calculate(attempt);
			logger.info("Lock {}ms till {}", calculate(attempt), Utils.getISO8601Time(new Date(e.lastDelayTo)));
			
		}else{
			e.lastDelayTo = System.currentTimeMillis() + calculate(attempt);
			logger.info("Lock {}ms till {}", calculate(attempt), Utils.getISO8601Time(new Date(e.lastDelayTo)));
			
		}
		return e.lastDelayTo;
	}
	public long getDelay(String key){
		long till = getLockUntil(key);
		if(till  > System.currentTimeMillis()){
			return till-System.currentTimeMillis();
		}
		return 0;
	}
	public void hold(String key){
		long till = getLockUntil(key);
		if(till  > System.currentTimeMillis()){
			try {
				Thread.sleep(till-System.currentTimeMillis());
			} catch (InterruptedException e) {
				logger.error(e.getMessage(),e);
			}
		}
	}

	private long _calcBlackoutTimeLower(int x){
		return Math.round(Math.ceil(a + b * Math.pow(c, (x-1.0d)/d)));
	}

	private long _calcBlackoutTimeUpper(int x){
		return f_e+(x-e)*ddx_fe;
	}
	
	@Override
	public void _reload() {
		// security.login.blackout.a
		a = ConfigManager.getInstance().getLong("security.login.blackout.a",1000);
		b = ConfigManager.getInstance().getLong("security.login.blackout.b",2000);
		c = ConfigManager.getInstance().getLong("security.login.blackout.c",2);
		d = ConfigManager.getInstance().getLong("security.login.blackout.d",3);
		e = ConfigManager.getInstance().getInt("security.login.blackout.e",10);
		f_e = _calcBlackoutTimeLower(e);
		ddx_fe = _calcBlackoutTimeLower(e) - _calcBlackoutTimeLower(e-1);
	}
}

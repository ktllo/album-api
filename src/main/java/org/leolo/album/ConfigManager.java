package org.leolo.album;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.URL;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import org.apache.commons.configuration2.Configuration;
import org.apache.commons.configuration2.ConfigurationDecoder;
import org.apache.commons.configuration2.builder.fluent.Configurations;
import org.apache.commons.configuration2.ex.ConfigurationException;
import org.apache.commons.configuration2.interpol.ConfigurationInterpolator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class ConfigManager {
	private static final Logger logger = LoggerFactory.getLogger(ConfigManager.class);
	private static ConfigManager instance = null;
	Configuration config = null;
	private boolean ready = false;
	private Set<Reloadable> reloadList = new HashSet<>();
	private ConfigManager(){
//		URL url=getServletContext().getResource("/WEB-INF/config");
	}
	
	static synchronized void init(URL config){
		if(instance != null){
			return;
		}
		instance = new ConfigManager();
		instance._init(config);
	}
	
	private void _init(URL configURL){
		try {
			this.config = new Configurations().properties(configURL);
		} catch (ConfigurationException e) {
			logger.error(e.getMessage(), e);
		}
		ready = true;
	}
	
	public static synchronized ConfigManager getInstance(){
		return instance==null?null:(instance.ready?instance:null);
	}

	public boolean containsKey(String arg0) {
		return config.containsKey(arg0);
	}

	public <T> T get(Class<T> arg0, String arg1, T arg2) {
		return config.get(arg0, arg1, arg2);
	}

	public <T> T get(Class<T> arg0, String arg1) {
		return config.get(arg0, arg1);
	}

	public Object getArray(Class<?> arg0, String arg1, Object arg2) {
		return config.getArray(arg0, arg1, arg2);
	}

	public Object getArray(Class<?> arg0, String arg1) {
		return config.getArray(arg0, arg1);
	}

	public BigDecimal getBigDecimal(String arg0, BigDecimal arg1) {
		return config.getBigDecimal(arg0, arg1);
	}

	public BigDecimal getBigDecimal(String arg0) {
		return config.getBigDecimal(arg0);
	}

	public BigInteger getBigInteger(String arg0, BigInteger arg1) {
		return config.getBigInteger(arg0, arg1);
	}

	public BigInteger getBigInteger(String arg0) {
		return config.getBigInteger(arg0);
	}

	public boolean getBoolean(String arg0, boolean arg1) {
		return config.getBoolean(arg0, arg1);
	}

	public Boolean getBoolean(String arg0, Boolean arg1) {
		return config.getBoolean(arg0, arg1);
	}

	public boolean getBoolean(String arg0) {
		return config.getBoolean(arg0);
	}

	public byte getByte(String arg0, byte arg1) {
		return config.getByte(arg0, arg1);
	}

	public Byte getByte(String arg0, Byte arg1) {
		return config.getByte(arg0, arg1);
	}

	public byte getByte(String arg0) {
		return config.getByte(arg0);
	}

	public <T> Collection<T> getCollection(Class<T> arg0, String arg1, Collection<T> arg2, Collection<T> arg3) {
		return config.getCollection(arg0, arg1, arg2, arg3);
	}

	public <T> Collection<T> getCollection(Class<T> arg0, String arg1, Collection<T> arg2) {
		return config.getCollection(arg0, arg1, arg2);
	}

	public double getDouble(String arg0, double arg1) {
		return config.getDouble(arg0, arg1);
	}

	public Double getDouble(String arg0, Double arg1) {
		return config.getDouble(arg0, arg1);
	}

	public double getDouble(String arg0) {
		return config.getDouble(arg0);
	}

	public String getEncodedString(String arg0, ConfigurationDecoder arg1) {
		return config.getEncodedString(arg0, arg1);
	}

	public String getEncodedString(String arg0) {
		return config.getEncodedString(arg0);
	}

	public float getFloat(String arg0, float arg1) {
		return config.getFloat(arg0, arg1);
	}

	public Float getFloat(String arg0, Float arg1) {
		return config.getFloat(arg0, arg1);
	}

	public float getFloat(String arg0) {
		return config.getFloat(arg0);
	}

	public int getInt(String arg0, int arg1) {
		return config.getInt(arg0, arg1);
	}

	public int getInt(String arg0) {
		return config.getInt(arg0);
	}

	public Integer getInteger(String arg0, Integer arg1) {
		return config.getInteger(arg0, arg1);
	}

	public ConfigurationInterpolator getInterpolator() {
		return config.getInterpolator();
	}

	public Iterator<String> getKeys() {
		return config.getKeys();
	}

	public Iterator<String> getKeys(String arg0) {
		return config.getKeys(arg0);
	}

	public <T> List<T> getList(Class<T> arg0, String arg1, List<T> arg2) {
		return config.getList(arg0, arg1, arg2);
	}

	public <T> List<T> getList(Class<T> arg0, String arg1) {
		return config.getList(arg0, arg1);
	}

	public List<Object> getList(String arg0, List<?> arg1) {
		return config.getList(arg0, arg1);
	}

	public List<Object> getList(String arg0) {
		return config.getList(arg0);
	}

	public long getLong(String arg0, long arg1) {
		return config.getLong(arg0, arg1);
	}

	public Long getLong(String arg0, Long arg1) {
		return config.getLong(arg0, arg1);
	}

	public long getLong(String arg0) {
		return config.getLong(arg0);
	}

	public Properties getProperties(String arg0) {
		return config.getProperties(arg0);
	}

	public Object getProperty(String arg0) {
		return config.getProperty(arg0);
	}

	public short getShort(String arg0, short arg1) {
		return config.getShort(arg0, arg1);
	}

	public Short getShort(String arg0, Short arg1) {
		return config.getShort(arg0, arg1);
	}

	public short getShort(String arg0) {
		return config.getShort(arg0);
	}

	public String getString(String arg0, String arg1) {
		return config.getString(arg0, arg1);
	}

	public String getString(String arg0) {
		return config.getString(arg0);
	}

	public String[] getStringArray(String arg0) {
		return config.getStringArray(arg0);
	}

	public int size() {
		return config.size();
	}
	public void reload(URL url){
		ready = false;
		_init(url);
		
	}
	public void registerReload(Reloadable obj){
		this.reloadList.add(obj);
	}
	public void performReload(){
		for(Reloadable r:reloadList){
			r._reload();
		}
	}
}

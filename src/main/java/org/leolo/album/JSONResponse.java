package org.leolo.album;

import java.util.ArrayList;
import java.util.List;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import org.json.simple.JSONObject;

public class JSONResponse extends ResponsePackage {

	@Override
	public String getContentType() {
		return "application/json";
	}
	
	private JSONObject json = new JSONObject();
	
	@Override
	protected byte[] getData() {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		PrintWriter pw = new PrintWriter(baos);
		try {
			json.writeJSONString(pw);
		} catch (IOException e) {
			e.printStackTrace();
		}
		pw.flush();
		return baos.toByteArray();
	}
	

	@SuppressWarnings("unchecked")
	public void put(String name, String value){
		json.put(name, value);
	}
	@SuppressWarnings("unchecked")
	public void put(String name, Object value){
		if(value.getClass().isArray()){
			json.put(name, processArray((Object[])value));
		}else{
			json.put(name, value.toString());
		}
	}
	@SuppressWarnings("unchecked")
	public void put(String name, @SuppressWarnings("rawtypes") Map value){
		json.put(name, value);
	}
	@SuppressWarnings("unchecked")
	public void put(String name, @SuppressWarnings("rawtypes") List value){
		json.put(name, value);
	}
	@SuppressWarnings("unchecked")
	public void put(String name, int value){
		json.put(name, new Integer(value));
	}
	@SuppressWarnings("unchecked")
	public void put(String name, long value){
		json.put(name, new Long(value));
	}
	@SuppressWarnings("unchecked")
	public void put(String name, boolean value){
		json.put(name, new Boolean(value));
	}
	@SuppressWarnings("unchecked")
	public void put(String name, double value){
		json.put(name, new Double(value));
	}
	
	private ArrayList<Object> processArray(Object [] oa){
		ArrayList<Object> al = new ArrayList<>();
		for(Object o:oa){
			if(o.getClass().isArray()){
				al.add(processArray((Object[])o));
			}else if(o instanceof List || o instanceof Map){
				al.add(o);
			}else{
				al.add(o);
			}
		}
		return al;
	}
}

package org.leolo.album.function;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.leolo.album.APIProcess;
import org.leolo.album.JSONResponse;
import org.leolo.album.ResponsePackage;
import org.leolo.album.Utils;

public class Test implements APIProcess {

	@Override
	public ResponsePackage process(HttpServletRequest request, HttpServletResponse response, String[] tokens)
			throws ServletException, IOException {
		JSONResponse resp = new JSONResponse();
		Map<String, Object> version = new HashMap<>();
		version.put("version", "0.0.1");
		version.put("min-api-level", "0");
		version.put("max-api-level", "0");
		version.put("extension", new ArrayList<String>());
		resp.put("version", version);
		Map<Integer, String> token = new HashMap<>();
		for(int i=0;i<tokens.length;i++)
			token.put(new Integer(i), tokens[i]);
		resp.put("tokens", token);
		resp.put("datetime", Utils.getISO8601Time());
		Map<String, Object> params = new HashMap<>();
		for(String name:request.getParameterMap().keySet()){
			String [] array = request.getParameterValues(name);
			if(array.length==1){
				params.put(name, array[0]);
			}else{
				ArrayList<String> list = new ArrayList<>();
				for(String s:array){
					list.add(s);
				}
				params.put(name, list);
			}
		}
		resp.put("parameters", params);
		return resp;
	}

}

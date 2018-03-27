package org.leolo.album.function;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.leolo.album.APIProcess;
import org.leolo.album.JSONResponse;
import org.leolo.album.ResponsePackage;

public class Version implements APIProcess {

	@Override
	public ResponsePackage process(HttpServletRequest request, HttpServletResponse response, String[] tokens)
			throws ServletException, IOException {
		JSONResponse resp = new JSONResponse();
		resp.put("version", "0.0.1");
		resp.put("min-api-level", "0");
		resp.put("max-api-level", "0");
		resp.put("extension", new ArrayList<String>());
		return resp;
	}

}

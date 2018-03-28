package org.leolo.album.function;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.leolo.album.JSONResponse;
import org.leolo.album.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Servlet implementation class TestServlet
 */
@WebServlet({ "/test", "/test/*" })
public class TestServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private final Logger logger = LoggerFactory.getLogger(TestServlet.class);
    /**;
     * @see HttpServlet#HttpServlet()
     */
    public TestServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String requestURL = request.getRequestURI().substring(request.getContextPath().length());
		ArrayList<String> tokensList = new ArrayList<>();
		StringTokenizer st = new StringTokenizer(requestURL,"/");
		while(st.hasMoreTokens()){
			tokensList.add(st.nextToken());
		}
		String [] tokenArray = new String[tokensList.size()];
		tokensList.toArray(tokenArray);
		JSONResponse resp = new JSONResponse();
		Map<String, Object> version = new HashMap<>();
		version.put("version", "0.0.1");
		version.put("min-api-level", "0");
		version.put("max-api-level", "0");
		version.put("extension", new ArrayList<String>());
		resp.put("version", version);
		Map<Integer, String> token = new HashMap<>();
		for(int i=0;i<tokenArray.length;i++)
			token.put(new Integer(i), tokenArray[i]);
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
		InputStream stream = getServletContext().getResourceAsStream("/config");
		BufferedReader br = new BufferedReader(new InputStreamReader(stream));
		resp.put("p", br.readLine());
		resp.put("parameters", params);
		String rId = Utils.getNextRequestId();
		resp.put("requestId", rId);
		logger.info("rid={}",rId);
		response.setContentType(resp.getContentType());
		resp.write(response.getOutputStream());
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}

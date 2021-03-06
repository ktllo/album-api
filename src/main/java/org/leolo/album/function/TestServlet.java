package org.leolo.album.function;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.leolo.album.ConfigManager;
import org.leolo.album.JSONResponse;
import org.leolo.album.SessionManager;
import org.leolo.album.Utils;
import org.leolo.album.dao.SessionDao;
import org.leolo.album.dao.UserDao;
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
		
		String [] tokenArray = Utils.tokenize(requestURL, "/");
		JSONResponse resp = new JSONResponse();
		resp.put("version", Utils.getVersionMap());
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
		logger.info("Test="+ConfigManager.getInstance().getString("Test"));
		logger.info("boo="+ConfigManager.getInstance().getString("boo"));
		Map<String, Object> headers = new HashMap<>();
		Enumeration<String> headerNames = request.getHeaderNames();
		while(headerNames.hasMoreElements()){
			String name = headerNames.nextElement();
			Enumeration<String> values = request.getHeaders(name);
			ArrayList<String> list = new ArrayList<>();
			while(values.hasMoreElements()){
				list.add(values.nextElement());
			}
			if(list.size()==1){
				headers.put(name, list.get(0));
			}else{
				headers.put(name, list);
			}
		}
		resp.put("headers", headers);
		resp.put("parameters", params);
		resp.put("src", Utils.getSourceAddress(request));
		resp.put("postdata", Utils.getPostMap(request));
		String rId = Utils.getNextRequestId();
		resp.put("requestId", rId);
		logger.info("rid={}",rId);
		if(request.getParameter("token")!=null){
			SessionDao sDao = new SessionDao();
			resp.put("session_status", sDao.checkSession(request.getParameter("token")).name());
			sDao.renewSession(request.getParameter("token"));
		}
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

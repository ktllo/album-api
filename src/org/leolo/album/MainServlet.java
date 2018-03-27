package org.leolo.album;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.event.ListSelectionEvent;

import org.json.simple.JSONObject;
import org.leolo.album.function.Test;
import org.leolo.album.function.Version;

/**
 * Servlet implementation class MainServlet
 */
@WebServlet("/*")
public class MainServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MainServlet() {
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
		ResponsePackage resp = null;
		if("version".equals(tokenArray[0])){
			resp = new Version().process(request, response, tokenArray);
		}else if("test".equals(tokenArray[0])){
			resp = new Test().process(request, response, tokenArray);
		}else{
			JSONResponse r = new JSONResponse();
			r.put("error", "404");
			r.put("info", "API end point does not exists");
//			response.sendError(404);
			resp = r;
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

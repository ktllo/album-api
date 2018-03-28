package org.leolo.album.function;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.leolo.album.JSONResponse;

/**
 * Servlet implementation class VersionServlet
 */
@WebServlet("/version")
public class VersionServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public VersionServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		JSONResponse resp = new JSONResponse();
		resp.put("version", "0.0.1");
		resp.put("min-api-level", "0");
		resp.put("max-api-level", "0");
		resp.put("extension", new ArrayList<String>());
		resp.put("msg", "reo");
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

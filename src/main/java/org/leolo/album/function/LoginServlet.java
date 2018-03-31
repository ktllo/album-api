package org.leolo.album.function;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.leolo.album.JSONResponse;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet({ "/login", "/logon" })
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(LoginServlet.class);
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String user = request.getParameter("user");
		String password = request.getParameter("password");
		JSONResponse resp = new JSONResponse();
		if(user==null||password==null){
			resp.put("Result","Error");
			resp.put("info", "Parameter required");
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
		}else{
			
		}

		response.setContentType(resp.getContentType());
		resp.write(response.getOutputStream());
	}
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		JSONResponse resp = new JSONResponse();
		resp.put("Result","Error");
		resp.put("info", "This end point only available with HTTP method POST");
		response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
		response.setContentType(resp.getContentType());
		resp.write(response.getOutputStream());
	}
	
	
}

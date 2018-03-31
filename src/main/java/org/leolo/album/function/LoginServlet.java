package org.leolo.album.function;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
		if(user==null||password==null){
			
		}else{
			
		}
	}

}

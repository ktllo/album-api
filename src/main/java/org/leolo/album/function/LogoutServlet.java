package org.leolo.album.function;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.leolo.album.JSONResponse;
import org.leolo.album.SessionStatus;
import org.leolo.album.Utils;
import org.leolo.album.dao.SessionDao;

/**
 * Servlet implementation class LogoutServlet
 */
@WebServlet({ "/logout", "/logoff" })
public class LogoutServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LogoutServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		JSONResponse resp = new JSONResponse();
		resp.put("Result","Error");
		resp.put("info", "This end point only available with HTTP method POST");
		response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
		response.setContentType(resp.getContentType());
		resp.write(response.getOutputStream());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Map<String, Object> postData = Utils.getPostMap(request);
		String token = postData==null?null:(String) postData.get("token");
		JSONResponse resp = new JSONResponse();
		if(token==null){
			resp.put("Result","Error");
			resp.put("message", "Parameter required");
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
		}else{
			SessionDao sDao = new SessionDao();
			if(sDao.checkSession(token)==SessionStatus.VALID){
				sDao.invalidate(token);
			}
			resp.put("Result","OK");
			resp.put("message", "Logout Success");
		}
		response.setContentType(resp.getContentType());
		resp.write(response.getOutputStream());
	}

}

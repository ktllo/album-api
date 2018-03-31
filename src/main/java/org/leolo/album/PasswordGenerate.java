package org.leolo.album;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;

/**
 * Servlet implementation class PasswordGenerate
 */
@WebServlet("/helper/PasswordGenerate")
public class PasswordGenerate extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(PasswordGenerate.class);
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PasswordGenerate() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		JSONResponse resp = new JSONResponse();
		String password = request.getParameter("password");
		if(password==null){
			resp.put("message", "Please append get/post parameter password to the request");
		}else{
			String hash = Utils.hashPassword(password);
			logger.debug("{}:{}",password,hash);
			logger.debug("Selftest {}", Utils.verifyPassword(password, hash));
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				logger.error(e.getMessage(),e);
			}
			logger.debug("Selftest {}", Utils.verifyPassword(password, hash));
			resp.put("hash",hash );
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

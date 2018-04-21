package org.leolo.album.function;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.leolo.album.JSONResponse;
import org.leolo.album.LoginDelayManager;
import org.leolo.album.Utils;
import org.leolo.album.dao.UserDao;

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
		logger.debug("LOGIN START...");
		Map<String, Object> postData = Utils.getPostMap(request);
		String user = postData==null?null:(String) postData.get("username");
		String password = postData==null?null:(String) postData.get("password");
		String ip = Utils.getSourceAddress(request);
		long delay = LoginDelayManager.getInstance().getDelay(ip);
		if(delay <=0){
			
		}else if(delay > 500){
			JSONResponse resp = new JSONResponse();
			resp.put("Result","Error");
			long delayS = Math.round(Math.ceil(delay/1000));
			delayS++;//Off by one 
			resp.put("message", "You are still in login blackout period, please retry in "+delayS+" seconds.");
			resp.put("blackout_till", Utils.getISO8601Time(LoginDelayManager.getInstance().getLockUntil(ip)));
			resp.put("backout", delay);
			response.setStatus(481);
			response.setContentType(resp.getContentType());
			resp.write(response.getOutputStream());
			return;
		}else{
			try {
				Thread.sleep(delay);
			} catch (InterruptedException e) {
				logger.error(e.getMessage(),e);
			}
		}
		JSONResponse resp = new JSONResponse();
		if(user==null||password==null){
			resp.put("Result","Error");
			resp.put("message", "Parameter required");
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
		}else{
			String token = new UserDao().login(user, password);
			if(token == null){
				logger.info("User {} from {} cannot login because incorrect username/password", user, Utils.getSourceAddress(request));
				resp.put("Result","Error");
				resp.put("message", "Username/password incorrect");
				long till = LoginDelayManager.getInstance().setLock(ip);
				resp.put("backout", LoginDelayManager.getInstance().getDelay(ip));
				resp.put("blackout_till", Utils.getISO8601Time(till));
				response.setStatus(480);
			}else{
				logger.info("User {} from {} is logged in", user, Utils.getSourceAddress(request));
				LoginDelayManager.getInstance().remove(ip);
				resp.put("Result","OK");
				resp.put("message", "");
				resp.put("token", token);
				resp.put("motd", "");
			}
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

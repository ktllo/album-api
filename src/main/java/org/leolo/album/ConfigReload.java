package org.leolo.album;

import java.io.IOException;
import java.util.Arrays;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Servlet implementation class ConfigReload
 */
@WebServlet("/ConfigReload/*")
public class ConfigReload extends HttpServlet {
	
	Logger logger = LoggerFactory.getLogger(ConfigReload.class);
	
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ConfigReload() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String rId = Utils.getNextRequestId();
		String [] tokens = Utils.getURLPart(request);
		logger.info("tokens={}", Arrays.toString(tokens));
		JSONResponse resp = new JSONResponse();
		if(tokens.length<=1){
			resp.put("Result","Error");
			resp.put("info", "Token required");
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			resp.put("requestId", rId);
			response.setContentType(resp.getContentType());
			resp.write(response.getOutputStream());
		}else if(tokens[1].equals(ConfigManager.getInstance().getString("system.config.reloadToken"))){
			logger.warn("Request {} initized config reload.", rId);
			resp.put("sdatetime", Utils.getISO8601TimeWithMilliSecond());
			ConfigManager.getInstance().reload(request.getServletContext().getResource("/WEB-INF/config"));
			resp.put("edatetime", Utils.getISO8601TimeWithMilliSecond());
			resp.put("Result","OK");
			resp.put("requestId", rId);
			response.setContentType(resp.getContentType());
			resp.write(response.getOutputStream());
		}else{
			logger.warn("Request {} initized config reload, but without valid token", rId);
			resp.put("Result","Error");
			resp.put("info", "Invalid token");
			response.setStatus(HttpServletResponse.SC_FORBIDDEN);
			resp.put("requestId", rId);
			response.setContentType(resp.getContentType());
			resp.write(response.getOutputStream());
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}

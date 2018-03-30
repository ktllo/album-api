package org.leolo.album;

import java.io.IOException;
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
@WebServlet("/ConfigReload")
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
		JSONResponse resp = new JSONResponse();
		logger.warn("Request {} initized config reload.", rId);
		resp.put("sdatetime", Utils.getISO8601Time());
		ConfigManager.getInstance().reload(request.getServletContext().getResource("/WEB-INF/config"));
		resp.put("edatetime", Utils.getISO8601Time());
		resp.put("Result","OK");
		resp.put("requestId", rId);
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

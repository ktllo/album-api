package org.leolo.album.function;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.leolo.album.JSONResponse;
import org.leolo.album.dao.AlbumDao;
import org.leolo.album.model.Album;

/**
 * Servlet implementation class AlbumList
 */
@WebServlet("/list/album")
public class AlbumList extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AlbumList() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		JSONResponse resp = new JSONResponse();
		ArrayList<Map<String,Object>> list = new ArrayList<>();
		for(Album album:new AlbumDao().getAlbumList()){
			list.add(album.toJSONMap());
		}
		resp.put("list", list );
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

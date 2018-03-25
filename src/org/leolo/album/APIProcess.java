package org.leolo.album;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface APIProcess {
	void process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException;
}

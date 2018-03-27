package org.leolo.album;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface APIProcess {
	ResponsePackage process(HttpServletRequest request, HttpServletResponse response, String [] tokens) throws ServletException, IOException;
}

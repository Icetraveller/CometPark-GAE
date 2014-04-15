package com.cometpark.server.api;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cometpark.server.util.JsonHandler;

public class ProcessRequestServlet extends HttpServlet{
	private static final Logger log = Logger
			.getLogger(ProcessRequestServlet.class.getName());

	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		log.setLevel(Level.INFO);
		
		String lotId = req.getParameter("lot");
		log.info(" POST"+lotId);
		String jsonMessage = JsonHandler.fetchSpotsByLot(lotId);
		log.info(" jsonMessage: "+jsonMessage);
		resp.setContentType("text/html");
		resp.getWriter().write(jsonMessage);

	}
}

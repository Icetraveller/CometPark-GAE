package com.cometpark.server.api;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cometpark.server.util.Config;
import com.cometpark.server.util.JsonHandler;

public class ProcessRequestServlet extends HttpServlet {
	private static final Logger log = Logger
			.getLogger(ProcessRequestServlet.class.getName());

	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		log.setLevel(Level.INFO);

		String type = req.getParameter(Config.TYPE);
		String jsonMessage = "";
		log.info(" type: " + Integer.parseInt(type));

		switch (Integer.parseInt(type)) {
		case Config.TYPE_REQUEST_SPOTS_IN_LOT:
			String lotId = req.getParameter(Config.JSON_KEY_LOT);
			log.info(" POST" + lotId);
			jsonMessage = JsonHandler.fetchSpotsByLot(lotId);
			break;
		case Config.TYPE_REQUEST_LOTS_STATUS:
			jsonMessage = JsonHandler.fetchLotsStatusInfo();
			
			break;
		case Config.TYPE_REQUEST_LOTS_INFO:
			jsonMessage = JsonHandler.fetchLots();
			break;
		case Config.TYPE_REQUEST_SPOTS_INFO:
			jsonMessage = JsonHandler.fetchSpots();
			break;
		}
		log.info(" jsonMessage: " + jsonMessage);
		resp.setContentType("text/html");
		resp.getWriter().write(jsonMessage);
	}
}

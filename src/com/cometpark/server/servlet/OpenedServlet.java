package com.cometpark.server.servlet;

import java.io.FileReader;
import java.io.IOException;
import java.nio.CharBuffer;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cometpark.SignGuestbookServlet;
import com.cometpark.server.util.Utils;
import com.google.appengine.api.channel.ChannelMessage;
import com.google.appengine.api.channel.ChannelService;
import com.google.appengine.api.channel.ChannelServiceFactory;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;

public class OpenedServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7186312326738061248L;
	private static final Logger log = Logger.getLogger(OpenedServlet.class
			.getName());

	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		log.setLevel(Level.INFO);
		String token = req.getParameter(Utils.TOKEN_ID);
		log.info("OpenServlet post " + token);
		// push origin data store to client
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		String message = Utils.initQuery(datastore);
		log.info(message);
		ChannelService channelService = ChannelServiceFactory
				.getChannelService();
		try{
		channelService.sendMessage(new ChannelMessage(token, message));
		}catch(Exception e){
			log.info(e.getMessage());
		}
	}
}

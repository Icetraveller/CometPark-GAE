package com.cometpark;

import java.io.FileReader;
import java.io.IOException;
import java.nio.CharBuffer;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cometpark.server.util.DatastoreHelper;
import com.cometpark.server.util.Utils;
import com.google.appengine.api.channel.ChannelMessage;
import com.google.appengine.api.channel.ChannelService;
import com.google.appengine.api.channel.ChannelServiceFactory;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

public class SignGuestbookServlet extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6262806185256788819L;

	private static final Logger log = Logger
			.getLogger(SignGuestbookServlet.class.getName());
	String channelKey = "test";
	String token = "";

	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		log.setLevel(Level.INFO);
		log.info(" GET");

		// create channel
		ChannelService channelService = ChannelServiceFactory
				.getChannelService();
		token = channelService.createChannel(channelKey);
		log.info("GET " + token);

		// set response
		FileReader reader = new FileReader("index-template.jsp");
		CharBuffer buffer = CharBuffer.allocate(16384);
		reader.read(buffer);
		String index = new String(buffer.array());
		index = index.replaceAll("\\{\\{ token \\}\\}", token);
		resp.setContentType("text/html");
		resp.getWriter().write(index);
		
	}

	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		log.setLevel(Level.INFO);
		log.info(" Post");

		String jsonMessage = req.getParameter("message");
		log.info("POST input: " + jsonMessage);
		processJson(jsonMessage);
		log.setLevel(Level.INFO);
		resp.getWriter().println(token);
	}

	private void processJson(String jsonString) {
		JsonParser parser = new JsonParser();
		try {
			Object obj = parser.parse(jsonString);
			JsonObject jsonObject = (JsonObject) obj;
			int type = jsonObject.get("type").getAsInt();
			JsonElement controllerId = jsonObject.get("controllerId");
			DatastoreHelper datastoreHelper = new DatastoreHelper();
			switch (type) {
			case Utils.TYPE_SPOTS_UPDATE:
				JsonObject spotsJsonObject = jsonObject
						.getAsJsonObject("spots");
				updateClientView(spotsJsonObject.toString());
				datastoreHelper.updateRequest(spotsJsonObject);
				break;
			}
		} catch (JsonSyntaxException e) {
			log.info("processJson JsonSyntaxException");
		}
	}


	private void updateClientView(String jsonObjectString) {
		ChannelService channelService = ChannelServiceFactory
				.getChannelService();
		log.info(" Post " + token);
		channelService.sendMessage(new ChannelMessage(token, jsonObjectString));

	}
}
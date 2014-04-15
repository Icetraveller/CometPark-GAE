package com.cometpark.server.api;

import static com.google.appengine.api.taskqueue.TaskOptions.Builder.withUrl;

import java.io.FileReader;
import java.io.IOException;
import java.nio.CharBuffer;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cometpark.server.db.Datastore;
import com.cometpark.server.gcm.SendMessageServlet;
import com.cometpark.server.util.JsonHandler;
import com.cometpark.server.util.Utils;
import com.google.appengine.api.channel.ChannelMessage;
import com.google.appengine.api.channel.ChannelService;
import com.google.appengine.api.channel.ChannelServiceFactory;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.taskqueue.Queue;
import com.google.appengine.api.taskqueue.QueueFactory;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

public class StatusUpdateServlet extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6262806185256788819L;

	private static final Logger log = Logger
			.getLogger(StatusUpdateServlet.class.getName());
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

			int type = jsonObject.get(Utils.JSON_TYPE).getAsInt();
			switch (type) {
			case Utils.TYPE_SPOTS_STATUS_UPDATE: {
				JsonObject spotsJsonObject = jsonObject
						.getAsJsonObject(Utils.JSON_KEY_SPOTS);
				JsonHandler.updateSpots(spotsJsonObject);
				updateClientView(spotsJsonObject.toString());
				
				break;
			}
			case Utils.TYPE_CREATE_SPOTS: {
				JsonArray spotsJsonArray = jsonObject
						.getAsJsonArray(Utils.JSON_KEY_SPOTS);
				JsonHandler.createSpots(spotsJsonArray);
				break;
			}
			case Utils.TYPE_CREATE_LOTS: {
				JsonArray lotsJsonArray = jsonObject
						.getAsJsonArray(Utils.JSON_KEY_LOTS);
				JsonHandler.createLots(lotsJsonArray);
				break;
			}
			}
		} catch (JsonSyntaxException e) {
			log.info("processJson JsonSyntaxException");
		}
	}

	private void updateClientView(String jsonObjectString) {
		ChannelService channelService = ChannelServiceFactory
				.getChannelService();
		log.info(" Post " + token);
		log.info(" Post " + jsonObjectString);
		channelService.sendMessage(new ChannelMessage(token, jsonObjectString));
		
		String jsonMessage = JsonHandler.fetchSpotsByLot("0");

		List<String> devices = Datastore.getDevices();
		if (devices.isEmpty()) {
		} else {
			Queue queue = QueueFactory.getQueue("gcm");
			// NOTE: check below is for demonstration purposes; a real
			// application
			// could always send a multicast, even for just one recipient
			if (devices.size() == 1) {
				// send a single message using plain post
				String device = devices.get(0);
				queue.add(withUrl("/send").param(
						SendMessageServlet.PARAMETER_DEVICE, device).param("message", jsonMessage));
			}
		}

	}
}
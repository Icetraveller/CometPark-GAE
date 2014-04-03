package com.cometpark;

import com.google.appengine.api.channel.ChannelMessage;
import com.google.appengine.api.channel.ChannelService;
import com.google.appengine.api.channel.ChannelServiceFactory;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

import java.io.FileReader;
import java.io.IOException;
import java.net.URI;
import java.nio.CharBuffer;
import java.util.Date;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.logging.Level;
import java.util.logging.Logger;

public class SignGuestbookServlet extends HttpServlet {
	private static final Logger log = Logger
			.getLogger(SignGuestbookServlet.class.getName());
	String channelKey = "test";
	String token="";
	
	 @Override
	  public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		 
		 log.setLevel(Level.INFO);
			log.info(" GET");
		 ChannelService channelService = ChannelServiceFactory
					.getChannelService();
			token = channelService.createChannel(channelKey);
			log.info("GET "+token);
			
			FileReader reader = new FileReader("guestbook.jsp");
		    CharBuffer buffer = CharBuffer.allocate(16384);
		    reader.read(buffer);
		    String index = new String(buffer.array());
			/**
			 * 
			 */
			index = index.replaceAll("\\{\\{ token \\}\\}", token);
			index = index.replaceAll("\\{\\{ content \\}\\}", "lol");
			
			resp.setContentType("text/html");
		    resp.getWriter().write(index);
	  }

	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
//		UserService userService = UserServiceFactory.getUserService();
//		User user = userService.getCurrentUser();
//
//		String guestbookName = req.getParameter("guestbookName");
//		Key guestbookKey = KeyFactory.createKey("Guestbook", guestbookName);
		String content = req.getHeader("content");
		log.setLevel(Level.INFO);
		log.info(content + " Post");
//		Date date = new Date();
//
//		DatastoreService datastore = DatastoreServiceFactory
//				.getDatastoreService();
//		// Place greeting in same entity group as guestbook
//		Entity greeting = new Entity("Greeting", guestbookKey);
//		greeting.setProperty("user", user);
//		greeting.setProperty("date", date);
//		greeting.setProperty("content", content);
//		Query query = new Query("Greeting", guestbookKey).setAncestor(
//				guestbookKey).addSort("date", Query.SortDirection.DESCENDING);
//
//		List<Entity> greetings = datastore.prepare(query).asList(
//				FetchOptions.Builder.withLimit(10));
//		datastore.put(greeting);
		// TODO
		/*
		 * so for here we just do query, and put them into a list and pass to
		 * client java script
		 */
		// This is what actually sends the message.

		/**
		 * 
		 */
		ChannelService channelService = ChannelServiceFactory.getChannelService();
		log.info(" Post "+token);
		channelService.sendMessage(new ChannelMessage(token, content));
	}
}
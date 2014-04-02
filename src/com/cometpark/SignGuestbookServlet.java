package com.cometpark;

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

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.logging.Level;
import java.util.logging.Logger;

public class SignGuestbookServlet extends HttpServlet {
	 private static final Logger log = Logger.getLogger(SignGuestbookServlet.class.getName());
	 
	 
	
	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws IOException { 
		UserService userService = UserServiceFactory.getUserService();
		User user = userService.getCurrentUser();
		
		ChannelService channelService = ChannelServiceFactory.getChannelService();
		String channelKey = "test";
		String token = channelService.createChannel(channelKey);

		String guestbookName = req.getParameter("guestbookName");
		Key guestbookKey = KeyFactory.createKey("Guestbook", guestbookName);
		String content = req.getHeader("content");
		log.setLevel(Level.INFO);
		log.info(content+" An informational message:"+req.toString());
		Date date = new Date();

		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		// Place greeting in same entity group as guestbook
		Entity greeting = new Entity("Greeting", guestbookKey);
		greeting.setProperty("user", user);
		greeting.setProperty("date", date);
		greeting.setProperty("content", content);
		Query query = new Query("Greeting", guestbookKey).setAncestor(
				guestbookKey).addSort("date", Query.SortDirection.DESCENDING);

		List<Entity> greetings = datastore.prepare(query).asList(
				FetchOptions.Builder.withLimit(10));
		datastore.put(greeting);

		resp.sendRedirect("/guestbook.jsp?guestbookName=" + guestbookName);
	}
}
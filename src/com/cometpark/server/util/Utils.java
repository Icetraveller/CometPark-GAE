package com.cometpark.server.util;

import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.cometpark.SignGuestbookServlet;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.labs.repackaged.org.json.JSONObject;

public class Utils {
	private static final Logger log = Logger.getLogger(Utils.class.getName());

	public static final int TYPE_SPOTS_UPDATE = 1;
	public static final String SPOT_KIND = "Spot";
	public static final String SPOT_ID = "Spot_id";
	public static final String SPOT_AVAILABILITY = "availability";
	
	public static final String TOKEN_ID = "token_id";

	public static String initQuery(DatastoreService datastore) {
		Query query = new Query(SPOT_KIND);
		PreparedQuery pq = datastore.prepare(query);
		HashMap<Long, Integer> map = new HashMap<Long, Integer>();
		for (Entity result : pq.asIterable()) {
			Key key = result.getKey();
			Long id = key.getId();
			String availability = result.getProperty(SPOT_AVAILABILITY).toString();
			map.put(id, Integer.parseInt(availability));
		}
		JSONObject message = new JSONObject(map);
		return message.toString();
	}
}

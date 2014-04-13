package com.cometpark.server.util;

import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.cometpark.server.servlet.StatusUpdateServlet;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.labs.repackaged.org.json.JSONObject;

public class Utils {
	private static final Logger log = Logger.getLogger(Utils.class.getName());

	public static final String SPOT_KIND = "Spot";
	public static final String SPOT_ID = "Spot_id";
	public static final String SPOT_AVAILABILITY = "availability";
	
	public static final String TOKEN_ID = "token_id";
	
	public static final int PERMIT_TYPE_GREEN = 1;
	public static final int PERMIT_TYPE_GOLD = 2;
	public static final int PERMIT_TYPE_PURPLE = 3;
	public static final int PERMIT_TYPE_ORANGE = 4;
	
	public static final int STATUS_AVAILABLE = 0;
	public static final int STATUS_OCCUPIED = 1;
	
	public static final int TYPE_SPOTS_STATUS_UPDATE = 1;
	public static final int TYPE_LOTS_STATUS_UPDATE = 2;
	public static final int TYPE_CREATE = 3;
	public static final int TYPE_DELETE = 4;
	
	public static final String JSON_TYPE = "type";
	public static final String JSON_CONTROLLER_ID = "controllerId";
	public static final String JSON_SPOTS = "spots";
	public static final String JSON_LOTS = "lots";

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

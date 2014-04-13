package com.cometpark.server.util;

import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.cometpark.server.api.StatusUpdateServlet;
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
	public static final int TYPE_CREATE_SPOTS = 3;
	public static final int TYPE_CREATE_LOTS = 4;
	public static final int TYPE_DELETE_SPOTS = 5;
	public static final int TYPE_DELETE_LOTS = 6;
	
	public static final String JSON_TYPE = "type";
	public static final String JSON_CONTROLLER_ID = "controllerId";
	public static final String JSON_KEY_SPOTS = "spots";
	public static final String JSON_KEY_LOTS = "lots";
	
	public static final String JSON_KEY_ID = "id";
	public static final String JSON_KEY_LOT = "lot";
	public static final String JSON_PERMIT_TYPE = "permitType";
	public static final String JSON_KEY_NAME = "name";
	public static final String JSON_KEY_FILENAME = "filename";
	public static final String JSON_KEY_URL = "url";
	public static final String JSON_KEY_STATUS = "status";
	public static final String JSON_KEY_LOCATION = "location";
	public static final String JSON_KEY_LAT = "lat";
	public static final String JSON_KEY_LNG = "lng";
	public static final String JSON_KEY_DATA_VERSION = "dataVersion";
	
	
	public static final String JSON_KEY_TOP_LEFT = "topLeft";
	public static final String JSON_KEY_TOP_RIGHT = "topRight";
	public static final String JSON_KEY_BOTTOM_LEFT = "bottomLeft";
	public static final String JSON_KEY_BOTTOM_RIGHT = "bottomRight";

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

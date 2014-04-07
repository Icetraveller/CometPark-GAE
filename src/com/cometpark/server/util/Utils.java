package com.cometpark.server.util;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;

public class Utils {
	public static final int TYPE_SPOTS_UPDATE = 1;
	public static final String SPOT_KIND = "Spot";
	
	public static void initQuery(DatastoreService datastore){
		Query query = new Query(SPOT_KIND);
		PreparedQuery pq = datastore.prepare(query);
		
	}
}


package com.cometpark.server.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class DatastoreHelper {
	
	public void updateRequest(JsonObject spotsJsonObject) {
		Iterator<Entry<String, JsonElement>> iterator = spotsJsonObject
				.entrySet().iterator();
		HashMap<Integer, Integer> map = new HashMap<Integer,Integer>();
		while (iterator.hasNext()) {
			Entry<String, JsonElement> entry = iterator.next();
			//TODO catch illegal number exc
			int spotId = Integer.parseInt(entry.getKey());
			int availability = entry.getValue().getAsInt();
			map.put(spotId, availability);
		}
		if(map.size() >0){
			updateDB(map);
		}
	}
	
	private void updateDB(HashMap<Integer, Integer> map) {
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		Iterator<Entry<Integer, Integer>> iterator = map.entrySet().iterator();
		ArrayList<Entity> arrayList = new ArrayList<Entity>();
		while(iterator.hasNext()){
			Entry<Integer, Integer> entry = iterator.next();
			int spotId = entry.getKey();
			int availability = entry.getValue();
			Entity spot = new Entity("Spot",spotId);
			spot.setProperty("availability", availability);
			arrayList.add(spot);
		}
		datastore.put(arrayList);
	}
}

package com.cometpark.server.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import com.cometpark.server.db.LotStore;
import com.cometpark.server.db.SpotStore;
import com.cometpark.server.db.models.Lot;
import com.cometpark.server.db.models.Spot;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class JsonHandler {
	
	public static String fetchSpotsByLot(String lotId){
		HashMap map = new HashMap();
		List<Spot> spotList = SpotStore.findSpotsByLotId(lotId);
		long dataVersion = System.currentTimeMillis();
		Gson gson = new GsonBuilder().create();
		map.put(Config.JSON_KEY_SPOTS, spotList);
		map.put(Config.JSON_KEY_LOT, lotId);
		map.put(Config.JSON_KEY_DATA_VERSION, dataVersion);
		return gson.toJson(map);
	}

	public static void updateSpots(JsonObject spotsJsonObject) {
		Iterator<Entry<String, JsonElement>> iterator = spotsJsonObject
				.entrySet().iterator();
		while (iterator.hasNext()) {
			Entry<String, JsonElement> entry = iterator.next();
			String spotId = entry.getKey();
			int status = entry.getValue().getAsInt();
			SpotStore.updateSpotStatus(spotId, status);
		}
	}

	public static void createSpots(JsonArray spotsJsonArray) {
		List<Spot> spotList = new ArrayList<Spot>();
		Iterator<JsonElement> iterator = spotsJsonArray.iterator();
		while (iterator.hasNext()) {
			JsonObject spotsJsonObject = iterator.next().getAsJsonObject();
			String spotId = spotsJsonObject.get(Config.JSON_KEY_ID)
					.getAsString();
			String lotId = spotsJsonObject.get(Config.JSON_KEY_LOT)
					.getAsString();
			int type = spotsJsonObject.get(Config.JSON_PERMIT_TYPE).getAsInt();
			double lat = spotsJsonObject.get(Config.JSON_KEY_LAT).getAsDouble();
			double lng = spotsJsonObject.get(Config.JSON_KEY_LNG).getAsDouble();
			int status = spotsJsonObject.get(Config.JSON_KEY_STATUS).getAsInt();
			Spot spot = SpotStore.addSpot(spotId, lotId, type, lat, lng);
			if(spot !=null){
				spotList.add(spot);
			}
		}
		
		//apply batch
		SpotStore.createSpots(spotList);
	}

	public static void createLots(JsonArray spotsJsonArray) {
		List<Lot> lotList = new ArrayList<Lot>();
		Iterator<JsonElement> iterator = spotsJsonArray.iterator();
		while (iterator.hasNext()) {
			JsonObject lotsJsonObject = iterator.next().getAsJsonObject();
			String lotId = lotsJsonObject.get(Config.JSON_KEY_ID).getAsString();
			String name = lotsJsonObject.get(Config.JSON_KEY_NAME).getAsString();
			String filename = lotsJsonObject.get(Config.JSON_KEY_FILENAME)
					.getAsString();
			String url = lotsJsonObject.get(Config.JSON_KEY_URL).getAsString();
			int status = lotsJsonObject.get(Config.JSON_KEY_STATUS).getAsInt();
			JsonObject locationJsonObject = lotsJsonObject.get(Config.JSON_KEY_LOCATION).getAsJsonObject();
			
			double[] locationTopLeft = parseLocation(locationJsonObject.get(
					Config.JSON_KEY_TOP_LEFT).getAsJsonObject());
			double[] locationTopRight = parseLocation(locationJsonObject.get(
					Config.JSON_KEY_TOP_RIGHT).getAsJsonObject());
			double[] locationBottomLeft = parseLocation(locationJsonObject.get(
					Config.JSON_KEY_BOTTOM_LEFT).getAsJsonObject());
			double[] locationBottomRight = parseLocation(locationJsonObject.get(
					Config.JSON_KEY_BOTTOM_RIGHT).getAsJsonObject());
			Lot lot = LotStore.addLot(lotId, name, filename, url, locationTopLeft,
					locationTopRight, locationBottomLeft, locationBottomRight);
			
			if(lot !=null){
				lotList.add(lot);
			}
		}
		
		LotStore.createLots(lotList);
	}

	public static double[] parseLocation(JsonObject locationJsonObject) {
		double lat = locationJsonObject.get(Config.JSON_KEY_LAT).getAsDouble();
		double lng = locationJsonObject.get(Config.JSON_KEY_LNG).getAsDouble();
		return new double[] { lat, lng };
	}

}

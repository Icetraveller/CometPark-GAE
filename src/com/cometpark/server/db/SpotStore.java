package com.cometpark.server.db;

import static com.cometpark.server.db.OfyService.ofy;

import java.util.List;
import java.util.logging.Logger;

import com.cometpark.server.db.models.Lot;
import com.cometpark.server.db.models.Spot;
import com.cometpark.server.util.Utils;

public class SpotStore {
	private static final Logger LOG = Logger.getLogger(SpotStore.class
			.getName());

	public static void addSpot(String spotId, String lotId, int type,
			double lat, double lng) {
		LOG.info("Add spot( " + spotId + " ) in Lot(" + lotId + " )");
		Spot oldSpot = findSpotBySpotId(spotId);
		Lot oldLot = LotStore.findLotByLotId(lotId);
		if (oldLot == null) {
			return;
		}
		if (oldSpot == null) {
			// existing spot not found
			Spot newSpot = new Spot();
			newSpot.setId(spotId);
			newSpot.setLot(lotId);
			newSpot.setType(type);
			newSpot.setLat(lat);
			newSpot.setLng(lng);
			newSpot.setStatus(Utils.STATUS_AVAILABLE);
			ofy().save().entity(newSpot);
		} else {
			LOG.warning(spotId + " is already added");
			if (lotId .equals( oldSpot.getLot())) {
				oldSpot.setId(spotId);
				oldSpot.setLot(lotId);
				oldSpot.setType(type);
				oldSpot.setLat(lat);
				oldSpot.setLng(lng);
				oldSpot.setStatus(Utils.STATUS_AVAILABLE);
				ofy().save().entity(oldSpot);
			}
		}
	}
	
	public static void deleteSpot(String spotId){
		Spot oldSpot = findSpotBySpotId(spotId);
		if(oldSpot ==null){
			LOG.warning(spotId + " is already deleted");
			return;
		}
		LOG.warning("Deleting spot "+ spotId);
		ofy().delete().entity(oldSpot);
	}
	
	public static void updateSpotStatus(String spotId, int status){
		Spot oldSpot = findSpotBySpotId(spotId);
		if(oldSpot != null){
			oldSpot.setStatus(status);
			ofy().save().entity(oldSpot);
		}else{
			LOG.warning("spot "+spotId+" not exists");
		}
	}
	
	

	public static Spot findSpotBySpotId(String id) {
		return ofy().load().type(Spot.class).id(id).get();
	}
	
	public static List<Spot> findSpotByLotId(String lotId){
		return ofy().load().type(Spot.class).filter("lot", lotId).list();
	}

}

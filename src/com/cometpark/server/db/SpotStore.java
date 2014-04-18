package com.cometpark.server.db;

import static com.cometpark.server.db.OfyService.ofy;

import java.util.List;
import java.util.logging.Logger;

import com.cometpark.server.db.models.Lot;
import com.cometpark.server.db.models.Spot;
import com.cometpark.server.util.Config;

public class SpotStore {
	private static final Logger LOG = Logger.getLogger(SpotStore.class
			.getName());

	public static Spot addSpot(String spotId, String lotId, int type,
			double lat, double lng) {
		LOG.info("Add spot( " + spotId + " ) in Lot(" + lotId + " )");
		Spot oldSpot = findSpotBySpotId(spotId);
		Lot oldLot = LotStore.findLotByLotId(lotId);
		if (oldLot == null) {
			LOG.warning("lot does not exist");
			return null;
		}
		if (oldSpot == null) {
			// existing spot not found
			Spot newSpot = new Spot();
			newSpot.setId(spotId);
			newSpot.setLot(lotId);
			newSpot.setType(type);
			newSpot.setLat(lat);
			newSpot.setLng(lng);
			newSpot.setStatus(Config.STATUS_AVAILABLE);
			return newSpot;
//			ofy().save().entity(newSpot);
		} else {
			LOG.warning(spotId + " is already added");
			if (lotId.equals(oldSpot.getLot())) {
				oldSpot.setId(spotId);
				oldSpot.setLot(lotId);
				oldSpot.setType(type);
				oldSpot.setLat(lat);
				oldSpot.setLng(lng);
				oldSpot.setStatus(Config.STATUS_AVAILABLE);
				return oldSpot;
//				ofy().save().entity(oldSpot);
			}
		}
		return null;
	}
	
	public static void createSpots(List<Spot> spotsList){
		ofy().save().entities(spotsList);
	}

	public static void deleteSpot(String spotId) {
		Spot oldSpot = findSpotBySpotId(spotId);

		if (oldSpot == null) {
			LOG.warning(spotId + " is already deleted");
			return;
		}
		LOG.warning(spotId + " is illegal");
		LOG.warning("Deleting spot " + spotId);
		ofy().delete().entity(oldSpot);
	}

	public static void updateSpotStatus(String spotId, int status) {
		Spot oldSpot = findSpotBySpotId(spotId);
		if (oldSpot != null) {
			oldSpot.setStatus(status);
			ofy().save().entity(oldSpot);
		} else {
			LOG.warning("spot " + spotId + " not exists");
		}
	}

	public static Spot findSpotBySpotId(String id) {
		return ofy().load().type(Spot.class).id(id).get();
	}
	
	public static List<Spot> fetchAllSpots(){
		return ofy().load().type(Spot.class).list();
	}

	public static List<Spot> findSpotsByLotId(String lotId) {
		return ofy().cache(false).load().type(Spot.class).filter(Config.JSON_KEY_LOT, lotId)
				.list();
	}

}

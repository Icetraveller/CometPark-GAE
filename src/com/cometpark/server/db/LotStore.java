package com.cometpark.server.db;

import static com.cometpark.server.db.OfyService.ofy;

import java.util.logging.Logger;

import com.cometpark.server.db.models.Lot;
import com.cometpark.server.util.Config;

public class LotStore {
	private static final Logger LOG = Logger
			.getLogger(LotStore.class.getName());

	public static void addLot(String id, String name, String filename,
			String url, double[] locationTopLeft, double[] locationTopRight,
			double[] locationBottomLeft, double[] locationBottomRight) {
		LOG.info("id=" + id + " name=" + name + " filename=" + filename
				+ " url=" + url);

		Lot oldLot = findLotByLotId(id);
		if (oldLot == null) {
			Lot newLot = new Lot();
			newLot.setId(id);
			newLot.setName(name);
			newLot.setFilename(filename);
			newLot.setUrl(url);
			newLot.setLocationTopLeft(locationTopLeft);
			newLot.setLocationTopRight(locationTopRight);
			newLot.setLocationBottomLeft(locationBottomLeft);
			newLot.setLocationBottomRight(locationBottomRight);
			newLot.setStatus(Config.STATUS_AVAILABLE);
			ofy().save().entity(newLot);
		} else {
			if (oldLot.getName().equals(name)) {
				oldLot.setId(id);
				oldLot.setName(name);
				oldLot.setFilename(filename);
				oldLot.setUrl(url);
				oldLot.setLocationTopLeft(locationTopLeft);
				oldLot.setLocationTopRight(locationTopRight);
				oldLot.setLocationBottomLeft(locationBottomLeft);
				oldLot.setLocationBottomRight(locationBottomRight);
				oldLot.setStatus(Config.STATUS_AVAILABLE);
				ofy().save().entity(oldLot);
			}
		}
	}

	public static void deleteLot(String lotId) {
		Lot oldLot = findLotByLotId(lotId);
		if (oldLot == null) {
			LOG.warning(lotId + " is already deleted");
			return;
		}
		LOG.warning("Deleting lot " + lotId);
		ofy().delete().entity(oldLot);
	}

	public static void updateLotStatus(String lotId, int status) {
		Lot oldLot = findLotByLotId(lotId);
		if (oldLot != null) {
			oldLot.setStatus(status);
			ofy().save().entity(oldLot);
		} else {
			LOG.warning("lot " + oldLot + " not exists");
		}
	}

	public static Lot findLotByLotId(String id) {
		return ofy().load().type(Lot.class).id(id).get();
	}
}

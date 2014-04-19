package com.cometpark.server.db;

import static com.cometpark.server.db.OfyService.ofy;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;

import com.cometpark.server.api.ProcessRequestServlet;
import com.cometpark.server.db.models.Lot;
import com.cometpark.server.db.models.LotStatus;
import com.cometpark.server.db.models.Spot;
import com.cometpark.server.util.Config;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.cmd.Query;

public class LotStatusStore {

	private static final Logger log = Logger
			.getLogger(ProcessRequestServlet.class.getName());

	public static LotStatus findLotByLotId(String id) {
		return ofy().load().type(LotStatus.class).id(id).get();
	}

	/**
	 * Very inefficient query of available spots and max spots in whole parking
	 * lot. The max spots number are stored in int array[ permit type number +1]
	 */
	public static List<LotStatus> work() {
		Iterable<Key<Lot>> lotKeys = ofy().load().type(Lot.class).keys();
		Iterator<Key<Lot>> iterator = lotKeys.iterator();

		List<LotStatus> lotStatusList = new ArrayList<LotStatus>();
		while (iterator.hasNext()) {
			Key<Lot> key = iterator.next();
			String lotId = key.getName();
			Query<Spot> q = ofy().load().type(Spot.class)
					.filter(Config.JSON_KEY_LOT, lotId);
			int max = q.count();
			q = q.filter(Config.JSON_KEY_STATUS, Config.STATUS_AVAILABLE);
			int[] availableSpotsCount = new int[Config.PERMIT_TYPE_SUM + 1];
			for (int i = 0; i < Config.PERMIT_TYPE_SUM; i++) {
				availableSpotsCount[i] = q.filter(Config.JSON_PERMIT_TYPE, i)
						.count();
			}
			availableSpotsCount[Config.PERMIT_TYPE_SUM] = max;

			LotStatus lotStatus = new LotStatus();
			lotStatus.setLotId(lotId);
			lotStatus.setAvailableSpotsCount(availableSpotsCount);
			lotStatusList.add(lotStatus);
		}
		return lotStatusList;
	}

	/**
	 * Very inefficient query of available spots and max spots in whole parking
	 * lot. The max spots number are stored in int array[ permit type number +1]
	 * This one preferred.
	 */
	public static List<LotStatus> work2() {
		Iterable<Key<Lot>> lotKeys = ofy().load().type(Lot.class).keys();
		Iterator<Key<Lot>> iterator = lotKeys.iterator();

		List<LotStatus> lotStatusList = new ArrayList<LotStatus>();
		while (iterator.hasNext()) {
			Key<Lot> key = iterator.next();
			String lotId = key.getName();
			Iterator<Spot> spotIterator = ofy().cache(false).load().type(Spot.class)
					.filter(Config.JSON_KEY_LOT, lotId).iterator();
			int max = 0;
			int[] availableSpotsCount = new int[Config.PERMIT_TYPE_SUM + 1];
			while (spotIterator.hasNext()) {
				Spot spot = spotIterator.next();
				max++;
				int status = spot.getStatus();
				if(status == Config.STATUS_AVAILABLE){
					int permitType = spot.getType();
					availableSpotsCount[permitType]++;
				}
			}
			availableSpotsCount[Config.PERMIT_TYPE_SUM] = max;
			LotStatus lotStatus = new LotStatus();
			lotStatus.setLotId(lotId);
			lotStatus.setAvailableSpotsCount(availableSpotsCount);
			lotStatusList.add(lotStatus);
		}
		return lotStatusList;
	}

	public static List<LotStatus> fetchLotStatusInfo() {
		return ofy().load().type(LotStatus.class).list();
	}

}

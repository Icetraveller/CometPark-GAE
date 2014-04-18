package com.cometpark.server.db;

import static com.cometpark.server.db.OfyService.ofy;

import java.util.HashMap;
import java.util.Iterator;
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
	 * Very inefficient query of available spots and max spots in whole parking lot.
	 * The max spots number are stored in int array[ permit type number +1]
	 */
	public static void work() {
		Iterable<Key<Lot>> lotKeys = ofy().load().type(Lot.class).keys();
		Iterator<Key<Lot>> iterator = lotKeys.iterator();
		HashMap<String, int[]> map = new HashMap<String, int[]>();
		while (iterator.hasNext()) {
			Key<Lot> key = iterator.next();
			String lotId = key.getName();
			Query<Spot> q = ofy().load().type(Spot.class)
					.filter(Config.JSON_KEY_LOT, lotId);
			int max = q.count();
			q = q.filter(Config.JSON_KEY_STATUS, Config.STATUS_AVAILABLE);
			int[] availableSpotsCount = new int[Config.PERMIT_TYPE_SUM+1];
			for (int i = 0; i < Config.PERMIT_TYPE_SUM; i++) {
				availableSpotsCount[i] = q.filter(Config.JSON_PERMIT_TYPE, i)
						.count();
			}
			availableSpotsCount[Config.PERMIT_TYPE_SUM] = max;
			map.put(lotId, availableSpotsCount);
		}
	}

}

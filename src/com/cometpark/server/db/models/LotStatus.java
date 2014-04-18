package com.cometpark.server.db.models;

import com.googlecode.objectify.annotation.Cache;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

@Entity
@Cache(expirationSeconds= 60) //TODO in final release it will be 5*60
public class LotStatus {
	@Id private String lotId;
	private int[] available_spots_count;
	
	public int[] getAvailableSpotsCount() {
		return available_spots_count;
	}
	public void setAvailableSpotsCount(int[] availableSpotsCount) {
		this.available_spots_count = availableSpotsCount;
	}
	public String getLotId() {
		return lotId;
	}
	public void setLotId(String lotId) {
		this.lotId = lotId;
	}
}

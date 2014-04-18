package com.cometpark.server.db.models;

import com.googlecode.objectify.annotation.Cache;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

@Entity
@Cache(expirationSeconds= 60) //TODO in final release it will be 5*60
public class LotStatus {
	@Id private String lotId;
	public String getLotId() {
		return lotId;
	}
	public void setLotId(String lotId) {
		this.lotId = lotId;
	}
	public int getMax() {
		return max;
	}
	public void setMax(int max) {
		this.max = max;
	}
	public int getAvailable() {
		return available;
	}
	public void setAvailable(int available) {
		this.available = available;
	}
	private int max;
	private int available;
}

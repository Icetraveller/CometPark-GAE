package com.cometpark.server.db.models;

import com.googlecode.objectify.annotation.Embed;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

@Entity
public class Lot {
	@Id private String id;
	private String name;
	private String filename;
	private String url;
	private int status;
	
	@Embed
	public class Location {
		@Id String locationId;
	    String lat;
	    String lng;
	}
	private Location locationTopLeft;
	private Location locationTopRight;
	private Location locationBottomLeft;
	private Location locationBottomRight;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getFilename() {
		return filename;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public Location getLocationTopLeft() {
		return locationTopLeft;
	}
	public void setLocationTopLeft(Location locationTopLeft) {
		this.locationTopLeft = locationTopLeft;
	}
	public Location getLocationTopRight() {
		return locationTopRight;
	}
	public void setLocationTopRight(Location locationTopRight) {
		this.locationTopRight = locationTopRight;
	}
	public Location getLocationBottomLeft() {
		return locationBottomLeft;
	}
	public void setLocationBottomLeft(Location locationBottomLeft) {
		this.locationBottomLeft = locationBottomLeft;
	}
	public Location getLocationBottomRight() {
		return locationBottomRight;
	}
	public void setLocationBottomRight(Location locationBottomRight) {
		this.locationBottomRight = locationBottomRight;
	}
}

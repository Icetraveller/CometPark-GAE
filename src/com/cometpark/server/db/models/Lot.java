package com.cometpark.server.db.models;

import com.googlecode.objectify.annotation.Embed;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
@Embed
class LocationTopLeft {
	double lat;
	double lng;
}

@Embed
class LocationTopRight {
	double lat;
	double lng;
}

@Embed
class LocationBottomLeft {
	double lat;
	double lng;
}

@Embed
class LocationBottomRight {
	double lat;
	double lng;
}
@Entity
public class Lot {
	@Id private String id;
	private String name;
	private String filename;
	private String url;
	private int status;
	private LocationTopLeft locationTopLeft = new LocationTopLeft();
	private LocationTopRight locationTopRight = new LocationTopRight();
	private LocationBottomLeft locationBottomLeft = new LocationBottomLeft();
	private LocationBottomRight locationBottomRight = new LocationBottomRight();

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
	
	public void setLocationTopLeft(double[] d){
		locationTopLeft.lat = d[0];
		locationTopLeft.lng = d[1];
	}
	public void setLocationTopRight(double[] d){
		locationTopRight.lat = d[0];
		locationTopRight.lng = d[1];
	}
	public void setLocationBottomLeft(double[] d){
		locationBottomLeft.lat = d[0];
		locationBottomLeft.lng = d[1];
	}
	public void setLocationBottomRight(double[] d){
		locationBottomRight.lat = d[0];
		locationBottomRight.lng = d[1];
	}

}

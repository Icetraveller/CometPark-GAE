package com.cometpark.server.db.models;

import com.googlecode.objectify.annotation.Cache;
import com.googlecode.objectify.annotation.Embed;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.OnSave;
@Embed
class Location {
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
	private Location topLeft;
	private Location topRight ;
	private Location bottomLeft ;
	private Location bottomRight ;
	
	
	public Lot(){
		topLeft = new Location();
		topRight = new Location();
		bottomLeft = new Location();
		bottomRight = new Location();
	}
	
	public String toString(){
		return topLeft.lat+","+topLeft.lng;
	}

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
		topLeft.lat = d[0];
		topLeft.lng = d[1];
	}
	public void setLocationTopRight(double[] d){
		topRight.lat = d[0];
		topRight.lng = d[1];
	}
	public void setLocationBottomLeft(double[] d){
		bottomLeft.lat = d[0];
		bottomLeft.lng = d[1];
	}
	public void setLocationBottomRight(double[] d){
		bottomRight.lat = d[0];
		bottomRight.lng = d[1];
	}
	
	public Location getTopLeft() {
		return topLeft;
	}

	public Location getTopRight() {
		return topRight;
	}

	public Location getBottomLeft() {
		return bottomLeft;
	}

	public Location getBottomRight() {
		return bottomRight;
	}
}

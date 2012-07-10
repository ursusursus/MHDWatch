package com.ursus.mhdwatch;

import android.location.Location;

public class PointOfInterest {
	
	
	private Location location;
	private String name;
	private String[] urls;
	
	
	public PointOfInterest(String name, Location location, String[] urls) {
		
		this.name = name;
		this.location = location;
		this.urls = urls;
		
	}
	
	public String getName() {
		
		return name;
		
	}

	public Location getLocation() {
		
		return location;
		
	}
	
	public String[] getMHDUrls() {
		
		return urls;
		
	}

}

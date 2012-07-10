package com.ursus.mhdwatch.item;


public class ListItem extends Item {
	
	
	private String departureTime;
	private String remainingTime;
	
		
	public ListItem(String departureTime, String remainingTime) {
		
		this.departureTime = departureTime;
		this.remainingTime = remainingTime;
		
	}
	
	public String getDepartureTime() {
		
		return departureTime;
		
	}

	public String getRemainingTime() {
		
		return remainingTime;
		
	}


	
	

}

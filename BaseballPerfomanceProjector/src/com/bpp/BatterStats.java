package com.bpp;

public class BatterStats {
	private String playerId;
	private String date;
	private String lastName;
	private String firstName;
	private String batHand;
	private String throwHand;
	private String position;
	
	public BatterStats(String playerId, String date, String lastName, String firstName, String batHand, String throwHand, String position) {
		this.playerId = playerId;
		this.date = date;
		this.lastName = lastName;
		this.firstName = firstName;
		this.batHand = batHand;
		this.throwHand = throwHand;
		this.position = position;
	}
	
	public boolean equals(Player player) {
		if(playerId.equals(player.getPlayerId())){
			return true;
		}
		return false;
	}
	
	public String getPlayerId() {
		return playerId;
	}
	
	public String getDate() {
		return date;
	}
	
	public String getLastName() {
		return lastName;
	}
	
	public String getFirstName() {
		return firstName;
	}
	
	public String getBatHand() {
		return batHand;
	}
	
	public String getThrowHand() {
		return throwHand;
	}
	
	public String getPosition() {
		return position;
	}
	
	public String getPlayerFullName() {
		return firstName + " " + lastName;
	}
}
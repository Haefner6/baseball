package com.bpp;

public class Player implements Comparable<Player> {
	private String playerId;
	private String lastName;
	private String firstName;
	private String batHand;
	private String throwHand;
	private String team;
	private String position;
	
	public Player() {
		this.playerId = "";
		this.lastName = "";
		this.firstName = "";
		this.batHand = "";
		this.throwHand = "";
		this.team = "";
		this.position = "";
	}
	
	public Player(String playerId, String lastName, String firstName, String batHand, String throwHand, String team, String position) {
		this.playerId = playerId;
		this.lastName = lastName;
		this.firstName = firstName;
		this.batHand = batHand;
		this.throwHand = throwHand;
		this.team = team;
		this.position = position;
	}
	
	public boolean equals(Player player) {
		if(playerId.equals(player.getPlayerId())){
			return true;
		}
		return false;
	}
	
	public void setPlayerAttributes(String playerId, String lastName, String firstName, String batHand, String throwHand, String team, String position) {
		this.playerId = playerId;
		this.lastName = lastName;
		this.firstName = firstName;
		this.batHand = batHand;
		this.throwHand = throwHand;
		this.team = team;
		this.position = position;
	}
	
	public boolean hasId() {
		return (this.playerId.length() > 4);
	}
	
	public void setPlayerId(String id) {
		this.playerId = id;
	}
	public void setPlayerLastName(String lastName) {
		this.lastName = lastName;
	}
	
	public String getPlayerId() {
		return playerId;
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
	
	public String getTeam() {
		return team;
	}
	
	public String getPosition() {
		return position;
	}
	
	public String getPlayerFullName() {
		return firstName + " " + lastName;
	}
	
	public String getPlayerSummary() {
		return lastName + ", " + firstName + " " + position + " " + team;
	}

	@Override
	public int compareTo(Player comparePlayer) {
		// TODO Auto-generated method stub
		String compareString = comparePlayer.getPlayerSummary();
		return this.getPlayerSummary().compareTo(compareString);
	}
	
	public void clearPosition() {
		position = "";
	}
	
	public void addEligibility(String newPosition) {
		if(position.equals("")) {
			position = newPosition;
		} else {
			if(position.contains(newPosition)) {
				return;
			} else {
				position = position + "," + newPosition;
			}
		}
	}
}
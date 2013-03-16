package com.bpp;

public class PitcherStats {
	private String playerId;
	private String date;
	private String wins;
	private String saves;
	private String strikeouts;
	private String era;
	private String whip;
	
	public PitcherStats(String playerId, String date, String wins, String saves, String strikeouts, String era, String whip) {
		this.playerId = playerId;
		this.date = date;
		this.wins = wins;
		this.saves = saves;
		this.strikeouts = strikeouts;
		this.era = era;
		this.whip = whip;
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
	
	public String getWins() {
		return wins;
	}
	
	public String getSaves() {
		return saves;
	}
	
	public String getStrikeouts() {
		return strikeouts;
	}
	
	public String getEra() {
		return era;
	}
	
	public String getWhip() {
		return whip;
	}
}
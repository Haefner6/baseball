package com.bpp;

public class BatterStats {
	private String playerId;
	private String date;
	private String homeRuns;
	private String runs;
	private String rbi;
	private String netSteals;
	private String onBasePercentage;
	
	public BatterStats(String playerId, String date, String homeRuns, String runs, String rbi, String netSteals, String onBasePercentage) {
		this.playerId = playerId;
		this.date = date;
		this.homeRuns = homeRuns;
		this.runs = runs;
		this.rbi = rbi;
		this.netSteals = netSteals;
		this.onBasePercentage = onBasePercentage;
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
	
	public String getHomeRuns() {
		return homeRuns;
	}
	
	public String getRuns() {
		return runs;
	}
	
	public String getRbi() {
		return rbi;
	}
	
	public String getNetSteals() {
		return netSteals;
	}
	
	public String getOBP() {
		return onBasePercentage;
	}
}
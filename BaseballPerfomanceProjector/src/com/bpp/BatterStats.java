package com.bpp;

public class BatterStats {
	private String playerId;
	private String leagueId;
	private String date;
	private String placedPosition;
	private String hits;
	private String homeRuns;
	private String rbi;
	private String walks;
	private String runs;
	private String steals;
	private String netSteals;
	private String strikeouts;
	private String obp;
	private String ops;

	public BatterStats(String playerId, String leagueId, String date,
			String placedPosition) {
		this.playerId = playerId;
		this.leagueId = leagueId;
		this.date = date;
		this.placedPosition = placedPosition;

		hits = "--";
		homeRuns = "--";
		rbi = "--";
		walks = "--";
		runs = "--";
		steals = "--";
		netSteals = "--";
		strikeouts = "--";
		obp = "--";
		ops = "--";
	}

	public BatterStats(String playerId, String leagueId, String date,
			String hits,String rbi,String runs, String homeRuns,  String walks,
			 String steals, String netSteals, String strikeouts,
			String obp, String ops, String placedPosition) {
		this.playerId = playerId;
		this.leagueId = leagueId;
		this.date = date;
		this.placedPosition = placedPosition;

		this.hits = hits;
		this.homeRuns = homeRuns;
		this.rbi = rbi;
		this.walks = walks;
		this.runs = runs;
		this.steals = steals;
		this.netSteals = netSteals;
		this.strikeouts = strikeouts;
		this.obp = obp;
		this.ops = ops;
	}

	public void setStats(String hits, String homeRuns, String rbi,
			String walks, String runs, String steals, String netSteals,
			String strikeouts, String obp, String ops) {
		this.hits = hits;
		this.homeRuns = homeRuns;
		this.rbi = rbi;
		this.walks = walks;
		this.runs = runs;
		this.steals = steals;
		this.netSteals = netSteals;
		this.strikeouts = strikeouts;
		this.obp = obp;
		this.ops = ops;
	}

	public boolean equals(Player player) {
		if (playerId.equals(player.getPlayerId())) {
			return true;
		}
		return false;
	}

	public String getPlayerId() {
		return playerId;
	}

	public String getLeagueId() {
		return leagueId;
	}

	public String getDate() {
		return date;
	}

	public String getHits() {
		return hits;
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

	public String getWalks() {
		return walks;
	}

	public String getSteals() {
		return steals;
	}

	public String getNetSteals() {
		return netSteals;
	}

	public String getStrikeouts() {
		return strikeouts;
	}

	public String getOBP() {
		return obp;
	}

	public String getOPS() {
		return ops;
	}

	public String[] toArray() {
		String[] statsArray = new String[10];

		statsArray[0] = hits;
		statsArray[1] = homeRuns;
		statsArray[2] = rbi;
		statsArray[3] = walks;
		statsArray[4] = runs;
		statsArray[5] = steals;
		statsArray[6] = netSteals;
		statsArray[7] = strikeouts;
		statsArray[8] = obp;
		statsArray[9] = ops;

		return statsArray;
	}

	public void setPosition(String position) {
		placedPosition = position;
	}

	public String getPosition() {
		return placedPosition;
	}
	
	public String toString() {
		String summary = "";
		
		summary += playerId;
		summary += ", ";
		summary += leagueId;
		summary += ", ";
		summary += date;
		summary += ", ";
		
		summary += hits;
		summary += ", ";
		summary += homeRuns;
		summary += ", ";
		summary += rbi;
		summary += ", ";
		summary += walks;
		summary += ", ";
		summary += runs;
		summary += ", ";
		summary += steals;
		summary += ", ";
		summary += netSteals;
		summary += ", ";
		summary += strikeouts;
		summary += ", ";
		summary += obp;
		summary += ", ";
		summary +=  ops;
		
		return summary;
	}
}
package com.bpp;

import java.util.ArrayList;

public class League {
	private String leagueId; 
	private int defaultLeague;
	private int catchers;
	private int firstBase;
	private int secondBase;
	private int shortStop;
	private int thirdBase;
	private int middle;
	private int corner;
	private int outfield;
	private int utility;
	private int pitchers;
	private int startingPitchers;
	private int reliefPitchers;
	private int hits;
	private int rbi;
	private int runs;
	private int homeRuns;
	private int walks;
	private int steals;
	private int netSteals;
	private int strikeouts;
	private int obp;
	private int ops;
	
	public League(String leagueId, int defaultLeague, int catchers,int firstBase,int secondBase,int shortStop,int thirdBase,int middle,int corner,int outfield,
			int utility,int pitchers,int startingPitchers,int reliefPitchers,int hits,int rbi,int runs,int homeRuns,int walks,
			int steals,int netSteals,int strikeouts,int obp,int ops) {
		this.leagueId = leagueId;
		this.defaultLeague = defaultLeague;
		this.catchers = catchers;
		this.firstBase = firstBase;
		this.secondBase = secondBase;
		this.shortStop = shortStop;
		this.thirdBase = thirdBase;
		this.middle = middle;
		this.corner = corner;
		this.outfield = outfield;
		this.utility = utility;
		this.pitchers = pitchers;
		this.startingPitchers = startingPitchers;
		this.reliefPitchers = reliefPitchers;
		this.hits = hits;
		this.rbi = rbi;
		this.runs = runs;
		this.homeRuns = homeRuns;
		this.walks = walks;
		this.steals = steals;
		this.netSteals = netSteals;
		this.strikeouts = strikeouts;
		this.obp = obp;
		this.ops = ops;
	}
	
	public boolean equals(League league) {
		if(leagueId.equals(league.getLeagueId())){
			return true;
		}
		return false;
	}

	public void setleagueId(String id) {
		this.leagueId = id;
	}
	
	public void setDefaultLeague(boolean isDefaultLeague) {
		if(isDefaultLeague) {
			this.defaultLeague = 1;
		} else {
		this.defaultLeague = 0;
		}
	}

	public String getLeagueId() {
		return leagueId;
	}
	
	public boolean isDefaultLeague() {
		if(defaultLeague ==1) {
			return true;
		} else {
			return false;
		}
	}

	public int getNumCatchers() {
		return catchers;
	}
	
	public int getNumFirstBase() {
		return firstBase;
	}
	
	public int getNumSecondBase() {
		return secondBase;
	}
	
	public int getNumShortStop() {
		return shortStop;
	}
	
	public int getNumThirdBase() {
		return thirdBase;
	}
	
	public int getNumMiddle() {
		return middle;
	}
	
	public int getNumCorner() {
		return corner;
	}
	
	public int getNumOutfield() {
		return outfield;
	}
	
	public int getNumUtility() {
		return utility;
	}
	
	public int getNumPitchers() {
		return pitchers;
	}
	
	public int getNumStartingPitchers() {
		return startingPitchers;
	}
	
	public int getNumReliefPitchers() {
		return reliefPitchers;
	}
	
	public boolean hasHits() {
		if(hits == 1) {
			return true;
		} else {
			return false;
		}
	}
	
	public boolean hasRBI() {
		if(rbi == 1) {
			return true;
		} else {
			return false;
		}
	}
	
	public boolean hasRuns() {
		if(runs == 1) {
			return true;
		} else {
			return false;
		}
	}
	
	public boolean hasHomeRuns() {
		if(homeRuns == 1) {
			return true;
		} else {
			return false;
		}
	}
	
	public boolean hasWalks() {
		if(walks == 1) {
			return true;
		} else {
			return false;
		}
	}
	
	public boolean hasSteals() {
		if(steals == 1) {
			return true;
		} else {
			return false;
		}
	}
	
	public boolean hasNetSteals() {
		if(netSteals == 1) {
			return true;
		} else {
			return false;
		}
	}
	
	public boolean hasStrikeouts() {
		if(strikeouts == 1) {
			return true;
		} else {
			return false;
		}
	}
	
	public boolean hasOBP() {
		if(obp == 1) {
			return true;
		} else {
			return false;
		}
	}
	
	public boolean hasOPS() {
		if(ops == 1) {
			return true;
		} else {
			return false;
		}
	}
	
	public void setCatchers(int x) {
		catchers = x;
	}
	public void setFirstBase(int x) {
		firstBase = x;
	}
	public void setSecondBase(int x) {
		secondBase = x;
	}
	public void setShortStop(int x) {
		shortStop = x;
	}
	public void setThirdBase(int x) {
		thirdBase = x;
	}
	public void setMiddle(int x) {
		middle = x;
	}
	public void setCorner(int x) {
		corner = x;
	}
	public void setOutfield(int x) {
		outfield = x;
	}
	public void setUtility(int x) {
		utility = x;
	}
	public void setPitchers(int x) {
		pitchers = x;
	}
	public void setStartingPitchers(int x) {
		startingPitchers = x;
	}
	
	public void setReliefPitchers(int x) {
		reliefPitchers = x;
	}
	
	public void setHits(boolean hasStat) {
		int x = 0;
		if(hasStat) {
			x= 1;
		}
		hits = x;
	}
	public void setRBI(boolean hasStat) {
		int x = 0;
		if(hasStat) {
			x= 1;
		}
		rbi = x;
	}
	public void setRuns(boolean hasStat) {
		int x = 0;
		if(hasStat) {
			x= 1;
		}
		runs = x;
	}
	public void setHomeRuns(boolean hasStat) {
		int x = 0;
		if(hasStat) {
			x= 1;
		}
		homeRuns = x;
	}
	public void setWalks(boolean hasStat) {
		int x = 0;
		if(hasStat) {
			x= 1;
		}
		walks = x;
	}
	public void setSteals(boolean hasStat) {
		int x = 0;
		if(hasStat) {
			x= 1;
		}
		steals = x;
	}
	public void setNetSteals(boolean hasStat) {
		int x = 0;
		if(hasStat) {
			x= 1;
		}
		netSteals = x;
	}
	public void setStrikeouts(boolean hasStat) {
		int x = 0;
		if(hasStat) {
			x= 1;
		}
		strikeouts = x;
	}
	public void setOBP(boolean hasStat) {
		int x = 0;
		if(hasStat) {
			x= 1;
		}
		obp = x;
	}
	public void setOPS(boolean hasStat) {
		int x = 0;
		if(hasStat) {
			x= 1;
		}
		ops = x;
	}
	
	public ArrayList<String> getBatterPositions() {
		ArrayList<String> positions = new ArrayList<String>();
		
		for(int i=0; i< catchers; i++) {
			positions.add("C");
		}
		for(int i=0; i< firstBase; i++) {
			positions.add("1B");
		}
		for(int i=0; i< secondBase; i++) {
			positions.add("2B");
		}
		for(int i=0; i< shortStop; i++) {
			positions.add("SS");
		}
		for(int i=0; i< thirdBase; i++) {
			positions.add("3B");
		}
		for(int i=0; i< middle; i++) {
			positions.add("2B/SS");
		}
		for(int i=0; i< corner; i++) {
			positions.add("1B/3B");
		}
		for(int i=0; i< outfield; i++) {
			positions.add("OF");
		}
		for(int i=0; i< utility; i++) {
			positions.add("UT");
		}
		return positions;
	}
	
	public ArrayList<String> getLeagueBatterStats() {
		ArrayList<String> leagueBatterStats = new ArrayList<String>();

		if(hasHits()) {
			leagueBatterStats.add("H");
		}
		if(hasRBI()) {
			leagueBatterStats.add("RBI");
		}
		if(hasRuns()) {
			leagueBatterStats.add("R");
		}
		if(hasHomeRuns()) {
			leagueBatterStats.add("HR");
		}
		if(hasSteals()) {
			leagueBatterStats.add("SB");
		}
		if(hasNetSteals()) {
			leagueBatterStats.add("NSB");
		}
		if(hasWalks()) {
			leagueBatterStats.add("BB");
		}
		if(hasStrikeouts()) {
			leagueBatterStats.add("K");
		}
		if(hasOBP()) {
			leagueBatterStats.add("OBP");
		}
		if(hasOPS()) {
			leagueBatterStats.add("OPS");
		}
		
		return leagueBatterStats;
	}
	
	public ArrayList<String> getAllPossibleBatterStats() {
		ArrayList<String> possibleStats = new ArrayList<String>();
		
		possibleStats.add("H");
		possibleStats.add("R");
		possibleStats.add("RBI");
		possibleStats.add("HR");
		possibleStats.add("BB");
		possibleStats.add("K");
		possibleStats.add("SB");
		possibleStats.add("NSB");
		possibleStats.add("OBP");
		possibleStats.add("OPS");
		
		return possibleStats;
	}
}
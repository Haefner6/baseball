package com.bpp;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHandler extends SQLiteOpenHelper {
	public final String TAG = "myDebug";
	// All Static variables
	// Database Version
	private static final int DATABASE_VERSION = 1;

	// Database Name
	private static final String DATABASE_NAME = "user";

	// Tables
	private static final String TABLE_LEAGUES = "leagues";
	private static final String TABLE_BATTERS_STATS = "batterStats";
	private static final String TABLE_USER_PLAYERS = "userPlayers";

	// batterStats Table Column names
	private static final String BATTER_ID = "id";
	private static final String BATTER_DATE = "date";
	private static final String BATTER_FIRST = "firstName";
	private static final String BATTER_LAST = "lastName";
	private static final String BATTER_BAT = "batHand";
	private static final String BATTER_THROWS = "throwHand";
	private static final String BATTER_TEAM = "team";
	private static final String BATTER_POSITION = "position";
	private static final String BATTER_PLACED_POSITION = "placedPosition";

	// League Settings
	private static final String LEAGUE_ID = "leagueId";
	private static final String LEAGUE_DEFAULT_LEAGUE = "defaultLeague";
	private static final String LEAGUE_CATCHER = "C";
	private static final String LEAGUE_FIRST_BASE = "firstBase";
	private static final String LEAGUE_SECOND_BASE = "secondBase";
	private static final String LEAGUE_SHORTSTOP = "shortstop";
	private static final String LEAGUE_THIRD_BASE = "thirdbase";
	private static final String LEAGUE_MIDDLE_INFIELDER = "middleInfield";
	private static final String LEAGUE_CORNER_INFIELDER = "cornerInfield";
	private static final String LEAGUE_OUTFIELD = "OF";
	private static final String LEAGUE_UTILITY = "UT";
	private static final String LEAGUE_PITCHER = "P";
	private static final String LEAGUE_STARTING_PITCHER = "SP";
	private static final String LEAGUE_RELIEF_PITCHER = "RP";
	private static final String LEAGUE_STAT_H = "H";
	private static final String LEAGUE_STAT_RBI = "RBI";
	private static final String LEAGUE_STAT_R = "R";
	private static final String LEAGUE_STAT_HR = "HR";
	private static final String LEAGUE_STAT_BB = "BB";
	private static final String LEAGUE_STAT_SB = "SB";
	private static final String LEAGUE_STAT_NSB = "NSB";
	private static final String LEAGUE_STAT_K = "K";
	private static final String LEAGUE_STAT_OBP = "OBP";
	private static final String LEAGUE_STAT_OPS = "OPS";

	public DatabaseHandler(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	// Creating Tables
	@Override
	public void onCreate(SQLiteDatabase db) {
		String CREATE_BATTER_STATS_TABLE = "CREATE TABLE "
				+ TABLE_BATTERS_STATS + "(" + BATTER_ID + " TEXT," + LEAGUE_ID
				+ " TEXT," + BATTER_DATE + " TEXT," + LEAGUE_STAT_H + " TEXT,"
				+ LEAGUE_STAT_RBI + " TEXT," + LEAGUE_STAT_R + " TEXT,"
				+ LEAGUE_STAT_HR + " TEXT," + LEAGUE_STAT_BB + " TEXT,"
				+ LEAGUE_STAT_SB + " TEXT," + LEAGUE_STAT_NSB + " TEXT,"
				+ LEAGUE_STAT_K + " TEXT," + LEAGUE_STAT_OBP + " TEXT,"
				+ LEAGUE_STAT_OPS + " TEXT," + BATTER_PLACED_POSITION + " TEXT"
				+ ")";
		String CREATE_USER_PLAYERS_TABLE = "CREATE TABLE " + TABLE_USER_PLAYERS
				+ "(" + BATTER_ID + " TEXT," + LEAGUE_ID + " TEXT,"
				+ BATTER_LAST + " TEXT," + BATTER_FIRST + " TEXT," + BATTER_BAT
				+ " TEXT," + BATTER_THROWS + " TEXT," + BATTER_TEAM + " TEXT,"
				+ BATTER_POSITION + " TEXT" + ")";
		String CREATE_LEAGUES_TABLE = "CREATE TABLE " + TABLE_LEAGUES + "("
				+ LEAGUE_ID + " TEXT PRIMARY KEY," + LEAGUE_DEFAULT_LEAGUE
				+ " INTEGER," + LEAGUE_CATCHER + " INTEGER,"
				+ LEAGUE_FIRST_BASE + " INTEGER," + LEAGUE_SECOND_BASE
				+ " INTEGER," + LEAGUE_SHORTSTOP + " INTEGER,"
				+ LEAGUE_THIRD_BASE + " INTEGER," + LEAGUE_MIDDLE_INFIELDER
				+ " INTEGER," + LEAGUE_CORNER_INFIELDER + " INTEGER,"
				+ LEAGUE_OUTFIELD + " INTEGER," + LEAGUE_UTILITY + " INTEGER,"
				+ LEAGUE_PITCHER + " INTEGER," + LEAGUE_STARTING_PITCHER
				+ " INTEGER," + LEAGUE_RELIEF_PITCHER + " INTEGER,"
				+ LEAGUE_STAT_H + " INTEGER," + LEAGUE_STAT_RBI + " INTEGER,"
				+ LEAGUE_STAT_R + " INTEGER," + LEAGUE_STAT_HR + " INTEGER,"
				+ LEAGUE_STAT_BB + " INTEGER," + LEAGUE_STAT_SB + " INTEGER,"
				+ LEAGUE_STAT_NSB + " INTEGER," + LEAGUE_STAT_K + " INTEGER,"
				+ LEAGUE_STAT_OBP + " INTEGER," + LEAGUE_STAT_OPS + " INTEGER"
				+ ")";

		db.execSQL(CREATE_BATTER_STATS_TABLE);
		db.execSQL(CREATE_USER_PLAYERS_TABLE);
		db.execSQL(CREATE_LEAGUES_TABLE);
	}

	// Upgrading database
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// Drop older table if existed
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_BATTERS_STATS);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER_PLAYERS);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_LEAGUES);
		// Create tables again
		onCreate(db);
	}

	public void dropTables() {
		SQLiteDatabase db = this.getWritableDatabase();
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_BATTERS_STATS);

		String CREATE_BATTER_STATS_TABLE = "CREATE TABLE "
				+ TABLE_BATTERS_STATS + "(" + BATTER_ID + " TEXT," + LEAGUE_ID
				+ " TEXT," + BATTER_DATE + " TEXT," + LEAGUE_STAT_H + " TEXT,"
				+ LEAGUE_STAT_RBI + " TEXT," + LEAGUE_STAT_R + " TEXT,"
				+ LEAGUE_STAT_HR + " TEXT," + LEAGUE_STAT_BB + " TEXT,"
				+ LEAGUE_STAT_SB + " TEXT," + LEAGUE_STAT_NSB + " TEXT,"
				+ LEAGUE_STAT_K + " TEXT," + LEAGUE_STAT_OBP + " TEXT,"
				+ LEAGUE_STAT_OPS + " TEXT," + BATTER_PLACED_POSITION + " TEXT"
				+ ")";
		String CREATE_USER_PLAYERS_TABLE = "CREATE TABLE " + TABLE_USER_PLAYERS
				+ "(" + BATTER_ID + " TEXT PRIMARY KEY," + LEAGUE_ID + " TEXT,"
				+ BATTER_LAST + " TEXT," + BATTER_FIRST + " TEXT," + BATTER_BAT
				+ " TEXT," + BATTER_THROWS + " TEXT," + BATTER_TEAM + " TEXT,"
				+ BATTER_POSITION + " TEXT" + ")";
		String CREATE_LEAGUES_TABLE = "CREATE TABLE " + TABLE_LEAGUES + "("
				+ LEAGUE_ID + " TEXT PRIMARY KEY," + LEAGUE_DEFAULT_LEAGUE
				+ " INTEGER," + LEAGUE_CATCHER + " INTEGER,"
				+ LEAGUE_FIRST_BASE + " INTEGER," + LEAGUE_SECOND_BASE
				+ " INTEGER," + LEAGUE_SHORTSTOP + " INTEGER,"
				+ LEAGUE_THIRD_BASE + " INTEGER," + LEAGUE_MIDDLE_INFIELDER
				+ " INTEGER," + LEAGUE_CORNER_INFIELDER + " INTEGER,"
				+ LEAGUE_OUTFIELD + " INTEGER," + LEAGUE_UTILITY + " INTEGER,"
				+ LEAGUE_PITCHER + " INTEGER," + LEAGUE_STARTING_PITCHER
				+ " INTEGER," + LEAGUE_RELIEF_PITCHER + " INTEGER,"
				+ LEAGUE_STAT_H + " INTEGER," + LEAGUE_STAT_RBI + " INTEGER,"
				+ LEAGUE_STAT_R + " INTEGER," + LEAGUE_STAT_HR + " INTEGER,"
				+ LEAGUE_STAT_BB + " INTEGER," + LEAGUE_STAT_SB + " INTEGER,"
				+ LEAGUE_STAT_NSB + " INTEGER," + LEAGUE_STAT_K + " INTEGER,"
				+ LEAGUE_STAT_OBP + " INTEGER," + LEAGUE_STAT_OPS + " INTEGER"
				+ ")";

		db.execSQL(CREATE_BATTER_STATS_TABLE);
		db.execSQL(CREATE_USER_PLAYERS_TABLE);
		db.execSQL(CREATE_LEAGUES_TABLE);
		db.close();
	}

	// Add New user_player
	public void addUserPlayer(Player player) {
		String selectQuery = "SELECT  * FROM " + TABLE_USER_PLAYERS + " WHERE "
				+ BATTER_ID + " = '" + player.getPlayerId() + "' " + "AND "
				+ LEAGUE_ID + "='" + player.getLeagueId() + "';";

		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);
		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			db.close();
			return;
		}

		ContentValues values = new ContentValues();
		values.put(BATTER_ID, player.getPlayerId());
		values.put(LEAGUE_ID, player.getLeagueId());
		values.put(BATTER_FIRST, player.getFirstName()); // Player first name
		values.put(BATTER_LAST, player.getLastName());
		values.put(BATTER_BAT, player.getBatHand());
		values.put(BATTER_THROWS, player.getThrowHand());
		values.put(BATTER_TEAM, player.getTeam());
		values.put(BATTER_POSITION, player.getPosition());

		// Inserting Row
		db.insert(TABLE_USER_PLAYERS, null, values);
		db.close(); // Closing database connection
	}

	public void modifyPosition(Player player) {
		SQLiteDatabase db = this.getWritableDatabase();

		String updateQuery = "UPDATE " + TABLE_USER_PLAYERS + " SET "
				+ BATTER_POSITION + " = '" + player.getPosition() + "' WHERE "
				+ BATTER_ID + "='" + player.getPlayerId() + "' AND "
				+ LEAGUE_ID + " = '" + player.getLeagueId() + "';";
		db.execSQL(updateQuery);
		db.close();
	}

	// Removes Player from user_players AND batters_stats tables
	public void removePlayer(Player player) {
		SQLiteDatabase db = this.getWritableDatabase();

		String updateQuery = "DELETE FROM " + TABLE_USER_PLAYERS + " WHERE "
				+ BATTER_ID + "='" + player.getPlayerId() + "' AND "
				+ LEAGUE_ID + " = '" + player.getLeagueId() + "';";
		db.execSQL(updateQuery);
		
		updateQuery = "DELETE FROM " + TABLE_BATTERS_STATS + " WHERE "
				+ BATTER_ID + "='" + player.getPlayerId() + "' AND "
				+ LEAGUE_ID + " = '" + player.getLeagueId() + "';";
		db.execSQL(updateQuery);
		
		db.close();
	}
	
	public boolean hasPlayer(Player player) {
		String selectQuery = "SELECT  * FROM " + TABLE_USER_PLAYERS + " WHERE "
				+ BATTER_ID + " = '" + player.getPlayerId() + "' " + "AND "
				+ LEAGUE_ID + "='" + player.getLeagueId() + "';";

		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);
		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			db.close();
			return true;
		} else {
			return false;
		}
	}

	public List<Player> getAllUserPlayers(String leagueId) {
		List<Player> playerList = new ArrayList<Player>();
		// Select All Query
		String selectQuery = "SELECT  * FROM " + TABLE_USER_PLAYERS + " WHERE "
				+ LEAGUE_ID + " = '" + leagueId + "';";

		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);

		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			do {
				Player player = new Player(cursor.getString(0),
						cursor.getString(2), cursor.getString(3),
						cursor.getString(4), cursor.getString(5),
						cursor.getString(6), cursor.getString(7));
				playerList.add(player);
			} while (cursor.moveToNext());
		}
		db.close();
		return playerList;
	}

	// Add New BatterStats
	public void addBatterStats(BatterStats batterStats) {
		String selectQuery = "SELECT  * FROM " + TABLE_BATTERS_STATS
				+ " WHERE " + BATTER_ID + " = '" + batterStats.getPlayerId()
				+ "' " + "AND " + BATTER_DATE + " = '" + batterStats.getDate()
				+ "' " + "AND " + LEAGUE_ID + "='" + batterStats.getLeagueId()
				+ "';";
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);
		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			db.close();
			return;
		}
		
		Log.println(Log.DEBUG, TAG, "Adding BatterStats: " + batterStats.toString());

		ContentValues values = new ContentValues();
		values.put(BATTER_ID, batterStats.getPlayerId());
		values.put(LEAGUE_ID, batterStats.getLeagueId());
		values.put(BATTER_DATE, batterStats.getDate());
		values.put(LEAGUE_STAT_H, batterStats.getHits());
		values.put(LEAGUE_STAT_RBI, batterStats.getRbi());
		values.put(LEAGUE_STAT_R, batterStats.getRuns());
		values.put(LEAGUE_STAT_HR, batterStats.getHomeRuns());
		values.put(LEAGUE_STAT_BB, batterStats.getWalks());
		values.put(LEAGUE_STAT_SB, batterStats.getSteals());
		values.put(LEAGUE_STAT_NSB, batterStats.getNetSteals());
		values.put(LEAGUE_STAT_K, batterStats.getStrikeouts());
		values.put(LEAGUE_STAT_OBP, batterStats.getOBP());
		values.put(LEAGUE_STAT_OPS, batterStats.getOPS());
		values.put(BATTER_PLACED_POSITION, batterStats.getPosition());

		// Inserting Row
		db.insert(TABLE_BATTERS_STATS, null, values);
		db.close(); // Closing database connection
	}
	
	public BatterStats getBatterStats(String playerId, String leagueId, String date) {
		String selectQuery = "SELECT  * FROM " + TABLE_BATTERS_STATS
				+ " WHERE " + BATTER_ID + " = '" + playerId
				+ "' " + "AND " + BATTER_DATE + " = '" + date
				+ "' " + "AND " + LEAGUE_ID + "='" + leagueId + "';";
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);

		BatterStats batterStats = null;

		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			batterStats = new BatterStats(cursor.getString(0), cursor.getString(1),
					cursor.getString(2), cursor.getString(3), cursor.getString(4),
					cursor.getString(5), cursor.getString(6), cursor.getString(7),
					cursor.getString(8), cursor.getString(9), cursor.getString(10),
					cursor.getString(11), cursor.getString(12), cursor.getString(13));
		}
		db.close();
		
		return batterStats;
	}
	
	public List<BatterStats> getAllBatterStats(String leagueId, String date) {
		List<BatterStats> batterStatsList = new ArrayList<BatterStats>();
		// Select All Query
		String selectQuery = "SELECT  * FROM " + TABLE_BATTERS_STATS 
				+ " WHERE "+ LEAGUE_ID + " = '" + leagueId 
				+ "' AND " + BATTER_DATE + " = '" + date + "';";

		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);

		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			do {
				BatterStats batterStats = new BatterStats(cursor.getString(0), cursor.getString(1),
						cursor.getString(2), cursor.getString(3), cursor.getString(4),
						cursor.getString(5), cursor.getString(6), cursor.getString(7),
						cursor.getString(8), cursor.getString(9), cursor.getString(10),
						cursor.getString(11), cursor.getString(12), cursor.getString(13));
				batterStatsList.add(batterStats);
			} while (cursor.moveToNext());
		}
		db.close();
		return batterStatsList;
	}
	
	public void updateBatterStats(BatterStats batterStats) {
		SQLiteDatabase db = this.getWritableDatabase();

		String updateQuery = "UPDATE " + TABLE_BATTERS_STATS + " SET "
				+ LEAGUE_STAT_H + "=" + batterStats.getHits() + ","
				+ LEAGUE_STAT_RBI + "=" + batterStats.getRbi() + ","
				+ LEAGUE_STAT_R + "=" + batterStats.getRuns()  + ","
				+ LEAGUE_STAT_HR + "=" + batterStats.getHomeRuns() + ","
				+ LEAGUE_STAT_BB + "=" + batterStats.getWalks() + ","
				+ LEAGUE_STAT_SB + "=" + batterStats.getSteals() + ","
				+ LEAGUE_STAT_NSB + "=" + batterStats.getNetSteals() + ","
				+ LEAGUE_STAT_K + "=" + batterStats.getStrikeouts() + ","
				+ LEAGUE_STAT_OBP + "=" + batterStats.getOBP() + ","
				+ LEAGUE_STAT_OPS + "=" + batterStats.getOPS()
				+ " WHERE " + BATTER_ID + " = '" + batterStats.getPlayerId()
				+ "' " + "AND " + BATTER_DATE + " = '" + batterStats.getDate()
				+ "' " + "AND " + LEAGUE_ID + "='" + batterStats.getLeagueId() + "';";
		db.execSQL(updateQuery);
		db.close();
	}
	
	public void updateBatterStatsPosition(BatterStats batterStats, String newPosition) {
		SQLiteDatabase db = this.getWritableDatabase();

		String updateQuery = "UPDATE " + TABLE_BATTERS_STATS + " SET "
				+ BATTER_PLACED_POSITION + "=" + newPosition + ","
				+ " WHERE " + BATTER_ID + " = '" + batterStats.getPlayerId()
				+ "' " + "AND " + BATTER_DATE + " = '" + batterStats.getDate()
				+ "' " + "AND " + LEAGUE_ID + "='" + batterStats.getLeagueId() + "';";
		db.execSQL(updateQuery);
		db.close();
	}

	public League getLeague(String id) {
		String selectQuery = "SELECT  * FROM " + TABLE_LEAGUES + " WHERE "
				+ LEAGUE_ID + " = '" + id + "';";

		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);

		League league = null;

		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			league = new League(cursor.getString(0), cursor.getInt(1),
					cursor.getInt(2), cursor.getInt(3), cursor.getInt(4),
					cursor.getInt(5), cursor.getInt(6), cursor.getInt(7),
					cursor.getInt(8), cursor.getInt(9), cursor.getInt(10),
					cursor.getInt(11), cursor.getInt(12), cursor.getInt(13),
					cursor.getInt(14), cursor.getInt(15), cursor.getInt(16),
					cursor.getInt(17), cursor.getInt(18), cursor.getInt(19),
					cursor.getInt(20), cursor.getInt(21), cursor.getInt(22),
					cursor.getInt(23));
		}
		db.close();
		return league;
	}

	public ArrayList<String> getLeagueNames() {
		ArrayList<String> leagueNames = new ArrayList<String>();
		String selectQuery = "SELECT " + LEAGUE_ID + " FROM " + TABLE_LEAGUES;
		SQLiteDatabase db = this.getReadableDatabase();

		Cursor cursor = db.rawQuery(selectQuery, null);

		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			do {
				leagueNames.add(cursor.getString(0));
			} while (cursor.moveToNext());
		}
		db.close();
		return leagueNames;
	}

	// Add New League
	public void addLeague(League league) {
		SQLiteDatabase db = this.getWritableDatabase();

		// Check to see if leagueId already exists and return if it does
		Cursor cursor = db.query(TABLE_LEAGUES, new String[] { LEAGUE_ID },
				LEAGUE_ID + "=?",
				new String[] { String.valueOf(league.getLeagueId()) }, null,
				null, null, null);
		if (cursor.moveToFirst()) {
			Log.println(Log.DEBUG, TAG, "League already exists");
			return;
		}
		ContentValues values = new ContentValues();

		Log.println(Log.DEBUG, TAG,
				"AddLeague() getNumCatchers(): " + league.getNumCatchers());

		values.put(LEAGUE_ID, league.getLeagueId());
		values.put(LEAGUE_DEFAULT_LEAGUE,
				booleanToInt(league.isDefaultLeague()));
		values.put(LEAGUE_CATCHER, league.getNumCatchers());
		values.put(LEAGUE_FIRST_BASE, league.getNumFirstBase());
		values.put(LEAGUE_SECOND_BASE, league.getNumSecondBase());
		values.put(LEAGUE_SHORTSTOP, league.getNumShortStop());
		values.put(LEAGUE_THIRD_BASE, league.getNumThirdBase());
		values.put(LEAGUE_MIDDLE_INFIELDER, league.getNumMiddle());
		values.put(LEAGUE_CORNER_INFIELDER, league.getNumCorner());
		values.put(LEAGUE_OUTFIELD, league.getNumOutfield());
		values.put(LEAGUE_UTILITY, league.getNumUtility());
		values.put(LEAGUE_PITCHER, league.getNumPitchers());
		values.put(LEAGUE_STARTING_PITCHER, league.getNumStartingPitchers());
		values.put(LEAGUE_RELIEF_PITCHER, league.getNumReliefPitchers());
		values.put(LEAGUE_STAT_H, booleanToInt(league.hasHits()));
		values.put(LEAGUE_STAT_RBI, booleanToInt(league.hasRBI()));
		values.put(LEAGUE_STAT_R, booleanToInt(league.hasRuns()));
		values.put(LEAGUE_STAT_HR, booleanToInt(league.hasHomeRuns()));
		values.put(LEAGUE_STAT_BB, booleanToInt(league.hasWalks()));
		values.put(LEAGUE_STAT_SB, booleanToInt(league.hasSteals()));
		values.put(LEAGUE_STAT_NSB, booleanToInt(league.hasNetSteals()));
		values.put(LEAGUE_STAT_K, booleanToInt(league.hasStrikeouts()));
		values.put(LEAGUE_STAT_OBP, booleanToInt(league.hasOBP()));
		values.put(LEAGUE_STAT_OPS, booleanToInt(league.hasOPS()));

		// Inserting Row
		db.insert(TABLE_LEAGUES, null, values);
		db.close(); // Closing database connection
	}

	public void removeLeague(String id) {
		SQLiteDatabase db = this.getWritableDatabase();

		String updateQuery = "DELETE FROM " + TABLE_LEAGUES + " WHERE "
				+ LEAGUE_ID + "='" + id + "';";
		Log.println(Log.DEBUG, TAG, updateQuery);
		db.execSQL(updateQuery);
		
		updateQuery = "DELETE FROM " + TABLE_BATTERS_STATS + " WHERE "
				+ LEAGUE_ID + "='" + id + "';";
		Log.println(Log.DEBUG, TAG, updateQuery);
		db.execSQL(updateQuery);
		
		updateQuery = "DELETE FROM " + TABLE_USER_PLAYERS + " WHERE "
				+ LEAGUE_ID + "='" + id + "';";
		Log.println(Log.DEBUG, TAG, updateQuery);
		db.execSQL(updateQuery);
		
		db.close();
	}

	public void updateLeague(League league) {
		SQLiteDatabase db = this.getWritableDatabase();

		String updateQuery = "UPDATE " + TABLE_LEAGUES + " SET "
				+ LEAGUE_DEFAULT_LEAGUE + "="
				+ booleanToInt(league.isDefaultLeague()) + "," + LEAGUE_CATCHER
				+ "=" + league.getNumCatchers() + "," + LEAGUE_FIRST_BASE + "="
				+ league.getNumFirstBase() + "," + LEAGUE_SECOND_BASE + "="
				+ league.getNumSecondBase() + "," + LEAGUE_SHORTSTOP + "="
				+ league.getNumShortStop() + "," + LEAGUE_THIRD_BASE + "="
				+ league.getNumThirdBase() + "," + LEAGUE_MIDDLE_INFIELDER
				+ "=" + league.getNumMiddle() + "," + LEAGUE_CORNER_INFIELDER
				+ "=" + league.getNumCorner() + "," + LEAGUE_OUTFIELD + "="
				+ league.getNumOutfield() + "," + LEAGUE_UTILITY + "="
				+ league.getNumUtility() + "," + LEAGUE_PITCHER + "="
				+ league.getNumPitchers() + "," + LEAGUE_STARTING_PITCHER + "="
				+ league.getNumStartingPitchers() + "," + LEAGUE_RELIEF_PITCHER
				+ "=" + league.getNumReliefPitchers() + "," + LEAGUE_STAT_H
				+ "=" + booleanToInt(league.hasHits()) + "," + LEAGUE_STAT_RBI
				+ "=" + booleanToInt(league.hasRBI()) + "," + LEAGUE_STAT_R
				+ "=" + booleanToInt(league.hasRuns()) + "," + LEAGUE_STAT_HR
				+ "=" + booleanToInt(league.hasHomeRuns()) + ","
				+ LEAGUE_STAT_BB + "=" + booleanToInt(league.hasWalks()) + ","
				+ LEAGUE_STAT_SB + "=" + booleanToInt(league.hasSteals()) + ","
				+ LEAGUE_STAT_NSB + "=" + booleanToInt(league.hasNetSteals())
				+ "," + LEAGUE_STAT_K + "="
				+ booleanToInt(league.hasStrikeouts()) + "," + LEAGUE_STAT_OBP
				+ "=" + booleanToInt(league.hasOBP()) + "," + LEAGUE_STAT_OPS
				+ "=" + booleanToInt(league.hasOPS()) + " WHERE " + LEAGUE_ID
				+ "='" + league.getLeagueId() + "';";
		db.execSQL(updateQuery);
		db.close();
	}

	// Removes default league status from all leagues
	public void removeDefaultLeagueStatus() {
		SQLiteDatabase db = this.getWritableDatabase();

		String updateQuery = "UPDATE " + TABLE_LEAGUES + " SET "
				+ LEAGUE_DEFAULT_LEAGUE + "=" + 0 + ";";
		db.execSQL(updateQuery);
		db.close();
	}

	public int booleanToInt(boolean bool) {
		if (bool) {
			return 1;
		} else {
			return 0;
		}
	}
}
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
		String CREATE_BATTERS_TABLE = "CREATE TABLE " + TABLE_BATTERS_STATS
				+ "(" + BATTER_ID + " TEXT," + BATTER_DATE + " TEXT,"
				+ BATTER_LAST + " TEXT," + BATTER_FIRST + " TEXT," + BATTER_BAT
				+ " TEXT," + BATTER_THROWS + " TEXT," + BATTER_POSITION
				+ " TEXT" + ")";
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

		db.execSQL(CREATE_BATTERS_TABLE);
		db.execSQL(CREATE_USER_PLAYERS_TABLE);
		db.execSQL(CREATE_LEAGUES_TABLE);
	}

	// Upgrading database
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// Drop older table if existed
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_BATTERS_STATS);
		// Create tables again
		onCreate(db);
	}

	public void dropTables() {
		SQLiteDatabase db = this.getWritableDatabase();
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_BATTERS_STATS);

		String CREATE_PLAYERS_TABLE = "CREATE TABLE " + TABLE_BATTERS_STATS
				+ "(" + BATTER_ID + " TEXT," + BATTER_LAST + " TEXT,"
				+ BATTER_FIRST + " TEXT," + BATTER_BAT + " TEXT,"
				+ BATTER_THROWS + " TEXT," + BATTER_POSITION + " TEXT" + ")";
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

		db.execSQL(CREATE_PLAYERS_TABLE);
		db.execSQL(CREATE_USER_PLAYERS_TABLE);
		db.execSQL(CREATE_LEAGUES_TABLE);
		db.close();
	}

	// Add New user_player
	public void addUserPlayer(Player player) {
		SQLiteDatabase db = this.getWritableDatabase();

		// Check to see if playerId already exists and return if it does
		Cursor cursor = db.query(TABLE_USER_PLAYERS, new String[] { BATTER_ID,
				LEAGUE_ID, BATTER_LAST, BATTER_FIRST, BATTER_BAT,
				BATTER_THROWS, BATTER_TEAM, BATTER_POSITION },
				BATTER_ID + "=?",
				new String[] { String.valueOf(player.getPlayerId()) }, null,
				null, null, null);
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

	public void removePlayer(Player player) {
		SQLiteDatabase db = this.getWritableDatabase();

		String updateQuery = "DELETE FROM " + TABLE_USER_PLAYERS + " WHERE "
				+ BATTER_ID + "='" + player.getPlayerId() + "' AND "
				+ LEAGUE_ID + " = '" + player.getLeagueId() + "';";
		db.execSQL(updateQuery);
		db.close();
	}

	/*
	 * 
	 * // Add new batterStat public void addBatter(BatterStats batterStats) {
	 * SQLiteDatabase db = this.getWritableDatabase();
	 * 
	 * // Check to see if playerId already exists and return if it does Cursor
	 * cursor = db.query(TABLE_BATTERS, new String[] { BATTER_ID, BATTER_DATE,
	 * BATTER_LAST, BATTER_FIRST, BATTER_BAT, BATTER_THROWS, BATTER_POSITION},
	 * BATTER_ID + "=?", new String[] {
	 * String.valueOf(batterStats.getPlayerId()) }, null, null, null, null); if
	 * (cursor.moveToFirst()) { Log.println(Log.DEBUG, TAG,
	 * "Player already exists"); return; }
	 * 
	 * ContentValues values = new ContentValues(); values.put(BATTER_ID,
	 * batterStats.getPlayerId()); values.put(BATTER_DATE,
	 * batterStats.getDate()); values.put(BATTER_FIRST,
	 * batterStats.getFirstName()); // Player first name values.put(BATTER_LAST,
	 * batterStats.getLastName()); values.put(BATTER_BAT,
	 * batterStats.getBatHand()); values.put(BATTER_THROWS,
	 * batterStats.getThrowHand()); values.put(BATTER_POSITION,
	 * batterStats.getPosition());
	 * 
	 * // Inserting Row db.insert(TABLE_BATTERS, null, values); db.close(); //
	 * Closing database connection }
	 * 
	 * 
	 * public Player getBatter(String id) { SQLiteDatabase db =
	 * this.getReadableDatabase();
	 * 
	 * Cursor cursor = db.query(TABLE_BATTERS_STATS, new String[] { BATTER_ID,
	 * BATTER_LAST, BATTER_DATE, BATTER_FIRST, BATTER_BAT, BATTER_THROWS,
	 * BATTER_POSITION }, BATTER_ID + "=?", new String[] { String.valueOf(id) },
	 * null, null, null, null); if (cursor != null) cursor.moveToFirst();
	 * 
	 * Player player = new Player(cursor.getString(0), cursor.getString(1),
	 * cursor.getString(2), cursor.getString(3), cursor.getString(4),
	 * cursor.getString(5), cursor.getString(6)); return player; }
	 */

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

	public League getLeague(String id) {
		String selectQuery = "SELECT  * FROM " + TABLE_LEAGUES;

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
		
		Log.println(Log.DEBUG, TAG, "AddLeague() getNumCatchers(): " + league.getNumCatchers());

		values.put(LEAGUE_ID, league.getLeagueId());
		values.put(LEAGUE_DEFAULT_LEAGUE,booleanToInt(league.isDefaultLeague()));
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
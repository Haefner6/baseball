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
    private static final String TABLE_BATTERS = "batterStats";
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
 
    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
 
    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_BATTERS_TABLE = "CREATE TABLE " + TABLE_BATTERS + "("
                + BATTER_ID + " TEXT," + BATTER_DATE + " TEXT," + BATTER_LAST + " TEXT," + BATTER_FIRST + " TEXT,"
        		+ BATTER_BAT + " TEXT," + BATTER_THROWS + " TEXT," + BATTER_POSITION + " TEXT" + ")";     
        Log.println(Log.DEBUG, TAG, "Create Players Table statement: " + CREATE_BATTERS_TABLE);
        String CREATE_USER_PLAYERS_TABLE = "CREATE TABLE " + TABLE_USER_PLAYERS + "("
                + BATTER_ID + " TEXT PRIMARY KEY," + BATTER_LAST + " TEXT," + BATTER_FIRST + " TEXT,"
        		+ BATTER_BAT + " TEXT," + BATTER_THROWS + " TEXT," + BATTER_TEAM + " TEXT," + BATTER_POSITION + " TEXT" + ")";     
        
        db.execSQL(CREATE_BATTERS_TABLE);
        db.execSQL(CREATE_USER_PLAYERS_TABLE);
    }
 
    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_BATTERS);
        // Create tables again
        onCreate(db);
    }
    
    public void dropTables() {
    	SQLiteDatabase db = this.getWritableDatabase();
    	db.execSQL("DROP TABLE IF EXISTS " + TABLE_BATTERS);
    	
    	String CREATE_PLAYERS_TABLE = "CREATE TABLE " + TABLE_BATTERS + "("
                + BATTER_ID + " TEXT," + BATTER_LAST + " TEXT," + BATTER_FIRST + " TEXT,"
        		+ BATTER_BAT + " TEXT," + BATTER_THROWS + " TEXT," + BATTER_POSITION + " TEXT" + ")";
    	String CREATE_USER_PLAYERS_TABLE = "CREATE TABLE " + TABLE_USER_PLAYERS + "("
                + BATTER_ID + " TEXT PRIMARY KEY," + BATTER_LAST + " TEXT," + BATTER_FIRST + " TEXT,"
        		+ BATTER_BAT + " TEXT," + BATTER_THROWS + " TEXT," + BATTER_TEAM + " TEXT," + BATTER_POSITION + " TEXT" + ")";    
        
        db.execSQL(CREATE_PLAYERS_TABLE);
        db.execSQL(CREATE_USER_PLAYERS_TABLE);
    }
    
    // Add New user_player
    public void addUserPlayer(Player player) {
        SQLiteDatabase db = this.getWritableDatabase();
        
        // Check to see if playerId already exists and return if it does
        Cursor cursor = db.query(TABLE_USER_PLAYERS, new String[] { BATTER_ID, BATTER_LAST,
        		BATTER_FIRST, BATTER_BAT, BATTER_THROWS, BATTER_TEAM, BATTER_POSITION}, BATTER_ID + "=?",
                new String[] { String.valueOf(player.getPlayerId()) }, null, null, null, null);
        if (cursor.moveToFirst()) {
            return;
        }
     
        ContentValues values = new ContentValues();
        values.put(BATTER_ID, player.getPlayerId());
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

    	String updateQuery = "UPDATE " + TABLE_USER_PLAYERS + " SET " + BATTER_POSITION + " = '" 
    			+  player.getPosition() + "' WHERE " + BATTER_ID + "='" + player.getPlayerId() + "';";
    	db.execSQL(updateQuery);
    	db.close();
    }
    
    public void removePlayer(Player player) {
    	SQLiteDatabase db = this.getWritableDatabase();

    	String updateQuery = "DELETE FROM " + TABLE_USER_PLAYERS + " WHERE " 
    			+ BATTER_ID + "='" + player.getPlayerId() + "';";
    	db.execSQL(updateQuery);
    	db.close();
    }
    
    // Add new batterStat
    public void addBatter(BatterStats batterStats) {
        SQLiteDatabase db = this.getWritableDatabase();
        
        // Check to see if playerId already exists and return if it does
        Cursor cursor = db.query(TABLE_BATTERS, new String[] { BATTER_ID, BATTER_DATE, BATTER_LAST,
        		BATTER_FIRST, BATTER_BAT, BATTER_THROWS, BATTER_POSITION}, BATTER_ID + "=?",
                new String[] { String.valueOf(batterStats.getPlayerId()) }, null, null, null, null);
        if (cursor.moveToFirst()) {
        	Log.println(Log.DEBUG, TAG, "Player already exists");
            return;
        }
     
        ContentValues values = new ContentValues();
        values.put(BATTER_ID, batterStats.getPlayerId());
        values.put(BATTER_DATE, batterStats.getDate());
        values.put(BATTER_FIRST, batterStats.getFirstName()); // Player first name
        values.put(BATTER_LAST, batterStats.getLastName());
        values.put(BATTER_BAT, batterStats.getBatHand());
        values.put(BATTER_THROWS, batterStats.getThrowHand());
        values.put(BATTER_POSITION, batterStats.getPosition());
     
        // Inserting Row
        db.insert(TABLE_BATTERS, null, values);
        db.close(); // Closing database connection
    }
    
    public Player getBatter(String id) {
        SQLiteDatabase db = this.getReadableDatabase();
     
        Cursor cursor = db.query(TABLE_BATTERS, new String[] { BATTER_ID, BATTER_LAST, BATTER_DATE,
        		BATTER_FIRST, BATTER_BAT, BATTER_THROWS, BATTER_POSITION}, BATTER_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();
     
        Player player = new Player(cursor.getString(0), cursor.getString(1), cursor.getString(2), cursor.getString(3),
        		cursor.getString(4), cursor.getString(5), cursor.getString(6));
        return player;
    }
    
    public List<Player> getAllUserPlayers() {
        List<Player> playerList = new ArrayList<Player>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_USER_PLAYERS;
     
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
     
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
            	Player player = new Player(cursor.getString(0), cursor.getString(1), cursor.getString(2), cursor.getString(3),
                		cursor.getString(4), cursor.getString(5), cursor.getString(6));
                playerList.add(player);
            } while (cursor.moveToNext());
        }
        return playerList;
    }
}
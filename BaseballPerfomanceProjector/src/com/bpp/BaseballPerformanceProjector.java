package com.bpp;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.res.Configuration;
import android.database.SQLException;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.PorterDuff.Mode;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TextView;

public class BaseballPerformanceProjector extends Activity implements HorizontalScrollViewListener, ScrollViewListener {

	ArrayList<Player> playersArrayList;
	ArrayList<String>positionList;
	String[] activePlayers;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        initializeTabs();
        loadPlayersDatabase();
        
        playersArrayList = new ArrayList<Player>();
        positionList = new ArrayList<String>();
        
        activePlayers = new String[9];
        
        setImageSizes();
        syncStatsScrollWithHeader();
        
        fillPositionList();
        restorePreviousPlayerConfiguration();
        addUserPlayers(); 
    }
    
    @Override
    public void onConfigurationChanged(Configuration configure){
        super.onConfigurationChanged(configure);
        LinearLayout appLayout = (LinearLayout)findViewById(R.id.appLayout);
        TabHost tabHost = (TabHost)findViewById(R.id.tabhost);
        LinearLayout uiControlsLayout = (LinearLayout)findViewById(R.id.uiControls);
        LinearLayout.LayoutParams uiControlsParams;
        LinearLayout.LayoutParams playerAndStatsParams;
        FrameLayout dividerLine = (FrameLayout)findViewById(R.id.dividerLine);
        float scale = getResources().getDisplayMetrics().density;
		int dividerWidth = (int) (2 * scale + 0.5f);
        
        if(configure.orientation == Configuration.ORIENTATION_PORTRAIT) {
        	appLayout.setOrientation(LinearLayout.VERTICAL);
        	uiControlsParams = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, 0, 2);
        	playerAndStatsParams = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, 0, 8);
        	
        	dividerLine.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, dividerWidth));
        	tabHost.setLayoutParams(playerAndStatsParams);
        	uiControlsLayout.setLayoutParams(uiControlsParams);
        } else {
        	appLayout.setOrientation(LinearLayout.HORIZONTAL);
        	uiControlsParams = new LinearLayout.LayoutParams(0, LayoutParams.MATCH_PARENT, 3);
        	playerAndStatsParams = new LinearLayout.LayoutParams(0, LayoutParams.MATCH_PARENT, 7);
        	
        	dividerLine.setLayoutParams(new LinearLayout.LayoutParams(dividerWidth, LayoutParams.MATCH_PARENT));
        	tabHost.setLayoutParams(playerAndStatsParams);
        	uiControlsLayout.setLayoutParams(uiControlsParams);
        }
    }
    
    private void setImageSizes() {
    	Button addPlayerButton = (Button)findViewById(R.id.addPlayerImage);
    	//scaleImage(addPlayerImage, 150);
    	final int pressedColor = Color.GREEN;
    	//addPlayerImage.setColorFilter(pressedColor, Mode.SRC_ATOP);
    	
    }
    
    private void scaleImage(ImageView view, int boundBoxInDp)
    {
        Drawable drawing = view.getDrawable();
        Bitmap bitmap = ((BitmapDrawable)drawing).getBitmap();
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();

        float xScale = ((float) boundBoxInDp) / width;
        float yScale = ((float) boundBoxInDp) / height;
        float scale = (xScale <= yScale) ? xScale : yScale;
        Matrix matrix = new Matrix();
        matrix.postScale(scale, scale);

        Bitmap scaledBitmap = Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, true);
        BitmapDrawable result = new BitmapDrawable(scaledBitmap);
        width = scaledBitmap.getWidth();
        height = scaledBitmap.getHeight();

        view.setImageDrawable(result);
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) view.getLayoutParams();
        params.width = width;
        params.height = height;
        view.setLayoutParams(params);
    }
    
    private int dpToPx(int dp)
    {
        float density = getApplicationContext().getResources().getDisplayMetrics().density;
        return Math.round((float)dp * density);
    }
    
    // Set the tab host specifications
    private void initializeTabs() {
    	TabHost tabs = (TabHost)findViewById(R.id.tabhost);
        tabs.setup();
        // Tab 1
        TabHost.TabSpec spec = tabs.newTabSpec("tag1");
        spec.setContent(R.id.battersTab);
        spec.setIndicator("Batters");
        tabs.addTab(spec);
        
        //Tab 2
        spec = tabs.newTabSpec("tag2");
        spec.setContent(R.id.pitchersTab);
        spec.setIndicator("Pitchers");
        tabs.addTab(spec);
        
        for (int i = 0; i < tabs.getTabWidget().getChildCount(); i++) {
            tabs.getTabWidget().getChildAt(i).setPadding(10,5,10,10);
        } // end for
    }
    
    public void loadPlayersDatabase() {
    	PlayersDatabaseHelper playersDatabase = new PlayersDatabaseHelper(this);
        try {
        	playersDatabase.createDataBase();
	 	} catch (IOException ioe) {
	 		throw new Error("Unable to create database");
	 	}
	 	try {
	 		playersDatabase.openDataBase();
	 	}catch(SQLException sqle){
	 		throw sqle;
	 	}
	 	playersDatabase.close();
    }
    
    public void syncStatsScrollWithHeader() {
    	TrackableHorizontalScrollView statsScroll = (TrackableHorizontalScrollView)findViewById(R.id.statsScrollView);
    	TrackableScrollView playersScroll = (TrackableScrollView)findViewById(R.id.playerListScrollView);
    	HorizontalScrollView statsHeader = (HorizontalScrollView)findViewById(R.id.statsHeaderScrollView);
    	TrackableScrollView statsScrollViewWrapper = (TrackableScrollView)findViewById(R.id.statsScrollViewWrapper);
    	
    	statsScrollViewWrapper.setScrollViewListener(this);
    	statsHeader.setHorizontalScrollBarEnabled(false);
    	statsScroll.setHorizontalScrollBarEnabled(false);
    	statsScroll.setScrollViewListener(this);
    	playersScroll.setScrollViewListener(this);
    }
    
    public void restorePreviousPlayerConfiguration() {
    	LinearLayout playersListLayout = (LinearLayout)findViewById(R.id.playersListLayout);
    	LinearLayout statsListLayout = (LinearLayout)findViewById(R.id.statsListLayout);
    	playersListLayout.removeAllViews();
    	statsListLayout.removeAllViews();
    	String[] viewableStats = getViewableStats();
    	
    	for(int i=0; i < positionList.size(); i++) {
    		playersListLayout.addView(new PlayerView(this, "", "--", positionList.get(i)));
    		statsListLayout.addView(new StatsView(this, viewableStats));
    	}
    }
    
    public String[] getViewableStats() {
    	String[] viewableStats = new String[5];
    	viewableStats[0] = "HR";
    	viewableStats[1] = "R";
    	viewableStats[2] = "RBI";
    	viewableStats[3] = "SB";
    	viewableStats[4] = "OBP";
    	
    	return viewableStats;
    }
    
    public void fillPositionList() {
    	positionList.add("C");
    	positionList.add("1B");
    	positionList.add("2B");
    	positionList.add("SS");
    	positionList.add("3B");
    	positionList.add("OF");
    	positionList.add("OF");
    	positionList.add("OF");
    	positionList.add("UT");
    }
    
 // add the sample players
    private void addUserPlayers() {
       // load the array of player names from resources
       DatabaseHandler databaseHandler = new DatabaseHandler(this);
       List<Player> userPlayerList = databaseHandler.getAllUserPlayers();

       for (int i = 0; i < userPlayerList.size(); i++) {
    	   Log.println(Log.DEBUG, "myDebug", "Adding " + userPlayerList.get(i).getPlayerFullName() + " " + userPlayerList.get(i).getPosition());
    	   addPlayer(userPlayerList.get(i));
       } // end for loop
    } // end method addSamplePlayers
    
    // add a new player to the player Array List and slot it
    public void addPlayer(Player player) {
    	// Check for duplicate player 
    	for(int i=0; i < playersArrayList.size(); i++) {
    		if(playersArrayList.get(i).equals(player)) {
    			return;
    		}
    	} // end for loop
    	playersArrayList.add(player);
    	if(fillEmptySlot(player)) {
    		return;
    	} else {
    		//Log.println(Log.DEBUG, "myDebug", "Adding " + player.getPlayerFullName() + " to bench");
    		addBenchPlayer(player);
    	}
    } // end method addPlayer
    
    // Places a player in an available spot.  Returns false if none available
    public boolean fillEmptySlot(final Player player) {
    	LinearLayout playersListLayout = (LinearLayout)findViewById(R.id.playersListLayout);
    	
    	for(int i=0; i < playersListLayout.getChildCount(); i++) {
    		PlayerView playerView = (PlayerView)playersListLayout.getChildAt(i);
    		if(!playerView.hasPlayer() && (player.getPosition().contains(playerView.getPosition()) ||
    				playerView.getPosition().equals("UT"))) {
    			playerView.setPlayerId(player.getPlayerId());
    			playerView.setPlayerName(player.getPlayerFullName());
    			playerView.setOnLongClickListener(new View.OnLongClickListener() {
    	   			public boolean onLongClick(View v) 
    	   			{
    	   				modifyPlayerDialog(player);
    	   			    return true;
    	   			}
    	   		});
    			// Add Player to user_player table
    			DatabaseHandler userDatabase = new DatabaseHandler(this);
    	    	userDatabase.addUserPlayer(player);
    			return true;
    		}
    	}
    	return false;
    } // End method fillEmptySlot
    
    public void addBenchPlayer(final Player player) {
    	LinearLayout playersListLayout = (LinearLayout)findViewById(R.id.playersListLayout);
    	LinearLayout statsListLayout = (LinearLayout)findViewById(R.id.statsListLayout);
    	PlayerView playerView = new PlayerView(this, player.getPlayerId(), player.getPlayerFullName(), "Bench - " + player.getPosition());
    	String[] viewableStats = getViewableStats();
    	StatsView statsView = new StatsView(this, viewableStats);
    	playerView.setOnLongClickListener(new View.OnLongClickListener() {
   			public boolean onLongClick(View v) 
   			{ 
   				modifyPlayerDialog(player);
   			    return true;
   			}
   		});
    	playersListLayout.addView(playerView);
    	statsListLayout.addView(statsView);
    	// Add Player to user_player table
    	DatabaseHandler userDatabase = new DatabaseHandler(this);
    	userDatabase.addUserPlayer(player);
    	userDatabase.close();
    }
    
    public void removePlayer(Player player) {
    	DatabaseHandler userDatabase = new DatabaseHandler(BaseballPerformanceProjector.this);
    	userDatabase.removePlayer(player);
    	userDatabase.close();
    	int playerIndex = getIndexOfPlayerId(player.getPlayerId());
    	LinearLayout playerListLayout = (LinearLayout)findViewById(R.id.playersListLayout);
    	LinearLayout statsListLayout = (LinearLayout)findViewById(R.id.statsListLayout);
    	PlayerView playerView = (PlayerView)playerListLayout.getChildAt(playerIndex);
    	StatsView statsView= (StatsView)statsListLayout.getChildAt(playerIndex);
    	for(int i = 0; i < playersArrayList.size(); i++) {
    		if(playersArrayList.get(i).getPlayerId().equals(player.getPlayerId())) {
    			playersArrayList.remove(i);
    			break;
    		}
    	}
    	if(playerView.getHeader().contains("Bench")) {
    		playerListLayout.removeView(playerView);
    		statsListLayout.removeView(statsView);
    	} else {
    		playerView.clearPlayer();
    		playerView.setOnLongClickListener(null);
    		statsView.clearStats();
    	}
    }
    
    public void modifyPlayerDialog(final Player player) {
    	LayoutInflater inflater = LayoutInflater.from(this);
    	final View modifyPlayerView = inflater.inflate(R.layout.modify_player, null);
    	TextView playerName = (TextView)modifyPlayerView.findViewById(R.id.playerName);
    	AlertDialog.Builder modifyPlayerAlert = new AlertDialog.Builder(this);
    	
    	playerName.setText(player.getPlayerFullName());
    	
    	final CheckBox catcherCheckbox = (CheckBox)modifyPlayerView.findViewById(R.id.checkbox_catcher);
    	final CheckBox firstBaseCheckbox = (CheckBox)modifyPlayerView.findViewById(R.id.checkbox_firstBase);
    	final CheckBox secondBaseCheckbox = (CheckBox)modifyPlayerView.findViewById(R.id.checkbox_secondBase);
    	final CheckBox shortstopCheckbox = (CheckBox)modifyPlayerView.findViewById(R.id.checkbox_shortstop);
    	final CheckBox thirdBaseCheckbox = (CheckBox)modifyPlayerView.findViewById(R.id.checkbox_thirdBase);
    	final CheckBox outfieldCheckbox = (CheckBox)modifyPlayerView.findViewById(R.id.checkbox_outfield);
    	// Check appropriate position boxes
    	if(player.getPosition().contains("C")) {
    		catcherCheckbox.setChecked(true);
    	} else { catcherCheckbox.setChecked(false);}
    	if(player.getPosition().contains("1B")) {
    		firstBaseCheckbox.setChecked(true);
    	} else { firstBaseCheckbox.setChecked(false);}
    	if(player.getPosition().contains("2B")) {
    		secondBaseCheckbox.setChecked(true);
    	} else { secondBaseCheckbox.setChecked(false);}
    	if(player.getPosition().contains("3B")) {
    		thirdBaseCheckbox.setChecked(true);
    	} else { thirdBaseCheckbox.setChecked(false);}
    	if(player.getPosition().contains("SS")) {
    		shortstopCheckbox.setChecked(true);
    	} else { shortstopCheckbox.setChecked(false);}
    	if(player.getPosition().contains("OF")) {
    		outfieldCheckbox.setChecked(true);
    	} else { outfieldCheckbox.setChecked(false);}
    	
    	modifyPlayerAlert.setView(modifyPlayerView);
    	modifyPlayerAlert.setPositiveButton("Save Changes", new DialogInterface.OnClickListener() {
    		public void onClick(DialogInterface dialog, int which) {
    			try {
    				player.clearPosition();
    				if(catcherCheckbox.isChecked()) {
    					player.addEligibility("C");
    		    	}
    				if(firstBaseCheckbox.isChecked()) {
    					player.addEligibility("1B");
    		    	}
    				if(secondBaseCheckbox.isChecked()) {
    					player.addEligibility("2B");
    		    	}
    				if(shortstopCheckbox.isChecked()) {
    					player.addEligibility("SS");
    		    	}
    				if(thirdBaseCheckbox.isChecked()) {
    					player.addEligibility("3B");
    		    	}
    				if(outfieldCheckbox.isChecked()) {
    					player.addEligibility("OF");
    		    	}
    				DatabaseHandler userDatabase = new DatabaseHandler(BaseballPerformanceProjector.this);
    		    	userDatabase.modifyPosition(player);
    		    	userDatabase.close();
    		    	movePlayerIfNoLongerEligible(player);
    			} 
    			catch(Exception e) {
    				Log.println(Log.DEBUG, "myDebug", "Caught exception in modifyPlayerDialog()");
    				Log.println(Log.DEBUG, "myDebug", e.getMessage());
    				return;
    			}
    		}	
    	});
    	modifyPlayerAlert.setNeutralButton("Remove", new DialogInterface.OnClickListener() {
    		public void onClick(DialogInterface dialog, int which) {
    			try {
    				removePlayer(player);
    			} 
    			catch(Exception e) {
    				Log.println(Log.DEBUG, "myDebug", "Caught exception in modifyPlayerDialog()");
    				return;
    			}
    		}	
    	});
    	modifyPlayerAlert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
    		public void onClick(DialogInterface dialog, int which) {
    			try {
    				
    			} 
    			catch(Exception e) {
    				Log.println(Log.DEBUG, "myDebug", "Caught exception in modifyPlayerDialog()");
    				return;
    			}
    		}	
    	}).show();
    }
    
    public void movePlayerIfNoLongerEligible(Player player) {
    	int playerIndex = getIndexOfPlayerId(player.getPlayerId());
    	LinearLayout playerListLayout = (LinearLayout)findViewById(R.id.playersListLayout);
    	PlayerView playerView = (PlayerView)playerListLayout.getChildAt(playerIndex);
    	String placedPosition = playerView.getPosition();
    	if(!player.getPosition().contains(placedPosition)) {
    		removePlayer(player);
    		addPlayer(player);
    	}
    }

	@Override
	public void onScrollChanged(TrackableHorizontalScrollView scrollView,int x, int y, int oldx, int oldy) {
		HorizontalScrollView statsHeader = (HorizontalScrollView)findViewById(R.id.statsHeaderScrollView);
		statsHeader.scrollTo(x, y);
	}
	
	@Override
	public void onScrollChanged(TrackableScrollView scrollView, int x, int y,int oldx, int oldy) {
		TrackableScrollView statsScrollWrapper = (TrackableScrollView)findViewById(R.id.statsScrollViewWrapper);
		TrackableScrollView playersScroll = (TrackableScrollView)findViewById(R.id.playerListScrollView);

		if(scrollView.getId() == statsScrollWrapper.getId()) {
			playersScroll.scrollTo(playersScroll.getScrollX(), y);
		} else {
			statsScrollWrapper.scrollTo(statsScrollWrapper.getScrollX(), y);
		}
	}
	
	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
    	populateMenu(menu);
    	return(super.onCreateOptionsMenu(menu));
    }
    
    private void populateMenu(Menu menu) {
    	menu.add(Menu.NONE, 1, Menu.NONE, "Project Performance");
    	menu.add(Menu.NONE, 2, Menu.NONE, "Add Player");
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
    	int nItem =item.getItemId();
    	switch(nItem) {
        	case 1:
        		makeDailyProjections();
        		break;
        	case 2:
        		addPlayerDialog();
        		break;
    	}
    	return true;
    }
    
    private void addPlayerDialog()
    {
    	LayoutInflater inflater = LayoutInflater.from(this);
    	PlayersDatabaseHelper playersDatabase = new PlayersDatabaseHelper(this);
    	List<Player> addablePlayersList = playersDatabase.getAllPlayers();
    	final View add_player = inflater.inflate(R.layout.add_player, null);
    	TextView title = (TextView)add_player.findViewById(R.id.title);
    	final TextView chosenPlayer = (TextView)add_player.findViewById(R.id.chosenPlayer);
    	final Player[] addablePlayersArray = new Player[addablePlayersList.size()];
    	title.setText("Add Player");
    	
    	addablePlayersList.toArray(addablePlayersArray);
    	Arrays.sort(addablePlayersArray);
    	String[] playerSummaries = new String[addablePlayersArray.length];
	    
    	for(int i=0; i < playerSummaries.length; i++) {
    		playerSummaries[i] = addablePlayersArray[i].getPlayerSummary();
    	}
	 	final Player selectedPlayer = new Player();
	 	Log.println(Log.DEBUG, "myDebug", "Number of addable plaeyres: " + addablePlayersList.size());
	    
	    ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, playerSummaries);
        final AutoCompleteTextView autoCompleteTextView = (AutoCompleteTextView)add_player.findViewById(R.id.players_list);
        autoCompleteTextView.setAdapter(arrayAdapter);
        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> adapter, View view, int position,long id) {
				String playerSummary = (String)adapter.getItemAtPosition(position);
				Log.println(Log.DEBUG, "myDebug", "Clicked: " + playerSummary);
				int playerIndex = 0;
				for(int i=0; i < addablePlayersArray.length; i ++) {
					if(addablePlayersArray[i].getPlayerSummary().equals(playerSummary)) {
						playerIndex = i;
						break;
					}
				}
				selectedPlayer.setPlayerAttributes(addablePlayersArray[playerIndex].getPlayerId(), addablePlayersArray[playerIndex].getLastName(),
						addablePlayersArray[playerIndex].getFirstName(), addablePlayersArray[playerIndex].getBatHand(), 
						addablePlayersArray[playerIndex].getThrowHand(), addablePlayersArray[playerIndex].getTeam(), 
						addablePlayersArray[playerIndex].getPosition());
				autoCompleteTextView.setText("");
				chosenPlayer.setText(playerSummary);
			}
        });
    	
    	Log.println(Log.DEBUG, "myDebug", "Inside addPlayerDialog()");
    	
    	AlertDialog.Builder addPlayerAlert = new AlertDialog.Builder(this);
    	addPlayerAlert.setView(add_player);
    	addPlayerAlert.setPositiveButton("Add", new DialogInterface.OnClickListener() {
    		public void onClick(DialogInterface dialog, int which) {
    			try {
    				if(selectedPlayer.hasId()) {
    					Log.println(Log.DEBUG, "myDebug", "Should add " + selectedPlayer.getPlayerSummary());
    					addPlayer(selectedPlayer);
    				}
    			} 
    			catch(Exception e) {
    				Log.println(Log.DEBUG, "myDebug", "Caught exception in addPlayerDialog()");
    				return;
    			}
    		}	
    	});
    	addPlayerAlert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
    		public void onClick(DialogInterface dialog, int which) {
    			try {
    				
    			} 
    			catch(Exception e) {
    				Log.println(Log.DEBUG, "myDebug", "Caught exception in addPlayerDialog()");
    				return;
    			}
    		}	
    	}).show();
    }
    
    public boolean hasBenchPlayer() {
    	LinearLayout playersListLayout = (LinearLayout)findViewById(R.id.playersListLayout);
    	for(int i=0; i < playersListLayout.getChildCount(); i++) {
    		PlayerView playerView = (PlayerView)playersListLayout.getChildAt(i);
    		if(playerView.getHeader().equals("Bench")) {
    			return true;
    		}
    	}
    	return false;
    }
    
    public void makeDailyProjections() {
    	for(int i=0; i < playersArrayList.size(); i++) {
    		//sendContent(playersArrayList.get(i).getPlayerId());
    		String playerIds[] = new String[1];
            playerIds[0] = playersArrayList.get(i).getPlayerId();
            new WebServiceCall().execute(playerIds);
    	}
    } // End makeDailyProjections()
    
    public int getIndexOfPlayerId(String playerId) {
    	LinearLayout playersListLayout = (LinearLayout)findViewById(R.id.playersListLayout);
    	
    	for(int i=0; i < playersListLayout.getChildCount(); i++) {
    		PlayerView playerView = (PlayerView)playersListLayout.getChildAt(i);
    		if(playerView.getPlayerId().equals(playerId)) {
    			return i;
    		}
    	}
    	Log.println(Log.DEBUG, "myDebug", "Function getIndexOfPlayerId() reaching failsafe not intended to be reached.");
    	return 0; // This should never be reached
    }
    
	private class WebServiceCall extends AsyncTask<String, Void, String[]> {
		@Override
		protected String[] doInBackground(String... playerId) {
			String url = "http://threemuskets.com/BaseballApp/getBatterProjection.php";
			//Log.println(Log.DEBUG, "myDebug", url);
			String response = "ERROR";
			HttpPost httppost;
	        HttpClient client;
	        List<NameValuePair> nameValuePairs;
	        try {
	                client = new DefaultHttpClient();
	                httppost = new HttpPost(url);
	                // Add your data
	                nameValuePairs = new ArrayList<NameValuePair>(2);
	                nameValuePairs.add(new BasicNameValuePair("player", playerId[0]));
	                nameValuePairs.add(new BasicNameValuePair("date", "3/31/2013"));
	                httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
	                // Execute HTTP Post Request
	                ResponseHandler<String> responseHandler = new BasicResponseHandler();
	        		response = client.execute(httppost, responseHandler);
	        }
	        catch (Exception e) {
	                //Toast.makeText(this, "error" + e.toString(), Toast.LENGTH_LONG).show();
	        	Log.println(Log.DEBUG, "myDebug", "error" + e.toString());
	        }
	        Log.println(Log.DEBUG, "myDebug", "Response for " + playerId[0] + ": " + response);
	        String[] statsResults = parseResponse(response);
	    	return statsResults;
		} 
		
		@Override
        protected void onPostExecute(String[] response) {
			String[] statsResults = new String[0];
			if(response.length > 0) {
				statsResults = new String[response.length - 1];
				for(int i = 0; i < statsResults.length; i++) {
					statsResults[i] = response[i+1];
				}
				changeStats(response[0], statsResults);
			}
        }
        
        public void changeStats(String playerId, String[] statsResults) {
        	//Log.println(Log.DEBUG, "myDebug", "Calling getIndexOfPlayer with: " + playerId);
        	int index = getIndexOfPlayerId(playerId);
        	//Log.println(Log.DEBUG, "myDebug", "PlayerId index: " + index);
        	LinearLayout statsListLayout = (LinearLayout)findViewById(R.id.statsListLayout);
        	StatsView statsView= (StatsView)statsListLayout.getChildAt(index);
        	statsView.setStats(statsResults);
        }
        
        public String[] parseResponse(String response) {
        	int numStats = countOccurrences(response, ',') + 1;
        	//Log.println(Log.DEBUG, "myDebug", "Response is: " + response);
        	String[] statsResults = new String[numStats];
        	for(int i=0; i < numStats; i++) {
        		String stat = "";
        		if(response.contains(",")) {
        			stat = response.substring(0, response.indexOf(","));
        		} else {
        			stat = response;
        		}
        		if((stat.length() > 4) && (i !=0)) {
        			stat = stat.substring(0, 5);
        		}
        		//Log.println(Log.DEBUG, "myDebug", "Adding " + stat + " to statsResults");
        		response = response.substring(response.indexOf(",") + 1, response.length());
        		statsResults[i] = stat;
        	}
        	return statsResults;
        }
        
        public int countOccurrences(String word, char searchTerm)
        {
            int count = 0;
            for (int i=0; i < word.length(); i++)
            {
                if (word.charAt(i) == searchTerm)
                {
                     count++;
                }
            }
            return count;
        }
	 } // End private class WebServiceCall
	
} // End class BaseballPerformanceProjector

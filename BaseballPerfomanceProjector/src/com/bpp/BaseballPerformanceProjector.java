package com.bpp;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
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
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.database.SQLException;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TabHost;
import android.widget.TabWidget;
import android.widget.TextView;

public class BaseballPerformanceProjector extends Activity implements
		HorizontalScrollViewListener, ScrollViewListener {
	String league_name;
	ArrayList<Player> playersArrayList;
	ArrayList<String> positionList;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		league_name = "";
		Bundle extras = getIntent().getExtras();
        if(getIntent().hasExtra("league_name"))
        {
        	league_name = extras.getString("league_name");
        }

		playersArrayList = new ArrayList<Player>();
		positionList = new ArrayList<String>();

		syncStatsScrollWithHeader();

		fillPositionList();
		restorePreviousPlayerConfiguration();
		addUserPlayers();

		setInterfaceFeatures();
		initializeTabs();
	}
 
	@Override
	public void onWindowFocusChanged(boolean hasFocus) {
		// TODO Auto-generated method stub
		super.onWindowFocusChanged(hasFocus);
		fitTextSizes();
	}

	@Override
	public void onConfigurationChanged(Configuration configure) {
		super.onConfigurationChanged(configure);

		LinearLayout appLayout = (LinearLayout) findViewById(R.id.appLayout);
		LinearLayout tabsStatsPlayersLayout = (LinearLayout) findViewById(R.id.tabsStatsPlayersLayout);

		TabControls tabControls = (TabControls) findViewById(R.id.tabControls);
		FrameLayout tabContent = (FrameLayout) findViewById(R.id.tabcontent);

		InterfaceControls interfaceControls = (InterfaceControls) findViewById(R.id.uiControls);

		LinearLayout.LayoutParams tabsStatsPlayersParams;
		LinearLayout.LayoutParams tabControlParams;
		LinearLayout.LayoutParams tabContentParams;
		LinearLayout.LayoutParams uiControlsParams;

		FrameLayout dividerLine = (FrameLayout) findViewById(R.id.dividerLine);
		float scale = getResources().getDisplayMetrics().density;
		int dividerWidth = (int) (2 * scale + 0.5f);

		if (configure.orientation == Configuration.ORIENTATION_PORTRAIT) {
			appLayout.setOrientation(LinearLayout.VERTICAL);
			tabsStatsPlayersLayout.setOrientation(LinearLayout.VERTICAL);

			tabControls.setVerticalOrientation();

			tabsStatsPlayersParams = new LinearLayout.LayoutParams(
					LayoutParams.MATCH_PARENT, 0, 8);
			tabsStatsPlayersLayout.setLayoutParams(tabsStatsPlayersParams);

			tabControlParams = new LinearLayout.LayoutParams(
					LayoutParams.MATCH_PARENT, 0, 1);
			tabContentParams = new LinearLayout.LayoutParams(
					LayoutParams.MATCH_PARENT, 0, 8);

			tabControls.setLayoutParams(tabControlParams);
			tabContent.setLayoutParams(tabContentParams);

			uiControlsParams = new LinearLayout.LayoutParams(
					LayoutParams.MATCH_PARENT, 0, 2);

			dividerLine.setLayoutParams(new LinearLayout.LayoutParams(
					LayoutParams.MATCH_PARENT, dividerWidth));
			interfaceControls.setLayoutParams(uiControlsParams);
			interfaceControls.setVerticalOrientation();
		} else {
			appLayout.setOrientation(LinearLayout.HORIZONTAL);
			tabsStatsPlayersLayout.setOrientation(LinearLayout.HORIZONTAL);
			tabControls.setHorizontalOrientation();

			tabsStatsPlayersParams = new LinearLayout.LayoutParams(0,
					LayoutParams.MATCH_PARENT, 8);
			tabsStatsPlayersLayout.setLayoutParams(tabsStatsPlayersParams);

			tabControlParams = new LinearLayout.LayoutParams(0,
					LayoutParams.MATCH_PARENT, 1);
			tabContentParams = new LinearLayout.LayoutParams(0,
					LayoutParams.MATCH_PARENT, 8);

			tabControls.setLayoutParams(tabControlParams);
			tabContent.setLayoutParams(tabContentParams);

			uiControlsParams = new LinearLayout.LayoutParams(0,
					LayoutParams.MATCH_PARENT, 2);
			dividerLine.setLayoutParams(new LinearLayout.LayoutParams(
					dividerWidth, LayoutParams.MATCH_PARENT));
			interfaceControls.setLayoutParams(uiControlsParams);
			interfaceControls.setHorizontalOrientation();
		}
	}

	public void fitTextSizes() {
		LinearLayout playersListLayout = (LinearLayout) findViewById(R.id.playersListLayout);
		LinearLayout statsListLayout = (LinearLayout) findViewById(R.id.statsListLayout);
		PlayerView playerView;

		for (int i = 0; i < playersListLayout.getChildCount(); i++) {
			playerView = (PlayerView) playersListLayout.getChildAt(i);
			playerView.fitAllTextSizes();
		}
	}

	public void setInterfaceFeatures() {
		final InterfaceControls interfaceControls = (InterfaceControls) findViewById(R.id.uiControls);
		// Eventually will check user's last day
		Calendar calendar = Calendar.getInstance();
		interfaceControls.setDate(calendar);
		interfaceControls.setPreviousDateClickListener();
		interfaceControls.setNextDateClickListener();
		interfaceControls.setAddPlayerListener(new View.OnClickListener() {
			public void onClick(View v) {
				interfaceControls.setAddPlayerClickable(false);
				addPlayerDialog();
			}
		});
		interfaceControls.setProjectPerformanceListener(new View.OnClickListener() {
			public void onClick(View v) {
				makeDailyProjections();
			}
		});
		interfaceControls.setSettingsClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				settingsDialog();
			}
		});
	}

	public void syncStatsScrollWithHeader() {
		TrackableHorizontalScrollView statsScroll = (TrackableHorizontalScrollView) findViewById(R.id.statsScrollView);
		TrackableScrollView playersScroll = (TrackableScrollView) findViewById(R.id.playerListScrollView);
		HorizontalScrollView statsHeader = (HorizontalScrollView) findViewById(R.id.statsHeaderScrollView);
		TrackableScrollView statsScrollViewWrapper = (TrackableScrollView) findViewById(R.id.statsScrollViewWrapper);

		statsScrollViewWrapper.setScrollViewListener(this);
		statsHeader.setHorizontalScrollBarEnabled(false);
		statsScroll.setHorizontalScrollBarEnabled(false);
		statsScroll.setScrollViewListener(this);
		playersScroll.setScrollViewListener(this);
	}

	public void restorePreviousPlayerConfiguration() {
		LinearLayout playersListLayout = (LinearLayout) findViewById(R.id.playersListLayout);
		LinearLayout statsListLayout = (LinearLayout) findViewById(R.id.statsListLayout);
		playersListLayout.removeAllViews();
		statsListLayout.removeAllViews();
		String[] viewableStats = getViewableStats();

		for (int i = 0; i < positionList.size(); i++) {
			playersListLayout.addView(new PlayerView(this, "", "--",
					positionList.get(i)));
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
		DatabaseHandler database = new DatabaseHandler(this);
		
		League league = database.getLeague(league_name);
		
		positionList = league.getBatterPositions();
		
		Log.println(Log.DEBUG, "myDebug", "Pos: " + positionList.toString());
	}

	private void addUserPlayers() {
		// load the array of player names from resources
		DatabaseHandler databaseHandler = new DatabaseHandler(this);
		List<Player> userPlayerList = databaseHandler.getAllUserPlayers(league_name);

		for (int i = 0; i < userPlayerList.size(); i++) {
			addPlayer(userPlayerList.get(i));
		} // end for loop
		databaseHandler.close();
	} // end method addSamplePlayers

	// add a new player to the player Array List and slot it
	public void addPlayer(Player player) {
		// Check for duplicate player
		for (int i = 0; i < playersArrayList.size(); i++) {
			if (playersArrayList.get(i).equals(player)) {
				return;
			}
		} // end for loop
		playersArrayList.add(player);
		if (fillEmptySlot(player)) {
			return;
		} else {
			// Log.println(Log.DEBUG, "myDebug", "Adding " +
			// player.getPlayerFullName() + " to bench");
			addBenchPlayer(player);
		}
	} // end method addPlayer

	// Places a player in an available spot. Returns false if none available
	public boolean fillEmptySlot(final Player player) {
		LinearLayout playersListLayout = (LinearLayout) findViewById(R.id.playersListLayout);

		for (int i = 0; i < playersListLayout.getChildCount(); i++) {
			PlayerView playerView = (PlayerView) playersListLayout
					.getChildAt(i);
			if (!playerView.hasPlayer()
					&& (player.getPosition().contains(playerView.getPosition()) || playerView
							.getPosition().equals("UT"))) {
				playerView.setPlayerId(player.getPlayerId());
				playerView.setPlayerName(player.getPlayerFullName());
				playerView
						.setOnLongClickListener(new View.OnLongClickListener() {
							public boolean onLongClick(View v) {
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
		LinearLayout playersListLayout = (LinearLayout) findViewById(R.id.playersListLayout);
		LinearLayout statsListLayout = (LinearLayout) findViewById(R.id.statsListLayout);
		PlayerView playerView = new PlayerView(this, player.getPlayerId(),
				player.getPlayerFullName(), "Bench - " + player.getPosition());
		String[] viewableStats = getViewableStats();
		StatsView statsView = new StatsView(this, viewableStats);
		playerView.setOnLongClickListener(new View.OnLongClickListener() {
			public boolean onLongClick(View v) {
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
		DatabaseHandler userDatabase = new DatabaseHandler(
				BaseballPerformanceProjector.this);
		userDatabase.removePlayer(player);
		userDatabase.close();
		int playerIndex = getIndexOfPlayerId(player.getPlayerId());
		LinearLayout playerListLayout = (LinearLayout) findViewById(R.id.playersListLayout);
		LinearLayout statsListLayout = (LinearLayout) findViewById(R.id.statsListLayout);
		PlayerView playerView = (PlayerView) playerListLayout
				.getChildAt(playerIndex);
		StatsView statsView = (StatsView) statsListLayout
				.getChildAt(playerIndex);
		for (int i = 0; i < playersArrayList.size(); i++) {
			if (playersArrayList.get(i).getPlayerId()
					.equals(player.getPlayerId())) {
				playersArrayList.remove(i);
				break;
			}
		}
		if (playerView.getHeader().contains("Bench")) {
			playerListLayout.removeView(playerView);
			statsListLayout.removeView(statsView);
		} else {
			playerView.clearPlayer();
			playerView.setOnLongClickListener(null);
			statsView.clearStats();
		}
	}

	public void initializeTabs() {
		TabControls tabControls = (TabControls) findViewById(R.id.tabControls);
		LinearLayout battersTab = (LinearLayout) findViewById(R.id.battersTab);
		LinearLayout pitchersTab = (LinearLayout) findViewById(R.id.pitchersTab);

		tabControls.setBatterOnClick(this, battersTab, pitchersTab);
		tabControls.setPitcherOnClick(this, pitchersTab, battersTab);
	}

	public void modifyPlayerDialog(final Player player) {
		LayoutInflater inflater = LayoutInflater.from(this);
		final View modifyPlayerView = inflater.inflate(R.layout.modify_player,
				null);
		TextView playerName = (TextView) modifyPlayerView
				.findViewById(R.id.playerName);
		AlertDialog.Builder modifyPlayerAlert = new AlertDialog.Builder(this);

		playerName.setText(player.getPlayerFullName());

		final CheckBox catcherCheckbox = (CheckBox) modifyPlayerView
				.findViewById(R.id.checkbox_catcher);
		final CheckBox firstBaseCheckbox = (CheckBox) modifyPlayerView
				.findViewById(R.id.checkbox_firstBase);
		final CheckBox secondBaseCheckbox = (CheckBox) modifyPlayerView
				.findViewById(R.id.checkbox_secondBase);
		final CheckBox shortstopCheckbox = (CheckBox) modifyPlayerView
				.findViewById(R.id.checkbox_shortstop);
		final CheckBox thirdBaseCheckbox = (CheckBox) modifyPlayerView
				.findViewById(R.id.checkbox_thirdBase);
		final CheckBox outfieldCheckbox = (CheckBox) modifyPlayerView
				.findViewById(R.id.checkbox_outfield);
		// Check appropriate position boxes
		if (player.getPosition().contains("C")) {
			catcherCheckbox.setChecked(true);
		} else {
			catcherCheckbox.setChecked(false);
		}
		if (player.getPosition().contains("1B")) {
			firstBaseCheckbox.setChecked(true);
		} else {
			firstBaseCheckbox.setChecked(false);
		}
		if (player.getPosition().contains("2B")) {
			secondBaseCheckbox.setChecked(true);
		} else {
			secondBaseCheckbox.setChecked(false);
		}
		if (player.getPosition().contains("3B")) {
			thirdBaseCheckbox.setChecked(true);
		} else {
			thirdBaseCheckbox.setChecked(false);
		}
		if (player.getPosition().contains("SS")) {
			shortstopCheckbox.setChecked(true);
		} else {
			shortstopCheckbox.setChecked(false);
		}
		if (player.getPosition().contains("OF")) {
			outfieldCheckbox.setChecked(true);
		} else {
			outfieldCheckbox.setChecked(false);
		}

		modifyPlayerAlert.setView(modifyPlayerView);
		modifyPlayerAlert.setPositiveButton("Save Changes",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						try {
							player.clearPosition();
							if (catcherCheckbox.isChecked()) {
								player.addEligibility("C");
							}
							if (firstBaseCheckbox.isChecked()) {
								player.addEligibility("1B");
							}
							if (secondBaseCheckbox.isChecked()) {
								player.addEligibility("2B");
							}
							if (shortstopCheckbox.isChecked()) {
								player.addEligibility("SS");
							}
							if (thirdBaseCheckbox.isChecked()) {
								player.addEligibility("3B");
							}
							if (outfieldCheckbox.isChecked()) {
								player.addEligibility("OF");
							}
							DatabaseHandler userDatabase = new DatabaseHandler(
									BaseballPerformanceProjector.this);
							userDatabase.modifyPosition(player);
							userDatabase.close();
							movePlayerIfNoLongerEligible(player);
						} catch (Exception e) {
							Log.println(Log.DEBUG, "myDebug",
									"Caught exception in modifyPlayerDialog()");
							Log.println(Log.DEBUG, "myDebug", e.getMessage());
							return;
						}
					}
				});
		modifyPlayerAlert.setNeutralButton("Remove",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						try {
							removePlayer(player);
						} catch (Exception e) {
							Log.println(Log.DEBUG, "myDebug","Caught exception in modifyPlayerDialog()");
							return;
						}
					}
				});
		modifyPlayerAlert.setNegativeButton("Cancel",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						try {

						} catch (Exception e) {
							Log.println(Log.DEBUG, "myDebug",
									"Caught exception in modifyPlayerDialog()");
							return;
						}
					}
				}).show();
	}

	public void movePlayerIfNoLongerEligible(Player player) {
		int playerIndex = getIndexOfPlayerId(player.getPlayerId());
		LinearLayout playerListLayout = (LinearLayout) findViewById(R.id.playersListLayout);
		PlayerView playerView = (PlayerView) playerListLayout
				.getChildAt(playerIndex);
		String placedPosition = playerView.getPosition();
		if (!player.getPosition().contains(placedPosition)) {
			removePlayer(player);
			addPlayer(player);
		}
	}

	@Override
	public void onScrollChanged(TrackableHorizontalScrollView scrollView,
			int x, int y, int oldx, int oldy) {
		HorizontalScrollView statsHeader = (HorizontalScrollView) findViewById(R.id.statsHeaderScrollView);
		statsHeader.scrollTo(x, y);
	}

	@Override
	public void onScrollChanged(TrackableScrollView scrollView, int x, int y,
			int oldx, int oldy) {
		TrackableScrollView statsScrollWrapper = (TrackableScrollView) findViewById(R.id.statsScrollViewWrapper);
		TrackableScrollView playersScroll = (TrackableScrollView) findViewById(R.id.playerListScrollView);

		if (scrollView.getId() == statsScrollWrapper.getId()) {
			playersScroll.scrollTo(playersScroll.getScrollX(), y);
		} else {
			statsScrollWrapper.scrollTo(statsScrollWrapper.getScrollX(), y);
		}
	}

	private void addPlayerDialog() {
		LayoutInflater inflater = LayoutInflater.from(this);
		PlayersDatabaseHelper playersDatabase = new PlayersDatabaseHelper(this);
		List<Player> addablePlayersList = playersDatabase.getAllPlayers();
		playersDatabase.close();
		final View add_player = inflater.inflate(R.layout.add_player, null);
		TextView title = (TextView) add_player.findViewById(R.id.title);
		final TextView chosenPlayer = (TextView) add_player
				.findViewById(R.id.chosenPlayer);
		final Player[] addablePlayersArray = new Player[addablePlayersList
				.size()];
		title.setText("Add Player");

		addablePlayersList.toArray(addablePlayersArray);
		Arrays.sort(addablePlayersArray);
		String[] playerSummaries = new String[addablePlayersArray.length];

		for (int i = 0; i < playerSummaries.length; i++) {
			playerSummaries[i] = addablePlayersArray[i].getPlayerSummary();
		}
		final Player selectedPlayer = new Player();
		Log.println(Log.DEBUG, "myDebug", "Number of addable players: "
				+ addablePlayersList.size());

		ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_dropdown_item_1line, playerSummaries);
		final AutoCompleteTextView autoCompleteTextView = (AutoCompleteTextView) add_player
				.findViewById(R.id.players_list);
		autoCompleteTextView.setAdapter(arrayAdapter);

		final InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		if (inputMethodManager != null) {
			inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED,
					0);
		}

		autoCompleteTextView
				.setOnItemClickListener(new AdapterView.OnItemClickListener() {
					@Override
					public void onItemClick(AdapterView<?> adapter, View view,
							int position, long id) {
						String playerSummary = (String) adapter
								.getItemAtPosition(position);
						Log.println(Log.DEBUG, "myDebug", "Clicked: "
								+ playerSummary);
						int playerIndex = 0;
						for (int i = 0; i < addablePlayersArray.length; i++) {
							if (addablePlayersArray[i].getPlayerSummary()
									.equals(playerSummary)) {
								playerIndex = i;
								break;
							}
						}
						selectedPlayer.setPlayerAttributes(
								addablePlayersArray[playerIndex].getPlayerId(),
								addablePlayersArray[playerIndex].getLastName(),
								addablePlayersArray[playerIndex].getFirstName(),
								addablePlayersArray[playerIndex].getBatHand(),
								addablePlayersArray[playerIndex].getThrowHand(),
								addablePlayersArray[playerIndex].getTeam(),
								addablePlayersArray[playerIndex].getPosition());
						autoCompleteTextView.setText("");
						chosenPlayer.setText(playerSummary);
						inputMethodManager.hideSoftInputFromWindow(
								autoCompleteTextView.getWindowToken(), 0);
					}
				});

		Log.println(Log.DEBUG, "myDebug", "Inside addPlayerDialog()");

		AlertDialog.Builder addPlayerAlert = new AlertDialog.Builder(this);
		addPlayerAlert.setView(add_player);
		addPlayerAlert.setPositiveButton("Add",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						if (selectedPlayer.hasId()) {
							Log.println(Log.DEBUG, "myDebug", "Should add "
									+ selectedPlayer.getPlayerSummary());
							selectedPlayer.setLeagueId(league_name);
							addPlayer(selectedPlayer);
							inputMethodManager.hideSoftInputFromWindow(
									autoCompleteTextView.getWindowToken(), 0);
							final InterfaceControls interfaceControls = (InterfaceControls) findViewById(R.id.uiControls);
							interfaceControls.setAddPlayerClickable(true);
						}
					}
				});
		addPlayerAlert.setNegativeButton("Cancel",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						final InterfaceControls interfaceControls = (InterfaceControls) findViewById(R.id.uiControls);
						interfaceControls.setAddPlayerClickable(true);
						inputMethodManager.hideSoftInputFromWindow(
								autoCompleteTextView.getWindowToken(), 0);
					}
				}).show();
	}
	
	private void settingsDialog() {
		LayoutInflater inflater = (LayoutInflater) this.getSystemService(LAYOUT_INFLATER_SERVICE);
		final View settings_layout = inflater.inflate(R.layout.settings, null);
		AlertDialog.Builder settingsAlert = new AlertDialog.Builder(this);
		settingsAlert.setView(settings_layout);

		final Dialog alert = settingsAlert.create();
		
		final RelativeLayout makeDefaultTeamLayout = (RelativeLayout)settings_layout.findViewById(R.id.makeDefaultTeamLayout);
		final CheckBox makeDefaultTeamCheckbox = (CheckBox)settings_layout.findViewById(R.id.makeDefaultTeamCheckbox);
		
		final RelativeLayout editLeaguePositionsLayout = (RelativeLayout)settings_layout.findViewById(R.id.editLeaguePositionsLayout);
		final TextView editLeaguePositionsLabel = (TextView)settings_layout.findViewById(R.id.editLeaguePositionsLabel);
		final TextView editLeaguePositionsExtraLabel = (TextView)settings_layout.findViewById(R.id.editLeaguePositionsExtraLabel);
		final LinearLayout positionsLayout = (LinearLayout)settings_layout.findViewById(R.id.positionsLayout);
		
		final RelativeLayout editScoringLayout = (RelativeLayout)settings_layout.findViewById(R.id.editScoringLayout);
		final LinearLayout scoringLayout = (LinearLayout)settings_layout.findViewById(R.id.scoringLayout);
		final TextView editScoringLabel = (TextView)settings_layout.findViewById(R.id.editScoringLabel) ;
		final TextView editScoringExtraLabel = (TextView)settings_layout.findViewById(R.id.editScoringExtraLabel);
		
		final RelativeLayout mainMenuLayout = (RelativeLayout)settings_layout.findViewById(R.id.mainMenuLayout);
		final TextView mainMenuLabel = (TextView)settings_layout.findViewById(R.id.mainMenuLabel);
		
		makeDefaultTeamLayout.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				makeDefaultTeamCheckbox.setChecked(!makeDefaultTeamCheckbox.isChecked());
			}  
		});
		
		editLeaguePositionsLayout.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if(positionsLayout.getVisibility() == View.GONE) {
					editLeaguePositionsLayout.setBackgroundColor(Color.YELLOW);
					editLeaguePositionsLabel.setTextColor(Color.BLACK);
					editLeaguePositionsExtraLabel.setTextColor(Color.BLACK);
					positionsLayout.setVisibility(View.VISIBLE);
				} else {
					editLeaguePositionsLayout.setBackgroundColor(Color.BLACK);
					editLeaguePositionsLabel.setTextColor(Color.WHITE);
					editLeaguePositionsExtraLabel.setTextColor(Color.WHITE);
					positionsLayout.setVisibility(View.GONE);
				}
			}  
		});
		
		editScoringLayout.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if(scoringLayout.getVisibility() == View.GONE) {
					editScoringLayout.setBackgroundColor(Color.YELLOW);
					editScoringLabel.setTextColor(Color.BLACK);
					editScoringExtraLabel.setTextColor(Color.BLACK);
					scoringLayout.setVisibility(View.VISIBLE);
				} else {
					editScoringLayout.setBackgroundColor(Color.BLACK);
					editScoringLabel.setTextColor(Color.WHITE);
					editScoringExtraLabel.setTextColor(Color.WHITE);
					scoringLayout.setVisibility(View.GONE);
				}
			}
		});

		mainMenuLayout.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				mainMenuLayout.setBackgroundColor(Color.YELLOW);
				mainMenuLabel.setTextColor(Color.BLACK);
				alert.dismiss();
				
				Intent intent = new Intent(getApplicationContext(), HomeScreen.class);
				startActivity(intent);
				finish();
			}
		});
		
		CheckBox checkbox_homeRuns = (CheckBox)settings_layout.findViewById(R.id.checkbox_homeRuns);

		alert.show();
	}

	public boolean hasBenchPlayer() {
		LinearLayout playersListLayout = (LinearLayout) findViewById(R.id.playersListLayout);
		for (int i = 0; i < playersListLayout.getChildCount(); i++) {
			PlayerView playerView = (PlayerView) playersListLayout
					.getChildAt(i);
			if (playerView.getHeader().equals("Bench")) {
				return true;
			}
		}
		return false;
	}

	public void makeDailyProjections() {
		InterfaceControls interfaceControls = (InterfaceControls) findViewById(R.id.uiControls);
		String dateAndPlayerIds[] = new String[playersArrayList.size() + 1];
		dateAndPlayerIds[0] = interfaceControls.getDate();
		for (int i = 0; i < playersArrayList.size(); i++) {
			dateAndPlayerIds[i + 1] = playersArrayList.get(i).getPlayerId();
		}
		new WebServiceCall().execute(dateAndPlayerIds);
	} // End makeDailyProjections()

	public int getIndexOfPlayerId(String playerId) {
		LinearLayout playersListLayout = (LinearLayout) findViewById(R.id.playersListLayout);

		for (int i = 0; i < playersListLayout.getChildCount(); i++) {
			PlayerView playerView = (PlayerView) playersListLayout
					.getChildAt(i);
			if (playerView.getPlayerId().equals(playerId)) {
				return i;
			}
		}
		Log.println(Log.DEBUG, "myDebug",
				"Function getIndexOfPlayerId() reaching failsafe not intended to be reached.");
		return 0; // This should never be reached
	}

	private class WebServiceCall extends AsyncTask<String, Void, BatterStats[]> {
		@Override
		protected void onPreExecute() {
			InterfaceControls interfaceControls = (InterfaceControls) findViewById(R.id.uiControls);
			interfaceControls.setProjectPerformanceClickable(false);
	    }
		
		@Override
		protected BatterStats[] doInBackground(String... dateAndPlayerIds) {
			String url = "http://threemuskets.com/BaseballApp/getBatterProjection.php";
			// Log.println(Log.DEBUG, "myDebug", url);
			String response = "ERROR";
			HttpPost httppost;
			HttpClient client;
			String playerIdsString = "";
			String date = "";
			List<NameValuePair> nameValuePairs;
			try {
				client = new DefaultHttpClient();
				httppost = new HttpPost(url);
				date = dateAndPlayerIds[0];
				for(int i=1; i< dateAndPlayerIds.length; i++) {
					if(i !=1) {
						playerIdsString = playerIdsString + ",";
					}
					playerIdsString = playerIdsString + dateAndPlayerIds[i];
				}
				Log.println(Log.DEBUG, "myDebug", "Date: " + date);
				Log.println(Log.DEBUG, "myDebug", "Sending: " + playerIdsString);
				// Add your data
				nameValuePairs = new ArrayList<NameValuePair>(2);
				nameValuePairs.add(new BasicNameValuePair("players", playerIdsString));
				nameValuePairs.add(new BasicNameValuePair("date", dateAndPlayerIds[0]));
				httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
				// Execute HTTP Post Request
				ResponseHandler<String> responseHandler = new BasicResponseHandler();
				response = client.execute(httppost, responseHandler);
			} catch (Exception e) {
				Log.println(Log.DEBUG, "myDebug", "error" + e.toString());
				return null;
			}
			Log.println(Log.DEBUG, "myDebug", "Response: " + response);
			BatterStats[] batterStats = parseResponse(response, date);
			return batterStats;
		}

		@Override
		protected void onPostExecute(BatterStats[] batterStats) {
			if(batterStats.equals(null)) {
				// Throw a toast alerting the user to try again later.
				return;
			}
			for (int i = 0; i < batterStats.length; i++) {
				changeStats(batterStats[i]);
			}
			InterfaceControls interfaceControls = (InterfaceControls) findViewById(R.id.uiControls);
			interfaceControls.setProjectPerformanceClickable(true);
		}

		public void changeStats(BatterStats batterStats) {
			// Log.println(Log.DEBUG, "myDebug",
			// "Calling getIndexOfPlayer with: " + playerId);
			int index = getIndexOfPlayerId(batterStats.getPlayerId());
			// Log.println(Log.DEBUG, "myDebug", "PlayerId index: " + index);
			LinearLayout statsListLayout = (LinearLayout) findViewById(R.id.statsListLayout);
			StatsView statsView = (StatsView) statsListLayout.getChildAt(index);
			
			String[] statsResults = new String[5];
			statsResults[0] = batterStats.getHomeRuns();
			statsResults[1] = batterStats.getRuns();
			statsResults[2] = batterStats.getRbi();
			statsResults[3] = batterStats.getNetSteals();
			statsResults[4] = batterStats.getOBP();
			
			statsView.setStats(statsResults);
		}

		public BatterStats[] parseResponse(String response, String date) {
			int numPlayers = countOccurrences(response, '*');
			Log.println(Log.DEBUG, "myDebug", "Parsing: " + response);
			BatterStats[] batterStats = new BatterStats[numPlayers];
			for (int i = 0; i < numPlayers; i++) {
				String playerId;
				String homeRuns;
				String runs;
				String rbi;
				String netSteals;
				String obp;
				
				playerId = response.substring(0, response.indexOf(","));
				response = response.substring(response.indexOf(",") + 1, response.length()); // Line minus playerId
				
				homeRuns = response.substring(0, response.indexOf(","));
				response = response.substring(response.indexOf(",") + 1, response.length()); // Line minus homeRuns
				
				runs = response.substring(0, response.indexOf(","));
				response = response.substring(response.indexOf(",") + 1, response.length()); // Line minus runs
				
				rbi = response.substring(0, response.indexOf(","));
				response = response.substring(response.indexOf(",") + 1, response.length()); // Line minus rbi
				
				netSteals = response.substring(0, response.indexOf(","));
				response = response.substring(response.indexOf(",") + 1, response.length()); // Line minus netSteals
				
				obp = response.substring(0, response.indexOf("*"));
				response = response.substring(response.indexOf("*") + 1, response.length()); // Line minus obp
				
				batterStats[i] = new BatterStats(playerId, date, homeRuns, runs, rbi, netSteals, obp);
			}
			return batterStats;
		}

		public int countOccurrences(String word, char searchTerm) {
			int count = 0;
			for (int i = 0; i < word.length(); i++) {
				if (word.charAt(i) == searchTerm) {
					count++;
				}
			}
			return count;
		}
	} // End private class WebServiceCall

} // End class BaseballPerformanceProjector

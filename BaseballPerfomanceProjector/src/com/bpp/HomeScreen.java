package com.bpp;

import java.io.IOException;
import java.util.ArrayList;
import android.app.Activity;
import android.content.Intent;
import android.database.SQLException;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class HomeScreen extends Activity {
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.home);
		
		loadPlayersDatabase();

		initializeOptions();
	}

	private void initializeOptions() {
		final DatabaseHandler database = new DatabaseHandler(this);
		final ArrayList<String> leagueNames = database.getLeagueNames();
		database.close();

		final RelativeLayout addNewTeamLayout = (RelativeLayout) findViewById(R.id.addNewTeamLayout);
		final TextView addNewTeamLabel = (TextView) findViewById(R.id.addNewTeamLabel);
		final LinearLayout newTeamLayout = (LinearLayout) findViewById(R.id.newTeamLayout);

		final RelativeLayout viewTeamsLayout = (RelativeLayout) findViewById(R.id.viewTeamsLayout);
		final LinearLayout teamsLayout = (LinearLayout) findViewById(R.id.teamsLayout);
		final TextView viewTeamsLabel = (TextView) findViewById(R.id.viewTeamsLabel);

		final Button addNewTeamButton = (Button) findViewById(R.id.addNewTeamButton);
		final EditText newTeamName = (EditText) findViewById(R.id.newTeamName);
		
		fillTeamLayout(leagueNames);
		
		addNewTeamButton.setEnabled(false);

		newTeamName.addTextChangedListener(new TextWatcher() {
			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1,
					int arg2, int arg3) {}
			@Override
			public void onTextChanged(CharSequence arg0, int arg1, int arg2,
					int arg3) {}

			@Override
			public void afterTextChanged(Editable s) {
				if(s.toString().trim().length() != 0) {
					addNewTeamButton.setEnabled(true);
				} else {
					addNewTeamButton.setEnabled(false);
				}
			}
		});

		addNewTeamButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				String name = newTeamName.getText().toString();
				name = name.trim();
				
				if (!leagueNames.contains(name)) {
					Log.println(Log.DEBUG, "myDebug", "All good.  Go!");
					League league = new League(name, 0, 1,1,1,1,1,0,0,3,
							1,2,2,2,0,1,1,1,0,0,1,1,1,0,1,1,1,1,1);
					Log.println(Log.DEBUG, "myDebug", "Newly Created League Pos: " +league.getBatterPositions().toString());
					database.addLeague(league);
					database.close();
					
					Intent intent = new Intent(getApplicationContext(), BaseballPerformanceProjector.class);
	        		intent.putExtra("league_name", name);
					startActivity(intent);
					finish();
				} else {
					Log.println(Log.DEBUG, "myDebug", "Bad.  User typed a league name that already exists.");
				}
			} 
		});

		addNewTeamLayout.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (newTeamLayout.getVisibility() == View.GONE) {
					addNewTeamLayout.setBackgroundColor(Color.YELLOW);
					addNewTeamLabel.setTextColor(Color.BLACK);
					newTeamLayout.setVisibility(View.VISIBLE);
					newTeamName.requestFocus();
					
					viewTeamsLayout.setBackgroundColor(Color.BLACK);
					viewTeamsLabel.setTextColor(Color.WHITE);
					teamsLayout.setVisibility(View.GONE);
				} else {
					addNewTeamLayout.setBackgroundColor(Color.BLACK);
					addNewTeamLabel.setTextColor(Color.WHITE);
					newTeamLayout.setVisibility(View.GONE);
				}
			}
		});

		if(leagueNames.size() == 0) {
			viewTeamsLabel.setTextColor(Color.GRAY);
		} else {
			viewTeamsLayout.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					if (teamsLayout.getVisibility() == View.GONE) {
						viewTeamsLayout.setBackgroundColor(Color.YELLOW);
						viewTeamsLabel.setTextColor(Color.BLACK);
						teamsLayout.setVisibility(View.VISIBLE);
						
						addNewTeamLayout.setBackgroundColor(Color.BLACK);
						addNewTeamLabel.setTextColor(Color.WHITE);
						newTeamLayout.setVisibility(View.GONE);
					} else {
						viewTeamsLayout.setBackgroundColor(Color.BLACK);
						viewTeamsLabel.setTextColor(Color.WHITE);
						teamsLayout.setVisibility(View.GONE);
					}
				}
			});
		}
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
		} catch (SQLException sqle) {
			throw sqle;
		}
		playersDatabase.close();
	}

	public void fillTeamLayout(final ArrayList<String> leagueNames) {
		final RadioGroup radioTeams = (RadioGroup) findViewById(R.id.radioTeams);
		final Button removeTeamButton = (Button)findViewById(R.id.removeTeamButton);
		final Button selectTeamButton = (Button)findViewById(R.id.selectTeamButton);
		final DatabaseHandler database = new DatabaseHandler(this);
		
		removeTeamButton.setEnabled(false);
		selectTeamButton.setEnabled(false);
		
		for(int i=0; i < leagueNames.size(); i++) {
			RadioButton newTeam = new RadioButton(this);
			newTeam.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT));
			newTeam.setText(leagueNames.get(i));
			newTeam.setTextColor(Color.WHITE);
			newTeam.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					removeTeamButton.setEnabled(true);
					selectTeamButton.setEnabled(true);
				} 
			});
			radioTeams.addView(newTeam);
		}

		removeTeamButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				RadioButton selected = (RadioButton)findViewById(radioTeams.getCheckedRadioButtonId());
				String name = selected.getText().toString();
				
				database.removeLeague(name);
				removeTeamButton.setEnabled(false);
				selectTeamButton.setEnabled(false);
				
				radioTeams.removeView(selected);
				leagueNames.remove(name);
			} 
		});
		
		selectTeamButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				RadioButton selected = (RadioButton)findViewById(radioTeams.getCheckedRadioButtonId());
				String name = selected.getText().toString();
				Intent intent = new Intent(getApplicationContext(), BaseballPerformanceProjector.class);
        		intent.putExtra("league_name", name);
				startActivity(intent);
				finish();
			} 
		});
	}
	
	public int countOccurrences(String word, char c) {
		int count = 0;
		for (int i = 0; i < word.length(); i++) {
			if (word.charAt(i) == c) {
				count++;
			}
		}
		return count;
	}
}
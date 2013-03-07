package com.bpp;

import android.app.Activity;
import android.app.AlertDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class HomeScreen extends Activity {
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.home);

		initializeOptions();
	}

	private void initializeOptions() {

		final RelativeLayout addNewTeamLayout = (RelativeLayout)findViewById(R.id.addNewTeamLayout);
		final TextView addNewTeamLabel = (TextView)findViewById(R.id.addNewTeamLabel);
		final LinearLayout newTeamLayout = (LinearLayout)findViewById(R.id.newTeamLayout);
		
		final RelativeLayout viewTeamsLayout = (RelativeLayout) findViewById(R.id.viewTeamsLayout);
		final LinearLayout teamsLayout = (LinearLayout) findViewById(R.id.teamsLayout);
		final TextView viewTeamsLabel = (TextView) findViewById(R.id.viewTeamsLabel);
		
		final Button addNewTeamButton = (Button)findViewById(R.id.addNewTeamButton);
		final EditText newTeamName = (EditText)findViewById(R.id.newTeamName);
		
		addNewTeamButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				String name = newTeamName.getText().toString();
				name = name.trim();
				if((name.length() > 0) && (countOccurrences(name, ' ') != name.length())) {
					Log.println(Log.DEBUG, "myDebug", "All good.  Go!");
				} else {
					Log.println(Log.DEBUG, "myDebug", "Bad.");
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
				} else {
					addNewTeamLayout.setBackgroundColor(Color.BLACK);
					addNewTeamLabel.setTextColor(Color.WHITE);
					newTeamLayout.setVisibility(View.GONE);
				}
			}
		});

		viewTeamsLayout.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (teamsLayout.getVisibility() == View.GONE) {
					viewTeamsLayout.setBackgroundColor(Color.YELLOW);
					viewTeamsLabel.setTextColor(Color.BLACK);
					teamsLayout.setVisibility(View.VISIBLE);
				} else {
					viewTeamsLayout.setBackgroundColor(Color.BLACK);
					viewTeamsLabel.setTextColor(Color.WHITE);
					teamsLayout.setVisibility(View.GONE);
				}
			}
		});
	}
	
	public int countOccurrences(String word, char c)
	{
	    int count = 0;
	    for (int i=0; i < word.length(); i++)
	    {
	        if (word.charAt(i) == c)
	        {
	             count++;
	        }
	    }
	    return count;
	}
}
package com.bpp;

import android.R.color;
import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class PlayerView extends LinearLayout {
	private String playerId;
	private String playerName;
	private String position;
	
	public PlayerView(Context context, AttributeSet attrs) 
	{
		 super(context, attrs);
		
		this.setBackgroundColor(color.background_light);
		this.setBackgroundResource(color.background_light);
		float scale = getContext().getResources().getDisplayMetrics().density;
		int heightDP = (int) (41 * scale + 0.5f);
		RelativeLayout relativeLayout = new RelativeLayout(context);
		scale = getContext().getResources().getDisplayMetrics().density;
		heightDP = (int) (40 * scale + 0.5f);
		relativeLayout.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT,heightDP));
		
		playerId = "";
		playerName = "--";
		position = "";
		
		addHeader(context, "", relativeLayout);
		addPlayerName(context, relativeLayout);
		this.addView(relativeLayout);
		addHorizontalBreak(context);
	}
	
	public PlayerView(Context context, String playerId, String playerName, String position) {
		super(context);
		float scale = getContext().getResources().getDisplayMetrics().density;
		int heightDP = (int) (41 * scale + 0.5f);
		this.setOrientation(VERTICAL);
		this.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT,heightDP));
		this.setBackgroundColor(color.background_light);
		
		RelativeLayout relativeLayout = new RelativeLayout(context);
		scale = getContext().getResources().getDisplayMetrics().density;
		heightDP = (int) (40 * scale + 0.5f);
		relativeLayout.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT,heightDP));

		this.playerId = playerId;
		this.playerName = playerName;
		this.position = position;
		
		addHeader(context, position, relativeLayout);
		addPlayerName(context, relativeLayout);
		this.addView(relativeLayout);
		addHorizontalBreak(context);
	}
	
	public void addHeader(Context context, String header, RelativeLayout relativeLayout) {
		TextView textView = new TextView(context);

		textView.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
		textView.setText(header);
		
		relativeLayout.addView(textView);
	}
	
	public void addPlayerName(Context context, RelativeLayout relativeLayout) {
		TextView textView = new TextView(context);
		final float scale = getContext().getResources().getDisplayMetrics().density;
		int indentDP = (int) (12 * scale + 0.5f);
		
		textView.setGravity(Gravity.CENTER);
		textView.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
		textView.setText(playerName);
		if(!playerName.equals("--")) {
			textView.setGravity(Gravity.CENTER_VERTICAL);
			textView.setPadding(indentDP, indentDP, 0, 0);
		}
		
		relativeLayout.addView(textView);
	}
	
	public void addHorizontalBreak(Context context) {
		TextView textView = new TextView(context);
		final float scale = getContext().getResources().getDisplayMetrics().density;
		int heightDP = (int) (1 * scale + 0.5f);
		
		textView.setBackgroundResource(color.darker_gray);
		textView.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, heightDP));
		textView.setGravity(Gravity.BOTTOM);
		this.addView(textView);
	}
	
	public boolean hasPlayer() {
		return !playerId.equals("");
	}
	
	public String getPlayerName() {
		return playerName;
	}
	
	public String getPlayerId() {
		return playerId;
	}
	
	public String getPosition() {
		return position;
	}
	
	public void setPlayerId(String playerId) {
		this.playerId = playerId;
	}
	
	public void setPlayerName(String playerName) {
		this.playerName = playerName;
		RelativeLayout relativeLayout = (RelativeLayout)this.getChildAt(0);
		TextView textView = (TextView)relativeLayout.getChildAt(1);
		final float scale = getContext().getResources().getDisplayMetrics().density;
		int indentDP = (int) (12 * scale + 0.5f);
		
		textView.setText(playerName);
		if(!playerName.equals("--")) {
			textView.setGravity(Gravity.CENTER_VERTICAL);
			textView.setPadding(indentDP, indentDP, 0, 0);
		}
	}
	
	public String getHeader() {
		RelativeLayout relativeLayout = (RelativeLayout)this.getChildAt(0);
		TextView textView = (TextView)relativeLayout.getChildAt(0);
		return textView.getText().toString();
	}
	
	public void clearPlayer() {
		setPlayerId("");
		setPlayerName("--");
	}
}
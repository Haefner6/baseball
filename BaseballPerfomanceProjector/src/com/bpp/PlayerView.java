package com.bpp;

import android.R.color;
import android.content.Context;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class PlayerView extends LinearLayout {
	private String playerId;
	private String playerName;
	private String position;
	private Context context;
	private int currentWidth;
	private float DEFAULT_TEXT_SIZE = 14;
	
	public PlayerView(Context context, String playerId, String playerName, String position) {
		super(context);
		this.context = context;
		float scale = getContext().getResources().getDisplayMetrics().density;
		int heightDP = (int) (41 * scale + 0.5f);
		this.setOrientation(VERTICAL);
		this.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,heightDP));
		this.setBackgroundColor(color.background_light);
		
		RelativeLayout relativeLayout = new RelativeLayout(context);
		scale = getContext().getResources().getDisplayMetrics().density;
		heightDP = (int) (40 * scale + 0.5f);
		relativeLayout.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,heightDP));

		this.playerId = playerId;
		this.playerName = playerName;
		this.position = position;
		
		addHeader(context, position, relativeLayout);
		addPlayerName(context, relativeLayout);
		this.addView(relativeLayout);
		addHorizontalBreak(context);
		this.currentWidth = this.getWidth();
	}

	public void addHeader(Context context, String header, RelativeLayout relativeLayout) {
		TextView textView = new TextView(context);
		final float scale = getContext().getResources().getDisplayMetrics().density;
		int indentDP = (int) (3 * scale + 0.5f);

		textView.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
		textView.setText(header);
		textView.setPadding(indentDP, 0, 0, 0);
		textView.setTypeface(null,Typeface.BOLD);
		
		relativeLayout.addView(textView, 0);
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
		
		relativeLayout.addView(textView, 1);
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
	
	public void fitAllTextSizes() {
		RelativeLayout relativeLayout = (RelativeLayout)this.getChildAt(0);
		TextView headerView = (TextView)relativeLayout.getChildAt(0);
		TextView playerNameView = (TextView)relativeLayout.getChildAt(1);

		fitTextSize(headerView);
		fitTextSize(playerNameView);
	}
	 
	public void fitTextSize(TextView textView) {
		String text = textView.getText().toString();
		
		final float scale = getContext().getResources().getDisplayMetrics().density;
		int textViewWidth = (int) (138 * scale + 0.5f);

    	float textSize = DEFAULT_TEXT_SIZE;
    	
    	Paint paint = textView.getPaint();
    	
	    for (float i = textSize; i > 8; i--) {
	    	textView.setTextSize(i);
	   	    if (paint.measureText(text, 0, text.length()) < textViewWidth) {
	   	    	//Log.println(Log.DEBUG, "myDebug","Changing textSize of: " + text + " to: " + i);
	   	    	textView.setTextSize(i);
	   	        break; 
	    	}
	    }
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
	
	public void setCurrentWidth() {
		this.currentWidth = this.getWidth();
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
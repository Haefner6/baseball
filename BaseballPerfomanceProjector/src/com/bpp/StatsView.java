package com.bpp;

import android.R.color;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

public class StatsView extends LinearLayout {
	public StatsView(Context context, String[] statsList) {
		super(context);
		float scale = getContext().getResources().getDisplayMetrics().density;
		int heightDP = (int) (41 * scale + 0.5f);
		this.setOrientation(VERTICAL);
		this.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT,heightDP));
		this.setBackgroundColor(color.background_light);
		
		LinearLayout linearLayout = new LinearLayout(context);
		linearLayout.setOrientation(HORIZONTAL);
		scale = getContext().getResources().getDisplayMetrics().density;
		heightDP = (int) (40 * scale + 0.5f);
		linearLayout.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT,heightDP));
		
		fillViews(context, linearLayout);
	}
	
	public void fillViews(Context context, LinearLayout linearLayout) {
		//Log.println(Log.DEBUG, "myDebug", "Inside fillViews");
		addTextView(context, "HR", linearLayout);
		addVerticalBreak(context, linearLayout);
		addTextView(context, "R", linearLayout);
		addVerticalBreak(context, linearLayout);
		addTextView(context, "RBI", linearLayout);
		addVerticalBreak(context, linearLayout);
		addTextView(context, "SB", linearLayout);
		addVerticalBreak(context, linearLayout);
		addTextView(context, "OBP", linearLayout);
		addVerticalBreak(context, linearLayout);
		this.addView(linearLayout);
		addHorizontalBreak(context);
	}
	
	public void addTextView(Context context, String stat, LinearLayout linearLayout) {
		TextView textView = new TextView(context);
		final float scale = getContext().getResources().getDisplayMetrics().density;
		int widthDP = (int) (50 * scale + 0.5f);

		textView.setGravity(Gravity.CENTER);
		textView.setLayoutParams(new LinearLayout.LayoutParams(widthDP, LayoutParams.MATCH_PARENT));
		textView.setText("--");
		linearLayout.addView(textView);
	}
	
	public void addVerticalBreak(Context context, LinearLayout linearLayout) {
		FrameLayout frameLayout = new FrameLayout(context);
		final float scale = getContext().getResources().getDisplayMetrics().density;
		int widthDP = (int) (1 * scale + 0.5f);

		frameLayout.setBackgroundResource(color.darker_gray);
		frameLayout.setLayoutParams(new LinearLayout.LayoutParams(widthDP, LayoutParams.MATCH_PARENT));
		
		linearLayout.addView(frameLayout);
		//Log.println(Log.DEBUG, "myDebug", "Added vertical break");
	}
	
	public void addHorizontalBreak(Context context) {
		FrameLayout frameLayout = new FrameLayout(context);
		final float scale = getContext().getResources().getDisplayMetrics().density;
		int heightDP = (int) (1 * scale + 0.5f);
		
		frameLayout.setBackgroundResource(color.darker_gray);
		frameLayout.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, heightDP));
		this.addView(frameLayout);
	}
	
	public void setStats(String[] stats) {
		LinearLayout linearLayout = (LinearLayout)this.getChildAt(0);
		int arrayIndex = 0;
		for(int i=0; i < linearLayout.getChildCount(); i=i+2) {
			TextView textView = (TextView)linearLayout.getChildAt(i);
			//Log.println(Log.DEBUG, "myDebug", "Trying to change index " + i);
			textView.setText(stats[arrayIndex]);
			arrayIndex++;
		}
	}
	
	public void clearStats() {
		LinearLayout linearLayout = (LinearLayout)this.getChildAt(0);
		for(int i=0; i < linearLayout.getChildCount(); i=i+2) {
			TextView textView = (TextView)linearLayout.getChildAt(i);
			textView.setText("--");
		}
	}
}
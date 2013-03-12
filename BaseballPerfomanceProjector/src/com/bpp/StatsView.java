package com.bpp;

import java.util.ArrayList;

import android.R.color;
import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

public class StatsView extends LinearLayout {
	public StatsView(Context context, League league, boolean isHeader) {
		super(context);
		float scale = getContext().getResources().getDisplayMetrics().density;
		int heightDP = (int) (41 * scale + 0.5f);
		this.setOrientation(VERTICAL);
		this.setBackgroundColor(color.background_light);

		LinearLayout linearLayout = new LinearLayout(context);
		linearLayout.setOrientation(HORIZONTAL);
		scale = getContext().getResources().getDisplayMetrics().density;
		int innerHeightDP = (int) (40 * scale + 0.5f);

		if (isHeader) {
			this.setLayoutParams(new ViewGroup.LayoutParams(
					ViewGroup.LayoutParams.WRAP_CONTENT,
					ViewGroup.LayoutParams.MATCH_PARENT));
			linearLayout.setLayoutParams(new ViewGroup.LayoutParams(
					ViewGroup.LayoutParams.FILL_PARENT,
					ViewGroup.LayoutParams.WRAP_CONTENT));
		} else {
			this.setLayoutParams(new ViewGroup.LayoutParams(
					ViewGroup.LayoutParams.FILL_PARENT, heightDP));
			linearLayout.setLayoutParams(new ViewGroup.LayoutParams(
					ViewGroup.LayoutParams.FILL_PARENT, innerHeightDP));
		}

		fillViews(context, linearLayout, league, isHeader);
	}

	public void fillViews(Context context, LinearLayout linearLayout,
			League league, boolean isHeader) {
		// Log.println(Log.DEBUG, "myDebug", "Inside fillViews");
		ArrayList<String> possibleStats = new ArrayList<String>();
		ArrayList<String> viewableStats = new ArrayList<String>();

		possibleStats = league.getAllPossibleBatterStats();
		viewableStats = league.getLeagueBatterStats();

		for (int i = 0; i < possibleStats.size(); i++) {
			if (viewableStats.contains(possibleStats.get(i))) {
				addTextView(context, possibleStats.get(i), linearLayout,
						View.VISIBLE, isHeader);
				addVerticalBreak(context, linearLayout, View.VISIBLE);
			} else {
				addTextView(context, possibleStats.get(i), linearLayout,
						View.GONE, isHeader);
				addVerticalBreak(context, linearLayout, View.GONE);
			}

		}
		this.addView(linearLayout);
		addHorizontalBreak(context);
	}

	public void addTextView(Context context, String stat,
			LinearLayout linearLayout, int visibility, boolean isHeader) {
		TextView textView = new TextView(context);
		final float scale = getContext().getResources().getDisplayMetrics().density;
		int widthDP = (int) (50 * scale + 0.5f);

		textView.setGravity(Gravity.CENTER);
		textView.setLayoutParams(new LinearLayout.LayoutParams(widthDP,
				LayoutParams.MATCH_PARENT));
		if (isHeader) {
			textView.setText(stat);
			textView.setTypeface(null, Typeface.BOLD);
		} else {
			textView.setText("--");
		}
		textView.setVisibility(visibility);
		linearLayout.addView(textView);
	}

	public void addVerticalBreak(Context context, LinearLayout linearLayout,
			int visibility) {
		FrameLayout frameLayout = new FrameLayout(context);
		final float scale = getContext().getResources().getDisplayMetrics().density;
		int widthDP = (int) (1 * scale + 0.5f);

		frameLayout.setBackgroundResource(color.darker_gray);
		frameLayout.setLayoutParams(new LinearLayout.LayoutParams(widthDP,
				LayoutParams.MATCH_PARENT));
		frameLayout.setVisibility(visibility);
		linearLayout.addView(frameLayout);
		// Log.println(Log.DEBUG, "myDebug", "Added vertical break");
	}

	public void addHorizontalBreak(Context context) {
		FrameLayout frameLayout = new FrameLayout(context);
		final float scale = getContext().getResources().getDisplayMetrics().density;
		int heightDP = (int) (1 * scale + 0.5f);

		frameLayout.setBackgroundResource(color.darker_gray);
		frameLayout.setLayoutParams(new LinearLayout.LayoutParams(
				LayoutParams.MATCH_PARENT, heightDP));
		this.addView(frameLayout);
	}

	public void setStats(String[] stats) {
		LinearLayout linearLayout = (LinearLayout) this.getChildAt(0);
		int arrayIndex = 0;
		for (int i = 0; i < linearLayout.getChildCount(); i = i + 2) {
			TextView textView = (TextView) linearLayout.getChildAt(i);
			// Log.println(Log.DEBUG, "myDebug", "Trying to change index " + i);
			textView.setText(stats[arrayIndex]);
			arrayIndex++;
		}
	}

	public void clearStats() {
		LinearLayout linearLayout = (LinearLayout) this.getChildAt(0);
		for (int i = 0; i < linearLayout.getChildCount(); i = i + 2) {
			TextView textView = (TextView) linearLayout.getChildAt(i);
			textView.setText("--");
		}
	}
}
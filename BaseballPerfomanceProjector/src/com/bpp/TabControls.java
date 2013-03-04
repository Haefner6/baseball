package com.bpp;

import android.R.color;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class TabControls extends LinearLayout {
	public final int TAB_COLOR = color.background_light;
	public final int SELECTION_COLOR = color.holo_blue_bright;
	
	public TabControls(Context context, AttributeSet attrs) 
	{
		 super(context, attrs);
		
		this.setBackgroundColor(TAB_COLOR);
		this.setBackgroundResource(TAB_COLOR);
		
		LinearLayout topContainer = new LinearLayout(context);
		topContainer.setId(500);
		
		topContainer.addView(getBatterContainer(context));
		topContainer.addView(getDividerContainer(context));
		topContainer.addView(getPitcherContainer(context));
		
		this.addView(topContainer);
		addBuffer(context);
		
		setVerticalOrientation();
		selectBatters();
	}
	
	public LinearLayout getBatterContainer(Context context) {
		LinearLayout linearLayout = new LinearLayout(context);
		linearLayout.setId(1000);

		TextView batters = new TextView(context);
		TextView batterSelected = new TextView(context);
		
		batters.setId(1001);
		batterSelected.setId(1002);
		
		linearLayout.addView(batters);
		linearLayout.addView(batterSelected);
		
		return linearLayout;
	}
	
	public LinearLayout getDividerContainer(Context context) {
		LinearLayout linearLayout = new LinearLayout(context);
		linearLayout.setId(2000);

		TextView topSpacer = new TextView(context);
		TextView middleSpacer = new TextView(context);
		TextView bottomSpacer = new TextView(context);
		
		topSpacer.setId(2001);
		middleSpacer.setId(2002);
		bottomSpacer.setId(2003);
		
		middleSpacer.setBackgroundColor(color.darker_gray);
		middleSpacer.setBackgroundResource(color.darker_gray);
		
		linearLayout.addView(topSpacer);
		linearLayout.addView(middleSpacer);
		linearLayout.addView(bottomSpacer);
		
		return linearLayout;
	}
	
	public LinearLayout getPitcherContainer(Context context) {
		LinearLayout linearLayout = new LinearLayout(context);
		linearLayout.setId(3000);

		TextView pitchers = new TextView(context);
		TextView pitcherSelected = new TextView(context);
		
		pitchers.setId(3001);
		pitcherSelected.setId(3002);
		
		linearLayout.addView(pitchers);
		linearLayout.addView(pitcherSelected);
		
		return linearLayout;
	}
	
	public void addBuffer(Context context) {
		LinearLayout linearLayout = new LinearLayout(context);
		linearLayout.setId(4000);
		
		linearLayout.setBackgroundColor(SELECTION_COLOR);
		linearLayout.setBackgroundResource(SELECTION_COLOR);
		
		this.addView(linearLayout);
	}
	
	public void selectBatters() {
		TextView batterSelected = (TextView)this.findViewById(1002);
		TextView pitcherSelected = (TextView)this.findViewById(3002);
		
		batterSelected.setBackgroundColor(SELECTION_COLOR);
		batterSelected.setBackgroundResource(SELECTION_COLOR);
		
		pitcherSelected.setBackgroundColor(TAB_COLOR);
		pitcherSelected.setBackgroundResource(TAB_COLOR);
	}
	
	public void selectPitchers() {
		TextView batterSelected = (TextView)this.findViewById(1002);
		TextView pitcherSelected = (TextView)this.findViewById(3002);
		
		batterSelected.setBackgroundColor(TAB_COLOR);
		batterSelected.setBackgroundResource(TAB_COLOR);
		
		pitcherSelected.setBackgroundColor(SELECTION_COLOR);
		pitcherSelected.setBackgroundResource(SELECTION_COLOR);
	}
	
	public void setVerticalOrientation() {
		this.setOrientation(LinearLayout.VERTICAL);
		
		// Top Container
		LinearLayout topContainer = (LinearLayout)this.findViewById(500);
		topContainer.setOrientation(LinearLayout.HORIZONTAL);
		LinearLayout.LayoutParams topContainerParams = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, 0, 100);
		topContainer.setLayoutParams(topContainerParams);
		
		// BatterLayout
		LinearLayout batterLayout = (LinearLayout)this.findViewById(1000);
		batterLayout.setOrientation(LinearLayout.VERTICAL);
		LinearLayout.LayoutParams batterLayoutParams = new LinearLayout.LayoutParams(0, LayoutParams.MATCH_PARENT, 2);
		batterLayout.setLayoutParams(batterLayoutParams);
		
		// Batter 
		TextView batter = (TextView)this.findViewById(1001);
		LinearLayout.LayoutParams batterParams = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, 0, 5);
		batter.setLayoutParams(batterParams);
		batter.setText("BATTERS");
		batter.setTypeface(null,Typeface.BOLD);
		batter.setGravity(Gravity.CENTER);

		// BatterSelected
		TextView batterSelected = (TextView)this.findViewById(1002);
		LinearLayout.LayoutParams batterSelectedParams = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, 0, 1);
		batterSelected.setLayoutParams(batterSelectedParams);
		
		// PitcherLayout   - shares layout parameters with batter
		LinearLayout pitcherLayout = (LinearLayout)this.findViewById(3000);
		pitcherLayout.setOrientation(LinearLayout.VERTICAL);
		TextView pitcher = (TextView)this.findViewById(3001);
		TextView pitcherSelected = (TextView)this.findViewById(3002);
		pitcherLayout.setLayoutParams(batterLayoutParams);
		pitcher.setLayoutParams(batterParams);
		pitcherSelected.setLayoutParams(batterSelectedParams);
		pitcher.setText("PITCHERS");
		pitcher.setTypeface(null,Typeface.BOLD);
		pitcher.setGravity(Gravity.CENTER);
		
		// Divider Layout
		LinearLayout dividerLayout = (LinearLayout)this.findViewById(2000);
		LinearLayout.LayoutParams dividerLayoutParams = new LinearLayout.LayoutParams(1, LayoutParams.MATCH_PARENT);
		dividerLayout.setOrientation(LinearLayout.VERTICAL);
		dividerLayout.setLayoutParams(dividerLayoutParams);
		
		TextView topDivider = (TextView)this.findViewById(2001);
		LinearLayout.LayoutParams topDividerParams = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, 0, 1);
		topDivider.setLayoutParams(topDividerParams);
		
		TextView middleDivider = (TextView)this.findViewById(2002);
		LinearLayout.LayoutParams middleDividerParams = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, 0, 4);
		middleDivider.setLayoutParams(middleDividerParams);
		
		TextView bottomDivider = (TextView)this.findViewById(2003);
		bottomDivider.setLayoutParams(topDividerParams);
		
		// Buffer Layout
		LinearLayout bufferLayout = (LinearLayout)this.findViewById(4000);
		LinearLayout.LayoutParams bufferParams = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, 0, 1);
		bufferLayout.setLayoutParams(bufferParams);
	}
	
	public void setHorizontalOrientation() {
		this.setOrientation(LinearLayout.HORIZONTAL);
		
		// Top Container
		LinearLayout topContainer = (LinearLayout)this.findViewById(500);
		topContainer.setOrientation(LinearLayout.VERTICAL);
		LinearLayout.LayoutParams topContainerParams = new LinearLayout.LayoutParams(0, LayoutParams.MATCH_PARENT, 100);
		topContainer.setLayoutParams(topContainerParams);
		
		// BatterLayout
		LinearLayout batterLayout = (LinearLayout)this.findViewById(1000);
		batterLayout.setOrientation(LinearLayout.HORIZONTAL);
		LinearLayout.LayoutParams batterLayoutParams = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, 0, 2);
		batterLayout.setLayoutParams(batterLayoutParams);
		
		// Batter 
		TextView batter = (TextView)this.findViewById(1001);
		LinearLayout.LayoutParams batterParams = new LinearLayout.LayoutParams(0, LayoutParams.MATCH_PARENT, 5);
		batter.setLayoutParams(batterParams);
		batter.setText("B\nA\nT\nT\nE\nR\nS");
		batter.setTypeface(null,Typeface.BOLD);
		batter.setGravity(Gravity.CENTER);
		batter.setLineSpacing(0, (float) 0.8);

		// BatterSelected
		TextView batterSelected = (TextView)this.findViewById(1002);
		LinearLayout.LayoutParams batterSelectedParams = new LinearLayout.LayoutParams(0, LayoutParams.MATCH_PARENT, 1);
		batterSelected.setLayoutParams(batterSelectedParams);
		
		// PitcherLayout   - shares layout parameters with batter
		LinearLayout pitcherLayout = (LinearLayout)this.findViewById(3000);
		pitcherLayout.setOrientation(LinearLayout.HORIZONTAL);
		TextView pitcher = (TextView)this.findViewById(3001);
		TextView pitcherSelected = (TextView)this.findViewById(3002);
		pitcherLayout.setLayoutParams(batterLayoutParams);
		pitcher.setLayoutParams(batterParams);
		pitcherSelected.setLayoutParams(batterSelectedParams);
		pitcher.setText("P\nI\nT\nC\nH\nE\nR\nS");
		pitcher.setTypeface(null,Typeface.BOLD);
		pitcher.setGravity(Gravity.CENTER);
		pitcher.setLineSpacing(0, (float) 0.8);
		
		//Log.println(Log.DEBUG, "myDebug", "Header Width: "); 
		
		// Divider Layout
		LinearLayout dividerLayout = (LinearLayout)this.findViewById(2000);
		LinearLayout.LayoutParams dividerLayoutParams = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, 1);
		dividerLayout.setOrientation(LinearLayout.HORIZONTAL);
		dividerLayout.setLayoutParams(dividerLayoutParams);
		
		TextView topDivider = (TextView)this.findViewById(2001);
		LinearLayout.LayoutParams topDividerParams = new LinearLayout.LayoutParams(0, LayoutParams.MATCH_PARENT, 1);
		topDivider.setLayoutParams(topDividerParams);
		
		TextView middleDivider = (TextView)this.findViewById(2002);
		LinearLayout.LayoutParams middleDividerParams = new LinearLayout.LayoutParams(0, LayoutParams.MATCH_PARENT, 4);
		middleDivider.setLayoutParams(middleDividerParams);
		
		TextView bottomDivider = (TextView)this.findViewById(2003);
		bottomDivider.setLayoutParams(topDividerParams);
		
		// Buffer Layout
		LinearLayout bufferLayout = (LinearLayout)this.findViewById(4000);
		LinearLayout.LayoutParams bufferParams = new LinearLayout.LayoutParams(0, LayoutParams.MATCH_PARENT, 1);
		bufferLayout.setLayoutParams(bufferParams);
	}
	
	public void maximizeTextSize(TextView textView) {
    	String text = textView.getText().toString();
    	int textViewWidth = textView.getWidth();
    	int textSize = 20;

    	Paint paint = textView.getPaint();
    	for (textSize = 20; textSize <= 100; ++textSize) {
    	    if (paint.measureText(text, 0, text.length()) > textViewWidth) {
    	    	textView.setTextSize(textSize);
    	        break;
    	    }
    	}
    }
	
	public void setBatterOnClick(Context context, final LinearLayout showView, final LinearLayout hideView) {
		LinearLayout batterLayout = (LinearLayout)this.findViewById(1000);
		
		batterLayout.setOnClickListener(new View.OnClickListener()
        {
        	public void onClick(View v) 
        	{ 	
        		showView.setVisibility(View.VISIBLE);
        		hideView.setVisibility(View.GONE);
        		selectBatters();
       		}
        });
	}
	
	public void setPitcherOnClick(Context context, final LinearLayout showView, final LinearLayout hideView) {
		LinearLayout pitcherLayout = (LinearLayout)this.findViewById(3000);
		
		pitcherLayout.setOnClickListener(new View.OnClickListener()
        {
        	public void onClick(View v) 
        	{ 	
        		showView.setVisibility(View.VISIBLE);
        		hideView.setVisibility(View.GONE);
        		selectPitchers();
       		}
        });
	}
}
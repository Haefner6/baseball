package com.bpp;

import java.util.Calendar;
import java.util.Date;

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

public class InterfaceControls extends LinearLayout {
	public InterfaceControls(Context context, AttributeSet attrs) {
		super(context, attrs);

		this.setBackgroundColor(color.background_light);
		this.setBackgroundResource(color.background_light);

		addAddAndSettingsLayout(context);
		addDatesAndProjectionsLayout(context);

		setVerticalOrientation();
	}

	public void addAddAndSettingsLayout(Context context) {
		LinearLayout linearLayout = new LinearLayout(context);

		linearLayout.setId(1000);

		Button addPlayerButton = new Button(context);
		addPlayerButton.setId(1001);
		addPlayerButton.setBackgroundResource(R.drawable.add_player_button);

		Button settingsButton = new Button(context);
		settingsButton.setId(1002);
		settingsButton.setBackgroundResource(R.drawable.settings_button);

		linearLayout.addView(addPlayerButton);
		linearLayout.addView(settingsButton);

		this.addView(linearLayout);
	}

	public void addDatesAndProjectionsLayout(Context context) {
		LinearLayout linearLayout = new LinearLayout(context);

		linearLayout.setId(2000);
		linearLayout.addView(getAddDatesLayout(context));
		linearLayout.addView(getAddProjectionsLayout(context));

		this.addView(linearLayout);
	}

	public LinearLayout getAddDatesLayout(Context context) {
		LinearLayout linearLayout = new LinearLayout(context);

		linearLayout.setId(3000);

		Button previousDateButton = new Button(context);
		previousDateButton.setId(3001);
		previousDateButton
				.setBackgroundResource(R.drawable.previous_date_button);

		TextView currentDate = new TextView(context);
		currentDate.setId(3002);
		currentDate.setText("4/12");
		currentDate.setTypeface(null, Typeface.BOLD);

		Button nextDateButton = new Button(context);
		nextDateButton.setId(3003);
		nextDateButton.setBackgroundResource(R.drawable.next_date_button);

		linearLayout.addView(previousDateButton);
		linearLayout.addView(currentDate);
		linearLayout.addView(nextDateButton);

		return linearLayout;
	}

	public LinearLayout getAddProjectionsLayout(Context context) {
		LinearLayout linearLayout = new LinearLayout(context);

		linearLayout.setId(4000);

		Button projectPerformanceButton = new Button(context);
		projectPerformanceButton.setId(4001);
		projectPerformanceButton
				.setBackgroundResource(R.drawable.bpp_button_selector);
		projectPerformanceButton.setText("Project\nPerformance");
		projectPerformanceButton.setTextColor(Color.WHITE);

		TextView performanceButtonDivider = new TextView(context);
		performanceButtonDivider.setId(4002);

		Button optimizeLineupButton = new Button(context);
		optimizeLineupButton.setId(4003);
		optimizeLineupButton
				.setBackgroundResource(R.drawable.bpp_button_selector);
		optimizeLineupButton.setText("Optimize\nLineup");
		optimizeLineupButton.setTextColor(Color.WHITE);

		linearLayout.addView(projectPerformanceButton);
		linearLayout.addView(performanceButtonDivider);
		linearLayout.addView(optimizeLineupButton);

		return linearLayout;
	}

	public void setVerticalOrientation() {
		this.setOrientation(LinearLayout.HORIZONTAL);

		// AddPlayer and Settings Buttons
		LinearLayout addAndSettingsLayout = (LinearLayout) this
				.findViewById(1000);
		addAndSettingsLayout.setOrientation(LinearLayout.VERTICAL);
		LinearLayout.LayoutParams addAndSettingsParams = new LinearLayout.LayoutParams(
				0, LayoutParams.MATCH_PARENT, 2);

		Button addPlayerButton = (Button) this.findViewById(1001);
		Button settingsButton = (Button) this.findViewById(1002);
		LinearLayout.LayoutParams addPlayerParams = new LinearLayout.LayoutParams(
				LayoutParams.WRAP_CONTENT, 0, 2);
		addPlayerParams.gravity = Gravity.CENTER_VERTICAL;

		addAndSettingsLayout.setLayoutParams(addAndSettingsParams);
		addPlayerButton.setLayoutParams(addPlayerParams);
		settingsButton.setLayoutParams(addPlayerParams);

		// Dates and Projections Layout
		LinearLayout datesAndProjectionsLayout = (LinearLayout) findViewById(2000);
		datesAndProjectionsLayout.setPadding(4, 0, 4, 4);
		datesAndProjectionsLayout.setOrientation(LinearLayout.VERTICAL);
		LinearLayout.LayoutParams datesAndProjectionsParams = new LinearLayout.LayoutParams(
				0, LayoutParams.MATCH_PARENT, 6);
		datesAndProjectionsLayout.setLayoutParams(datesAndProjectionsParams);

		// Dates Layout
		LinearLayout datesLayout = (LinearLayout) findViewById(3000);
		datesLayout.setOrientation(LinearLayout.HORIZONTAL);
		LinearLayout.LayoutParams datesParams = new LinearLayout.LayoutParams(
				LayoutParams.MATCH_PARENT, 0, 3);
		datesLayout.setLayoutParams(datesParams);

		Button previousDate = (Button) this.findViewById(3001);
		TextView currentDate = (TextView) this.findViewById(3002);
		Button nextDate = (Button) this.findViewById(3003);

		LinearLayout.LayoutParams previousDateParams = new LinearLayout.LayoutParams(
				0, LayoutParams.WRAP_CONTENT, 2);
		previousDateParams.gravity = Gravity.CENTER;
		LinearLayout.LayoutParams currentDateParams = new LinearLayout.LayoutParams(
				0, LayoutParams.WRAP_CONTENT, 1);
		currentDateParams.gravity = Gravity.CENTER;

		previousDate.setLayoutParams(previousDateParams);
		currentDate.setLayoutParams(currentDateParams);
		currentDate.setPadding(0, 0, 0, 5);
		nextDate.setLayoutParams(previousDateParams);

		// Projection Buttons Layout
		LinearLayout projectionButtonsLayout = (LinearLayout) this
				.findViewById(4000);
		projectionButtonsLayout.setOrientation(LinearLayout.HORIZONTAL);
		LinearLayout.LayoutParams projectionButtonsParams = new LinearLayout.LayoutParams(
				LayoutParams.MATCH_PARENT, 0, 3);
		projectionButtonsLayout.setLayoutParams(projectionButtonsParams);

		Button projectPerformanceButton = (Button) findViewById(4001);
		TextView projectionButtonsDivider = (TextView) findViewById(4002);
		Button optimizeLineupButton = (Button) findViewById(4003);

		LinearLayout.LayoutParams projectPerformanceParams = new LinearLayout.LayoutParams(
				0, LayoutParams.WRAP_CONTENT, 10);
		LinearLayout.LayoutParams projectionButtonsDividerParams = new LinearLayout.LayoutParams(
				0, LayoutParams.WRAP_CONTENT, 1);
		projectPerformanceButton.setLayoutParams(projectPerformanceParams);
		projectionButtonsDivider
				.setLayoutParams(projectionButtonsDividerParams);
		optimizeLineupButton.setLayoutParams(projectPerformanceParams);

		maximizeTextSize(currentDate);
	}

	public void setHorizontalOrientation() {
		this.setOrientation(LinearLayout.VERTICAL);

		// AddPlayer and Settings Buttons
		LinearLayout addAndSettingsLayout = (LinearLayout) this
				.findViewById(1000);
		addAndSettingsLayout.setOrientation(LinearLayout.HORIZONTAL);
		LinearLayout.LayoutParams addAndSettingsParams = new LinearLayout.LayoutParams(
				LayoutParams.MATCH_PARENT, 0, 2);

		Button addPlayerButton = (Button) this.findViewById(1001);
		Button settingsButton = (Button) this.findViewById(1002);
		LinearLayout.LayoutParams addPlayerParams = new LinearLayout.LayoutParams(
				0, LayoutParams.WRAP_CONTENT, 2);
		addPlayerParams.gravity = Gravity.CENTER_HORIZONTAL;

		addAndSettingsLayout.setLayoutParams(addAndSettingsParams);
		addPlayerButton.setLayoutParams(addPlayerParams);
		settingsButton.setLayoutParams(addPlayerParams);

		// Dates and Projections Layout
		LinearLayout datesAndProjectionsLayout = (LinearLayout) findViewById(2000);
		datesAndProjectionsLayout.setPadding(4, 0, 4, 4);
		datesAndProjectionsLayout.setOrientation(LinearLayout.VERTICAL);
		LinearLayout.LayoutParams datesAndProjectionsParams = new LinearLayout.LayoutParams(
				LayoutParams.MATCH_PARENT, 0, 6);
		datesAndProjectionsLayout.setLayoutParams(datesAndProjectionsParams);

		// Dates Layout
		LinearLayout datesLayout = (LinearLayout) findViewById(3000);
		datesLayout.setOrientation(LinearLayout.HORIZONTAL);
		LinearLayout.LayoutParams datesParams = new LinearLayout.LayoutParams(
				LayoutParams.MATCH_PARENT, 0, 3);
		datesLayout.setLayoutParams(datesParams);

		Button previousDate = (Button) this.findViewById(3001);
		TextView currentDate = (TextView) this.findViewById(3002);
		Button nextDate = (Button) this.findViewById(3003);

		LinearLayout.LayoutParams previousDateParams = new LinearLayout.LayoutParams(
				0, LayoutParams.WRAP_CONTENT, 1);
		previousDateParams.gravity = Gravity.CENTER;
		LinearLayout.LayoutParams currentDateParams = new LinearLayout.LayoutParams(
				0, LayoutParams.WRAP_CONTENT, 2);
		currentDateParams.gravity = Gravity.CENTER;

		previousDate.setLayoutParams(previousDateParams);
		currentDate.setLayoutParams(currentDateParams);
		currentDate.setGravity(Gravity.CENTER);
		// currentDate.setPadding(0, 0, 0, 5);
		nextDate.setLayoutParams(previousDateParams);

		// Projection Buttons Layout
		LinearLayout projectionButtonsLayout = (LinearLayout) this
				.findViewById(4000);
		projectionButtonsLayout.setOrientation(LinearLayout.VERTICAL);
		LinearLayout.LayoutParams projectionButtonsLayoutParams = new LinearLayout.LayoutParams(
				LayoutParams.MATCH_PARENT, 0, 3);
		projectionButtonsLayout.setLayoutParams(projectionButtonsLayoutParams);
		projectionButtonsLayout.setPadding(5, 5, 5, 5);

		Button projectPerformanceButton = (Button) findViewById(4001);
		TextView projectionButtonsDivider = (TextView) findViewById(4002);
		Button optimizeLineupButton = (Button) findViewById(4003);

		LinearLayout.LayoutParams projectPerformanceParams = new LinearLayout.LayoutParams(
				LayoutParams.MATCH_PARENT, 0, 10);
		LinearLayout.LayoutParams projectionButtonsDividerParams = new LinearLayout.LayoutParams(
				LayoutParams.WRAP_CONTENT, 0, 1);

		projectPerformanceParams.gravity = Gravity.CENTER_HORIZONTAL;

		projectPerformanceButton.setLayoutParams(projectPerformanceParams);
		projectionButtonsDivider
				.setLayoutParams(projectionButtonsDividerParams);
		optimizeLineupButton.setLayoutParams(projectPerformanceParams);

		maximizeTextSize(currentDate);
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

	public void setAddPlayerListener(View.OnClickListener onClickListener) {
		Button addPlayerButton = (Button) this.findViewById(1001);
		addPlayerButton.setOnClickListener(onClickListener);
	}

	public void setAddPlayerClickable(boolean isClickable) {
		Button addPlayerButton = (Button) this.findViewById(1001);
		addPlayerButton.setClickable(isClickable);
	}

	public void setProjectPerformanceListener(
			View.OnClickListener onClickListener) {
		Button projectPerformanceButton = (Button) this.findViewById(4001);
		projectPerformanceButton.setOnClickListener(onClickListener);
	}

	public void setSettingsClickListener(View.OnClickListener onClickListener) {
		Button settingsButton = (Button) this.findViewById(1002);
		settingsButton.setOnClickListener(onClickListener);
	}

	public void setProjectPerformanceClickable(boolean isClickable) {
		Button projectPerformanceButton = (Button) this.findViewById(4001);
		projectPerformanceButton.setClickable(isClickable);
	}

	public void setPreviousDateClickable(boolean isClickable) {
		Button previousDateButton = (Button) this.findViewById(3001);
		previousDateButton.setClickable(isClickable);
	}

	public void setNextDateClickable(boolean isClickable) {
		Button nextDateButton = (Button) this.findViewById(3003);
		nextDateButton.setClickable(isClickable);
	}

	public void setPreviousDateClickListener(View.OnClickListener onClickListener) {
		Button previousDateButton = (Button) this.findViewById(3001);
		previousDateButton.setOnClickListener(onClickListener);
	}

	public void setNextDateClickListener(View.OnClickListener onClickListener) {
		Button nextDateButton = (Button) this.findViewById(3003);
		nextDateButton.setOnClickListener(onClickListener);
	}

	public void setDate(Calendar calendar) {
		int month = calendar.get(Calendar.MONTH);
		int day = calendar.get(Calendar.DAY_OF_MONTH);
		TextView currentDate = (TextView) this.findViewById(3002);

		if (month < 3) {
			month = 2;
			day = 31;
		} else if (month > 8) {
			month = 8;
			day = 30;
		}
		month++;
		currentDate.setText(month + "/" + day);
	}

	public boolean previousDate() {
		TextView currentDate = (TextView) this.findViewById(3002);
		String dateString = currentDate.getText().toString();
		int month = Integer.parseInt(dateString.substring(0,
				dateString.indexOf("/")));
		int day = Integer.parseInt(dateString.substring(
				dateString.indexOf("/") + 1, dateString.length()));
		Calendar calendar = Calendar.getInstance();

		calendar.set(2013, month - 1, day);
		calendar.add(calendar.DAY_OF_YEAR, -1);
		setDate(calendar);
		String changedDateString = currentDate.getText().toString();
		if(dateString.equals(changedDateString)) {
			return false;
		}
		return true;
	}

	// Changes the date.  Returns false if date cannot go further out
	public boolean nextDate() {
		TextView currentDate = (TextView) this.findViewById(3002);
		String dateString = currentDate.getText().toString();
		int month = Integer.parseInt(dateString.substring(0,
				dateString.indexOf("/")));
		int day = Integer.parseInt(dateString.substring(
				dateString.indexOf("/") + 1, dateString.length()));
		Calendar calendar = Calendar.getInstance();
		Calendar systemCalendar = Calendar.getInstance();

		calendar.set(2013, month - 1, day);
		calendar.add(calendar.DAY_OF_YEAR, 1);

		//Limit user to setting the date only 5 days out
		int daysBetween = daysBetween(systemCalendar, calendar);
		if(daysBetween > 5) {
			return false;
		}
		setDate(calendar);
		String changedDateString = currentDate.getText().toString();
		if(dateString.equals(changedDateString)) {
			return false;
		}
		return true;
	}

	public String getDate() {
		TextView currentDate = (TextView) this.findViewById(3002);
		String dateString = currentDate.getText().toString() + "/2013";

		return dateString;
	}

	public int daysBetween(Calendar startDate, Calendar endDate) {
		Calendar minimumDate = Calendar.getInstance();
		Calendar maximumDate = Calendar.getInstance();
		minimumDate.set(2013, 2, 31);
		maximumDate.set(2013, 8, 30);
		
		if(startDate.before(minimumDate)) {
			startDate = minimumDate;
		}

		int daysBetween = 0;
		while (startDate.before(endDate)) {
			startDate.add(Calendar.DAY_OF_MONTH, 1);
			daysBetween++;
		}
		return daysBetween;
	}
}
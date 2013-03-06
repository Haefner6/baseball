package com.bpp;

import android.R.color;
import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class NumberIncrement extends LinearLayout {
	public final int TAB_COLOR = color.background_light;
	public final int SELECTION_COLOR = color.holo_blue_bright;

	public NumberIncrement(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.setPadding(5, 5, 5, 5);

		Button decreaseButton = new Button(context);
		Button increaseButton = new Button(context);
		TextView numberTextView = new TextView(context);

		LinearLayout.LayoutParams buttonParams = new LinearLayout.LayoutParams(0,LayoutParams.MATCH_PARENT, 2);
		buttonParams.setMargins(3, 3, 3, 3);
		
		decreaseButton.setId(1000);
		decreaseButton.setText("-");
		decreaseButton.setGravity(Gravity.CENTER);
		decreaseButton.setLayoutParams(buttonParams);
		decreaseButton.setBackgroundResource(R.color.LightGrey);
		decreaseButton.setPadding(2, 2, 2, 2);

		LinearLayout.LayoutParams numberParams = new LinearLayout.LayoutParams(0, LayoutParams.MATCH_PARENT, 3);
		numberParams.setMargins(3, 3, 3, 3);
		numberTextView.setId(1001);
		numberTextView.setBackgroundResource(R.color.LightGrey);
		numberTextView.setGravity(Gravity.CENTER);
		numberTextView.setText("0");
		numberTextView.setLayoutParams(numberParams);

		increaseButton.setId(1002);
		increaseButton.setText("+");
		increaseButton.setGravity(Gravity.CENTER);
		increaseButton.setLayoutParams(buttonParams);
		increaseButton.setBackgroundResource(R.color.LightGrey);
		increaseButton.setPadding(2, 2, 2, 2);

		decreaseButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				decreaseNumber();
			}
		});

		increaseButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				increaseNumber();
			}
		});
		this.addView(decreaseButton);
		this.addView(numberTextView);
		this.addView(increaseButton);
	}

	public void setNumber(int number) {
		TextView numberTextView = (TextView) this.findViewById(1001);
		numberTextView.setText("" + number);
	}

	public void decreaseNumber() {
		TextView numberTextView = (TextView) this.findViewById(1001);
		int number = Integer.parseInt(numberTextView.getText().toString());
		number--;
		if (number < 0) {
			number = 0;
		}
		numberTextView.setText("" + number);
	}

	public void increaseNumber() {
		TextView numberTextView = (TextView) this.findViewById(1001);
		int number = Integer.parseInt(numberTextView.getText().toString());
		number++;

		numberTextView.setText("" + number);
	}

	public int getNumber() {
		TextView numberTextView = (TextView) this.findViewById(1001);
		int number = Integer.parseInt(numberTextView.getText().toString());
		return number;
	}
}
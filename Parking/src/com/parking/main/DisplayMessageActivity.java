package com.parking.main;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class DisplayMessageActivity extends Activity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// Get the message from the intent
		Intent intent = getIntent();
		String message = intent.getStringExtra(ParkingActivity.EXTRA_MESSAGE);
		String start_message = intent.getStringExtra(ParkingActivity.START_MESSAGE);
		String end_message = intent.getStringExtra(ParkingActivity.END_MESSAGE);

		// Create the text view
		TextView textView = new TextView(this);
		textView.setTextSize(60);
		textView.setText(message + "Start Time : " + start_message +  ": ENDTIME " + end_message);

		setContentView(textView);
	}
}

package com.parking.main;

import java.util.Calendar;

import android.app.Activity;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

public class ParkingActivity extends Activity {

	public final static String EXTRA_MESSAGE = "com.example.myapp.MESSAGE";
	public final static String START_MESSAGE = "com.example.myapp.START";
	public final static String END_MESSAGE = "com.example.myapp.END";

	private TextView tvDisplayTimeStart;
	private TimePicker timePicketStart;
	
	private TextView tvDisplayTimeEnd;
	private TimePicker timePicketEnd;
	private Button btnChangeTime;

	private int hour;
	private int minute;

	static final int TIME_DIALOG_ID = 999;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		setCurrentTimeOnView();
	}

	/** Called when the user selects the Send button */
	public void sendMessage(View view) {
		// Do something in response to button
		Intent intent = new Intent(this, DisplayMessageActivity.class);
		EditText editText = (EditText) findViewById(R.id.edit_message);
		String message = editText.getText().toString();
		
		TimePicker start = (TimePicker) findViewById(R.id.timePicketStart);
		TimePicker end = (TimePicker) findViewById(R.id.timePickerEnd);
		
		intent.putExtra(EXTRA_MESSAGE, message);
		intent.putExtra(START_MESSAGE, start.getCurrentHour() + ":" + start.getCurrentMinute());
		intent.putExtra(END_MESSAGE, end.getCurrentHour() + ":" + end.getCurrentMinute());
		startActivity(intent);
	}
	
	// display current time
		public void setCurrentTimeOnView() {

			timePicketStart = (TimePicker) findViewById(R.id.timePicketStart);
			timePicketEnd = (TimePicker) findViewById(R.id.timePickerEnd);

			final Calendar c = Calendar.getInstance();
			hour = c.get(Calendar.HOUR_OF_DAY);
			minute = c.get(Calendar.MINUTE);
	
			// set current time into timepicker Start
			timePicketStart.setCurrentHour(hour);
			timePicketStart.setCurrentMinute(minute);
			
			// Set current time + 2 into timepicker End
			timePicketStart.setCurrentHour(hour + 1); // lame :(
			timePicketStart.setCurrentMinute(minute);

		}

		private static String pad(int c) {
			if (c >= 10)
				return String.valueOf(c);
			else
				return "0" + String.valueOf(c);
		}
}
package com.parking.main;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.parking.main.model.ParkingLotInformation;
import com.parking.main.utils.ParkingLotUtil;

public class ParkingLotInfoActivity extends Activity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.parking_info_lt);
		
		Intent myIntend = getIntent();
		Bundle extras = myIntend.getExtras(); 
		
		int pid = 0;
		if (extras != null && extras.containsKey("pid")){
			pid = extras.getInt("pid");
			Log.v("ParkingLotInfoActivity->pid=",pid+"");
		}
		
		ParkingLotUtil plu = new ParkingLotUtil();
		
		ParkingLotInformation pl = plu.getParkingLotInfoById(pid);
			
		TextView nameTitleView = (TextView) findViewById (R.id.nameTitle);
		nameTitleView.setBackgroundColor(Color.YELLOW);
		nameTitleView.setTextColor(Color.BLACK);
		nameTitleView.setText(pl.getName());
		
		
		TextView addressRow = (TextView) findViewById(R.id.address);
		addressRow.setText(pl.getAddress());
		addressRow.setTextColor(Color.BLACK);
		
		TextView spots = (TextView) findViewById(R.id.spots);
		spots.setText("#Spots Available: " + pl.getTotalSpots());
		spots.setTextColor(Color.BLACK);
		
		TextView rate = (TextView) findViewById(R.id.rate);
		rate.setText("$" + pl.getRate().toString() + " /- Per Hour"); 
		rate.setTextColor(Color.RED);
		
		
		
	}
	
}

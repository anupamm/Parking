package com.parking.main;

import java.util.List;

import com.parking.main.model.ParkingLotInformation;
import com.parking.main.utils.ParkingLotUtil;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TableRow;
import android.widget.TextView;

public class ParkingLotInfoActivity extends Activity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.parking_info_display_layout);
		
		ParkingLotUtil plu = new ParkingLotUtil();
		
		ParkingLotInformation pl = plu.getParkingLotInfoById(0);
			
		TableRow nameTitleView = (TableRow) findViewById (R.id.nameTitle);
		nameTitleView.setBackgroundColor(Color.YELLOW);
		
		TextView textView = new TextView(this);
		textView.setTextSize(20);
		textView.setTextColor(Color.BLACK);
		textView.setText(pl.getName());
		nameTitleView.addView(textView);
		
		TableRow addressRow = (TableRow) findViewById(R.id.address);
		addressRow.setBackgroundColor(Color.WHITE);
		
		TextView textView1 = new TextView(this);
		textView.setTextSize(20);
		textView.setTextColor(Color.BLACK);
		textView1.setText(pl.getAddress());
		addressRow.addView(textView1);
			
		
	}
	
}

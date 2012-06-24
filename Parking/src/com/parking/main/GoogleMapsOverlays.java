package com.parking.main;

import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.View.OnClickListener;
import android.widget.Toast;

import com.google.android.maps.ItemizedOverlay;
import com.google.android.maps.OverlayItem;

public class GoogleMapsOverlays extends ItemizedOverlay<OverlayItem> {

	private ArrayList<OverlayItem> mOverlays = new ArrayList<OverlayItem>();
	private Context mContext;
	private int clickedIndex;

	public GoogleMapsOverlays(Drawable defaultMarker, Context context) {
		super(boundCenterBottom(defaultMarker));
		mContext = context;
		clickedIndex = 0;
	}

	public void addOverlay(OverlayItem overlay) {
		mOverlays.add(overlay);
		populate();
	}

	@Override
	protected OverlayItem createItem(int i) {
		return mOverlays.get(i);
	}

	@Override
	public int size() {
		return mOverlays.size();
	}

	public int getLastClicked() {
		return clickedIndex;
	}

	@Override
	protected boolean onTap(int index) {
		OverlayItem item = mOverlays.get(index);
		
		/*
		AlertDialog.Builder dialog = new AlertDialog.Builder(mContext);
		dialog.setTitle(item.getTitle());
		dialog.setMessage(item.getSnippet());
		//dialog.setPositiveButton("OK", new OkOnClickListener());
		dialog.show();
		clickedIndex = index;		
		*/
		
		mContext.startActivity(new Intent(mContext,ParkingLotInfoActivity.class));
		
		return true;
	}

	private final class OkOnClickListener extends Activity implements DialogInterface.OnClickListener {
		public void onClick(DialogInterface dialog, int which) {
			Toast.makeText(mContext, "You clicked ok", Toast.LENGTH_LONG).show();
			Log.v("GoogleMapOverlays", "Buy button clicked!");
			Log.v("GoogleMapOverlays", (mContext==null)? "Context is null":"Context is not null");
			Intent pk1 = new Intent(mContext, ParkingLotInfoActivity.class);
			Log.v("GoogleMapOverlays", "Intent created!");
			
			pk1.putExtra("ParkingLotID", "Testing");
			
			Log.v("GoogleMapOverlays", "Test data set!");
			startActivity(pk1);
			Log.v("GoogleMapOverlays", "Fired activity!");
		}
	}
}
package com.parking.main;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;

public class GoogleMapActivity extends MapActivity implements OnClickListener {

	private GoogleMapsOverlays itemizedoverlay;
	private GoogleMapsOverlays currentlocation;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.googlemapslayout);
		MapView mapView = (MapView) findViewById(R.id.mapview);
		mapView.setBuiltInZoomControls(true);

		List<Overlay> mapOverlays = mapView.getOverlays();
		Drawable drawable = this.getResources().getDrawable(R.drawable.red_marker_small);
		itemizedoverlay = new GoogleMapsOverlays(drawable, this);
		
		GeoPoint point = new GeoPoint(30443769, -91158458);
		GeoPoint point2 = new GeoPoint(17385812, 78480667);
		//setOverlays(point,"Laissez les bon temps rouler!", "I'm in Louisiana!");		

		Drawable drawablelocation = this.getResources().getDrawable(R.drawable.blue_dot_circle);
		currentlocation = new GoogleMapsOverlays(drawablelocation, this);
		GeoPoint point3 = new GeoPoint(17385812, 78480667);
		setDestination(point3, "mylocation", "check it!");
		
		List<GeoPoint> PointsList = new ArrayList<GeoPoint>();
		PointsList.add(point);
		//PointsList.add(point2);
		PointsList.add(point3);
		
		setParkinglots(PointsList);

		mapOverlays.add(currentlocation);
		mapOverlays.add(itemizedoverlay);
		
		findViewById(R.id.editinfobutton).setOnClickListener(this);
	}

	private void setDestination(GeoPoint myPoint, String sDialog, String sMessage) {
		currentlocation.addOverlay(new OverlayItem(myPoint, sDialog, sMessage));
	}
	
	public void setParkinglots(List<GeoPoint> PointsList){
		if(PointsList != null){
			Iterator<GeoPoint> it = PointsList.iterator();
			while(it.hasNext()){
				setOverlays(it.next(),"Test","test");
			}
		}
	}

	private void setOverlays(GeoPoint myPoint, String sDialog, String sMessage) {
		itemizedoverlay.addOverlay(new OverlayItem(myPoint, sDialog, sMessage));
	}

	@Override
	protected boolean isRouteDisplayed() {
		return false;
	}

	@Override
	public void onClick(View v) {
		int id = v.getId();
		switch (id) {			
		case R.id.editinfobutton:
			Intent editInfoIntent = new Intent(this, ParkingActivity.class);
			startActivityForResult(editInfoIntent,Constants.EDIT_MY_ADDR_INFO);
			break;

		default:
			break;
		}
		
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {		
		switch (requestCode) {
		case Constants.EDIT_MY_ADDR_INFO:
			Bundle extras = (data != null)? data.getExtras() : null;
			Log.v("File output file","File was here");
			if (extras != null && extras.containsKey("Testing")){
				Log.v("Testing",extras.getString("Testing"));
			}
			break;
		default:
			Log.v("Button click","Default");
			break;
		}
	}
}
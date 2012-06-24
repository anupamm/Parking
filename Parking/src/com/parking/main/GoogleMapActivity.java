package com.parking.main;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;

public class GoogleMapActivity extends MapActivity implements OnClickListener,
		LocationListener {

	private GoogleMapsOverlays itemizedoverlay;
	private GoogleMapsOverlays currentlocation;
	private LocationManager service;
	private String provider;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.googlemapslayout);
		MapView mapView = (MapView) findViewById(R.id.mapview);
		mapView.setBuiltInZoomControls(true);

		List<Overlay> mapOverlays = mapView.getOverlays();
		Drawable drawable = this.getResources().getDrawable(
				R.drawable.red_marker_small);
		itemizedoverlay = new GoogleMapsOverlays(drawable, this);

		GeoPoint point = new GeoPoint(30443769, -91158458);
		GeoPoint point2 = new GeoPoint(17385812, 78480667);
		// setOverlays(point,"Laissez les bon temps rouler!",
		// "I'm in Louisiana!");

		service = (LocationManager) getSystemService(LOCATION_SERVICE);
		boolean enabled = service
				.isProviderEnabled(LocationManager.GPS_PROVIDER);

		if (!enabled) {
			Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
			startActivity(intent);
		}

		// Define the criteria how to select the locatioin provider -> use
		// default
		Criteria criteria = new Criteria();
		provider = service.getBestProvider(criteria, false);
		Location location = service.getLastKnownLocation(provider);

		int lat = (int) (location.getLatitude() * 1E6);
		int lng = (int) (location.getLongitude() * 1E6);

		Drawable drawablelocation = this.getResources().getDrawable(
				R.drawable.blue_dot_circle);
		currentlocation = new GoogleMapsOverlays(drawablelocation, this);
		GeoPoint point3 = new GeoPoint(lng, lat);
		setDestination(point3, "mylocation", point3.getLongitudeE6() + " x "
				+ point3.getLatitudeE6());

		List<GeoPoint> PointsList = new ArrayList<GeoPoint>();
		PointsList.add(point);
		PointsList.add(point2);

		setParkinglots(PointsList);

		mapOverlays.add(currentlocation);
		mapOverlays.add(itemizedoverlay);

		findViewById(R.id.editinfobutton).setOnClickListener(this);
	}

	private void setDestination(GeoPoint myPoint, String sDialog,
			String sMessage) {
		currentlocation.addOverlay(new OverlayItem(myPoint, sDialog, sMessage));
	}

	public void setParkinglots(List<GeoPoint> PointsList) {
		if (PointsList != null) {
			Iterator<GeoPoint> it = PointsList.iterator();
			GeoPoint myPoint;
			while (it.hasNext()) {
				myPoint = it.next();
				setOverlays(myPoint, "Test", myPoint.getLongitudeE6() + " x "
						+ myPoint.getLatitudeE6());
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
			startActivityForResult(editInfoIntent, Constants.EDIT_MY_ADDR_INFO);
			break;

		default:
			break;
		}

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (requestCode) {
		case Constants.EDIT_MY_ADDR_INFO:
			Bundle extras = (data != null) ? data.getExtras() : null;
			Log.v("File output file", "File was here");
			if (extras != null && extras.containsKey("Testing")) {
				Log.v("Testing", extras.getString("Testing"));
			}
			break;
		default:
			Log.v("Button click", "Default");
			break;
		}
	}

	/* Request updates at startup */
	@Override
	protected void onResume() {
		super.onResume();
		service.requestLocationUpdates(provider, 400, 1, this);
	}

	/* Remove the locationlistener updates when Activity is paused */
	@Override
	protected void onPause() {
		super.onPause();
		service.removeUpdates(this);
	}

	@Override
	public void onLocationChanged(Location location) {
		int lat = (int) (location.getLatitude());
		int lng = (int) (location.getLongitude());
		// TODO Do something with changed location 
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onProviderEnabled(String provider) {
		Toast.makeText(this, "Enabled new provider " + provider,
				Toast.LENGTH_SHORT).show();

	}

	@Override
	public void onProviderDisabled(String provider) {
		Toast.makeText(this, "Disabled provider " + provider,
				Toast.LENGTH_SHORT).show();
	}
}
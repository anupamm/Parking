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
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.MyLocationOverlay;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;

public class GoogleMapActivity extends MapActivity implements OnClickListener,
		LocationListener {

	private GoogleMapsOverlays itemizedoverlay;
	private GoogleMapsOverlays currentlocation;
	private MapController mapController;
	private LocationManager service;
	private String provider;
	private List<Overlay> mapOverlays;
	private MyLocationOverlay myLocationOverlay;
	private boolean DisableGPS = false;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.googlemapslayout);
		MapView mapView = (MapView) findViewById(R.id.mapview);
		
		mapController = mapView.getController();
		
		mapOverlays = mapView.getOverlays();
		Drawable drawable = this.getResources().getDrawable(
				R.drawable.red_marker_small);
		itemizedoverlay = new GoogleMapsOverlays(drawable, this);

		myLocationOverlay = new MyLocationOverlay(this, mapView);
		mapView.getOverlays().add(myLocationOverlay);

		myLocationOverlay.runOnFirstFix(new Runnable() {
			public void run() {
				((MapView)findViewById(R.id.mapview)).getController().animateTo(
						myLocationOverlay.getMyLocation());
			}
		});

		service = (LocationManager) getSystemService(LOCATION_SERVICE);
		boolean enabled = service
				.isProviderEnabled(LocationManager.GPS_PROVIDER);

		if (!enabled) {
			Log.v("GoogleMapActivity", "GPS Not enabled!");
			Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
			Log.v("GoogleMapActivity", "Firing GPS intent!");
			startActivity(intent);
			Log.v("GoogleMapActivity", "Should be enabled by now!");
		} else
			Log.v("GoogleMapActivity", "Already enabled!");

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
		GeoPoint mockLocation = new GeoPoint(47622220, -122362638);
		setDestination(
				mockLocation,
				"myLocation",
				mockLocation.getLongitudeE6() + " x "
						+ mockLocation.getLatitudeE6());

		GeoPoint parkingGarage1 = new GeoPoint(47618277, -122357779);
		GeoPoint parkingGarage2 = new GeoPoint(47608603, -122341127);
		GeoPoint parkingGarage3 = new GeoPoint(47610124, -122334721);

		List<GeoPoint> PointsList = new ArrayList<GeoPoint>();
		PointsList.add(parkingGarage1);
		PointsList.add(parkingGarage2);
		PointsList.add(parkingGarage3);

		setParkinglots(PointsList);

		//mapOverlays.add(currentlocation);
		mapOverlays.add(itemizedoverlay);

		findViewById(R.id.editinfobutton).setOnClickListener(this);
		findViewById(R.id.mapview).setOnClickListener(this);
		
		mapView.setBuiltInZoomControls(true);
		
		cleanZoom(PointsList);
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
	
	private void cleanZoom(List<GeoPoint> items){
		int minLat = Integer.MAX_VALUE;
		int maxLat = Integer.MIN_VALUE;
		int minLon = Integer.MAX_VALUE;
		int maxLon = Integer.MIN_VALUE;

		for (GeoPoint item : items) 
		{ 

		      int lat = item.getLatitudeE6();
		      int lon = item.getLongitudeE6();

		      maxLat = Math.max(lat, maxLat);
		      minLat = Math.min(lat, minLat);
		      maxLon = Math.max(lon, maxLon);
		      minLon = Math.min(lon, minLon);
		 }
		double fitFactor = 1.5;
		mapController.zoomToSpan((int) (Math.abs(maxLat - minLat) * fitFactor), (int)(Math.abs(maxLon - minLon) * fitFactor));
		mapController.animateTo(new GeoPoint( (maxLat + minLat)/2, 
		(maxLon + minLon)/2 )); 
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
			Log.v("GoogleMapActivity", "Edit button clicked!");
			Intent editInfoIntent = new Intent(this, ParkingActivity.class);
			startActivityForResult(editInfoIntent, Constants.EDIT_MY_ADDR_INFO);
			break;

		case R.id.mapview:
			Log.v("GoogleMapActivity", "Buy button clicked!");
			Intent ParkingLotActivityIntent1 = new Intent(this,
					ParkingLotInfoActivity.class);
			ParkingLotActivityIntent1.putExtra("ParkingLotID", "Testing");
			startActivityForResult(ParkingLotActivityIntent1,
					Constants.VIEW_PARKINGLOT_DATA);
		default:
			break;
		}

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (requestCode) {
		case Constants.EDIT_MY_ADDR_INFO:
			
			
			Bundle extras = (data != null) ? data.getExtras() : null;
			Log.v("GoogleMapActivity", "Edit button clicked!");
			if (extras != null && extras.containsKey(ParkingActivity.EXTRA_MESSAGE)) {
				Log.v("Address", extras.getString(ParkingActivity.EXTRA_MESSAGE));
				//Disable GPS
				DisableGPS = true;
			}
			if (extras != null && extras.containsKey(ParkingActivity.START_MESSAGE)) {
				Log.v("Start Time", extras.getString(ParkingActivity.START_MESSAGE));
				Log.v("End Time", extras.getString(ParkingActivity.END_MESSAGE));
				DisableGPS = false;
			}
			
			break;
		default:
			Log.v("GoogleMapActivity", "Result default break");
			break;
		}
	}

	/* Request updates at startup */
	@Override
	protected void onResume() {
		super.onResume();
		if(!DisableGPS){
			service.requestLocationUpdates(provider, 400, 1, this);
			myLocationOverlay.enableMyLocation();
		}
		else{
			myLocationOverlay.disableMyLocation();
			service.removeUpdates(this);
		}
	}

	/* Remove the locationlistener updates when Activity is paused */
	@Override
	protected void onPause() {
		super.onPause();
		myLocationOverlay.disableMyLocation();
		service.removeUpdates(this);
		
	}

	@Override
	public void onLocationChanged(Location location) {
		int lat = (int) (location.getLatitude() * 1E6);
		int lng = (int) (location.getLongitude() * 1E6);
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
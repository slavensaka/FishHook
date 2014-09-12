package com.fish.googlemaps;

import android.app.Dialog;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.fish.net.R;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMapLongClickListener;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class SveLokacijeActivity extends FragmentActivity implements
		LoaderCallbacks<Cursor> {
	GoogleMap googleMap;
	String text;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_maps_tvoja);
		Toast.makeText(getBaseContext(), "Kliknite na marker za detalje",
				Toast.LENGTH_SHORT).show();
		int status = GooglePlayServicesUtil
				.isGooglePlayServicesAvailable(getBaseContext());
		if (status != ConnectionResult.SUCCESS) {
			int requestCode = 10;
			Dialog dialog = GooglePlayServicesUtil.getErrorDialog(status, this,
					requestCode);
			dialog.show();
		} else {
			SupportMapFragment fm = (SupportMapFragment) getSupportFragmentManager()
					.findFragmentById(R.id.map);
			googleMap = fm.getMap();

			getSupportLoaderManager().initLoader(0, null, this);
		}
	
		// googleMap.setOnMapClickListener(new OnMapClickListener() {
		//
		// @Override
		// public void onMapClick(LatLng point) {
		//
		// // String text = showInputDialog();
		// // InsertMarker(point);
		// }
		// });
		
		googleMap.setOnMapLongClickListener(new OnMapLongClickListener() {
			@Override
			public void onMapLongClick(LatLng point) {
				googleMap.clear();
				LocationDeleteTask deleteTask = new LocationDeleteTask();
				deleteTask.execute();
				// LocationDelete delete = new LocationDelete();
				Toast.makeText(getBaseContext(), "Svi markeri su izbrisani",
						Toast.LENGTH_LONG).show();
			}
		}); 
		
		Button normala = (Button) findViewById(R.id.normala);
		Button hybrid = (Button) findViewById(R.id.hybrid);
		
		normala.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				googleMap.setMapType(googleMap.MAP_TYPE_NORMAL);
			}
		});

		hybrid.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				googleMap.setMapType(googleMap.MAP_TYPE_HYBRID);

			}
		});
		googleMap.setOnMarkerClickListener(new OnMarkerClickListener() {

			@Override
			public boolean onMarkerClick(Marker marker) {
				LatLng latlng = marker.getPosition();
				double longitude = latlng.longitude;
				double latitude = latlng.latitude;
				String s = marker.toString();
				return false;
			}
		});
	}

	// public void InsertMarker(LatLng point) {
	// drawMarker(point);
	// ContentValues contentValues = new ContentValues();
	// contentValues.put(LocationsDB.FIELD_LAT, point.latitude);
	// contentValues.put(LocationsDB.FIELD_LNG, point.longitude);
	// contentValues.put(LocationsDB.FIELD_ZOOM,
	// googleMap.getCameraPosition().zoom);
	// contentValues.put(LocationsDB.FIELD_TITLE, text);
	// LocationInsertTask insertTask = new LocationInsertTask();
	// insertTask.execute(contentValues);
	// Toast.makeText(getBaseContext(), "Marker is added to the Map",
	// Toast.LENGTH_SHORT).show();
	// }

	private void drawMarker(LatLng point, String title) {
		MarkerOptions markerOptions = new MarkerOptions();
		markerOptions.position(point);
		// markerOptions.title(title);
		markerOptions.anchor(0.5f, 1);
		markerOptions.title(title);
		markerOptions.snippet(" Lat: " + (int) point.latitude + "," + " Lng: "
				+ (int) point.longitude);
		markerOptions.icon(BitmapDescriptorFactory
				.fromResource(R.drawable.marker));
		googleMap.addMarker(markerOptions);
	}

	private class LocationDeleteTask extends AsyncTask<Void, Void, Void> {
		@Override
		protected Void doInBackground(Void... params) {
			getContentResolver().delete(LocationsContentProvider.CONTENT_URI,
					null, null);
			return null;
		}
	}

	@Override
	public Loader<Cursor> onCreateLoader(int arg0, Bundle arg1) {
		Uri uri = LocationsContentProvider.CONTENT_URI;
		return new CursorLoader(this, uri, null, null, null, null);
	}

	@Override
	public void onLoadFinished(Loader<Cursor> arg0, Cursor arg1) {
		int locationCount = 0;
		double lat = 0;
		double lng = 0;
		float zoom = 0;
		String title = "";
		locationCount = arg1.getCount();
		arg1.moveToFirst();
		for (int i = 0; i < locationCount; i++) {
			lat = arg1.getDouble(arg1.getColumnIndex(LocationsDB.FIELD_LAT));
			lng = arg1.getDouble(arg1.getColumnIndex(LocationsDB.FIELD_LNG));
			zoom = arg1.getFloat(arg1.getColumnIndex(LocationsDB.FIELD_ZOOM));
			title = arg1.getString(arg1.getColumnIndex(LocationsDB.FIELD_TITLE));
			LatLng location = new LatLng(lat, lng);
			drawMarker(location, title);// TU
			arg1.moveToNext();
		}
	}

	@Override
	public void onLoaderReset(Loader<Cursor> arg0) {

	}
}
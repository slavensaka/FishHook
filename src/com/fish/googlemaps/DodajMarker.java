package com.fish.googlemaps;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ContentValues;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.fish.net.R;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMapClickListener;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class DodajMarker extends FragmentActivity {
	GoogleMap googleMap;
	AlertDialog alert;
	String title;
	MarkerOptions markerOptions;
	Button b1;
	double longitude;
	double latitude;
	Button normala, hybrid;
	String mark;
	EditText et;
	Button b2;

	@SuppressWarnings("static-access")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_maps);
		b2 = (Button) findViewById(R.id.tvoja_lokacija);
		b2.setVisibility(View.INVISIBLE);
		int status = GooglePlayServicesUtil
				.isGooglePlayServicesAvailable(getBaseContext());
		if (status != ConnectionResult.SUCCESS) {
			int requestCode = 10;
			Dialog dialog = GooglePlayServicesUtil.getErrorDialog(status, this,
					requestCode);
			dialog.show();
		} else {
			SupportMapFragment supportMapFragment = (SupportMapFragment) getSupportFragmentManager()
					.findFragmentById(R.id.map);
			googleMap = supportMapFragment.getMap();
			googleMap.setMapType(googleMap.MAP_TYPE_HYBRID);
			googleMap.setMyLocationEnabled(true);
		}

		googleMap.setOnMapClickListener(new OnMapClickListener() {

			@Override
			public void onMapClick(LatLng point) {
				mark = showInputDialog();
				if (mark.toString().equals("")) {

					googleMap.clear();
					Toast.makeText(getBaseContext(),
							"Unesite prvo ime markera.", Toast.LENGTH_SHORT)
							.show();
				} else {
					b2.setVisibility(View.VISIBLE);
					Toast.makeText(getBaseContext(),
							"Marker se može i vuæi po mapi", Toast.LENGTH_SHORT)
							.show();
					markerOptions = new MarkerOptions();
					markerOptions.position(point);
					markerOptions.draggable(true);
					int lat = (int) point.latitude;
					int lng = (int) point.longitude;

					markerOptions.title("Lat: " + lat + " : " + "Lng: " + lng);
					markerOptions.icon(BitmapDescriptorFactory
							.fromResource(R.drawable.marker));

					googleMap.clear();
					googleMap.animateCamera(CameraUpdateFactory
							.newLatLng(point));
					googleMap.addMarker(markerOptions).showInfoWindow();
					longitude = point.longitude;
					latitude = point.latitude;
					title = mark;

				}
			}
		});
		googleMap.setOnMarkerClickListener(new OnMarkerClickListener() {
			@Override
			public boolean onMarkerClick(Marker marker) {
				return false;
			}
		});

		normala = (Button) findViewById(R.id.normala);
		hybrid = (Button) findViewById(R.id.hybrid);
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
	}

	public void Listen(View v) {
		b2.setVisibility(View.INVISIBLE);
		ContentValues contentValues = new ContentValues();
		contentValues.put(LocationsDB.FIELD_LAT, latitude);
		contentValues.put(LocationsDB.FIELD_LNG, longitude);
		contentValues.put(LocationsDB.FIELD_ZOOM,
				googleMap.getCameraPosition().zoom);
		contentValues.put(LocationsDB.FIELD_TITLE, title);
		LocationInsertTask insertTask = new LocationInsertTask();
		insertTask.execute(contentValues);
		Toast.makeText(getBaseContext(),
				"Marker je dodan na mapu. Možete unijeti novi.",
				Toast.LENGTH_SHORT).show();
		googleMap.clear();
		et.getText().clear();
	}

	public String showInputDialog() {
		et = (EditText) findViewById(R.id.mark);
		String mark = et.getText().toString();
		return mark;
	}

	private class LocationInsertTask extends
			AsyncTask<ContentValues, Void, Void> {
		@Override
		protected Void doInBackground(ContentValues... contentValues) {
			getContentResolver().insert(LocationsContentProvider.CONTENT_URI,
					contentValues[0]);
			return null;
		}
	}
}
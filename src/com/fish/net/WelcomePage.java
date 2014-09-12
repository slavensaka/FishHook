package com.fish.net;

import java.io.File;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;

import com.fish.camera.CameraActivity;
import com.fish.googlemaps.MapsActivity;
import com.fish.izgubljeno.IzgubljenoActivity;
import com.fish.oprema.activities.OpremaActivity;
import com.fish.riba.RibaActivity;
import com.fish.weather.gplaces.AutoCompActivity;

public class WelcomePage extends Activity {

	File folder;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		String extStorageDirectory = Environment.getExternalStorageDirectory()
				.toString();
		folder = new File(extStorageDirectory, "/Android/data/"
				+ getPackageName());
		if (!folder.exists()) {
			folder.mkdir();
		}

	}

	public void GoogleMapa(View v) {

		Intent mapaintent = new Intent(this, MapsActivity.class);
		startActivity(mapaintent);

	}

	public void CameraActivity(View v) {

		Intent cameraintent = new Intent(this, CameraActivity.class);
		startActivity(cameraintent);

	}

	public void WeatherActivity(View v) {

		Intent weatherintent = new Intent(this, AutoCompActivity.class);
		startActivity(weatherintent);

	}

	public void OpremaActivity(View v) {

		Intent opremaintent = new Intent(this, OpremaActivity.class);
		startActivity(opremaintent);
	}

	public void RibaActivity(View v) {

		Intent ribaintent = new Intent(this, RibaActivity.class);
		startActivity(ribaintent);
	}

	public void IzgubljenoActivity(View v) {

		Intent izgubljenaintent = new Intent(this, IzgubljenoActivity.class);
		startActivity(izgubljenaintent);
	}

	@Override
	public void onBackPressed() {
		finish();
	}
}

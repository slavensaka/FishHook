package com.fish.googlemaps;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.fish.net.R;
import com.google.android.gms.maps.GoogleMap;

public class MapsActivity extends Activity {
	GoogleMap googleMap;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_maps_pocetni);
	}
	
	public void DodajMarker(View v) {
		Intent dodajmarkerintent = new Intent(this, DodajMarker.class);
		startActivity(dodajmarkerintent);
	}	
	
	public void SveLokacije(View v) {
		Intent svelokacijeintent = new Intent(this, SveLokacijeActivity.class);
		startActivity(svelokacijeintent);
	}
}
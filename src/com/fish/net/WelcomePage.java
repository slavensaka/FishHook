package com.fish.net;

import com.fish.googlemaps.MapsActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class WelcomePage extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);
    }
    
    public void GoogleMapa(View v) {

		Intent mapaintent = new Intent(this, MapsActivity.class);
		startActivity(mapaintent);

	}
}

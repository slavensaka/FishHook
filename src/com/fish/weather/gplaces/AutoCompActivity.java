package com.fish.weather.gplaces;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;

import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.SimpleAdapter;

import com.fish.net.R;
import com.fish.weather.WeatherActivity;

public class AutoCompActivity extends Activity {
	AutoCompleteTextView atvPlaces;
	PlacesTask placesTask;
	ParserTask parserTask;
	Button button;
	Button button1;
	String loka;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_weather1);

		atvPlaces = (AutoCompleteTextView) findViewById(R.id.gplaces);
		atvPlaces.setThreshold(1);
		atvPlaces.addTextChangedListener(new TextWatcher() {

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				placesTask = new PlacesTask();
				placesTask.execute(s.toString());
			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {

			}

			@Override
			public void afterTextChanged(Editable s) {
				placesTask = new PlacesTask();
				placesTask.execute(s.toString());
				loka = s.toString();

			}
		});
	}

	public void UnosLokacije(View v) {
		Intent unosintent = new Intent(this, WeatherActivity.class);
		unosintent.putExtra("lokacije", loka);
		startActivity(unosintent);
	}

	private String downloadUrl(String strUrl) throws IOException {
		String data = "";
		InputStream iStream = null;
		HttpURLConnection urlConnection = null;
		try {
			URL url = new URL(strUrl);
			// Creating an http connection to communicate with url
			urlConnection = (HttpURLConnection) url.openConnection();
			// Connecting to url
			urlConnection.connect();
			// Reading data from url
			iStream = urlConnection.getInputStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(
					iStream));
			StringBuffer sb = new StringBuffer();
			String line = "";
			while ((line = br.readLine()) != null) {
				sb.append(line);
			}
			data = sb.toString();
			br.close();
		} catch (Exception e) {
			Log.d("Exception while downloading url", e.toString());
		} finally {
			iStream.close();
			urlConnection.disconnect();
		}
		return data;
	}

	// Fetches all places from GooglePlaces AutoComplete Web Service
	private class PlacesTask extends AsyncTask<String, Void, String> {

		@Override
		protected String doInBackground(String... place) {
			// For storing data from web service
			String data = "";
			// Obtain browser key from https://code.google.com/apis/console
			String key = "key=AIzaSyBze0u95QQdhEAs2TZ5Ng6kkXNCdNCdj-k";
			String input = "";
			try {
				input = "input=" + URLEncoder.encode(place[0], "utf-8");
			} catch (UnsupportedEncodingException e1) {
				e1.printStackTrace();
			}
			// place type to be searched
			String types = "types=geocode";
			// Sensor enabled
			String sensor = "sensor=true";
			// Building the parameters to the web service
			String parameters = input + "&" + types + "&" + sensor + "&" + key;
			// Output format
			String output = "json";
			// Building the url to the web service
			String url = "https://maps.googleapis.com/maps/api/place/autocomplete/"
					+ output + "?" + parameters;
			try {
				// Fetching the data from web service in background
				data = downloadUrl(url);
			} catch (Exception e) {
				Log.d("Background Task", e.toString());
			}
			return data;
		}

		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			// Creating ParserTask
			parserTask = new ParserTask();
			// Starting Parsing the JSON string returned by Web Service
			parserTask.execute(result);
		}
	}

	/** A class to parse the Google Places in JSON format */
	private class ParserTask extends
			AsyncTask<String, Integer, List<HashMap<String, String>>> {
		JSONObject jObject;

		@Override
		protected List<HashMap<String, String>> doInBackground(
				String... jsonData) {
			List<HashMap<String, String>> places = null;
			PlaceJSONParser placeJsonParser = new PlaceJSONParser();
			try {
				jObject = new JSONObject(jsonData[0]);
				// Getting the parsed data as a List construct
				places = placeJsonParser.parse(jObject);
			} catch (Exception e) {
				Log.d("Exception", e.toString());
			}
			return places;
		}

		@Override
		protected void onPostExecute(List<HashMap<String, String>> result) {
			String[] from = new String[] { "description" };
			int[] to = new int[] { android.R.id.text1 };
			// Creating a SimpleAdapter for the AutoCompleteTextView
			SimpleAdapter adapter = new SimpleAdapter(getBaseContext(), result,
					android.R.layout.simple_list_item_1, from, to);
			// Setting the adapter
			atvPlaces.setAdapter(adapter);
		}
	}

}

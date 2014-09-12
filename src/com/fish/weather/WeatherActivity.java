package com.fish.weather;

import org.json.JSONException;

import android.app.Activity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.fish.net.R;
import com.fish.weather.model.Weather;

public class WeatherActivity extends Activity {

	private TextView cityText;
	private TextView condDescr;
	private TextView temp;
	private TextView press;
	private TextView windSpeed;
	private TextView windDeg;

	private TextView hum;
	private ImageView imgView;
	String message = "";

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_weather);

		cityText = (TextView) findViewById(R.id.cityText);
		condDescr = (TextView) findViewById(R.id.condDescr);
		temp = (TextView) findViewById(R.id.temp);
		hum = (TextView) findViewById(R.id.hum);
		press = (TextView) findViewById(R.id.press);
		windSpeed = (TextView) findViewById(R.id.windSpeed);
		windDeg = (TextView) findViewById(R.id.windDeg);
		imgView = (ImageView) findViewById(R.id.condIcon);

		if (getIntent().hasExtra("lokacije")) {
			message = getIntent().getStringExtra("lokacije");

		} else {

		}

		// Bundle bundle = getIntent().getExtras();
		// String gPlacesLokacije = bundle.getString("locakija");

		JSONWeatherTask task = new JSONWeatherTask();
		task.execute(message);
	}

	private class JSONWeatherTask extends AsyncTask<String, Void, Weather> {

		@Override
		protected Weather doInBackground(String... params) {
			Weather weather = new Weather();
			String data = ((new WeatherHttpClient()).getWeatherData(params[0]));
			try {
				weather = JSONWeatherParser.getWeather(data);
				weather.iconData = ((new WeatherHttpClient())
						.getImage(weather.currentCondition.getIcon()));
			} catch (JSONException e) {
				e.printStackTrace();
			}
			return weather;
		}

		@Override
		protected void onPostExecute(Weather weather) {

			super.onPostExecute(weather);

			if (weather.iconData != null) {
				imgView.setImageDrawable(weather.iconData);

				// Bitmap img = BitmapFactory.decodeByteArray(weather.iconData,
				// 0, weather.iconData.length);
				// imgView.setImageBitmap(img);
			}

			cityText.setText(weather.location.getCity() + ","
					+ weather.location.getCountry());
			condDescr.setText(weather.currentCondition.getCondition() + "("
					+ weather.currentCondition.getDescr() + ")");
			temp.setText(""
					+ Math.round((weather.temperature.getTemp() - 273.15))
					+ "°C");
			hum.setText("" + weather.currentCondition.getHumidity() + "%");
			press.setText("" + weather.currentCondition.getPressure() + " hPa");
			windSpeed.setText("" + weather.wind.getSpeed() + " mps");
			windDeg.setText("" + weather.wind.getDeg() + "°");

		}
	}

}

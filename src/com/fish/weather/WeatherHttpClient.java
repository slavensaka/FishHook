package com.fish.weather;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import android.graphics.drawable.Drawable;

public class WeatherHttpClient {
	private static String BASE_URL = "http://api.openweathermap.org/data/2.5/weather?q=";
	// private static String IMG_URL = "http://openweathermap.org/img/w/";
	private static String IMG_URL = "http://slaven-sakacic.from.hr/icons/";

	public String getWeatherData(String location) {
		HttpURLConnection con = null;
		InputStream is = null;

		try {
			con = (HttpURLConnection) (new URL(BASE_URL + location))
					.openConnection();
			con.setRequestMethod("GET");
			con.setDoInput(true);
			con.setDoOutput(true);
			con.connect();
			StringBuffer buffer = new StringBuffer();
			is = con.getInputStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			String line = null;
			while ((line = br.readLine()) != null)
				buffer.append(line + "\r\n");

			is.close();
			con.disconnect();
			return buffer.toString();

		} catch (Throwable t) {
			t.printStackTrace();
		} finally {
			try {
				is.close();
			} catch (Throwable t) {
			}

			try {
				con.disconnect();
			} catch (Throwable t) {
			}
		}
		return null;
	}

	public Drawable getImage(String code) {

		try {
			InputStream is = (InputStream) new URL(IMG_URL + code + ".png")
					.getContent();
			Drawable d = Drawable.createFromStream(is, "slikica");
			return d;
			// con = (HttpURLConnection) (new URL(IMG_URL+code
			// +".png")).openConnection();
			// con.setRequestMethod("GET");
			// con.setDoInput(true);
			// con.setDoOutput(true);
			// con.connect();
			// is = con.getInputStream();
			// byte[] buffer = new byte[1024];
			// ByteArrayOutputStream baos = new ByteArrayOutputStream();
			//
			// while(is.read(buffer) != -1) baos.write(buffer);
			// return baos.toByteArray();

		} catch (Exception t) {
			t.printStackTrace();
		}

		return null;
	}
}

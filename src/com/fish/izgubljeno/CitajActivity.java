package com.fish.izgubljeno;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.widget.EditText;
import android.widget.Toast;

import com.fish.net.R;

public class CitajActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_izgubljeno_citaj);
		EditText txtData = (EditText) findViewById(R.id.txtData);

		Bundle extras = getIntent().getExtras();
		String izabrano = extras.getString("izabrano");

//		izabrano = izabrano.replaceAll("storage/sdcard0/", "");
//		File myFile = new File("/sdcard/" + izabrano);
		File extStore = Environment.getExternalStorageDirectory();
		String SD_folder = extStore.getAbsolutePath()+"/Android/data/com.fish.net/";
		File myFile = new File( izabrano);

//		Toast.makeText(getBaseContext(), myFile.getPath(), Toast.LENGTH_LONG);

		try {

//			Toast.makeText(getBaseContext(), myFile.getPath(),
//					Toast.LENGTH_LONG).show();
			FileInputStream fIn = new FileInputStream(myFile);
			BufferedReader myReader = new BufferedReader(new InputStreamReader(
					fIn));
			String aDataRow = "";
			String aBuffer = "";
			while ((aDataRow = myReader.readLine()) != null) {
				aBuffer += aDataRow + "\n";
			}
			txtData.setText(aBuffer);
			myReader.close();
			
		} catch (Exception e) {
			Toast.makeText(getBaseContext(), e.getMessage(), Toast.LENGTH_LONG)
					.show();
		}

	}
}

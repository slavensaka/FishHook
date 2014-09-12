package com.fish.camera;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;

import com.fish.net.R;
import com.fish.net.WelcomePage;

public class DisplayImageActivity extends Activity {
	Button btnDelete;
	ImageView imageDetail;
	int imageId;
	Bitmap theImage;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_display_c);
		btnDelete = (Button) findViewById(R.id.btnDelete);
		imageDetail = (ImageView) findViewById(R.id.imageView1);
		/**
		 * getting intent data from search and previous screen
		 */
		Intent intnt = getIntent();
		theImage = (Bitmap) intnt.getParcelableExtra("imagename");
		imageId = intnt.getIntExtra("imageid", 20);
		
		imageDetail.setImageBitmap(theImage);
		btnDelete.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				/**
				 * create DatabaseHandler object
				 */
				DataBaseHandler db = new DataBaseHandler(
						DisplayImageActivity.this);
				/**
				 * Deleting records from database
				 */
				
				db.deleteContact(new Camera(imageId));
				// /after deleting data go to main page
				Intent i = new Intent(DisplayImageActivity.this,
						CameraActivity.class);
				startActivity(i);
				finish();
			}
		});
	}
	@Override
	public void onBackPressed() {
		Intent i = new Intent(DisplayImageActivity.this,
				CameraActivity.class);
		startActivity(i);
		finish();
	}
}
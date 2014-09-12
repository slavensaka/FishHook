package com.fish.riba;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.fish.camera.Camera;
import com.fish.camera.CameraActivity;
import com.fish.camera.DataBaseHandler;
import com.fish.camera.DisplayImageActivity;
import com.fish.net.R;
import com.fish.riba.DatabaseHelper;

public class DisplayActivity extends Activity {
	ImageView imageDetail;
	TextView ribaid;
	TextView created_at;
	TextView vrsta;
	TextView tezina;
	TextView tehnika;
	TextView duljina;
	TextView vrstaRijeke;
	TextView upecao;
	TextView dogadaj;
	TextView opis;
	int intribaid;
	Button btnDelete;
	Bitmap theImage;

	String datetime;
	String vrsa;
	Float tezi;
	String tehni;
	Float dulji;
	String vrstaRij;
	String upec;
	String doga;
	String opi;
	Button azuriraj;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_riba_display);
		ribaid = (TextView) findViewById(R.id.ribaid);
		created_at = (TextView) findViewById(R.id.datetime);
		vrsta = (TextView) findViewById(R.id.vrsta);
		tezina = (TextView) findViewById(R.id.tezina);
		tehnika = (TextView) findViewById(R.id.tehnika);
		duljina = (TextView) findViewById(R.id.duljina);
		vrstaRijeke = (TextView) findViewById(R.id.vrstarijeke);
		upecao = (TextView) findViewById(R.id.upecao);
		dogadaj = (TextView) findViewById(R.id.dogadaj);
		opis = (TextView) findViewById(R.id.opis);
		btnDelete = (Button) findViewById(R.id.brisiRibu);
		azuriraj = (Button) findViewById(R.id.azuriraj);
		imageDetail = (ImageView) findViewById(R.id.slicica);

		Intent intnt = getIntent();

		intribaid = intnt.getIntExtra("ribaid", 20);
		datetime = intnt.getStringExtra("created_at");
		vrsa = intnt.getStringExtra("vrsta");
		tezi = intnt.getFloatExtra("tezina", 0);
		tehni = intnt.getStringExtra("tehnika");
		dulji = intnt.getFloatExtra("duljina", 0);
		vrstaRij = intnt.getStringExtra("vrstaRijeke");
		upec = intnt.getStringExtra("upecao");
		doga = intnt.getStringExtra("dogadaj");
		opi = intnt.getStringExtra("opis");
		theImage = (Bitmap) intnt.getParcelableExtra("imagename");

		String te = Float.toString(tezi);
		String dul = Float.toString(dulji);
		ribaid.setText(Integer.toString(intribaid));
		created_at.setText(datetime);
		vrsta.setText(vrsa);
		tezina.setText(te);
		tehnika.setText(tehni);
		duljina.setText(dul);
		vrstaRijeke.setText(vrstaRij);
		upecao.setText(upec);
		dogadaj.setText(doga);
		opis.setText(opi);
		imageDetail.setImageBitmap(theImage);

		btnDelete.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				/**
				 * create DatabaseHandler object
				 */
				DatabaseHelper db = new DatabaseHelper(DisplayActivity.this);
				/**
				 * Deleting records from database
				 */
				db.deleteContact(new Riba(intribaid));
				// /after deleting data go to main page
				Intent i = new Intent(DisplayActivity.this, ViewRiba.class);
				startActivity(i);
				finish();
			}
		});

		azuriraj.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				DatabaseHelper db = new DatabaseHelper(DisplayActivity.this);

				// db.deleteContact(new Riba(intribaid));

				TextView vrsta1 = (TextView) findViewById(R.id.vrsta);
				TextView tezina1 = (TextView) findViewById(R.id.tezina);
				TextView tehnika1 = (TextView) findViewById(R.id.tehnika);
				TextView duljina1 = (TextView) findViewById(R.id.duljina);
				TextView vrstaRijeke1 = (TextView) findViewById(R.id.vrstarijeke);
				TextView upecao1 = (TextView) findViewById(R.id.upecao);
				TextView dogadaj1 = (TextView) findViewById(R.id.dogadaj);
				TextView opis1 = (TextView) findViewById(R.id.opis);

				String vrs;
				float tez;
				String tehn;
				float dulj;
				String vrsRijeke;
				String upe;
				String doga;
				String op;

				vrs = vrsta1.getText().toString();
				tez = Float.parseFloat(tezina1.getText().toString());
				tehn = tehnika1.getText().toString();
				dulj = Float.parseFloat(duljina1.getText().toString());
				vrsRijeke = vrstaRijeke1.getText().toString();
				upe = upecao1.getText().toString();
				doga = dogadaj1.getText().toString();
				op = opis1.getText().toString();

				// // /after deleting data go to main page
				Intent intent = new Intent(DisplayActivity.this,
						UpdateActivity.class);
				intent.putExtra("ribaid", intribaid);
				intent.putExtra("vrsta", vrs);
				intent.putExtra("tezina", tez);
				intent.putExtra("tehnika", tehn);
				intent.putExtra("duljina", dulj);
				intent.putExtra("vrstaRijeke", vrsRijeke);
				intent.putExtra("upecao", upe);
				intent.putExtra("dogadaj", doga);
				intent.putExtra("opis", op);
				startActivity(intent);
				// finish();
			}
		});
	}

	@Override
	public void onBackPressed() {
		Intent i = new Intent(DisplayActivity.this, ViewRiba.class);
		startActivity(i);
		finish();
	}
}
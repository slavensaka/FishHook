package com.fish.riba;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.fish.net.R;

public class UpdateActivity extends Activity {

	String vrsa;
	Float tezi;
	String tehni;
	Float dulji;
	String vrstaRij;
	String upec;
	String doga;
	String opi;
	TextView timedate;
	EditText vrstaribe;
	EditText tezinaribe;
	EditText tehnikaa;
	EditText duljinaribe;
	EditText vrstarijeke;
	EditText upecao;
	EditText opis;
	Button azuri;
	int id;

	Spinner za_poslije_izradit;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_riba_update);
		Intent intnt = getIntent();

		za_poslije_izradit = (Spinner) findViewById(R.id.dogadaja1);

		azuri = (Button) findViewById(R.id.azuri);
		timedate = (TextView) findViewById(R.id.time_date);
		vrstaribe = (EditText) findViewById(R.id.vrstaribe);
		tezinaribe = (EditText) findViewById(R.id.tezinaribe);
		tehnikaa = (EditText) findViewById(R.id.tehnikaa);
		duljinaribe = (EditText) findViewById(R.id.duljinaribe);
		vrstarijeke = (EditText) findViewById(R.id.vrstarijeke);
		upecao = (EditText) findViewById(R.id.upecao);
		opis = (EditText) findViewById(R.id.opis);

		id = intnt.getIntExtra("ribaid", 20);
		vrsa = intnt.getStringExtra("vrsta");
		tezi = intnt.getFloatExtra("tezina", 0);
		tehni = intnt.getStringExtra("tehnika");
		dulji = intnt.getFloatExtra("duljina", 0);
		vrstaRij = intnt.getStringExtra("vrstaRijeke");
		upec = intnt.getStringExtra("upecao");
		// doga=intnt.getStringExtra("dogadaj");
		opi = intnt.getStringExtra("opis");
		za_poslije_izradit.setVisibility(View.INVISIBLE);
		String te = Float.toString(tezi);
		String dul = Float.toString(dulji);
		timedate.setText(Integer.toString(id));

		vrstaribe.setText(vrsa);
		tezinaribe.setText(te);
		tehnikaa.setText(tehni);
		duljinaribe.setText(dul);
		vrstarijeke.setText(vrstaRij);
		upecao.setText(upec);
		// dogadaj.setText(doga);
		opis.setText(opi);

		azuri.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				DatabaseHelper db = new DatabaseHelper(UpdateActivity.this);

				timedate = (TextView) findViewById(R.id.time_date);// Ovo je ID
				vrstaribe = (EditText) findViewById(R.id.vrstaribe);
				tezinaribe = (EditText) findViewById(R.id.tezinaribe);
				tehnikaa = (EditText) findViewById(R.id.tehnikaa);
				duljinaribe = (EditText) findViewById(R.id.duljinaribe);
				vrstarijeke = (EditText) findViewById(R.id.vrstarijeke);
				upecao = (EditText) findViewById(R.id.upecao);
				opis = (EditText) findViewById(R.id.opis);

				int ido = id;
				String vrst = vrstaribe.getText().toString();
				float tezi = Float.parseFloat(tezinaribe.getText().toString());
				String tehnik = tehnikaa.getText().toString();
				float dulji = Float
						.parseFloat(duljinaribe.getText().toString());
				String vrstarij = vrstarijeke.getText().toString();
				String upec = upecao.getText().toString();
				String op = opis.getText().toString();
				Riba riba = new Riba(ido, vrst, tezi, tehnik, dulji, vrstarij,
						upec, op);
				db.updateContact(riba);
				Toast.makeText(getBaseContext(), "Riba je ažurirana",
						Toast.LENGTH_SHORT).show();

				Intent i = new Intent(UpdateActivity.this, ViewRiba.class);
				startActivity(i);
				finish();
			}
		});
	}

	@Override
	public void onBackPressed() {
		Intent i = new Intent(UpdateActivity.this, DisplayActivity.class);
		startActivity(i);
		finish();
	}

}

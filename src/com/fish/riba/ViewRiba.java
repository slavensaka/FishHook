package com.fish.riba;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

import com.fish.camera.CameraActivity;
import com.fish.camera.DisplayImageActivity;
import com.fish.net.R;

public class ViewRiba extends Activity {

	DatabaseHelper db;
	ListView listRiba;
	ArrayList<Riba> imageArry = new ArrayList<Riba>();
	RibaAdapter imageAdapter;
	byte[] imageName;
	int ribaid;
	String created_at;
	String vrsta;
	float tezina;
	String tehnika;
	float duljina;
	String vrstaRijeke;
	String upecao;
	String dogadaj;
	String opis;
	Bitmap theImage;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_riba_view);
		listRiba = (ListView) findViewById(R.id.list);
		db = new DatabaseHelper(this);
		List<Riba> ribe = db.getAllRiba();
		for (Riba cn : ribe) {
			imageArry.add(cn);
		}

		imageAdapter = new RibaAdapter(this, R.layout.activity_riba_adapter,
				imageArry);
		listRiba.setAdapter(imageAdapter);
		
		if (listRiba != null) {
			listRiba.invalidate();
		}

		listRiba.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				ribaid = imageArry.get(position).getId();
				created_at = imageArry.get(position).getCreatedAt();
				vrsta = imageArry.get(position).getvrsta();
				tezina = imageArry.get(position).getTezina();
				tehnika = imageArry.get(position).getTehnika();
				duljina = imageArry.get(position).getDuljina();
				vrstaRijeke = imageArry.get(position).getVrstarijeke();
				upecao = imageArry.get(position).getUpecao();
				dogadaj = imageArry.get(position).getDogadaj();
				opis = imageArry.get(position).getOpis();
				imageName = imageArry.get(position).getSlikajedan();
				// Toast.makeText(getBaseContext(), idd,
				// Toast.LENGTH_LONG).show();
				ByteArrayInputStream imageStream = new ByteArrayInputStream(
						imageName);
				theImage = BitmapFactory.decodeStream(imageStream);
				Intent intent = new Intent(ViewRiba.this, DisplayActivity.class);
				intent.putExtra("ribaid", ribaid);
				intent.putExtra("created_at", created_at);
				intent.putExtra("vrsta", vrsta);
				intent.putExtra("tezina", tezina);
				intent.putExtra("tehnika", tehnika);
				intent.putExtra("duljina", duljina);
				intent.putExtra("vrstaRijeke", vrstaRijeke);
				intent.putExtra("upecao", upecao);
				intent.putExtra("dogadaj", dogadaj);
				intent.putExtra("opis", opis);
				intent.putExtra("imagename", theImage);
				startActivity(intent);
			}
		});
	}

	@Override
	public void onBackPressed() {
		Intent i = new Intent(ViewRiba.this, RibaActivity.class);
		startActivity(i);
		finish();
	}
}

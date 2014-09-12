package com.fish.riba;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Arrays;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.fish.camera.Camera;
import com.fish.camera.DataBaseHandler;
import com.fish.net.R;
import com.fish.net.WelcomePage;

public class RibaActivity extends Activity {

	private AutoCompleteTextView auto_vrsta_ribe;
	private AutoCompleteTextView auto_tehnika;
	private AutoCompleteTextView auto_vrsta_rijeke;
	private static final int CAMERA_REQUEST = 1;
	private static final int PICK_FROM_GALLERY = 2;
	ArrayList<Camera> imageAr = new ArrayList<Camera>();
	DatabaseHelper db;
	DataBaseHandler cameradb;
	Button slikaj;
	Button dodaj_ribu;
	EditText vrsta;
	EditText tezina;
	EditText tehnikaa;
	EditText duljina;
	EditText vrstarijeke;
	EditText upecao;
	EditText opis;
	TextView time;
	Button vidiSve;
	static byte[] slika=null;
	byte[] test=null;
	int imageId;
	Button addImage;
	 float tezi;
  float dulji;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_riba);
		final Spinner dogadaj = (Spinner) findViewById(R.id.dogadaja1);
		dodaj_ribu = (Button) findViewById(R.id.dodaj_ribu);
		db = new DatabaseHelper(getApplicationContext());

		vrsta = (EditText) findViewById(R.id.vrstaribe);
		tezina = (EditText) findViewById(R.id.tezinaribe);
		tehnikaa = (EditText) findViewById(R.id.tehnikaa);
		duljina = (EditText) findViewById(R.id.duljinaribe);
		vrstarijeke = (EditText) findViewById(R.id.vrstarijeke);
		upecao = (EditText) findViewById(R.id.upecao);
		opis = (EditText) findViewById(R.id.opis);
		time = (TextView) findViewById(R.id.time_date);
		addImage = (Button) findViewById(R.id.btnAdd);
		vidiSve = (Button) findViewById(R.id.btnView);

		// AutoComplete vrsta ribe
		String[] vrste_riba = getResources().getStringArray(
				R.array.lista_vrsta_ribe);
		ArrayAdapter<String> adapter_vrste_ribe = new ArrayAdapter<String>(
				this, android.R.layout.simple_list_item_1, vrste_riba);
		auto_vrsta_ribe = (AutoCompleteTextView) findViewById(R.id.vrstaribe);
		auto_vrsta_ribe.setAdapter(adapter_vrste_ribe);

		// AutoComplete tehnika
		String[] tehnika = getResources().getStringArray(R.array.lista_tehnika);
		ArrayAdapter<String> adapter_tehnike = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, tehnika);
		auto_tehnika = (AutoCompleteTextView) findViewById(R.id.tehnikaa);
		auto_tehnika.setAdapter(adapter_tehnike);

		// AutoComplete vrsta rijeke
		String[] vrsta_rijeke = getResources().getStringArray(
				R.array.lista_vrsta_rijeke);
		ArrayAdapter<String> adapter_vrsta_rijeke = new ArrayAdapter<String>(
				this, android.R.layout.simple_list_item_1, vrsta_rijeke);
		auto_vrsta_rijeke = (AutoCompleteTextView) findViewById(R.id.vrstarijeke);
		auto_vrsta_rijeke.setAdapter(adapter_vrsta_rijeke);

		ArrayAdapter<CharSequence> adapter_dogadaj = ArrayAdapter
				.createFromResource(this, R.array.lista_dogadaja,
						android.R.layout.simple_spinner_dropdown_item);
		dogadaj.setAdapter(adapter_dogadaj);

		final String doga = dogadaj.getSelectedItem().toString();

		cameradb = new DataBaseHandler(this);
		String vrijeme = db.getDateTime();
		time.setText(vrijeme);

		dodaj_ribu.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				String vrst = vrsta.getText().toString();
				String tehnik = tehnikaa.getText().toString();
				String vrstarij = vrstarijeke.getText().toString();
				String upec = upecao.getText().toString();
				String op = opis.getText().toString();
				
				
//				String in1=tezina.getText().toString();
//				 String in2=duljina.getText().toString(); 
//				 if((in1== null || in1.trim().equals(""))  ||
//					     (in2== null || in2.trim().equals(""))
//					     )	 {
//						tezina
//						duljina.setT
//					 } else {
//					 tezi=Float.parseFloat(tezina.getText().toString());
//					 dulji=Float.parseFloat(duljina.getText().toString());
//					 }
				
				
				try {

					String position = dogadaj.getSelectedItem().toString();
					
					 tezi=Float.parseFloat(tezina.getText().toString());
					 dulji=Float.parseFloat(duljina.getText().toString());
					
				if(!(Arrays.equals(slika, test))){
					
					
					
					
					
					Riba riba = new Riba(vrst,tezi , tehnik,dulji , vrstarij, upec, position,
							op, slika);
					db.createRiba(riba);
					Toast.makeText(getBaseContext(), "Riba je dodana u bazu",
							Toast.LENGTH_SHORT).show();
					slika = null;
				} else {
					Resources res = getResources();
					Drawable drawable = res.getDrawable(R.drawable.nopic);
					Bitmap bitmap = ((BitmapDrawable)drawable).getBitmap();
					ByteArrayOutputStream stream = new ByteArrayOutputStream();
					bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
					slika = stream.toByteArray();
					
					Riba riba = new Riba(vrst, Float.valueOf(tezina.getText()
							.toString()), tehnik, Float.valueOf(duljina
							.getText().toString()), vrstarij, upec, position,
							op, slika);
					db.createRiba(riba);
					Toast.makeText(getBaseContext(), "Riba je dodana u bazu",
							Toast.LENGTH_SHORT).show();
					slika = null;
					
				}
				} catch (RuntimeException e) {
					e.printStackTrace();
				}

			}
		});

		
		
		
		
		
		vidiSve.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent view = new Intent(RibaActivity.this, ViewRiba.class);
				startActivity(view);
			}
		});

		/**
		 * open dialog for choose camera/gallery
		 */

		final String[] option = new String[] { "Slikat",
				"Iz Galerije" };
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				android.R.layout.select_dialog_item, option);
		AlertDialog.Builder builder = new AlertDialog.Builder(this);

		builder.setTitle("Odaberite opciju");
		builder.setAdapter(adapter, new DialogInterface.OnClickListener() {

			public void onClick(DialogInterface dialog, int which) {
				if (which == 0) {
					callCamera();
				}
				if (which == 1) {
					callGallery();
				}

			}
		});
		final AlertDialog dialog = builder.create();
		addImage = (Button) findViewById(R.id.btnAdd);
		addImage.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				dialog.show();
			}
		});

	}
	
	
	@Override
	public void onBackPressed() {
		Intent i = new Intent(RibaActivity.this,
				WelcomePage.class);
		i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(i);
		finish();
	}

	/**
	 * On activity result
	 */
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode != RESULT_OK)
			return;

		switch (requestCode) {
		case CAMERA_REQUEST:
			if (requestCode == CAMERA_REQUEST
					&& resultCode == Activity.RESULT_OK) {
				 Bundle extras = data.getExtras();
				
				 if (extras != null) {
					 Bitmap yourImage = data.getExtras().getParcelable("data");
				 //Bitmap yourImage = extras.getParcelable("data");
				 // convert bitmap to byte
				 ByteArrayOutputStream stream = new ByteArrayOutputStream();
				 yourImage.compress(Bitmap.CompressFormat.PNG, 100, stream);
				 if(yourImage != null){
					
				 byte[] imageInByte = stream.toByteArray();
				 slika= imageInByte;
				 cameradb.addContact(new Camera("Riba", imageInByte));
				 }
			}
				 }
			break;
		case PICK_FROM_GALLERY:
			Bundle extras2 = data.getExtras();
			if (extras2 != null) {
				Bitmap yourImage = extras2.getParcelable("data");
				// convert bitmap to byte
				ByteArrayOutputStream stream = new ByteArrayOutputStream();
				yourImage.compress(Bitmap.CompressFormat.PNG, 100, stream);
				byte[] imageInByte = stream.toByteArray();
				slika = imageInByte;
				cameradb.addContact(new Camera("Riba", imageInByte));

			}
			break;
		}
	}

	/**
	 * open camera method
	 */
	public void callCamera() {
		Intent cameraIntent = new Intent(
				android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
		cameraIntent.putExtra("crop", "true");
		cameraIntent.putExtra("aspectX", 0);
		cameraIntent.putExtra("aspectY", 0);
		cameraIntent.putExtra("outputX", 150);
		cameraIntent.putExtra("outputY", 100);
		startActivityForResult(cameraIntent, CAMERA_REQUEST);
	}

	/**
	 * open gallery method
	 */
	public void callGallery() {
		Intent intent = new Intent();
		intent.setType("image/*");
		intent.setAction(Intent.ACTION_GET_CONTENT);
		intent.putExtra("crop", "true");
		intent.putExtra("aspectX", 0);
		intent.putExtra("aspectY", 0);
		intent.putExtra("outputX", 150);
		intent.putExtra("outputY", 100);
		intent.putExtra("return-data", true);
		startActivityForResult(
				Intent.createChooser(intent, "Provedi akciju pomoæu"),
				PICK_FROM_GALLERY);
	}

}

package com.fish.oprema.activities;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.fish.izgubljeno.CitajActivity;
import com.fish.izgubljeno.IzgubljenoActivity;
import com.fish.izgubljeno.SimpleFileDialog;
import com.fish.net.R;
import com.fish.oprema.DataBaseHelper;
import com.fish.oprema.Name;
import com.fish.oprema.Oprema;

public class OpremaActivity extends Activity {

	Spinner sp;
	EditText et;
	DataBaseHelper db;
	Button b1;
	Button spreminaSD;
	Button citajSD;
	Toast toast;
	List<String> categories;
	String oprema;
	List<Name> list;
	List<Name> all;
	List<Oprema> op;
	File folder;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_oprema);

		sp = (Spinner) findViewById(R.id.vrsta);
		et = (EditText) findViewById(R.id.name_opreme);
		b1 = (Button) findViewById(R.id.add_gear);
		spreminaSD = (Button) findViewById(R.id.spreminaSD);
		citajSD = (Button) findViewById(R.id.citajSD);
		db = new DataBaseHelper(getApplicationContext());
		categories = new ArrayList<String>();
		categories.add("Predvez(Metoda)");
		categories.add("Umjetni Mamac");
		categories.add("Živi Mamac");
		categories.add("Rola");
		categories.add("Štap");
		categories.add("Ostala oprema");

		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, categories);
		dataAdapter
				.setDropDownViewResource(android.R.layout.simple_expandable_list_item_1);
		sp.setAdapter(dataAdapter);
		final String oprema = sp.getSelectedItem().toString();

		b1.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				if (et.getText().toString().equals("")) {
					Toast.makeText(v.getContext(), "Prazno polje",
							Toast.LENGTH_LONG).show();
				} else {

					String position = sp.getSelectedItem().toString();
					Oprema novi = new Oprema(position);
					long tag = db.createOprema(novi);
					Name name = new Name(et.getText().toString(), position);

					db.createName(name, new long[] { tag });
					Toast.makeText(v.getContext(), "Dodano u bazu",
							Toast.LENGTH_SHORT).show();
				}
			}
		});

		spreminaSD.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				try {
					Date cDate = new Date();
					String fDate = new SimpleDateFormat("dd.MM HH MM")
							.format(cDate);
					File extStore = Environment.getExternalStorageDirectory();
					String SD_folder = extStore.getAbsolutePath()
							+ "/Android/data/com.fish.net/";
					String SD_File = fDate + "_oprema" + ".txt";
					String sd = SD_folder + SD_File;

					

					File myFile = new File(SD_folder + SD_File);
					myFile.createNewFile();
					FileOutputStream fOut = new FileOutputStream(myFile);
					OutputStreamWriter myOutWriter = new OutputStreamWriter(
							fOut);
					myOutWriter.append("ID" + "      " + "IME" + "      "
							+ "VRSTA");
					myOutWriter.append(System.getProperty("line.separator"));
					myOutWriter.append(System.getProperty("line.separator"));
					List<Name> allTags = db.getAllNames();
					for (Name name : allTags) {
						myOutWriter.append(name.getId() + ". ");
						myOutWriter.append(name.getName() + "   ");
						myOutWriter.append(name.getOprema());

						myOutWriter.append(System.getProperty("line.separator"));
					}
					myOutWriter.close();
					fOut.close();
					Toast.makeText(getBaseContext(),
							"Spremljeno na SDCard",
							Toast.LENGTH_SHORT).show();
				} catch (Exception e) {
					Toast.makeText(getBaseContext(), e.getMessage(),
							Toast.LENGTH_SHORT).show();
				}
			}
		});

		citajSD.setOnClickListener(new OnClickListener() {
			String m_chosen;

			@Override
			public void onClick(View v) {
				SimpleFileDialog FileOpenDialog = new SimpleFileDialog(
						OpremaActivity.this, "FileOpen",
						new SimpleFileDialog.SimpleFileDialogListener() {
							@Override
							public void onChosenDir(String chosenDir) {
								m_chosen = chosenDir;
								// Toast.makeText(IzgubljenoActivity.this,
								// "Chosen FileOpenDialog File: " +
								// m_chosen, Toast.LENGTH_LONG).show();

								Intent i = new Intent(OpremaActivity.this,
										CitajActivity.class);
								i.putExtra("izabrano", m_chosen);
								startActivity(i);
							}
						});
				FileOpenDialog.Default_File_Name = "";
				FileOpenDialog.chooseFile_or_Dir();
			}
		});
	}

	public void ViewActivity(View v) {
		Intent viewintent = new Intent(this, ViewActivity.class);
		startActivity(viewintent);
	}
}

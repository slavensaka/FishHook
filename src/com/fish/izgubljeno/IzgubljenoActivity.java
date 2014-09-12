package com.fish.izgubljeno;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.util.SparseBooleanArray;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.fish.net.R;

public class IzgubljenoActivity extends ListActivity {

	ArrayList<String> list = new ArrayList<String>();
	ArrayAdapter<String> adapter;
	EditText cijena;
	TextView konacno;
	int res;
	int res1;
	int dva;
	ListView lv;
	Button btnDel;
	EditText edit;
	String broj;
	String novi;
	String novii;
	int gla;
	Button btnWriteSDFile;
	Button btnReadSDFile;
	File folder1;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_izgubljene_opreme);
		Button btn = (Button) findViewById(R.id.izracunaj);
		btnDel = (Button) findViewById(R.id.btnDel);
		btnWriteSDFile = (Button) findViewById(R.id.spremiSD);
		btnReadSDFile = (Button) findViewById(R.id.citajSD);
		adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_checked, list);
		
		OnClickListener listener = new OnClickListener() {
			@Override
			public void onClick(View v) {

				edit = (EditText) findViewById(R.id.ime);
				cijena = (EditText) findViewById(R.id.cijena);
				konacno = (TextView) findViewById(R.id.konacno);

				if ((edit.getText().toString().equals(""))
						|| (cijena.getText().toString().equals(""))) {
					Toast.makeText(v.getContext(),
							"Oba polja moraju biti unesena", Toast.LENGTH_LONG)
							.show();
				} else {

					StringBuilder s = new StringBuilder(150);
					s.append(edit.getText().toString());
					s.append("          +");
					s.append(cijena.getText().toString());
					// s.append(" kn");
					String ss = s.toString();

					list.add(ss);

					broj = cijena.getText().toString();
					res1 += Integer.parseInt(broj);

					Dodaj(res1);

					edit.setText("");
					cijena.setText("");
					adapter.notifyDataSetChanged();
					edit.requestFocus();
				}
			}
		};

		OnClickListener listenerDel = new OnClickListener() {
			@Override
			public void onClick(View v) {

				SparseBooleanArray checkedItemPositions = getListView()
						.getCheckedItemPositions();
				int itemCount = getListView().getCount();

				for (int i = itemCount - 1; i >= 0; i--) {
					if (checkedItemPositions.get(i)) {
						adapter.remove(list.get(i));
					}
				}
				checkedItemPositions.clear();
				adapter.notifyDataSetChanged();
			}
		};

		OnClickListener listenerSD = new OnClickListener() {

			@Override
			public void onClick(View v) {

				try {
					Date cDate = new Date();
					String fDate = new SimpleDateFormat("dd.MM HH MM")
							.format(cDate);
					File extStore = Environment.getExternalStorageDirectory();
					String SD_folder = extStore.getAbsolutePath()
							+ "/Android/data/com.fish.net/";
					String SD_File = fDate + "_izg" + ".txt";

					File myFile = new File(SD_folder + SD_File);
					myFile.createNewFile();
					FileOutputStream fOut = new FileOutputStream(myFile);
					OutputStreamWriter myOutWriter = new OutputStreamWriter(
							fOut);

					myOutWriter.append("IZGUBLJENO"
							+ System.getProperty("line.separator")
							+ "Cijena izgubljene opreme:   "
							+ String.valueOf(res1) + "  kn"
							+ System.getProperty("line.separator")
							+ System.getProperty("line.separator")
							+ "Popis izgubljene opreme:  "
							+ System.getProperty("line.separator")
							+ System.getProperty("line.separator"));
					for (int i = 0; i < adapter.getCount(); i++) {
						Object obj = adapter.getItem(i);
						myOutWriter.append(obj.toString() + " kn");
						myOutWriter
								.append(System.getProperty("line.separator"));
					}
					myOutWriter.close();
					fOut.close();
					Toast.makeText(getBaseContext(),
							"Done writing SD 'mysdfile.txt'",
							Toast.LENGTH_SHORT).show();
				} catch (Exception e) {
					Toast.makeText(getBaseContext(), e.getMessage(),
							Toast.LENGTH_SHORT).show();
				}

			}
		};

		btnReadSDFile.setOnClickListener(new OnClickListener() {
			String m_chosen;

			@Override
			public void onClick(View v) {
				SimpleFileDialog FileOpenDialog = new SimpleFileDialog(
						IzgubljenoActivity.this, "FileOpen",
						new SimpleFileDialog.SimpleFileDialogListener() {
							@Override
							public void onChosenDir(String chosenDir) {
								m_chosen = chosenDir;
								// Toast.makeText(IzgubljenoActivity.this,
								// "Chosen FileOpenDialog File: " +
								// m_chosen, Toast.LENGTH_LONG).show();

								Intent i = new Intent(IzgubljenoActivity.this,
										CitajActivity.class);
								i.putExtra("izabrano", m_chosen);
								startActivity(i);

							}
						});
				FileOpenDialog.Default_File_Name = "";
				FileOpenDialog.chooseFile_or_Dir();

			}
		});

		// OnClickListener listenerReadSD = new OnClickListener() {
		//
		// @Override
		// public void onClick(View v) {
		// try {
		// File myFile = new File("/sdcard/mysdfile.txt");
		// FileInputStream fIn = new FileInputStream(myFile);
		//
		// BufferedReader myReader = new BufferedReader(
		// new InputStreamReader(fIn));
		// String aDataRow = "";
		// String aBuffer = "";
		// while ((aDataRow = myReader.readLine()) != null) {
		// aBuffer += aDataRow + "\n";
		// }
		// txtData.setText(aBuffer);
		// myReader.close();
		// Toast.makeText(getBaseContext(),
		// "Done reading SD 'mysdfile.txt'",
		// Toast.LENGTH_SHORT).show();
		// } catch (Exception e) {
		// Toast.makeText(getBaseContext(), e.getMessage(),
		// Toast.LENGTH_SHORT).show();
		// }
		//
		// }
		// };

		btn.setOnClickListener(listener);
		btnDel.setOnClickListener(listenerDel);
		setListAdapter(adapter);
		btnWriteSDFile.setOnClickListener(listenerSD);
		// btnReadSDFile.setOnClickListener(listenerReadSD);
	}

	public void onListItemClick(ListView parent, View v, int position, long id) {
		super.onListItemClick(parent, v, position, id);
		CheckedTextView check = (CheckedTextView) v;

		if (false != check.isChecked()) {
			// for(int i;check.isChecked()>
			String selectedFromList = (String) (parent
					.getItemAtPosition(position));
			String innerText = selectedFromList.substring(selectedFromList
					.indexOf("+"));
			String remove = innerText.substring(1);
			res1 -= Integer.parseInt(remove);
			String tt = "t";
			Dodaj(res1);
		} else {
			String f = "f";
			String selectedFromList = (String) (parent
					.getItemAtPosition(position));
			String innerText = selectedFromList.substring(selectedFromList
					.indexOf("+"));
			String remove = innerText.substring(1);
			res1 += Integer.parseInt(remove);
			Dodaj(res1);
		}
	}

	public void Dodaj(int dodaj) {
		String novi = String.valueOf(dodaj);
		konacno.setText("IZGUBLJENO " + novi + " kn");
	}

}

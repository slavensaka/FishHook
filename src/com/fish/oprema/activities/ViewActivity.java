package com.fish.oprema.activities;

import java.util.List;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.fish.net.R;
import com.fish.oprema.DataBaseHelper;
import com.fish.oprema.Name;

public class ViewActivity extends ListActivity {
	DataBaseHelper db;
	TextView t1, t2, t3;
	List<Name> allNames;
	Button izbrisi;
	EditText brisitext;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_oprema_view);
		izbrisi = (Button) findViewById(R.id.unesi);
		brisitext = (EditText) findViewById(R.id.zaizbrisat);
		db = new DataBaseHelper(getApplicationContext());
		allNames = db.getAllNames();
		setListAdapter(new CustomListAdapter(this, allNames));
		// db.deleteName(0);
		izbrisi.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				if (brisitext.getText().toString().equals("")) {
					Toast.makeText(v.getContext(), "Prazno polje",
							Toast.LENGTH_LONG).show();
				} else {
					db.deleteName(Integer.parseInt(brisitext.getText()
							.toString()));
					finish();
					startActivity(getIntent());
					Toast.makeText(v.getContext(),
							"IZBRISANO: " + brisitext.getText().toString(),
							Toast.LENGTH_LONG).show();
				}
			}
		});
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		// ListAdapter I =l.getAdapter();
		// l.g
		// I.
		// int i = (int) id;
		// Oprema oprema;
		// db.get
		// int it =l.getId();
		// db.deleteName(i+1);
		// Toast.makeText(getBaseContext(),
		// String.valueOf(it),Toast.LENGTH_LONG).show();

	}
}
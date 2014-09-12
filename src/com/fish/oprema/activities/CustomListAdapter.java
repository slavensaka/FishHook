package com.fish.oprema.activities;

import java.io.IOException;
import java.util.List;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.fish.net.R;
import com.fish.oprema.DataBaseHelper;
import com.fish.oprema.Name;

public class CustomListAdapter extends ArrayAdapter {
	private final Activity activity;
	private final List names;
	DataBaseHelper db;

	public CustomListAdapter(Activity activity, List objects) {
		super(activity, R.layout.activity_oprema_list_reader, objects);
		this.activity = activity;
		this.names = objects;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View rowView = convertView;
		Holder sqView = null;

		if (rowView == null) {
			LayoutInflater inflater = activity.getLayoutInflater();
			rowView = inflater.inflate(R.layout.activity_oprema_list_reader,
					null);
			sqView = new Holder();
			sqView.id = (TextView) rowView.findViewById(R.id.id);
			sqView.name = (TextView) rowView.findViewById(R.id.name);
			sqView.oprema = (TextView) rowView.findViewById(R.id.oprema);
			sqView.created_at = (TextView) rowView
					.findViewById(R.id.created_at);
			rowView.setTag(sqView);
		} else {
			sqView = (Holder) rowView.getTag();
		}
		Name currentStock = (Name) names.get(position);
		sqView.id.setText(String.valueOf(currentStock.getId()));
		sqView.name.setText(currentStock.getName().toString());
		sqView.oprema.setText(currentStock.getOprema().toString());
		sqView.created_at.setText(currentStock.getCreatedAt().toString());
		return rowView;
	}

	protected static class Holder {
		protected TextView id;
		protected TextView name;
		protected TextView oprema;
		protected TextView created_at;
	}
}
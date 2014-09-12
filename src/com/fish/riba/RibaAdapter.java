package com.fish.riba;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import com.fish.net.R;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class RibaAdapter extends ArrayAdapter<Riba> {
	Context context;
	int layoutResourceId;
	ArrayList<Riba> data = new ArrayList<Riba>();

	public RibaAdapter(Context context, int layoutResourceId,
			ArrayList<Riba> data) {
		super(context, layoutResourceId, data);
		this.layoutResourceId = layoutResourceId;
		this.context = context;
		this.data = data;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View row = convertView;
		ImageHolder holder = null;
		if (row == null) {
			LayoutInflater inflater = ((Activity) context).getLayoutInflater();
			row = inflater.inflate(layoutResourceId, parent, false);
			holder = new ImageHolder();
			holder.txtTitle = (TextView) row.findViewById(R.id.txtTitle);
			holder.imgIcon = (ImageView) row.findViewById(R.id.imgIcon);
			holder.tehnika = (TextView) row.findViewById(R.id.tehnika);
			holder.dogadaj = (TextView) row.findViewById(R.id.dogadaj);
			row.setTag(holder);
		} else {
			holder = (ImageHolder) row.getTag();
		}

		// Riba test = data.get(0);
		Riba riba = data.get(position);
		holder.txtTitle.setText(riba.vrsta);
		holder.tehnika.setText(riba.tehnika);
		holder.dogadaj.setText(riba.dogadaj);
		// Riba picture = data.get(position);
		// holder.txtTitle.setText(picture.vrsta);
		// //convert byte to bitmap take from contact class

		byte[] outImage = riba.slikajedan;
		ByteArrayInputStream imageStream = new ByteArrayInputStream(outImage);
		Bitmap theImage = BitmapFactory.decodeStream(imageStream);
		holder.imgIcon.setImageBitmap(theImage);
		return row;
	}

	static class ImageHolder {
		ImageView imgIcon;
		TextView txtTitle;
		TextView tehnika;
		TextView dogadaj;
	}
}

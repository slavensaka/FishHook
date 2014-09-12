package com.fish.googlemaps;

import com.fish.oprema.Name;
import com.fish.googlemaps.DodajMarker;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class LocationsDB extends SQLiteOpenHelper {

	private static String DBNAME = "locationmarker";
	private static int VERSION = 2;
	public static final String FIELD_ROW_ID = "_id";
	public static final String FIELD_LAT = "lat";
	public static final String FIELD_LNG = "lng";
	public static final String FIELD_ZOOM = "zom";
	public static final String FIELD_TITLE = "title";
	private static final String DATABASE_TABLE = "locations";
	private static final String LNG = "lng";
	private static final String LAT = "lat";
	private SQLiteDatabase mDB;

	public LocationsDB(Context context) {
		super(context, DBNAME, null, VERSION);
		this.mDB = getWritableDatabase();
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		String sql = "create table " + DATABASE_TABLE + " ( " + FIELD_ROW_ID
				+ " integer primary key autoincrement , " + FIELD_LNG
				+ " double , " + FIELD_LAT + " double , " + FIELD_ZOOM
				+ " text , " + FIELD_TITLE + " text " + " ) ";

		db.execSQL(sql);
	}

	public long insert(ContentValues contentValues) {
		long rowID = mDB.insert(DATABASE_TABLE, null, contentValues);
		return rowID;
	}

	public long find(Double lngi, Double lati) {
		String selectQuery = "SELECT * FROM " + DATABASE_TABLE + " WHERE "
				+ LNG + " = " + lngi + " AND " + LAT + " = " + lati;
		SQLiteDatabase db = this.getReadableDatabase();

		Cursor c = db.rawQuery(selectQuery, null);
		if (c != null) {
			c.moveToFirst();
		}
		return c.getLong(0);
	}

	public int del() {
		int cnt = mDB.delete(DATABASE_TABLE, null, null);
		return cnt;
	}

	public Cursor getAllLocations() {
		return mDB.query(DATABASE_TABLE, new String[] { FIELD_ROW_ID,
				FIELD_LAT, FIELD_LNG, FIELD_ZOOM, FIELD_TITLE }, null, null,
				null, null, null);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	}
}

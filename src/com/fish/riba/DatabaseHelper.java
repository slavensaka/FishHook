package com.fish.riba;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import com.fish.camera.Camera;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

	private static final int DATABASE_VERSION = 1;
	private static final String DATABASE_NAME = "bazariba";
	private static final String TABLE_RIBA = "riba";

	private static final String KEY_ID = "id";
	private static final String KEY_CREATED_AT = "created_at";
	private static final String KEY_SPECIES = "vrsta";
	private static final String KEY_WEIGHT = "tezina";
	private static final String KEY_TECHNIQUE = "tehnika";
	private static final String KEY_WIDTH = "duljina";
	private static final String KEY_RIVER = "vrstarijeke";
	private static final String KEY_CAUGHT_BY = "upecao";
	private static final String KEY_EVENT = "dogadaj";
	private static final String KEY_DESCRIPTION = "opis";
	private static final String KEY_IMAGE_JEDAN = "slikajedan";
	private static final String KEY_IMAGE_DVA = "slikadva";
	private static final String KEY_IMAGE_TRI = "slikatri";

	private static final String CREATE_TABLE_NAME = "CREATE TABLE "
			+ TABLE_RIBA + "(" + KEY_ID + " INTEGER PRIMARY KEY,"
			+ KEY_CREATED_AT + " DATETIME," + KEY_SPECIES + " TEXT,"
			+ KEY_WEIGHT + " FLOAT," + KEY_TECHNIQUE + " TEXT," + KEY_WIDTH
			+ " FLOAT," + KEY_RIVER + " TEXT," + KEY_CAUGHT_BY + " TEXT,"
			+ KEY_EVENT + " TEXT," + KEY_DESCRIPTION + " TEXT,"
			+ KEY_IMAGE_JEDAN + " BLOB"
			// + KEY_IMAGE_DVA + " BLOB,"
			// + KEY_IMAGE_TRI + " BLOB"
			+ ")";

	public DatabaseHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(CREATE_TABLE_NAME);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_RIBA);
		onCreate(db);
	}

	public long createRiba(Riba riba) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(KEY_CREATED_AT, getDateTime());
		values.put(KEY_SPECIES, riba.getvrsta());
		values.put(KEY_WEIGHT, riba.getTezina());
		values.put(KEY_TECHNIQUE, riba.getTehnika());
		values.put(KEY_WIDTH, riba.getDuljina());
		values.put(KEY_RIVER, riba.getVrstarijeke());
		values.put(KEY_CAUGHT_BY, riba.getUpecao());
		values.put(KEY_EVENT, riba.getDogadaj());
		values.put(KEY_DESCRIPTION, riba.getOpis());
		values.put(KEY_IMAGE_JEDAN, riba.slikajedan);
		// values.put(KEY_IMAGE_DVA, riba.slikadva);
		// values.put(KEY_IMAGE_TRI, riba.slikatri);
		long test = db.insert(TABLE_RIBA, null, values);
		return test;
	}

	public List<Riba> getAllRiba() {
		List<Riba> ribaList = new ArrayList<Riba>();
		String selectQuery = "SELECT  * FROM riba";
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);
		if (cursor.moveToFirst()) {
			do {
				Riba riba = new Riba();
				riba.setId(Integer.parseInt(cursor.getString(0)));
				riba.setCreatedAt(cursor.getString(1));
				riba.setVrsta(cursor.getString(2));
				riba.setTezina(Float.parseFloat(cursor.getString(3)));
				riba.setTehnika(cursor.getString(4));
				riba.setDuljina(Float.parseFloat(cursor.getString(5)));
				riba.setVrstarijeke(cursor.getString(6));
				riba.setUpecao(cursor.getString(7));
				riba.setDogadaj(cursor.getString(8));
				riba.setOpis(cursor.getString(9));
				riba.setSlikajedan(cursor.getBlob(10));
				ribaList.add(riba);
			} while (cursor.moveToNext());
		}
		db.close();
		return ribaList;
	}

	public void deleteContact(Riba riba) {
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete(TABLE_RIBA, KEY_ID + " = ?",
				new String[] { String.valueOf(riba.getId()) });
		db.close();
	}

	public int updateContact(Riba riba) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();

		values.put(KEY_SPECIES, riba.getvrsta());
		values.put(KEY_WEIGHT, riba.getTezina());
		values.put(KEY_TECHNIQUE, riba.getTehnika());
		values.put(KEY_WIDTH, riba.getDuljina());
		values.put(KEY_RIVER, riba.getVrstarijeke());
		values.put(KEY_CAUGHT_BY, riba.getUpecao());
		values.put(KEY_DESCRIPTION, riba.getOpis());

		return db.update(TABLE_RIBA, values, KEY_ID + " = ?",
				new String[] { String.valueOf(riba.getId()) });

	}

	public String getDateTime() {
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm",
				Locale.getDefault());
		Date date = new Date();
		return dateFormat.format(date);
	}
}

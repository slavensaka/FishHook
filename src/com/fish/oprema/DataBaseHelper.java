package com.fish.oprema;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DataBaseHelper extends SQLiteOpenHelper {

	private static final int DATABASE_VERSION = 2;
	private static final String DATABASE_NAME = "oprema";

	private static final String TABLE_NAMES = "names";
	private static final String TABLE_OPREME = "opreme";
	private static final String TABLE_NAME_OPREMA = "name_oprema";

	private static final String KEY_ID = "id";
	private static final String KEY_CREATED_AT = "created_at";
	private static final String KEY_NAME = "name";
	private static final String KEY_OPREMA_NAME = "oprema_name";

	private static final String KEY_NAME_ID = "name_id";
	private static final String KEY_OPREMA_ID = "oprema_id";

	private static final String CREATE_TABLE_NAME = "CREATE TABLE "
			+ TABLE_NAMES + "(" + KEY_ID + " INTEGER PRIMARY KEY," + KEY_NAME
			+ " TEXT," + KEY_OPREMA_NAME + " TEXT," + KEY_CREATED_AT
			+ " DATETIME" + ")";

	private static final String CREATE_TABLE_OPREMA = "CREATE TABLE "
			+ TABLE_OPREME + "(" + KEY_ID + " INTEGER PRIMARY KEY,"
			+ KEY_OPREMA_NAME + " TEXT," + KEY_CREATED_AT + " DATETIME" + ")";

	// todo_tag table create statement
	private static final String CREATE_TABLE_NAME_OPREMA = "CREATE TABLE "
			+ TABLE_NAME_OPREMA + "(" + KEY_ID + " INTEGER PRIMARY KEY,"
			+ KEY_NAME_ID + " INTEGER," + KEY_OPREMA_ID + " INTEGER,"
			+ KEY_CREATED_AT + " DATETIME" + ")";

	public DataBaseHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {

		// creating required tables
		db.execSQL(CREATE_TABLE_NAME);
		db.execSQL(CREATE_TABLE_OPREMA);
		db.execSQL(CREATE_TABLE_NAME_OPREMA);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// on upgrade drop older tables
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAMES);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_OPREME);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_OPREMA);

		// create new tables
		onCreate(db);
	}

	public long createName(Name name, long[] opreme) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(KEY_NAME, name.getName());
		values.put(KEY_CREATED_AT, getDateTime());
		values.put(KEY_OPREMA_NAME, name.getOprema());
		long name_id = db.insert(TABLE_NAMES, null, values);
		for (long oprema_id : opreme) {
			createNameOprema(name_id, oprema_id);
		}
		return name_id;
	}

	public Name getName(long name_id) {
		SQLiteDatabase db = this.getReadableDatabase();
		String selectQuery = "SELECT * FROM " + TABLE_NAMES + " WHERE "
				+ KEY_ID + " = " + name_id;
		Cursor c = db.rawQuery(selectQuery, null);
		if (c != null) {
			c.moveToFirst();
		}
		Name td = new Name();
		td.setId(c.getInt(c.getColumnIndex(KEY_ID)));
		td.setName((c.getString(c.getColumnIndex(KEY_NAME))));
		td.setCreatedAt(c.getString(c.getColumnIndex(KEY_CREATED_AT)));
		return td;
	}

	public List<Name> getAllNames() {
		List<Name> names = new ArrayList<Name>();
		String selectQuery = "SELECT * FROM " + TABLE_NAMES;
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor c = db.rawQuery(selectQuery, null);
		if (c.moveToFirst()) {
			do {
				Name td = new Name();
				td.setId(c.getInt(c.getColumnIndex(KEY_ID)));
				td.setName(c.getString(c.getColumnIndex(KEY_NAME)));
				td.setCreatedAt(c.getString(c.getColumnIndex(KEY_CREATED_AT)));
				td.setOprema(c.getString(c.getColumnIndex(KEY_OPREMA_NAME)));
				names.add(td);

			} while (c.moveToNext());
		}
		return names;
	}

	public List<Name> getAllNameByOprema(String oprema_name) {
		List<Name> names = new ArrayList<Name>();
		String selectQuery = "SELECT  * FROM " + TABLE_NAMES + " td, "
				+ TABLE_OPREME + " tg, " + TABLE_NAME_OPREMA + " tt WHERE tg."
				+ KEY_OPREMA_NAME + " = '" + oprema_name + "'" + " AND tg."
				+ KEY_ID + " = " + "tt." + KEY_OPREMA_ID + " AND td." + KEY_ID
				+ " = " + "tt." + KEY_NAME_ID;
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor c = db.rawQuery(selectQuery, null);
		if (c.moveToFirst()) {
			do {
				Name td = new Name();
				td.setId(c.getInt((c.getColumnIndex(KEY_ID))));
				td.setName((c.getString(c.getColumnIndex(KEY_NAME))));
				td.setCreatedAt(c.getString(c.getColumnIndex(KEY_CREATED_AT)));

				names.add(td);
			} while (c.moveToNext());
		}

		return names;
	}

	public int getnameCount() {
		String countQuery = "SELECT * FROM " + TABLE_NAMES;
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(countQuery, null);
		int count = cursor.getCount();
		cursor.close();
		return count;
	}

	public int updateName(Name name) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(KEY_NAME, name.getName());
		return db.update(TABLE_NAMES, values, KEY_ID + " = ?",
				new String[] { String.valueOf(name.getId()) });

	}

	public void deleteName(Integer name_id) {
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete(TABLE_NAMES, KEY_ID + " = ? ",
				new String[] { Integer.toString(name_id) });
		db.delete(TABLE_OPREME, KEY_ID + " = ? ",
				new String[] { Integer.toString(name_id) });
		db.delete(TABLE_NAME_OPREMA, KEY_ID + " = ? ",
				new String[] { Integer.toString(name_id) });
	}

	public long createOprema(Oprema oprema) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_OPREMA_NAME, oprema.getOprema());
		values.put(KEY_CREATED_AT, getDateTime());
		long tag_id = db.insert(TABLE_OPREME, null, values);

		return tag_id;
	}

	public List<Oprema> getAllOprema() {
		List<Oprema> tags = new ArrayList<Oprema>();
		String selectQuery = "SELECT  * FROM " + TABLE_OPREME;

		SQLiteDatabase db = this.getReadableDatabase();
		Cursor c = db.rawQuery(selectQuery, null);

		// looping through all rows and adding to list
		if (c.moveToFirst()) {
			do {
				Oprema t = new Oprema();
				t.setId(c.getInt((c.getColumnIndex(KEY_ID))));
				t.setOprema(c.getString(c.getColumnIndex(KEY_OPREMA_NAME)));

				// adding to tags list
				tags.add(t);
			} while (c.moveToNext());
		}
		return tags;
	}

	public int updateOprema(Oprema oprema) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_OPREMA_NAME, oprema.getOprema());

		// updating row
		return db.update(TABLE_OPREME, values, KEY_ID + " = ?",
				new String[] { String.valueOf(oprema.getId()) });
	}

	public void deleteTag(Oprema oprema, boolean should_delete_all_tag_todos) {
		SQLiteDatabase db = this.getWritableDatabase();

		if (should_delete_all_tag_todos) {

			List<Name> alloprema = getAllNameByOprema(oprema.getOprema());

			for (Name name : alloprema) {

				// deleteName(name.getId());
			}
		}

		db.delete(TABLE_OPREME, KEY_ID + " = ?",
				new String[] { String.valueOf(oprema.getId()) });
	}

	public int updateNoteTag(long id, long oprema_id) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_OPREMA_ID, oprema_id);

		// updating row
		return db.update(TABLE_NAMES, values, KEY_ID + " = ?",
				new String[] { String.valueOf(id) });
	}

	public void deleteNameOprema(long id) {
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete(TABLE_NAMES, KEY_ID + " = ?",
				new String[] { String.valueOf(id) });
	}

	// closing database
	public void closeDB() {
		SQLiteDatabase db = this.getReadableDatabase();
		if (db != null && db.isOpen())
			db.close();
	}

	public long createNameOprema(long name_id, long oprema_id) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(KEY_NAME_ID, name_id);
		values.put(KEY_OPREMA_ID, oprema_id);
		values.put(KEY_CREATED_AT, getDateTime());
		long id = db.insert(TABLE_NAME_OPREMA, null, values);
		return id;
	}

	private String getDateTime() {
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm",
				Locale.getDefault());
		Date date = new Date();
		return dateFormat.format(date);
	}

}

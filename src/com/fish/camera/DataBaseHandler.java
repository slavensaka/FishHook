package com.fish.camera;


import java.util.ArrayList;
import java.util.List;



import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DataBaseHandler extends SQLiteOpenHelper {
		
		private static final int DATABASE_VERSION = 1;
		private static final String DATABASE_NAME = "imagedb";
		private static final String TABLE_CONTACTS = "contacts";

		private static final String KEY_ID = "id";
		private static final String KEY_NAME = "name";
		private static final String KEY_IMAGE = "image";

		public DataBaseHandler(Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_CONTACTS + "("
					+ KEY_ID + " INTEGER PRIMARY KEY," + KEY_NAME + " TEXT,"
					+ KEY_IMAGE + " BLOB" + ")";
			db.execSQL(CREATE_CONTACTS_TABLE);
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			// Drop older table if existed
			db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTACTS);

			// Create tables again
			onCreate(db);
		}

		/**
		 * All CRUD(Create, Read, Update, Delete) Operations
		 */

		public// Adding new contact
		void addContact(Camera camera) {
			SQLiteDatabase db = this.getWritableDatabase();

			ContentValues values = new ContentValues();
			values.put(KEY_NAME, camera._name); // Contact Name
			values.put(KEY_IMAGE, camera._image); // Contact Phone

			// Inserting Row
			db.insert(TABLE_CONTACTS, null, values);
			db.close(); // Closing database connection
		}

		// Getting single contact
		Camera getContact(int id) {
			SQLiteDatabase db = this.getReadableDatabase();

			Cursor cursor = db.query(TABLE_CONTACTS, new String[] { KEY_ID,
					KEY_NAME, KEY_IMAGE }, KEY_ID + "=?",
					new String[] { String.valueOf(id) }, null, null, null, null);
			if (cursor != null)
				cursor.moveToFirst();

			Camera camera = new Camera(Integer.parseInt(cursor.getString(0)),
					cursor.getString(1), cursor.getBlob(1));

			// return contact
			return camera;

		}

		// Getting All Contacts
		public List<Camera> getAllContacts() {
			List<Camera> contactList = new ArrayList<Camera>();
			// Select All Query
			String selectQuery = "SELECT  * FROM contacts ORDER BY name";
			SQLiteDatabase db = this.getWritableDatabase();
			Cursor cursor = db.rawQuery(selectQuery, null);
			// looping through all rows and adding to list
			if (cursor.moveToFirst()) {
				do {
					Camera camera = new Camera();
					camera.setID(Integer.parseInt(cursor.getString(0)));
					camera.setName(cursor.getString(1));
					camera.setImage(cursor.getBlob(2));
					// Adding contact to list
					contactList.add(camera);
				} while (cursor.moveToNext());
			}
			// close inserting data from database
			db.close();
			// return contact list
			return contactList;

		}

		// Updating single contact
		public int updateContact(Camera camera) {
			SQLiteDatabase db = this.getWritableDatabase();

			ContentValues values = new ContentValues();
			values.put(KEY_NAME, camera.getName());
			values.put(KEY_IMAGE, camera.getImage());

			// updating row
			return db.update(TABLE_CONTACTS, values, KEY_ID + " = ?",
					new String[] { String.valueOf(camera.getID()) });

		}

		// Deleting single contact
		public void deleteContact(Camera camera) {
			SQLiteDatabase db = this.getWritableDatabase();
			db.delete(TABLE_CONTACTS, KEY_ID + " = ?",
					new String[] { String.valueOf(camera.getID()) });
			db.close();
		}

		// Getting contacts Count
		public int getContactsCount() {
			String countQuery = "SELECT  * FROM " + TABLE_CONTACTS;
			SQLiteDatabase db = this.getReadableDatabase();
			Cursor cursor = db.rawQuery(countQuery, null);
			cursor.close();

			// return count
			return cursor.getCount();
		}

	}






















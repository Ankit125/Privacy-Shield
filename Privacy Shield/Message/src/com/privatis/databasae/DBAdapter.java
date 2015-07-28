package com.privatis.databasae;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBAdapter {

	public static final String Contect_id = "ContectId";
	public static final String Contect_name = "Name";
	public static final String Contect_number = "Number";
	public static final String Contect_email = "email";

	public static final String Sysnc_id = "id";
	public static final String Sync_flag = "Flag";
	public static final String Sync_Date_Time = "DateTime";

	private static final String TAG = "DBAdapter";

	private static final String DATABASE_NAME = "Privatis_Contect.db";
	private static final String DATABASE_LOGIN = "Contect";
	private static final String DATABASE_Sync_Table = "sync_status";
	private static final int DATABASE_VERSION = 1;

	private static final String DATABASE_CREATE_LOGIN = "create table Contect(ContectId INTEGER PRIMARY KEY AUTOINCREMENT,Name TEXT,Number TEXT,email TEXT);";

	private static final String DATABASE_CREATE_SYNC = "create table sync_status(id INTEGER,Flag INTEGER,DateTime TEXT);";

	private final Context context;

	private DatabaseHelper DBHelper;
	private static SQLiteDatabase db;

	public DBAdapter(Context ctx) {
		this.context = ctx;
		DBHelper = new DatabaseHelper(context);
	}

	private static class DatabaseHelper extends SQLiteOpenHelper {
		DatabaseHelper(Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			db.execSQL(DATABASE_CREATE_LOGIN);
			db.execSQL(DATABASE_CREATE_SYNC);
			// db.execSQL(DATABASE_CREATE_HOTELDETAILS);
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			db.execSQL("DROP TABLE IF EXISTS " + DATABASE_LOGIN);
			db.execSQL("DROP TABLE IF EXESTS " + DATABASE_Sync_Table);
			onCreate(db);
		}
	}

	public DBAdapter open() throws SQLException {
		db = DBHelper.getWritableDatabase();
		return this;

	}

	public void close() {
		DBHelper.close();

	}

	public void delete_table() {
		db.execSQL("DROP TABLE IF EXISTS " + DATABASE_LOGIN);
		db.execSQL(DATABASE_CREATE_LOGIN);
	}

	// public Boolean isOpen() {
	// return db.isOpen();
	// }

	public long insert(String Name, String Number, String Email) {

		ContentValues initialvalues = new ContentValues();
		// initialvalues.put(Contect_id, id);
		initialvalues.put(Contect_name, Name);
		initialvalues.put(Contect_number, Number);
		initialvalues.put(Contect_email, Email);
		// return db.insert(DATABASE_LOGIN, null, initialvalues);
		long rows = db.insertWithOnConflict(DATABASE_LOGIN, null,
				initialvalues, SQLiteDatabase.CONFLICT_REPLACE);
		return rows;
	}

	// public long insert_sync(Integer Flag, String DateTime) {
	// ContentValues initialvalues = new ContentValues();
	// initialvalues.put(Sysnc_id, 1);
	// initialvalues.put(Sync_flag, Flag);
	// initialvalues.put(Sync_Date_Time, DateTime);
	// return db.insert(DATABASE_Sync_Table, null, initialvalues);
	// }
	//
	// public boolean update_DateTime(String datetime) {
	// ContentValues initialvalues = new ContentValues();
	// initialvalues.put(Sync_Date_Time, datetime);
	// return db.update(DATABASE_Sync_Table, initialvalues,
	// Sysnc_id + "=" + 1, null) > 0;
	// }
	//
	// public boolean update_flag(Integer flag) {
	// ContentValues initialvalues = new ContentValues();
	// initialvalues.put(Sync_flag, flag);
	// return db.update(DATABASE_Sync_Table, initialvalues,
	// Sysnc_id + "=" + 1, null) > 0;
	// }
	//
	// public Cursor getalldatafromsync() {
	// return db.query(DATABASE_Sync_Table, new String[] { Sync_flag,
	// Sync_Date_Time }, null, null, null, null, null);
	// }

	public boolean deleteall() {
		return db.delete(DATABASE_LOGIN, null, null) > 0;
	}

	public Cursor getalldata() {
		return db.query(DATABASE_LOGIN, new String[] { Contect_id,
				Contect_name, Contect_number, Contect_email }, null, null,
				null, null, null);
	}

	// public boolean update(String id, String f_name, String l_name,
	// String unique_id, String email, String password, String mobile,
	// String gender, String points, String remember, String payment_status) {
	// ContentValues initialvalues = new ContentValues();
	// initialvalues.put(login_id, id);
	// initialvalues.put(login_f_name, f_name);
	// initialvalues.put(login_l_name, l_name);
	// initialvalues.put(login_unique_id, unique_id);
	// initialvalues.put(login_emial, email);
	// initialvalues.put(login_password, password);
	// initialvalues.put(login_mobile, mobile);
	// initialvalues.put(login_gender, gender);
	// initialvalues.put(login_points, points);
	// initialvalues.put(login_remember, remember);
	// initialvalues.put(login_payment_status, payment_status);
	// return db.update(DATABASE_LOGIN, initialvalues, login_test + "=" + 1,
	// null) > 0;
	// }

	public boolean update(String id, String Name, String Number, String Email) {
		ContentValues initialvalues = new ContentValues();

		initialvalues.put(Contect_name, Name);
		initialvalues.put(Contect_number, Number);
		initialvalues.put(Contect_email, Email);
		return db.update(DATABASE_LOGIN, initialvalues, Contect_id + "=" + id,
				null) > 0;
	}

}

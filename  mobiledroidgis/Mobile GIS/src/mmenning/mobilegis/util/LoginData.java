/*
 * Copyright 2012 Mathias Menninghaus (mathias.menninghaus (at) googlemail (dot) com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package mmenning.mobilegis.util;

import mmenning.mobilegis.database.SQLiteOnSDCard;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

/**
 * 
 * @author Mathias Menninghaus
 * @version 03.06.2010
 *
 */
public class LoginData {

	private static final String DT = "LoginData";
	private static String DATABASE_NAME = "LoginData";
	private static int DATABASE_VERSION = 1;

	private static String LOGIN_TABLE = "Login";

	private static String HOST = "host";
	private static String USERNAME = "username";
	private static String PASSWORD = "password";

	private static String CREATE_LOGIN = "create table " + LOGIN_TABLE + "("
			+ HOST + " text primary key, " + USERNAME + " text, " + PASSWORD
			+ " text)";

	public static int USER = 0;
	public static int PW = 1;

	/**
	 * Inserts username and password for a host. If there already was one, it
	 * will be overriden.
	 * 
	 * @param host
	 * @param username
	 * @param password
	 */
	public void insertLogin(String host, String username, String password) {

		Log.d(DT, "Saving PW: "+password);
		
		db.execSQL("INSERT OR REPLACE INTO " + LOGIN_TABLE + " (" + HOST + ", "
				+ USERNAME + ", " + PASSWORD + ") VALUES ('" + host + "','"
				+ username + "','" + password + "')");
	}

	/**
	 * Return username and password for a given host in a String[], or null if
	 * it does not exist.
	 * 
	 * @param host
	 * @return
	 */
	public String[] getLogin(String host) {

		String[] ret = null;

		Cursor c = db.query(LOGIN_TABLE, new String[] { USERNAME, PASSWORD },
				HOST + "='" + host + "'", null, null, null, null);

		if (c.moveToFirst()) {
			ret = new String[2];
			ret[USER] = c.getString(0);
			ret[PW] = c.getString(1);
		}

		c.close();
		return ret;
	}

	/**
	 * Helper Class to manage connection and first-time generation of the
	 * database
	 */
	private DatabaseHelper DBHelper;

	/**
	 * the SQLite Database
	 */
	private SQLiteDatabase db;
	/**
	 * Context (e.g. Android-Activity) to which this model-instance is related
	 */
	private final Context context;

	/**
	 * Sets context of this class (e.g. Android-Activity)
	 * 
	 * @param ctx
	 *            Context on which the database works
	 */
	public LoginData(Context ctx) {
		this.context = ctx;
		DBHelper = new DatabaseHelper(context);
	}

	/**
	 * Close Database
	 */
	public void close() {
		db.close();
		DBHelper.close();
	}

	/**
	 * Connects to the database and returns this-Object If there already is an
	 * connection it will be closed an then a new one will be opened.
	 * 
	 * @return Connection to the database.
	 * @throws SQLException
	 *             if something goes wrong
	 */
	public LoginData open() throws SQLException {
		db = DBHelper.getWritableDatabase();
		return this;
	}

	/**
	 * Connects to databas in Read-Only. One should only access query methods!
	 * 
	 * @return
	 * @throws SQLExcpetion
	 */
	public LoginData openReadOnly() throws SQLException {
		db = DBHelper.getReadableDatabase();
		return this;
	}

	/**
	 * Inner Class for managing the connection and first-time initialization of
	 * the database
	 * 
	 * @author Mathias Menninghaus (mmening@uos.de)
	 * @version 22.06.2009
	 */
	private static class DatabaseHelper extends SQLiteOnSDCard {

		/**
		 * Create an new DatabaseHelper / SQLiteOpenHelper Class
		 * 
		 * @param context
		 */
		DatabaseHelper(Context context) {
			super(context, DATABASE_NAME, DATABASE_VERSION);
		}

		/**
		 * Only called when the database is created for the first time
		 */
		public void onCreate(SQLiteDatabase sqldb) {

			sqldb.execSQL(CREATE_LOGIN);

		}

		@Override
		public void onUpgrade(SQLiteDatabase sqldb, int oldVersion,
				int newVersion) {
			sqldb.execSQL("DROP TABLE IF EXISTS " + LOGIN_TABLE);

			onCreate(sqldb);

		}
	}

}

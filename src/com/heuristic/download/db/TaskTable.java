package com.heuristic.download.db;

import android.database.sqlite.SQLiteDatabase;

public class TaskTable implements DatabaseTable {

	public static final String TABLE_TASKS = "task";
	public static final String COLUMN_ID = "id";
	public static final String COLUMN_TYPE = "type";
	public static final String COLUMN_UUID = "uuid";
	public static final String COLUMN_URL = "url";
	public static final String COLUMN_FILE = "file";
	public static final String COLUMN_APP = "app";
	public static final String COLUMN_SIZE = "size";
	public static final String COLUMN_CREATEDON = "createdon";
	
	// Database creation sql statement
	public static final String DATABASE_CREATE = String
				.format("create table %s(%s integer primary key autoincrement, %s integer, %s text not null, %s text not null, %s text not null, %s text not null, %s long, %s long)",
						TABLE_TASKS, COLUMN_ID, COLUMN_TYPE, COLUMN_UUID, COLUMN_URL,
						COLUMN_FILE, COLUMN_APP, COLUMN_SIZE, COLUMN_CREATEDON);
	@Override
	public void onCreate(SQLiteDatabase database) {
		database.execSQL(DATABASE_CREATE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_TASKS);
		onCreate(db);
		
	}

}

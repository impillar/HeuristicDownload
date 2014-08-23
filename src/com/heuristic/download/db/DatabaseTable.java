package com.heuristic.download.db;

import android.database.sqlite.SQLiteDatabase;

public interface DatabaseTable {

	void onCreate(SQLiteDatabase database);

	void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion);
}

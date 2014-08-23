package com.heuristic.download.db;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class HolisticSQLiteOpenHelper extends SQLiteOpenHelper {

	private static final String DATABASE_NAME = "heuristicdownload.db";
	private static final int DATABASE_VERSION = 1;

	@SuppressWarnings("serial")
	private List<DatabaseTable> tables = new ArrayList<DatabaseTable>(){{
		this.add(new TaskTable());
	}};

	public HolisticSQLiteOpenHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase database) {
		for (DatabaseTable dt : tables){
			dt.onCreate(database);
		}
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		for (DatabaseTable dt : tables){
			dt.onUpgrade(db, oldVersion, newVersion);
		}
	}

}

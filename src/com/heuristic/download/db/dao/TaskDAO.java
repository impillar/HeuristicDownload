package com.heuristic.download.db.dao;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.heuristic.download.db.HolisticSQLiteOpenHelper;
import com.heuristic.download.db.TaskTable;

public class TaskDAO {

	private SQLiteDatabase database;
	private HolisticSQLiteOpenHelper sqliteHelper;
	private String[] allColumns = {
			TaskTable.COLUMN_ID,
			TaskTable.COLUMN_UUID,
			TaskTable.COLUMN_URL,
			TaskTable.COLUMN_FILE,
			TaskTable.COLUMN_APP,
			TaskTable.COLUMN_SIZE,
			TaskTable.COLUMN_CREATEDON
	};
	
	private static TaskDAO taskDAO = null;
	
	private TaskDAO(Context context){
		sqliteHelper = new HolisticSQLiteOpenHelper(context);
	}
	
	public static TaskDAO v(Context context){
		if (taskDAO == null){
			taskDAO = new TaskDAO(context);
		}
		return taskDAO;
	}
	
	public TaskDAO open() throws SQLException{
		if (database == null)
			database = sqliteHelper.getWritableDatabase();
		return taskDAO;
	}
	
	public void close(){
		sqliteHelper.close();
	}
	
	public Task createTask(Task task){
		ContentValues values = new ContentValues();
		values.put(TaskTable.COLUMN_UUID, task.getUuid());
		values.put(TaskTable.COLUMN_URL, task.getUrl());
		values.put(TaskTable.COLUMN_FILE, task.getFile());
		values.put(TaskTable.COLUMN_APP, task.getApp());
		values.put(TaskTable.COLUMN_SIZE, task.getSize());
		values.put(TaskTable.COLUMN_CREATEDON, task.getCreatedon());
		
		long insertId = database.insert(TaskTable.TABLE_TASKS, null, values);
		Cursor cursor = database.query(TaskTable.TABLE_TASKS, allColumns, String.format("%s = '%s'", TaskTable.COLUMN_ID, insertId), null, null, null, null);
		cursor.moveToFirst();
		
		Task insertedTask = cursorToTask(cursor);
		cursor.close();
		return insertedTask;
	}
	
	private Task cursorToTask(Cursor cursor){
		Task task = new Task();
		task.setId(cursor.getInt(cursor.getColumnIndex(TaskTable.COLUMN_ID)));
		task.setUuid(cursor.getString(cursor.getColumnIndex(TaskTable.COLUMN_UUID)));
		task.setUrl(cursor.getString(cursor.getColumnIndex(TaskTable.COLUMN_URL)));
		task.setApp(cursor.getString(cursor.getColumnIndex(TaskTable.COLUMN_APP)));
		task.setFile(cursor.getString(cursor.getColumnIndex(TaskTable.COLUMN_FILE)));
		task.setSize(cursor.getLong(cursor.getColumnIndex(TaskTable.COLUMN_SIZE)));
		task.setCreateon(cursor.getLong(cursor.getColumnIndex(TaskTable.COLUMN_CREATEDON)));
		return task;
	}
	
	public void deleteTask(int id){
		database.delete(TaskTable.TABLE_TASKS, String.format("%s = '%d'", TaskTable.COLUMN_ID, id), null);
	}
	
	public List<Task> getAllTasks(){
		List<Task> tasks = new ArrayList<Task>();
		Cursor cursor = database.query(TaskTable.TABLE_TASKS, allColumns, null, null, null, null, null, null);
		cursor.moveToFirst();
		while (!cursor.isAfterLast()){
			Task task = cursorToTask(cursor);
			tasks.add(task);
			cursor.moveToNext();
		}
		cursor.close();
		return tasks;
	}
}

package com.heuristic.download.activities.ds;

import android.content.pm.ApplicationInfo;

import com.heuristic.download.db.dao.Task;

public class TaskEntry {

	protected Task taskInfo;
	protected ApplicationInfo appInfo;
	
	public TaskEntry(Task task, ApplicationInfo ai){
		this.taskInfo = task;
		this.appInfo = ai;
	}
	
	public Task getTaskInfo() {
		return taskInfo;
	}
	public void setTaskInfo(Task taskInfo) {
		this.taskInfo = taskInfo;
	}
	public ApplicationInfo getAppInfo() {
		return appInfo;
	}
	public void setApp(ApplicationInfo app) {
		this.appInfo = app;
	}
	
}

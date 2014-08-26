package com.heuristic.download.activities.ds;

import android.content.pm.ApplicationInfo;
import android.os.Parcel;
import android.os.Parcelable;

import com.heuristic.download.db.dao.Task;

public class TaskEntry implements Parcelable {
	
	protected Task taskInfo;
	protected ApplicationInfo appInfo;
	
	public TaskEntry(Task task, ApplicationInfo ai){
		this.taskInfo = task;
		this.appInfo = ai;
	}
	
	public TaskEntry(Parcel in) {
		
		this.taskInfo = in.readParcelable(Task.class.getClassLoader());
		this.appInfo = in.readParcelable(ApplicationInfo.class.getClassLoader());
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

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeParcelable(taskInfo, flags);
		dest.writeParcelable(appInfo, flags);
	}
	public static final Parcelable.Creator<TaskEntry> CREATOR = new Parcelable.Creator<TaskEntry>() {
		public TaskEntry createFromParcel(Parcel in) {
		    return new TaskEntry(in);
		}
		
		public TaskEntry[] newArray(int size) {
		    return new TaskEntry[size];
		}
	};
}

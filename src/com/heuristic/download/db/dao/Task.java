package com.heuristic.download.db.dao;

import edu.ntu.cltk.date.DateFormater;
import android.os.Parcel;
import android.os.Parcelable;

public class Task implements Parcelable{
	
	public static int DOWNLOAD = 0;
	public static int UPLOAD = 1;
	
	private int id;
	private int type;
	private String uuid;
	private String url;
	private String file;
	private String app;
	private long size;
	private long createdon;
	
	public Task(){
		
	}
	
	public Task(Parcel in){
		this.id = in.readInt();
		this.type = in.readInt();
		this.uuid = in.readString();
		this.url = in.readString();
		this.file = in.readString();
		this.app = in.readString();
		this.size = in.readLong();
		this.createdon = in.readLong();
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getType(){
		return type;
	}
	public void setType(int type){
		this.type = type;
	}
	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getFile() {
		return file;
	}
	public void setFile(String file) {
		this.file = file;
	}
	public String getApp() {
		return app;
	}
	public void setApp(String app) {
		this.app = app;
	}
	public long getSize() {
		return size;
	}
	public void setSize(long size) {
		this.size = size;
	}
	public long getCreatedon(){
		return createdon;
	}
	public void setCreateon(long createdon) {
		this.createdon = createdon;
	}
	
	public static final Parcelable.Creator<Task> CREATOR = new Parcelable.Creator<Task>() {
		public Task createFromParcel(Parcel in) {
		    return new Task(in);
		}
		
		public Task[] newArray(int size) {
		    return new Task[size];
		}
	};
	
	@Override
	public int describeContents() {
		return 0;
	}
	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(id);
		dest.writeInt(type);
		dest.writeString(uuid);
		dest.writeString(url);
		dest.writeString(file);
		dest.writeString(app);
		dest.writeLong(size);
		dest.writeLong(createdon);
	}
	
	@Override
	public String toString(){
		StringBuilder sb = new StringBuilder();
		sb.append(String.format("{id:%d, type:%d, uuid:%s, url:%s, file:%s, app:%s, size:%d, createdon:%s}",id, type, uuid, url, file, app, size, DateFormater.formatString(createdon, "MM/dd HH:mm")));
		return sb.toString();
	}
	
	/**
	 * Return the download progress
	 * @param downloaded
	 * @param total
	 * @return
	 */
	public static double calProgress(long downloaded, long total){
		if (total <= 0)	return 0;
		if (downloaded > total) return 1;
		return downloaded * 1.0 / total;
	}
	
}

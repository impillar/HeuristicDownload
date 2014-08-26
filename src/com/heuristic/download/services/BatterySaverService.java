package com.heuristic.download.services;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.battery.batterysaver.DownloadTaskImpl;
import com.battery.batterysaver.interfaces.DownloadTask;
import com.heuristic.download.R;
import com.heuristic.download.activities.TaskDetail;
import com.heuristic.download.activities.ds.TaskEntry;
import com.heuristic.download.aidl.IBatterySaver;
import com.heuristic.download.db.dao.Task;
import com.heuristic.download.db.dao.TaskDAO;
import com.heuristic.download.util.Initiator;

import edu.ntu.cltk.android.pm.PackageMgr;

public class BatterySaverService extends Service {

	private Map<String, DownloadTask> taskMap = new HashMap<String, DownloadTask>();
	
	@Override
	public IBinder onBind(Intent intent) {
		return mBinder;
	}
	
	private void receivedDownloadRequest(Task task){
		
		ApplicationInfo ai = PackageMgr.searchApplicationInfo(this, task.getApp());
		
		Intent intent = new Intent(this, TaskDetail.class);
		intent.putExtra("task", new TaskEntry(task, ai));
		
		NotificationCompat.Builder mBuilder =
			    new NotificationCompat.Builder(this)
				.setSmallIcon(R.drawable.ic_notification)
			    .setContentTitle("Download Request")
			    .setContentText(String.format("%s asks to download the file at %s", (ai==null?task.getApp():ai.processName), task.getUrl()))
			    .setContentIntent(PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT))
			    .setAutoCancel(true);
		
		// Sets an ID for the notification
		int mNotificationId = 001;
		// Gets an instance of the NotificationManager service
		NotificationManager mNotifyMgr = 
		        (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
		// Builds the notification and issues it.
		mNotifyMgr.notify(mNotificationId, mBuilder.build());
	}
	
	private CharSequence downloadTask(String appId, String url, long duration, String folder) 
	{
		if (!folder.endsWith("/") && !folder.endsWith("\\")){
			folder += "/";
		}
		DownloadTask dt = new DownloadTaskImpl(appId, url, duration, folder);
		dt.startDownload();			
		
		UUID uuid = UUID.randomUUID();
		taskMap.put(uuid.toString(), dt);
		
		Task task = new Task();
		task.setApp(appId);
		task.setFile(folder+((DownloadTaskImpl)dt).getFileName());
		task.setSize(((DownloadTaskImpl)dt).getFileCapacity());
		task.setCreateon(System.currentTimeMillis());
		task.setUrl(url);
		task.setUuid(uuid.toString());
		TaskDAO.v(BatterySaverService.this).open().createTask(task);
		
		if (Initiator.DEBUG){
			Log.w("BatterySaverService", task.toString());
		}
		
		receivedDownloadRequest(task);
		return uuid.toString();
	}
	
	private final IBatterySaver.Stub mBinder = new IBatterySaver.Stub() {

		@Override
		public CharSequence download(String appId, String url, long duration, String folder)
				throws RemoteException {
			
			return downloadTask(appId, url, duration, folder);
		}

		@Override
		public double getProgress(CharSequence uuid) throws RemoteException {
			DownloadTask dt = null;
			if ((dt = taskMap.get(uuid)) != null){
				return dt.getProgress();
			}
			return 0;
		}
		
    };

}

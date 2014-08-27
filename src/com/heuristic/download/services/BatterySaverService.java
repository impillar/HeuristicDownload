package com.heuristic.download.services;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
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
	
	private CharSequence downloadTask(String appId, String url, long duration, String fileName) 
	{
		//Check if the current task is already contained
		for (Entry<String, DownloadTask> item : taskMap.entrySet()){
			
			DownloadTaskImpl sDTI = (DownloadTaskImpl)item.getValue();
			if (sDTI.getUrl().equals(url) && sDTI.getFileName().equals(fileName)){
				return item.getKey();
			}
		}
		
		DownloadTask dt = new DownloadTaskImpl(appId, url, duration, fileName);
		dt.startDownload();			
		
		UUID uuid = UUID.randomUUID();
		taskMap.put(uuid.toString(), dt);
		
		Task task = new Task();
		task.setApp(appId);
		task.setFile(fileName);
		task.setSize(-1);
		task.setCreateon(System.currentTimeMillis());
		task.setUrl(url);
		task.setUuid(uuid.toString());
		final int taskId = TaskDAO.v(BatterySaverService.this).open().createTask(task).getId();
		task.setId(taskId);
		
		if (Initiator.DEBUG){
			Log.w("BatterySaverService", task.toString());
		}
		
		receivedDownloadRequest(task);
		
		final DownloadTaskImpl dti = (DownloadTaskImpl)dt;
		//We continue to query the lenght of downloaded file
		Thread thr = new Thread(new Runnable(){

			int count = 10;
			
			@Override
			public void run() {
				do{
					if (Initiator.DEBUG){
						Log.w("BatterySaverService", String.format("File length = %d", dti.getFileCapacity()));
					}
					if (dti.getFileCapacity() <= 0){
						try {
							count--;
							Thread.sleep(5 * 1000);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							//e.printStackTrace();
						}
					}else{
						TaskDAO.v(BatterySaverService.this).open().updateTaskSize(dti.getFileCapacity(), taskId);
						break;
					}
				}while (count>0);
			}
			
		});
		thr.start();
		
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

package com.heuristic.download.activities;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;

import com.battery.batterysaver.DownloadTaskImpl;
import com.battery.batterysaver.interfaces.DownloadTask;
import com.heuristic.download.aidl.IBatterySaver;

public class BatterySaverService extends Service {

	private Map<String, DownloadTask> tasks = new HashMap<String, DownloadTask>();
	
	@Override
	public IBinder onBind(Intent intent) {
		return mBinder;
	}
	
	private final IBatterySaver.Stub mBinder = new IBatterySaver.Stub() {

		@Override
		public CharSequence download(String appId, String url, long duration, String folder)
				throws RemoteException {
			
			DownloadTask dt = new DownloadTaskImpl(appId, url, duration, folder);
			dt.startDownload();			
			
			UUID uuid = UUID.randomUUID();
			tasks.put(uuid.toString(), dt);
			
			return uuid.toString();
		}

		@Override
		public double getProgress(CharSequence uuid) throws RemoteException {
			DownloadTask dt = null;
			if ((dt = tasks.get(uuid)) != null){
				return dt.getProgress();
			}
			return 0;
		}
		
    };

}

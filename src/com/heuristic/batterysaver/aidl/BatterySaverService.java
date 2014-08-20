package com.heuristic.batterysaver.aidl;

import java.util.UUID;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;

import com.battery.batterysaver.interfaces.DownloadTask;

public class BatterySaverService extends Service {

	private DownloadTask dt;
	
	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return mBinder;
	}
	
	private final IBatterySaver.Stub mBinder = new IBatterySaver.Stub() {

		@Override
		public CharSequence download(String appId, String url, long duration, String folder)
				throws RemoteException {
			
			dt = new DownloadTaskImpl(url, );
			
			UUID uuid = UUID.randomUUID();
			return uuid.toString();
		}

		@Override
		public double getProgress(CharSequence uuid) throws RemoteException {
			// TODO Auto-generated method stub
			return 0;
		}
		
    };

}

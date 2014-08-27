package com.heuristic.download.util;

import android.content.Context;
import android.content.Intent;

import com.heuristic.download.services.BatterySaverService;

/**
 * The initiator of the application.
 * It will start the necessary services, create database
 * @author pillar
 *
 */
public class Initiator {

	private Context mContext;
	public static final boolean DEBUG = true;
	
	public static final String UPDATE_FILE = "hd_installer.apk";
	public static final String APPLICATION_NAME = "HeuristicDownloader";
	public static final String UPDATE_URL = "https://github.com/impillar/HeuristicDownload/blob/master/products/HeuristicDownloader.apk?raw=true";
	
	private Initiator(Context context){
		this.mContext = context;
	}
	
	private static Initiator initiator = null;
	
	public static Initiator v(Context context){
		if (initiator == null){
			initiator = new Initiator(context);
		}
		return initiator;
	}
	
	public void theCreation(){
		
		startServices();
	}
	
	/**
	 * Start services which the library needs
	 */
	private void startServices(){
		
		Intent intent = new Intent(mContext, BatterySaverService.class);
		mContext.startService(intent);
	}
}

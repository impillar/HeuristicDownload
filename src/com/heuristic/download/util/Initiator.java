package com.heuristic.download.util;

import java.util.UUID;

import android.content.Context;
import android.content.Intent;

import com.heuristic.download.activities.BatterySaverService;
import com.heuristic.download.db.dao.Task;
import com.heuristic.download.db.dao.TaskDAO;

/**
 * The initiator of the application.
 * It will start the necessary services, create database
 * @author pillar
 *
 */
public class Initiator {

	private Context mContext;
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

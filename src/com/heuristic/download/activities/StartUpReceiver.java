package com.heuristic.download.activities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class StartUpReceiver extends BroadcastReceiver {
	public StartUpReceiver() {
	}

	@Override
	public void onReceive(Context context, Intent intent) {
		if (Intent.ACTION_BOOT_COMPLETED.equals(intent.getAction())) {
            Intent serviceIntent = new Intent(context, BatterySaverService.class);
            context.startService(serviceIntent);
        }
	}
}

package com.heuristic.download.activities;

import java.io.File;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.heuristic.download.R;
import com.heuristic.download.util.Initiator;

/**
 * A simple {@link android.support.v4.app.Fragment} subclass. Activities that
 * contain this fragment must implement the
 * {@link AboutFragment.OnFragmentInteractionListener} interface to handle
 * interaction events. Use the {@link AboutFragment#newInstance} factory method
 * to create an instance of this fragment.
 * 
 */
public class AboutFragment extends Fragment {
	// TODO: Rename parameter arguments, choose names that match
	// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

	private Context mContext;
	private long downloadId = -1;
	
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		
		mContext = this.getActivity().getApplicationContext();

		// when initialize
		mContext.registerReceiver(downloadCompleteReceiver, downloadCompleteIntentFilter);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		View root = inflater.inflate(R.layout.fragment_about, container, false);
		TextView tv = (TextView) root.findViewById(R.id.about_update);
		tv.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				
				if (downloadId != -1){
					//The update process is already launched, ignore the request
					Toast.makeText(mContext, "Update process is already launched", Toast.LENGTH_SHORT).show();
					return;
				}
				
				DownloadManager downloadManager = (DownloadManager) mContext.getSystemService(Context.DOWNLOAD_SERVICE);
				String url = Initiator.UPDATE_URL;
				DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));

				// only download via WIFI
				request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI);
				request.setTitle(Initiator.APPLICATION_NAME);
				request.setDescription("Updating the latest version from " + Initiator.UPDATE_URL);

				// we just want to download silently
				//request.setVisibleInDownloadsUi(false);
				//request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_HIDDEN);
				request.setDestinationInExternalFilesDir(mContext, null, Initiator.UPDATE_FILE);
				downloadId = downloadManager.enqueue(request);
			}
			
		});
		return root;
	}
	
	private String downloadCompleteIntentName = DownloadManager.ACTION_DOWNLOAD_COMPLETE;
	private IntentFilter downloadCompleteIntentFilter = new IntentFilter(downloadCompleteIntentName);
	private BroadcastReceiver downloadCompleteReceiver = new BroadcastReceiver() {
	    @Override
	    public void onReceive(Context context, Intent intent) {
	    	long id = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, 0L);
	    	if (id != downloadId) {
	    	    //Log.v(TAG, "Ingnoring unrelated download " + id);
	    	    return;
	    	}
	    	
	    	downloadId = -1;
	    	
	    	Intent installIntent = new Intent(Intent.ACTION_VIEW);
	    	installIntent.setDataAndType(Uri.fromFile(new File(mContext.getExternalFilesDir(null), Initiator.UPDATE_FILE)),"application/vnd.android.package-archive");
	    	startActivity(installIntent);
	    }
	};


}

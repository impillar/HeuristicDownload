package com.heuristic.download.activities;

import android.app.DownloadManager;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

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
	
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		
		mContext = this.getActivity().getApplicationContext();
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
				DownloadManager downloadManager = (DownloadManager) mContext.getSystemService(Context.DOWNLOAD_SERVICE);
				String url = Initiator.UPDATE_URL;
				DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));

				// only download via WIFI
				request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI);
				request.setTitle(Initiator.APPLICATION_NAME);
				request.setDescription("Updating to a latest version");

				// we just want to download silently
				//request.setVisibleInDownloadsUi(false);
				//request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_HIDDEN);
				request.setDestinationInExternalFilesDir(mContext, null, Initiator.UPDATE_FILE);

				downloadManager.enqueue(request);
			}
			
		});
		return root;
	}

}

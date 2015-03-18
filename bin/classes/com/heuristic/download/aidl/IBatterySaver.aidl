package com.heuristic.download.aidl;

interface IBatterySaver {

	/**
	 * To download a file in a specific url, and store it in a specific folder
	 * The download process needs to be limited in "duration" seconds
	 * @param appId
	 * @param url
	 * @param duration
	 * @param file
	 * @return UUID, a unique identifier for the download request.
	 */
	CharSequence download(String appId, String url, long duration, String folder);
	
	/**
	 * To upload a file to a specific url.
	 * @param appId
	 * @param url
	 * @param duration
	 * @param file
	 * @return UUID, a unique identifier for the upload request.
	 */
	CharSequence upload(String appId, String url, long duration, String file);
	
	/**
	 * Users can query the current progress of requested download, by specifying the UUID of the request
	 * @param uuid
	 * @return
	 */
	double getProgress(CharSequence uuid);
}

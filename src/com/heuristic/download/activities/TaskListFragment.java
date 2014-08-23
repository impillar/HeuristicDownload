package com.heuristic.download.activities;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.heuristic.download.R;
import com.heuristic.download.activities.ds.TaskEntry;
import com.heuristic.download.db.dao.Task;
import com.heuristic.download.db.dao.TaskDAO;

import edu.ntu.cltk.android.pm.PackageMgr;

/**
 * The lifecycle for fragment activity
 * 
 * Start the application: onAttach onCreate onCreateView onActivityCreated
 * onStart onResume
 * 
 * Pause the application: onPause onStop
 * 
 * Re-enter the application: onStart onResume
 * 
 * Quit the application: onPause onStop onDestroyView onDestroy onDetach
 * 
 * onAttach: invoked when establishing a connection between fragment and
 * activity onActivityCreated: invoked after the invocation of onCreate in
 * Activity onDetach: invoked when fragment and activity separate.
 * 
 * @author pillar
 * 
 */
public class TaskListFragment extends ListFragment implements
		LoaderManager.LoaderCallbacks<List<TaskEntry>> {

	private Context mContext;
	private TaskListAdapter adapter;
	private List<TaskEntry> tasks;
	
	public static final String TAG = TaskListFragment.class.getName();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		mContext = this.getActivity().getApplicationContext();
		tasks = new ArrayList<TaskEntry>();
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		// Give some text to display if there is no data. In a real
		// application this would come from a resource.
		// setEmptyText("No tasks");

		// We have a menu item to show in action bar.
		setHasOptionsMenu(true);

		// Create an empty adapter we will use to display the loaded data.
		adapter = new TaskListAdapter(mContext, R.layout.fragment_tasklist, tasks);

		setListAdapter(adapter);

		// Start out with a progress indicator.
		// setListShown(false);

		// Prepare the loader. Either re-connect with an existing one,
		// or start a new one.
		getLoaderManager().initLoader(0, null, this);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		View root = inflater.inflate(R.layout.fragment_tasklist, container,
				false);

		return root;
	}
	
	/**
	 * One loader which loads tasks in the database
	 * 
	 * @author pillar
	 * 
	 */
	public static class TaskListLoader extends AsyncTaskLoader<List<TaskEntry>> {

		private Context mContext;

		public TaskListLoader(Context context) {
			super(context);
			this.mContext = context;
		}

		@Override
		public List<TaskEntry> loadInBackground() {
			List<Task> tasks = TaskDAO.v(mContext).getAllTasks();
			List<TaskEntry> taskEntries = new ArrayList<TaskEntry>();
			for (Task task : tasks){
				ApplicationInfo ai = PackageMgr.searchApplicationInfo(mContext, task.getApp());
				taskEntries.add(new TaskEntry(task, ai));
			}
			return taskEntries;
		}

	}

	@Override
	public Loader onCreateLoader(int arg0, Bundle arg1) {
		return new TaskListLoader(mContext);
	}

	@Override
	public void onLoadFinished(Loader<List<TaskEntry>> loader, List<TaskEntry> obj) {
		// Set the new data in the adapter.

		Log.i(TAG, "obj: " + (obj==null?"null":"size:"+obj.size()));
		tasks = obj;

		for (TaskEntry te : tasks){
			Log.i(TaskListFragment.class.getName(), te.getTaskInfo().getApp() + " - ");
		}
		adapter.notifyDataSetChanged();

		// The list should now be shown.
		if (isResumed()) {
			setListShown(true);
		} else {
			setListShownNoAnimation(true);
		}
	}

	@Override
	public void onLoaderReset(Loader<List<TaskEntry>> arg0) {
		// TODO Auto-generated method stub
		
	}
}

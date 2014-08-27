package com.heuristic.download.activities;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ListView;
import android.widget.Toast;

import com.heuristic.download.R;
import com.heuristic.download.activities.ds.TaskEntry;
import com.heuristic.download.db.dao.Task;
import com.heuristic.download.db.dao.TaskDAO;
import com.heuristic.download.util.Initiator;

import edu.ntu.cltk.android.pm.PackageMgr;

/**
 * The lifecycle for fragment activity
 * 
 * Start the application: 
 * onAttach 
 * onCreate 
 * onCreateView 
 * onActivityCreated
 * onStart 
 * onResume
 * 
 * Pause the application: 
 * onPause 
 * onStop
 * 
 * Re-enter the application: 
 * onStart 
 * onResume
 * 
 * Quit the application: 
 * onPause 
 * onStop 
 * onDestroyView 
 * onDestroy 
 * onDetach
 * 
 * onAttach: invoked when establishing a connection between fragment and activity 
 * onActivityCreated: invoked after the invocation of onCreate in Activity 
 * onDetach: invoked when fragment and activity separate.
 * 
 * @author pillar
 * 
 */
public class TaskListFragment extends ListFragment implements
		LoaderManager.LoaderCallbacks<List<TaskEntry>> {

	private Context mContext;
	private TaskListAdapter adapter;
	private List<TaskEntry> tasks;
	
	private boolean listViewShown = false;
	private View taskProgressBar;
	private View taskListView;
	
	
	public static final String TAG = TaskListFragment.class.getName();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		mContext = this.getActivity().getApplicationContext();
		tasks = new ArrayList<TaskEntry>();
		
		setHasOptionsMenu(true);
		
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
		adapter = new TaskListAdapter(mContext, R.layout.list_item_icon_text, tasks);

		setListAdapter(adapter);

		// Start out with a progress indicator.
		setListShown(false);

		// Prepare the loader. Either re-connect with an existing one,
		// or start a new one.
		getLoaderManager().initLoader(0, null, this);
		
	}
	
	//@Override
	public void setListShown(boolean shown){
	    setListShown(shown, true);
	}
	
	//@Override
	public void setListShownNoAnimation(boolean shown) {
	    setListShown(shown, false);
	}

	private void setListShown(boolean shown, boolean animate){
		if (listViewShown == shown) {
	        return;
	    }
		
		listViewShown = shown;
		
	    if (shown) {
	        if (animate) {
	        	taskProgressBar.startAnimation(AnimationUtils.loadAnimation(
	                    mContext, android.R.anim.fade_out));
	        	taskListView.startAnimation(AnimationUtils.loadAnimation(
	                    mContext, android.R.anim.fade_in));
	        }
	        taskProgressBar.setVisibility(View.GONE);
	        taskListView.setVisibility(View.VISIBLE);
	    } else {
	        if (animate) {
	        	taskProgressBar.startAnimation(AnimationUtils.loadAnimation(
	                    mContext, android.R.anim.fade_in));
	        	taskListView.startAnimation(AnimationUtils.loadAnimation(
	                    mContext, android.R.anim.fade_out));
	        }
	        taskProgressBar.setVisibility(View.VISIBLE);
	        taskListView.setVisibility(View.INVISIBLE);
	    }
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		View root = inflater.inflate(R.layout.fragment_tasklist, container, false);
		taskProgressBar = root.findViewById(R.id.task_load);
		taskListView = root.findViewById(R.id.task_list);

		return root;
	}
	
	@Override
	public void onCreateOptionsMenu (Menu menu, MenuInflater inflater) {
	    // Inflate the menu items for use in the action bar
		inflater.inflate(R.menu.task_list, menu);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    // Handle item selection
	    switch (item.getItemId()) {
	    case R.id.action_refresh:
	    	getLoaderManager().restartLoader(0, null, this);
	    	Toast.makeText(mContext, "Refresh Successfully", Toast.LENGTH_SHORT).show();
	    	return true;
	    case R.id.action_delete:
	    	
	    	AlertDialog deleteAlert = new AlertDialog.Builder(TaskListFragment.this.getActivity())
	    					.setTitle("Alert Dialog")
	    					.setMessage("Delete all download tasks?")
	    					.setCancelable(true)
	    					.setPositiveButton("OK", new OnClickListener(){

								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									TaskDAO.v(mContext).open().deleteAll();
									dialog.cancel();
									TaskListFragment.this.getLoaderManager().restartLoader(0, null, TaskListFragment.this);
									Toast.makeText(mContext, "Delete Successfully", Toast.LENGTH_SHORT).show();
								}
	    						
	    					})
	    					.setNegativeButton("Cancel", new OnClickListener(){
	    						
	    						@Override
	    						public void onClick(DialogInterface dialog, int which){
	    							dialog.cancel();
	    						}
	    					}).create();
	    	
	    	deleteAlert.show();
	    	
	    	return true;
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}
	
	/**
	 * One loader which loads tasks in the database
	 * 
	 * @author pillar
	 * 
	 */
	public static class TaskListLoader extends AsyncTaskLoader<List<TaskEntry>> {

		private Context mContext;
		private List<TaskEntry> mTasks;

		public TaskListLoader(Context context) {
			super(context);
			this.mContext = context;
			
		}

		@Override
		public List<TaskEntry> loadInBackground() {
			List<Task> tasks = TaskDAO.v(mContext).open().getAllTasks();
			List<TaskEntry> taskEntries = new ArrayList<TaskEntry>();
			for (Task task : tasks){
				ApplicationInfo ai = PackageMgr.searchApplicationInfo(mContext, task.getApp());
				taskEntries.add(new TaskEntry(task, ai));
			}
			return taskEntries;
		}
		
		@Override
		public void deliverResult(List<TaskEntry> tasks) {
			if (isReset()) {
				if (Initiator.DEBUG)
					Log.w(TAG,
							"+++ Warning! An async query came in while the Loader was reset! +++");
				// The Loader has been reset; ignore the result and invalidate
				// the data. This can happen when the Loader is reset while an
				// asynchronous query is working in the background. That is, when the background
				// thread finishes its work and attempts to deliver the results to the
				// client, it will see here that the Loader has been reset and discard
				// any resources associated with the new data as necessary.
				if (tasks != null) {
					releaseResources(tasks);
					return;
				}
			}

			// Hold a reference to the old data so it doesn't get garbage
			// collected.
			// We must protect it until the new data has been delivered.
			List<TaskEntry> oldTasks = mTasks;

			mTasks = tasks;

			if (isStarted()) {
				if (Initiator.DEBUG)
					Log.i(TAG,
							"+++ Delivering results to the LoaderManager for"
									+ " the ListFragment to display! +++");
				// If the Loader is in a started state, have the superclass
				// deliver the
				// results to the client.
				super.deliverResult(tasks);
			}

			// Invalidate the old data as we don't need it any more.
			if (oldTasks != null && oldTasks != tasks) {
				if (Initiator.DEBUG)
					Log.i(TAG,
							"+++ Releasing any old data associated with this Loader. +++");
				releaseResources(oldTasks);
			}
		}
		
		/**
	     * Handles a request to start the Loader.
	     */
	    @Override protected void onStartLoading() {
	        if (mTasks != null) {
	            // If we currently have a result available, deliver it
	            // immediately.
	            deliverResult(mTasks);
	        }else{
	        	forceLoad();
	        }
	    }

	    /**
	     * Handles a request to stop the Loader.
	     */
	    @Override protected void onStopLoading() {
	        // Attempt to cancel the current load task if possible.
	        cancelLoad();
	    }

	    /**
	     * Handles a request to cancel a load.
	     */
	    @Override public void onCanceled(List<TaskEntry> tasks) {
	        super.onCanceled(tasks);

	        // At this point we can release the resources associated with 'apps'
	        // if needed.
	        releaseResources(tasks);
	    }

	    /**
	     * Handles a request to completely reset the Loader.
	     */
	    @Override protected void onReset() {
	        super.onReset();

	        // Ensure the loader is stopped
	        onStopLoading();

	        // At this point we can release the resources associated with 'apps'
	        // if needed.
	        if (mTasks != null) {
	        	releaseResources(mTasks);
	            mTasks = null;
	        }
	    }
		
		private void releaseResources(List<TaskEntry> tasks){
			tasks.clear();
			tasks = null;
		}

	}

	@Override
	public Loader onCreateLoader(int arg0, Bundle arg1) {
		return new TaskListLoader(mContext);
	}

	@Override
	public void onLoadFinished(Loader<List<TaskEntry>> loader, List<TaskEntry> obj) {
		// Set the new data in the adapter.
		
		tasks = obj;
		
		Collections.sort(obj, new Comparator<TaskEntry>(){

			@Override
			public int compare(TaskEntry lhs, TaskEntry rhs) {
				return (int) (rhs.getTaskInfo().getCreatedon() - lhs.getTaskInfo().getCreatedon());
			}
			
		});
		
		adapter.clear();
		adapter.addAll(obj);

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
	
	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		
		if (position < tasks.size()){
			Intent intent = new Intent(mContext, TaskDetail.class);
			intent.putExtra("task", tasks.get(position));
			startActivity(intent);
		}
	}
}

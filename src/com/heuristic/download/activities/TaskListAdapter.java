package com.heuristic.download.activities;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.heuristic.download.R;
import com.heuristic.download.activities.ds.TaskEntry;
import com.heuristic.download.db.dao.Task;
import com.heuristic.download.util.Initiator;

import edu.ntu.cltk.android.pm.PackageMgr;
import edu.ntu.cltk.date.DateFormater;
import edu.ntu.cltk.file.FileUtil;

public class TaskListAdapter extends ArrayAdapter<TaskEntry> {

	protected Context mContext;
	protected List<TaskEntry> tasks;
	protected LayoutInflater mLayoutInflater;
	//protected int resourceId;
	
	public TaskListAdapter(Context context, int resource,
			List<TaskEntry> tasks) {
		super(context, resource, tasks);
		this.mContext = context;
		this.tasks = tasks;
		//this.resourceId = resource;		
		
		mLayoutInflater = (LayoutInflater) mContext.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View rootView = convertView;
		ViewHolder viewHolder = null;
		
		if (rootView == null){
			rootView = mLayoutInflater.inflate(R.layout.list_item_icon_text, null);
			//rootView = mLayoutInflater.inflate(R.layout.list_item_icon_text, null);
			
			viewHolder = new ViewHolder();
			viewHolder.iconImageView = (ImageView) rootView.findViewById(R.id.task_icon);
			viewHolder.appTextView = (TextView) rootView.findViewById(R.id.task_app);
			viewHolder.progressTextView = (TextView) rootView.findViewById(R.id.task_progress);
			viewHolder.createdonTextView = (TextView) rootView.findViewById(R.id.task_createdon);
			
			rootView.setTag(viewHolder);
		}
		viewHolder = (ViewHolder)rootView.getTag();
		
		if (position < tasks.size()){
			viewHolder.iconImageView.setImageDrawable(PackageMgr.getDrawableForApp(mContext, tasks.get(position).getAppInfo()));
			viewHolder.appTextView.setText(tasks.get(position).getTaskInfo().getApp());
			viewHolder.progressTextView.setText(String.format("Downloading %d%%", 
					Math.round(
							100 * Task.calProgress(
									FileUtil.getFileSize(tasks.get(position).getTaskInfo().getFile()), 
									tasks.get(position).getTaskInfo().getSize()))));
			viewHolder.createdonTextView.setText(DateFormater.formatString(tasks.get(position).getTaskInfo().getCreatedon(), "MM/dd HH:mm"));
			
		}
				
		return rootView;
	}
	
	/**
	 * Holder Pattern for caching view component, avoiding to call findViewById. It can make the process ~15% faster
	 * @author pillar
	 *
	 */
	public static class ViewHolder{
		protected ImageView iconImageView;
		protected TextView  appTextView;
		protected TextView 	progressTextView;
		protected TextView 	createdonTextView;
	}

}

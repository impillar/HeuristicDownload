package com.heuristic.download.activities;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.heuristic.download.R;
import com.heuristic.download.activities.ds.TaskEntry;
import com.heuristic.download.db.dao.Task;
import com.heuristic.download.db.dao.TaskDAO;
import com.heuristic.download.util.Initiator;

import edu.ntu.cltk.android.pm.PackageMgr;
import edu.ntu.cltk.date.DateFormater;
import edu.ntu.cltk.file.FileUtil;

public class TaskDetail extends ActionBarActivity {

	private TaskEntry te;
	private TextView urlTextView;
	private TextView folderTextView;
	private TextView createdTextView;
	
	private Button stopButton;
	private Button clearButton;
	
	private TextView appTextView;
	private TextView progressTextView;
	private ImageView iconImageView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_task_detail);	
		
		te = (TaskEntry)getIntent().getParcelableExtra("task");;
		
		if (te == null){
			onDestroy();
			return;
		}
		initWidget();
		
		android.support.v7.app.ActionBar actionBar = getSupportActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
		
		//Load the latest progress
		AsyncTask<Integer, Double, Integer> at = new AsyncTask<Integer, Double, Integer>(){

			@Override
			protected Integer doInBackground(Integer... params) {
				for (int param : params){
					Task task = TaskDAO.v(TaskDetail.this).open().getTask(te.getTaskInfo().getId());
					te.setTaskInfo(task);
					return (int)Math.round(100 * Task.calProgress(FileUtil.getFileSize(te.getTaskInfo().getFile()), task.getSize()));
				}
				return 0;
			}
			
			@Override
			protected void onPostExecute(Integer result) {
				if (progressTextView != null){
					progressTextView.setText(String.format("Downloading %d%%", result));
				}
			}
			
		};
		
		at.execute(te.getTaskInfo().getId());
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		//getMenuInflater().inflate(R.menu.task_detail, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	private void initWidget(){
		iconImageView = (ImageView)findViewById(R.id.taskdetail_icon);
		appTextView = (TextView)findViewById(R.id.taskdetail_app);
		progressTextView = (TextView)findViewById(R.id.taskdetail_progress);
		
		urlTextView = (TextView)findViewById(R.id.taskdetail_url_value);
		folderTextView = (TextView)findViewById(R.id.taskdetail_folder_value);
		createdTextView = (TextView) findViewById(R.id.taskdetail_createdon_value);
		
		stopButton = (Button) findViewById(R.id.taskdetail_stop);
		clearButton = (Button) findViewById(R.id.taskdetail_clear);
		
		iconImageView.setImageDrawable(PackageMgr.getDrawableForApp(this, te.getAppInfo()));
		appTextView.setText(te.getAppInfo().packageName);
		progressTextView.setText(String.format("Downloading %d%%", 
				Math.round(
						100 * Task.calProgress(
								FileUtil.getFileSize(te.getTaskInfo().getFile()), 
								te.getTaskInfo().getSize()))));
		
		if (Initiator.DEBUG){
			Log.w("TaskDetail", String.format("FileSize:%d, Total:%d",  FileUtil.getFileSize(te.getTaskInfo().getFile()), te.getTaskInfo().getSize()));
		}
		
		urlTextView.setText(te.getTaskInfo().getUrl());
		folderTextView.setText(te.getTaskInfo().getFile());
		createdTextView.setText(DateFormater.formatString(te.getTaskInfo().getCreatedon(),"MM/dd HH:mm"));
		
		stopButton.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				Toast.makeText(TaskDetail.this, "Not implemented yet", Toast.LENGTH_SHORT).show();
			}
			
		});
		
		clearButton.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				Toast.makeText(TaskDetail.this, "Not implemented yet", Toast.LENGTH_SHORT).show();
			}
			
		});
	}
}

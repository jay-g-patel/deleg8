package aston.cs3040.deleg8;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import aston.cs3040.activity.SingleFragmentActivity;
import aston.cs3040.model.WorkLoad;

public class ProjectActivity extends SingleFragmentActivity{
	private static boolean isnew = false;
	private int projectID;
	
	
	@Override
	protected Fragment createFragment() {
		Fragment f = null;
		int pos = getIntent().getIntExtra("pos", 0);
		projectID = getIntent().getIntExtra("PROJECTID", 0);
		boolean editMode = getIntent().getBooleanExtra("MODE", false);
		Bundle extras = getIntent().getExtras();
		boolean x= false;
		if(extras != null) {
			x = getIntent().getExtras().getBoolean("ISNEW");
		}
		else
		{
			Log.i(WorkLoad.TAG, "Bundle is  null ");
		}
		if(x || editMode)
		{
			Log.i(WorkLoad.TAG, "Project is new");
			f = new EditableProjectFragment();
		}
		else{
			Log.i(WorkLoad.TAG, "project is NOT new = "+ pos);
			f = new ProjectFragment();
			return ProjectFragment.newInstance(pos);
		}
		return f;
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.project_actions, menu);
		
		return super.onCreateOptionsMenu(menu);
	}
	
	@Override 
	public boolean onOptionsItemSelected(MenuItem item){
		//take the correct action for each action button clicked
		switch(item.getItemId()){
		case R.id.action_editProject:
			EditProject();
			return true;
		}
		return false;
	}
	
	private void EditProject(){
		Intent i = new Intent(this,ProjectActivity.class);
		i.putExtra("PROJECTID", projectID);
		Log.i(WorkLoad.TAG, "Project Activity, PID = "+projectID);
		i.putExtra("MODE", true);
		this.finish();
		startActivity(i);
		
	}

}

package aston.cs3040.deleg8;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import aston.cs3040.activity.SingleFragmentActivity;
import aston.cs3040.model.WorkLoad;

public class ProjectListActivity extends SingleFragmentActivity{

	@Override
	protected Fragment createFragment() {
		// TODO Auto-generated method stub
		return new ProjectListFragment();
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.activity_main_actions, menu);
		
		return super.onCreateOptionsMenu(menu);
	}
	
	@Override 
	public boolean onOptionsItemSelected(MenuItem item){
		//take the correct action for each action button clicked
		switch(item.getItemId()){
		case R.id.action_addProject:
			AddProject();
			return true;
		}
		return false;
	}
	
	private void AddProject(){
		Intent i = new Intent(ProjectListActivity.this,ProjectActivity.class);
		
		i.putExtra("ISNEW", true);
		//boolean x = i.getBooleanExtra("ISNEW", false);
		boolean x = i.getExtras().getBoolean("ISNEW");
		Log.i(WorkLoad.TAG, "ISNEW var has been set to " + x);
		
		startActivity(i);
		
	}

}

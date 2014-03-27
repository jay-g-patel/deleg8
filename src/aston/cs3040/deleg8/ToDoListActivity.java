package aston.cs3040.deleg8;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import aston.cs3040.activity.SingleFragmentActivity;
import aston.cs3040.model.Project;
import aston.cs3040.model.WorkLoad;

public class ToDoListActivity extends SingleFragmentActivity {
	int pID;
	Boolean isRefresh;

	@Override
	protected Fragment createFragment() {
		pID = getIntent().getExtras().getInt("PID");
		Log.i(WorkLoad.TAG, "PID = "+pID);
		return new ToDoListFragment();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.to_do_list_menu, menu);
		
		return super.onCreateOptionsMenu(menu);
	}
	
	@Override 
	public boolean onOptionsItemSelected(MenuItem item){
		//take the correct action for each action button clicked
		switch(item.getItemId()){
		case R.id.action_addToDoItem:
			AddToDoItem();
			return true;
		}
		return false;
	}
	
	private void AddToDoItem(){
		Intent i = new Intent(ToDoListActivity.this,ToDoItemActivity.class);
		i.putExtra("ISNEW", true);
		i.putExtra("PID", pID);
		startActivity(i);
		
	}

}

package aston.cs3040.deleg8;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.CalendarContract;
import android.provider.CalendarContract.Events;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import aston.cs3040.activity.SingleFragmentActivity;
import aston.cs3040.deleg8.meeting.MeetingActivity;
import aston.cs3040.deleg8.meeting.MeetingsListActivity;
import aston.cs3040.model.ToDoItem;
import aston.cs3040.model.WorkLoad;

public class ToDoItemActivity extends SingleFragmentActivity {

	private ToDoItem tdi;
	boolean editMode = false;
	
	@Override
	protected Fragment createFragment() {
		Fragment f = null;
		tdi = (ToDoItem) getIntent().getExtras().get("TODOITEM");
		boolean isNew = getIntent().getBooleanExtra("ISNEW", false);
		editMode = getIntent().getBooleanExtra("MODE", false);
		
		if(isNew || editMode)
		{
			//is a new to do item
			f = new EditableToDoItemFragment();
		}
		else
		{
			f = new ToDoItemFragment();
		}
		return f;
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		MenuInflater inflater = getMenuInflater();
		if(!editMode)
		{
		inflater.inflate(R.menu.project_actions, menu);
		}
		
		
		return super.onCreateOptionsMenu(menu);
	}
	
	@Override
	public void onResume()
	{
		super.onResume();
		
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
		Intent i = new Intent(this,ToDoItemActivity.class);
		i.putExtra("MODE", true);
		i.putExtra("TODOITEM", tdi);
		this.finish();
		startActivity(i);
		
	}
	
//	private void AddMeeting(){
//		Intent i = new Intent(this,MeetingActivity.class);
//		i.putExtra("ISNEW", true);
//		i.putExtra("MLINK", "TDI");
//		i.putExtra("TODOITEMID", tdi.getId());
//		Log.i(WorkLoad.getInstance().TAG, "Intent TDI ID is "+ tdi.getId());
//		i.putExtra("PROJECTID", tdi.getProjectid());
//		startActivity(i);
//		
//
//	}
	
	

}

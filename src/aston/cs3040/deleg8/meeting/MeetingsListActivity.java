package aston.cs3040.deleg8.meeting;

import android.net.Uri;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.provider.CalendarContract.Events;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import aston.cs3040.activity.SingleFragmentActivity;
import aston.cs3040.deleg8.R;
import aston.cs3040.deleg8.ToDoItemActivity;
import aston.cs3040.deleg8.ToDoListActivity;
import aston.cs3040.deleg8.R.layout;
import aston.cs3040.deleg8.R.menu;
import aston.cs3040.model.WorkLoad;

public class MeetingsListActivity extends SingleFragmentActivity
{

	private int projectID;
	
	
	@Override
	protected Fragment createFragment()
	{
		projectID =  getIntent().getIntExtra("PROJECTID", 0);
		Fragment f = new MeetingsListFragment();
		// TODO Auto-generated method stub
		return f;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.meeting_list_menu, menu);
		
		return super.onCreateOptionsMenu(menu);
	}
	
	@Override
	public void onResume()
	{
		super.onResume();
		getLastmeetingEntry();
	}
	
	public long getLastmeetingEntry()
	{
		long calID = 0;
		ContentResolver cr = getContentResolver();
		Uri calUri = CalendarContract.Events.CONTENT_URI;
		
			Cursor cur1 = cr.query(calUri, new String[]{Events.CALENDAR_ID}, null, null, "1 DESC");
			if(cur1 != null){
					cur1.moveToFirst();
					calID = cur1.getLong(0);
					Log.i(WorkLoad.TAG, "Calendar ID is - "+calID);				}
			
			cur1.close();
		

			return calID;
		
		
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data){
		super.onActivityResult(requestCode, resultCode, data);
		
		if(requestCode == 1001){
			
			Uri uri = data.getData();
			long eventID = Long.parseLong(uri.getLastPathSegment());
			Log.i(WorkLoad.TAG, "event ID is: "+  eventID);
		}
	}
	
	@Override 
	public boolean onOptionsItemSelected(MenuItem item){
		//take the correct action for each action button clicked
		switch(item.getItemId()){
		case R.id.action_addMeeting:
			AddMeeting();
			return true;
		}
		return false;
	}
	
	private void AddMeeting(){
		Intent i = new Intent(MeetingsListActivity.this,MeetingActivity.class);
		i.putExtra("ISNEW", true);
		i.putExtra("PROJECTID", projectID);
		startActivity(i);
		

	}
}

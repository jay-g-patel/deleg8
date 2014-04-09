package aston.cs3040.deleg8.meeting;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.CalendarContract.Calendars;
import android.provider.CalendarContract.Events;
import android.provider.ContactsContract.CommonDataKinds.Event;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import aston.cs3040.deleg8.R;
import aston.cs3040.deleg8.ToDoItemActivity;
import aston.cs3040.model.ToDoItem;
import aston.cs3040.model.WorkLoad;

public class MeetingsListFragment extends ListFragment
{
	private View v;
	public String calID;
	private MeetingListAdapter adapter;
	public ArrayList<Long> meetingsList = new ArrayList<Long>();
	


	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		int projectID = getActivity().getIntent().getIntExtra("PROJECTID", 0);
		//calID = getActivity().getIntent().getStringExtra("CALID");
		meetingsList = WorkLoad.getInstance().getAllProjectMeetings(projectID);
		adapter = new MeetingListAdapter(getActivity(), meetingsList);
		this.setListAdapter(adapter);
	}

	

	// LIST ITEM CLICK"!!!!!
	@Override
	public void onListItemClick(ListView l, View v, int pos, long id)
	{
		Log.i(WorkLoad.TAG, "something has been clicked");
		Long eventID = (Long) (getListAdapter()).getItem(pos);
		//Intent intent = new Intent(Intent.ACTION_VIEW);
//		Uri.Builder uri = Events.CONTENT_URI.buildUpon();
//		uri.appendPath(Long.toString(longID));
//		intent.setData(uri.build());
		
		Uri uri = ContentUris.withAppendedId(Events.CONTENT_URI, eventID);
		Intent intent = new Intent(Intent.ACTION_VIEW)
		   .setData(uri);

		    startActivity(intent);
		
		Log.i(WorkLoad.TAG, "Event ID = " + eventID);
		//startActivity(intent);
	}
	
	

	private class MeetingListAdapter extends ArrayAdapter<Long>
	{
		List<Long> items;

		public MeetingListAdapter(Activity a, ArrayList<Long> meetings)
		{
			super(a, 0, meetings);
			this.items = meetings;
		}

		// UPDATE MEETINGS LIST!!!!

		public View getView(int position, View convertView, ViewGroup parent)
		{
			if (convertView == null)
			{
				convertView = getActivity().getLayoutInflater().inflate(
						R.layout.list_layout_meeting, null);
			}
			Long eventID = items.get(position);
			TextView titleTextView = (TextView) convertView
					.findViewById(R.id.meeting_titleTextView);
			// titleTextView.setText(eventID.getName());
			
			String[] proj = new String[]{ Events._ID, Events.TITLE, Events.DESCRIPTION };
			Cursor cursor = getActivity()
					.getContentResolver()
					.query(
							Events.CONTENT_URI, proj, Events._ID + " = ? ",
							new String[]{ Long.toString(eventID) },
							null);
			if (cursor.moveToFirst())
			{
//				do{
//					long id = cursor.getLong(0);
//					String name = cursor.getString(1);
//				}
				
			}
			return convertView;
		}
	}

}

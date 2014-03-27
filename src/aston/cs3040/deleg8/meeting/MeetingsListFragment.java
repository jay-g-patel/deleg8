package aston.cs3040.deleg8.meeting;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.CalendarContract.Events;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import aston.cs3040.deleg8.R;
import aston.cs3040.model.Meeting;
import aston.cs3040.model.Project;
import aston.cs3040.model.WorkLoad;

public class MeetingsListFragment extends Fragment
{
	private View v;
	private Button viewCalendar;

	public String calID;

	
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		calID = getActivity().getIntent().getStringExtra("CALID");
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState){
		v = inflater.inflate(R.layout.activity_meetings_list,  parent, false);
		viewCalendarButtonListener();
		return v;
		
	}
	
	public void viewCalendarButtonListener()
	{
		//Log.i(WorkLoad.TAG, "Listener hit ");	
		
		viewCalendar = (Button)v.findViewById(R.id.go_to_todolist);
		viewCalendar.setVisibility(View.VISIBLE);
		viewCalendar.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View view)
			{
				Intent intent = new Intent(Intent.ACTION_VIEW);
				Uri.Builder uri = Events.CONTENT_URI.buildUpon();
				uri.appendPath("10");
				intent.setData(uri.build());
				startActivity(intent);
			}
		}
				);
			
	}
	
	
}

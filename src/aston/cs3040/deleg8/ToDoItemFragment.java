package aston.cs3040.deleg8;

import java.util.Calendar;
import java.util.Date;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.content.ContentUris;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import aston.cs3040.deleg8.meeting.MeetingActivity;
import aston.cs3040.deleg8.meeting.MeetingsListActivity;
import aston.cs3040.deleg8.meeting.MeetingFragment.StartDatePicker;
import aston.cs3040.model.*;

public class ToDoItemFragment extends Fragment{
	
	private ToDoItem toDoItem;
	private WorkLoad workLoad;
	private View v;
	
	
	
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		workLoad = WorkLoad.getInstance();
		int itemID = getActivity().getIntent().getIntExtra("ToDoItemid", 0);
		int pid = getActivity().getIntent().getIntExtra("PID", 0);
		Log.i(WorkLoad.TAG,"ToDoItemID = "+itemID+" AND pid = "+pid);
		if(itemID != 0){
		toDoItem = workLoad.getProjectByProjectID(pid).getToDoItem(itemID);
		}
		else
		{toDoItem = (ToDoItem)getActivity().getIntent().getExtras().get("TODOITEM");}
		Log.i(WorkLoad.TAG,"ToDoItemName = "+toDoItem.getName());
	
	}
	
	
	
	
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState){
		v = inflater.inflate(R.layout.activity_to_do_item,  parent, false);
		TextView ToDoItemName = (TextView)v.findViewById(R.id.ToDoItemName);
		ToDoItemName.setText(toDoItem.getName());	
		TextView completionDate = (TextView)v.findViewById(R.id.ToDoItemCompletionDate);
		if(toDoItem != null)
		{
			if(!toDoItem.getCompletionDate().equals(""))
			{
				completionDate.setText("To Be completed By  " +toDoItem.getCompletionDate());
			}
			
		}
		//Log.i(WorkLoad.TAG, "getting project name "+ project.getName());
		return v;
	}
	
	
	
	
	
	
//	
//	@Override
//	public void onClick(View view)
//	{
//		long startMillis = new Date().getTime();
//		
//		Uri.Builder builder = CalendarContract.CONTENT_URI.buildUpon();
//		builder.appendPath("time");
//		ContentUris.appendId(builder, startMillis);
//		Intent intent = new Intent(Intent.ACTION_VIEW)
//		    .setData(builder.build());
//		startActivity(intent);
//	}
//	
	

		

}

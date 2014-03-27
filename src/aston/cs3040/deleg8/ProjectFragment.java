package aston.cs3040.deleg8;


import java.io.IOException;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import aston.cs3040.deleg8.meeting.MeetingActivity;
import aston.cs3040.deleg8.meeting.MeetingsListActivity;
import aston.cs3040.model.Project;
import aston.cs3040.model.WorkLoad;

public class ProjectFragment extends Fragment{

	private Project project;
	private static int pos;
	private Button viewToDoListBtn;
	private Button viewCalendar;
	private View v;
	
	
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		if(project == null){
			project = WorkLoad.getInstance().getProject(pos);
		}
		
	}
	
	public static ProjectFragment newInstance(int position)
	{
		pos = position;
		Bundle args = new Bundle();
		args.putInt("PROJECT_POSITION", pos);
		ProjectFragment projFrag = new ProjectFragment();
		projFrag.setArguments(args);
		return projFrag;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState){
		v = inflater.inflate(R.layout.activity_project,  parent, false);
		TextView projectTitleField = (TextView)v.findViewById(R.id.project_titleTextView1);
		//Button btn = (Button)v.findViewById(R.id.go_to_todolist);
		projectTitleField.setText(project.getName());	
		//Log.i(WorkLoad.TAG, "getting project name "+ project.getName());
		viewToDoListButtonListener();
		viewCalendarButtonListener();
		//editProjectButtonListener();
		return v;
		
	}
	
	public void viewCalendarButtonListener()
	{
		//Log.i(WorkLoad.TAG, "Listener hit ");	
		
		viewCalendar = (Button)v.findViewById(R.id.viewMeetings);
		viewCalendar.setVisibility(View.VISIBLE);
		viewCalendar.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View view)
			{
				Intent i = new Intent(getActivity(), MeetingsListActivity.class);
				//i.putExtra("TODOITEMID", toDoItem.getId());
				i.putExtra("PROJECTID", project.getID());
				Log.i(WorkLoad.TAG, "projectID is - "+project.getID());
				startActivity(i);
			}
		}
				);
			
	}
	
	
	public void viewToDoListButtonListener()
	{
		//Log.i(WorkLoad.TAG, "Listener hit ");	
		
		viewToDoListBtn = (Button)v.findViewById(R.id.go_to_todolist);
		viewToDoListBtn.setVisibility(View.VISIBLE);
		viewToDoListBtn.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View view)
			{
				int pid = project.getID();
				Intent i = new Intent(getActivity(), ToDoListActivity.class);
				i.putExtra("Position", pos);
				i.putExtra("PID", pid);
				Log.i(WorkLoad.TAG,"Position = "+pos+" PID = "+pid);
				
					startActivity(i);
			}
		}
				);
			
	}
	
	
//	public void editProjectButtonListener()
//	{
//		//Log.i(WorkLoad.TAG, "Listener hit ");	
//		
//		editProjectBtn = (Button)v.findViewById(R.id.edit_project);
//		editProjectBtn.setVisibility(View.VISIBLE);
//		editProjectBtn.setOnClickListener(new View.OnClickListener()
//		{
//			@Override
//			public void onClick(View view)
//			{
//				Log.i(WorkLoad.TAG,"Edit Project Button has been Clicked");
//				Intent i = new Intent(getActivity(),ProjectActivity.class);
//				i.putExtra("PID", project.getID());
//				i.putExtra("MODE", true);
//				startActivity(i);
//			}
//		}
//				);
//			
//	}
//	
	
}

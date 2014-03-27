package aston.cs3040.deleg8;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import aston.cs3040.model.Project;
import aston.cs3040.model.ToDoItem;
import aston.cs3040.model.WorkLoad;

public class EditableToDoItemFragment  extends Fragment{
	
	View v = null;
	Button addToDoItemButton = null;
	Button saveToDoItemButton = null;
	int projectID;
	ToDoItem toDoItem = null;
	EditText toDoName = null;
	
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		toDoItem = (ToDoItem)getActivity().getIntent().getExtras().get("TODOITEM");
		if(toDoItem ==null)
		{
		projectID = getActivity().getIntent().getExtras().getInt("PID");
		Log.i(WorkLoad.TAG,"ProjectID = "+projectID);
		}
		else{
			projectID = toDoItem.getProjectid();
		}
		
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState){
		v = inflater.inflate(R.layout.editable_todoitem,  parent, false);
		toDoName = (EditText)v.findViewById(R.id.toDoItem_titleEditText);
		
		if(toDoItem == null)
		{
			addToDoItemButtonListener();
		}
		else
		{
			
			toDoName.setText(toDoItem.getName());
			addSaveButtonListener();
		}
		
		return v;
	}
	
	public void addToDoItemButtonListener()
	{
		Log.i(WorkLoad.TAG, "Listener hit ");	
		
		addToDoItemButton = (Button)v.findViewById(R.id.add_toDoItem_button);
		addToDoItemButton.setVisibility(View.VISIBLE);
		addToDoItemButton.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View view)
			{
				ToDoItem tmpTDI= new ToDoItem();
				
				tmpTDI.setName(toDoName.getText().toString());
				tmpTDI.setProjectID(projectID);
				///// add project into workload here
				WorkLoad.getInstance().addNewToDoItem(tmpTDI);
				Log.i(WorkLoad.TAG, "toDoItem added ");	
				Intent i = new Intent(getActivity(), ToDoListActivity.class);
				i.putExtra("PID", projectID);

				getActivity().finish();
				startActivity(i);
				//Log.i(WorkLoad.TAG, "loading intent");
			
				
			}
		}
				);
			
	}

	public void addSaveButtonListener()
	{
		Log.i(WorkLoad.TAG, "Listener hit ");	
		
		saveToDoItemButton = (Button)v.findViewById(R.id.save_toDoItem_button);
		saveToDoItemButton.setVisibility(View.VISIBLE);
		saveToDoItemButton.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View view)
			{
				toDoItem.setName(toDoName.getText().toString());
				///// add project into workload here
				WorkLoad.getInstance().updateToDoItem(toDoItem);
//				Log.i(WorkLoad.TAG, "toDoItem added ");	
				Intent i = new Intent(getActivity(), ToDoItemActivity.class);
				i.putExtra("PID", projectID);
				i.putExtra("TODOITEM", toDoItem);
				getActivity().finish();
				startActivity(i);
				//Log.i(WorkLoad.TAG, "loading intent");
			
				
			}
		}
				);
			
	}
	
	
}

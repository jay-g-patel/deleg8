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
import aston.cs3040.model.WorkLoad;
import aston.cs3040.model.Project;

public class EditableProjectFragment extends Fragment {
	
	private Button addProjectButton;
	private Button saveProjectButton;
	private boolean editMode;
	private Project p = null;
	private View v;
	EditText projectName = null;
	
	@Override
	public void onCreate(Bundle savedInstanceState){
		//Log.i(WorkLoad.TAG, "getting project at position before "+ this.getArguments().getInt("PROJECT_POSITION"));
		super.onCreate(savedInstanceState);
		int pID = getActivity().getIntent().getIntExtra("PROJECTID", 0);
		if( pID != 0){
			p = WorkLoad.getInstance().getProjectByProjectID(pID);
		}
		editMode = getActivity().getIntent().getBooleanExtra("MODE", false);		
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState){
		v = inflater.inflate(R.layout.editable_project,  parent, false);
		projectName = (EditText)v.findViewById(R.id.project_titleEditText);
		
		//Log.i(WorkLoad.TAG, "getting project name "+ project.getName());
		if(editMode){
			projectName.setText(p.getName());
			SaveProjectButtonListener();
		}
		else{
			addProjectButtonListener();
		}		
		return v;
	}
	
	public void addProjectButtonListener()
	{
		Log.i(WorkLoad.TAG, "Listener hit ");	
		
		
		addProjectButton = (Button)v.findViewById(R.id.add_project_button);
		addProjectButton.setVisibility(View.VISIBLE);
		addProjectButton.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View view)
			{
				Project tmpProject = new Project();
				
				tmpProject.setName(projectName.getText().toString());
				///// add project into workload here
				WorkLoad.getInstance().addNewProject(tmpProject);
				Log.i(WorkLoad.TAG, "project added ");	
				Intent i = new Intent(getActivity(), ProjectListActivity.class);
				//getActivity().finish();
				startActivity(i);
				//Log.i(WorkLoad.TAG, "loading intent");
			}
		}
				);
			
	}
	
	public void SaveProjectButtonListener()
	{
		Log.i(WorkLoad.TAG, "Listener hit ");	
		
		
		saveProjectButton = (Button)v.findViewById(R.id.save_project_button);
		saveProjectButton.setVisibility(View.VISIBLE);
		saveProjectButton.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View view)
			{
				p.setName(projectName.getText().toString());
				WorkLoad.getInstance().updateProject(p);
				Intent i = new Intent(getActivity(), ProjectListActivity.class);
				//Log.i(WorkLoad.TAG, "Save Button has been Pressed");
				//getActivity().finish();
				startActivity(i);
				}
		}
				);
			
	}

}

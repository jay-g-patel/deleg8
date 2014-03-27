package aston.cs3040.model;

import java.util.ArrayList;
import java.util.List;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import aston.cs3040.deleg8.ProjectListFragment;

public class WorkLoad {
	public static final String TAG = "DELEG8";

	public Context wAppContext;
	private static WorkLoad sInstance;
	private ProjectListFragment fragment;
	private DatabaseHelper dbHelper;
	private ArrayList<Project> projects;
	private ProgressDialog pDialog;

	private WorkLoad(Context appContext) {
		dbHelper = new DatabaseHelper(appContext);
		projects = new ArrayList<Project>();
		this.wAppContext = appContext;
	}

	public DatabaseHelper getDBHelper()
	{
		return this.dbHelper;
	}
	
	public static WorkLoad init(Context appContext) {
		if (sInstance == null) {
			sInstance = new WorkLoad(appContext);

		}
		return sInstance;

	}

	public static WorkLoad getInstance() {
		return sInstance;
	}

	public List<Project> getProjects() {
		List<Project> tmpProjects = dbHelper.getAllProjects();
		if (tmpProjects == null) {
			//Log.d("app", "projects are null");
		}
		setProjects();
		return tmpProjects;

	}
	
	public void addNewProject(Project project)
	{
		dbHelper.insertProject(project);
	}
	
	public void addNewToDoItem(ToDoItem tdItem)
	{
		dbHelper.insertToDoItem(tdItem);
		getProjectByProjectID(tdItem.projectID).setProjectToDoList();
	}

	public Project getProjectByProjectID(int projectID)
	{
		Project p = null;
		for(Project project:projects)
		{
			if(project.getID()==projectID)
			{
				p = project;
				return p;
			}
		}
		return p;
	}
	
	public void setProjects() {
		//Log.d("app", "projects have been set");
		projects = (ArrayList<Project>) dbHelper.getAllProjects();
	}
	

	public Project getProject(int position) {
		//Log.d("app", "projects have been gotten");
		if(projects == null || projects.isEmpty()){
			//Log.i(WorkLoad.TAG, "projects have not been gotten");
		}
		else{
			//Log.i(WorkLoad.TAG, "project is "+projects.get(position).getName());
		}
		return projects.get(position);
	}

	public void updateToDoItem(ToDoItem toDoItem) {
		dbHelper.updateToDoItem(toDoItem);
		
	}

	public void updateProject(Project p) {
		dbHelper.updateProject(p);
		
	}

	public void deleteProjectByID(Project p) {
		// TODO Auto-generated method stub
		dbHelper.deleteProject(p);
	}

	public void deleteToDoItemByID(ToDoItem selectedToDoItem) {
		// TODO Auto-generated method stub
		int projectID = selectedToDoItem.getProjectid();
		dbHelper.deleteToDoItem(selectedToDoItem);
		this.getProjectByProjectID(projectID).setProjectToDoList();
	}

}

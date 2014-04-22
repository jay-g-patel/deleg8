package aston.cs3040.model;

import java.util.ArrayList;
import java.util.List;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.CalendarContract.Calendars;
import android.util.Log;
import aston.cs3040.deleg8.ProjectListFragment;

public class WorkLoad {
	public static final String TAG = "DELEG8";
	
	private Context wAppContext;
	
	private static long CALID = 1;

	private static WorkLoad sInstance;
	private ProjectListFragment fragment;
	private DatabaseHelper dbHelper;
	private ArrayList<Project> projects;
	private ProgressDialog pDialog;

//	public static final String[] EVENT_PROJECTION = new String[]
//			{
//			Calendars._ID,			//0
//			Calendars.ACCOUNT_NAME,
//			Calendars.CALENDAR_DISPLAY_NAME,
//			Calendars.OWNER_ACCOUNT
//			};
//	
//	private static final int PROJECTION_ID_INDEX = 0;
//	private static final int PROJECTION_ACCOUNT_NAME_INDEX = 1;
//	private static final int PROJECTION_DISPLAY_NAME_INDEX = 2;
//	private static final int PROJECTION_OWNER_ACCOUNT_INDEX = 3;
	
//public void getCalID()
//{
//	// Run query
//	Cursor cur = null;
//	ContentResolver cr = getwAppContext().getContentResolver();
//	Uri uri = Calendars.CONTENT_URI;   
//	String selection = "((" + Calendars.ACCOUNT_NAME + " = ?) AND (" 
//	                        + Calendars.ACCOUNT_TYPE + " = ?) AND ("
//	                        + Calendars.OWNER_ACCOUNT + " = ?))";
//	String[] selectionArgs = new String[] {"sampleuser@gmail.com", "com.google",
//	        "sampleuser@gmail.com"}; 
//	// Submit the query and get a Cursor object back. 
//	cur = cr.query(uri, EVENT_PROJECTION, selection, selectionArgs, null);
//	//
//	long calID = 0;
//	while (cur.moveToNext()) {
//		 
//		 String displayName = null;
//		 String accountName = null;
//		 String ownerName = null;
//		   
//		 // Get the field values
//		 calID = cur.getLong(PROJECTION_ID_INDEX);
//		 displayName = cur.getString(PROJECTION_DISPLAY_NAME_INDEX);
//		 accountName = cur.getString(PROJECTION_ACCOUNT_NAME_INDEX);
//		 ownerName = cur.getString(PROJECTION_OWNER_ACCOUNT_INDEX);}
//	
//	Log.i(WorkLoad.TAG, "calendar id is "+calID);
//	this.setCALID(calID);
//	
//	
//}

           
	
	private WorkLoad(Context appContext) {
		dbHelper = new DatabaseHelper(appContext);
		projects = new ArrayList<Project>();
		this.setwAppContext(appContext);
//		getCalID();
		Log.i(WorkLoad.TAG, "fred" );
		
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
	public ArrayList<Long> getAllProjectMeetings(int projectID)
	{
		ArrayList<Long> toDoItemMeetings = (ArrayList<Long>) dbHelper.getAllProjectMeetings(projectID);
		return toDoItemMeetings;
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
	
	public void addMeeting(long eventID, int projectID, int toDoItemID)
	{
		dbHelper.insertMeeting(eventID, projectID);
	}



	public long getCALID()
	{
		return CALID;
	}



	public void setCALID(long cALID)
	{
		CALID = cALID;
	}



	public Context getwAppContext()
	{
		return wAppContext;
	}



	public void setwAppContext(Context wAppContext)
	{
		this.wAppContext = wAppContext;
	}
	
	public void addContactToDB(Contact contact)
	{
		dbHelper.addContactToDB(contact);
	}
	
	public List<String> getContacts(String projectID) {
		List<String> tmpcontacts = dbHelper.getAllContactsForProject(projectID);
		if (tmpcontacts == null) {
			//Log.d("app", "projects are null");
		}
		return tmpcontacts;

	}

	public void addImageToProject(String picturePath, int projectID)
	{
		dbHelper.addImageToProject(picturePath, projectID );
		
	} 

	public ArrayList<String> getAllProjectImages(int projectID)
	{
		return dbHelper.getAllProjectImages(projectID);
	}

	public void deleteContact(Contact contact)
	{
		dbHelper.deleteContact(contact);
		
	}

	public int getProjectContactID(Contact c)
	{
		return dbHelper.getProjectContactID(c);
		
	}
}

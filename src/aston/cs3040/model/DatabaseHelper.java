package aston.cs3040.model;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper{
	public static final String DBNAME = "projectList";
	
	public static final String projectTable = "Project";
	public static final String projectID = "ProjectID";
	public static final String projectName = "ProjectName";
	
	public static final String ToDoListItemTable = "ToDoListItemTable";
	public static final String ToDoItemID = "ToDoItemID";
	public static final String ToDoItemName = "ToDoItemName";
	
	public static final String MeetingsTable = "MeetingsTable";
	public static final String INCMeetingID = "INCMeetingID";
	public static final String EventID = "EventID";
	public static final String MeetingProjectID = "MeetingProjectID";
	public static final String MeetingToDoItemID = "MeetingToDoItemID";

	public DatabaseHelper(Context context) {
		super(context, DBNAME, null, 1);
		System.out.print("YAAAAYYYY1");
	
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		Log.i("DBHELPER","woopwoop");
		String sql = "CREATE TABLE " + projectTable + 
				" ("+projectID + " INTEGER PRIMARY KEY AUTOINCREMENT, "+ projectName+ " TEXT)";
		String sql1 = "CREATE TABLE " + ToDoListItemTable + 
				" ("+ToDoItemID + " INTEGER PRIMARY KEY AUTOINCREMENT, "+ ToDoItemName+ " TEXT, "+projectID+ " INTEGER)";
		String sqlmeetings = "CREATE TABLE " + MeetingsTable + 
				" ("+EventID+" LONG, "+ MeetingProjectID+" INTEGER)";
		
		db.execSQL(sql);
		db.execSQL(sql1);
		db.execSQL(sqlmeetings);
		
		insertInitialProjects(db);
		insertInitialToDoItems(db);
	}
	
	public long insertProject(Project project)
	{
		ContentValues cv1 = new ContentValues();
		cv1.put(projectName, project.getName());
		return getWritableDatabase().insert(projectTable, null, cv1);
	}
	
	public long insertToDoItem(ToDoItem tdi)
	{
		ContentValues cv1 = new ContentValues();
		cv1.put(ToDoItemName, tdi.getName());
		cv1.put(projectID, tdi.getProjectid());
		return getWritableDatabase().insert(ToDoListItemTable, null, cv1);
	}
	
	public long insertMeeting(long eventID, int mprojectID)
	{
		ContentValues cv1 = new ContentValues();
		cv1.put(EventID, eventID);
		cv1.put(MeetingProjectID, mprojectID);
		//cv1.put(MeetingToDoItemID, mtoDoItemID);
		return getWritableDatabase().insert(MeetingsTable, null, cv1);
		 	
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		
	}
	
	private void insertInitialProjects(SQLiteDatabase db)
	{
		ContentValues cv1 = new ContentValues();
		cv1.put(projectName, "project1");
		long projectOneId = db.insert(projectTable, projectID, cv1);
		
		cv1.put(projectName, "project2");
		long projectTwoId = db.insert(projectTable, projectID, cv1);
		
		cv1.put(projectName, "project3");
		long projectThreeId = db.insert(projectTable, projectID, cv1);
		
		cv1.put(projectName, "project4");
		long projectFourId = db.insert(projectTable, projectID, cv1);
		
		Log.i("DELEG8", " 1:" + projectOneId + "2:" + projectTwoId+ " 3:" + projectThreeId+ " 4:" + projectFourId);
		
	}
	
	private void insertInitialToDoItems(SQLiteDatabase db)
	{
		ContentValues cv1 = new ContentValues();
		cv1.put(ToDoItemName, "Item1 Project 1");
		cv1.put(projectID, "1");
		long ToDoItemOneId = db.insert(ToDoListItemTable, ToDoItemID, cv1);
		
		cv1.put(ToDoItemName, "Item2 Project 1");
		cv1.put(projectID, "1");
		long ToDoItemTwoId = db.insert(ToDoListItemTable, ToDoItemID, cv1);
		
		cv1.put(ToDoItemName, "Item3 Project 1");
		cv1.put(projectID, "1");
		long ToDoItemThreeId = db.insert(ToDoListItemTable, ToDoItemID, cv1);
		
		Log.i("DELEG8", " 1:" + ToDoItemOneId + " 2:" + ToDoItemTwoId+ " 3:" + ToDoItemThreeId);
		
		
	}
	
	public List<Project> getAllProjects()
	{
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery("SELECT * FROM "+ projectTable, null);
		List<Project> projects = new ArrayList<Project>();
		cursor.moveToFirst();
		while(!cursor.isAfterLast())
		{
			Project tmpProject = createProject(cursor);
			projects.add(tmpProject);
			cursor.moveToNext();
		}
		cursor.close();
		return projects;
		
	}
	
	public List<ToDoItem> getAllToDoListItems(int tmpprojectID)
	{
		SQLiteDatabase db = this.getReadableDatabase();
		//Cursor cursor = db.rawQuery("SELECT * FROM "+ ToDoListItemTable, null);
		
		Cursor cursor = db.rawQuery("SELECT * FROM "+ ToDoListItemTable + " WHERE " +projectID+ " = "+tmpprojectID+"", null);
		List<ToDoItem> toDoItems = new ArrayList<ToDoItem>();
		cursor.moveToFirst();
		while(!cursor.isAfterLast())
		{
			ToDoItem tmpToDoItem = createToDoItem(cursor);
			toDoItems.add(tmpToDoItem);
			cursor.moveToNext();
		}
		cursor.close();
		return toDoItems;
		
	}
	
	public List<Long> getAllProjectMeetings(int projectID)
	{
		SQLiteDatabase db = this.getReadableDatabase();
		//Cursor cursor = db.rawQuery("SELECT * FROM "+ ToDoListItemTable, null);
		
		Cursor cursor = db.rawQuery("SELECT * FROM "+ MeetingsTable + " WHERE " +MeetingProjectID+ " = "+projectID+"", null);
		ArrayList<Long> meetings = new ArrayList<Long>();
		cursor.moveToFirst();
		while(!cursor.isAfterLast())
		{
			meetings.add(cursor.getLong(0));
			cursor.moveToNext();
		}
		cursor.close();
		return meetings;
	}
	
	private Project createProject(Cursor cursor)
	{
		int pID = cursor.getInt(0);
		String name = cursor.getString(1);
		Project tmpPro = new Project(pID,name);
		return tmpPro;
	}
	
	private ToDoItem createToDoItem(Cursor cursor)
	{
		int tdlID = cursor.getInt(0);
		String name = cursor.getString(1);
		int pid = cursor.getInt(2); 
		ToDoItem tmpTDI = new ToDoItem();
		tmpTDI.setName(name);
		tmpTDI.setID(tdlID);
		tmpTDI.setProjectID(pid);
		return tmpTDI;
	}

	public void updateToDoItem(ToDoItem toDoItem) {
		SQLiteDatabase db = this.getWritableDatabase();
		
		ContentValues cv1 = new ContentValues();
		cv1.put(ToDoItemName, toDoItem.getName());
		
		db.update(ToDoListItemTable, cv1, ToDoItemID+" = ?", new String[] {String.valueOf(toDoItem.getId())});
		
	}

	public void updateProject(Project p) {
		SQLiteDatabase db = this.getWritableDatabase();
		
		ContentValues cv1 = new ContentValues();
		cv1.put(projectName, p.getName());
		
		db.update(projectTable, cv1, projectID+" =?", new String[] {String.valueOf(p.getID())} );
		
	}

	public void deleteProject(Project p) {
		// TODO Auto-generated method stub
		SQLiteDatabase db = this.getWritableDatabase();
		int id = p.getID();
		db.delete(projectTable, projectID+" = ?", new String[] {String.valueOf(id)});
		
		Log.i(WorkLoad.TAG, "project has been deleted");
	}

	public void deleteToDoItem(ToDoItem selectedToDoItem) {
		// TODO Auto-generated method stub
		SQLiteDatabase db = this.getWritableDatabase();
		int id = selectedToDoItem.getId();
		db.delete(ToDoListItemTable, ToDoItemID+" = ?", new String[] {String.valueOf(id)});
		
		Log.i(WorkLoad.TAG, "ToDoItem has been deleted");
		
	}
	
	
	

}

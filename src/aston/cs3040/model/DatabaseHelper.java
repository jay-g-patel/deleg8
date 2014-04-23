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

/**
 * @author Jayshree
 *
 */
public class DatabaseHelper extends SQLiteOpenHelper{
	public static final String DBNAME = "projectList";
	
	public static final String projectTable = "Project";
	public static final String projectID = "ProjectID";
	public static final String projectName = "ProjectName";
	
	public static final String ToDoListItemTable = "ToDoListItemTable";
	public static final String ToDoItemID = "ToDoItemID";
	public static final String ToDoItemName = "ToDoItemName";
	
	public static final String projectContactsID = "projectContactsID";
	public static final String ContactsTable = "ContactsTable";
	public static final String ContactID = "ContactID";
	public static final String ContactProjectID = "ContactProjectID";
	public static final String ContactProjectRoleID = "ContactProjectRoleID";
	
	public static final String MeetingsTable = "MeetingsTable";
	public static final String INCMeetingID = "INCMeetingID";
	public static final String EventID = "EventID";
	public static final String MeetingProjectID = "MeetingProjectID";
	public static final String MeetingToDoItemID = "MeetingToDoItemID";
	
	public static final String ImagesTable = "ImagesTable";
	public static final String ImageID = "ImageID";
	public static final String ImageURL = "ImageURL";
	public static final String ImageProjectID = "ImageProjectID";
	
	public static final String ProjectRolesTable = "ProjectRolesTable";
	public static final String ProjectRoleID = "ProjectRoleID";
	public static final String ProjectRole_ProjectID = "ProjectRole_ProjectID";
	public static final String ProjectRole_Description = "ProjectRole_ProjectDescription";
	
//	public static final String ProjectContactRoleTable = "ProjectContactRoleTable";
//	public static final String ProjectContactRole_ProjectRoleID = "ProjectContactRole_ProjectRoleID";
//	public static final String ProjectContactRole_ProjectContactID = "ProjectContactRole_ProjectContactID";

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
		String sqlContacts =  "CREATE TABLE " + ContactsTable + 
				" ("+projectContactsID + " INTEGER PRIMARY KEY AUTOINCREMENT, "+ContactID+" STRING, "+ContactProjectID+" String, "+ContactProjectRoleID+" Integer)";
		String sqlImages = "CREATE TABLE " + ImagesTable + 
				" ("+ImageID + " INTEGER PRIMARY KEY AUTOINCREMENT, "+ ImageURL+ " TEXT, "+ImageProjectID+" INTEGER)";
		String sqlProjectRoles = "CREATE TABLE " + ProjectRolesTable + 
				" ("+ProjectRoleID + " INTEGER PRIMARY KEY AUTOINCREMENT, "+ ProjectRole_ProjectID+ " INTEGER, "+ProjectRole_Description+" String)";
//		String sqlProjectContactRoles = "CREATE TABLE " + ProjectContactRoleTable + 
//				" ("+ProjectContactRole_ProjectContactID + " INTEGER PRIMARY KEY, "+ ProjectContactRole_ProjectRoleID+ " INTEGER)";
//		
		db.execSQL(sql);
		db.execSQL(sql1);
		db.execSQL(sqlmeetings);
		db.execSQL(sqlContacts);
		db.execSQL(sqlImages);
		db.execSQL(sqlProjectRoles);
//		db.execSQL(sqlProjectContactRoles);
		
		insertInitialProjects(db);
		insertInitialRoles(db);
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
	

	private void insertInitialRoles(SQLiteDatabase db)
	{
		ContentValues cv1 = new ContentValues();
		cv1.put(ProjectRole_ProjectID, 0);
		cv1.put(ProjectRole_Description, "Team Leader");
		long ToDoItemOneId = db.insert(ProjectRolesTable, ProjectRoleID, cv1);
		
		cv1.put(ProjectRole_ProjectID, 0);
		cv1.put(ProjectRole_Description, "photographer");
		long ToDoItemTwoId = db.insert(ProjectRolesTable, ProjectRoleID, cv1);
		
		cv1.put(ProjectRole_ProjectID, 0);
		cv1.put(ProjectRole_Description, "Team Member");
		long ToDoItemThreeId = db.insert(ProjectRolesTable, ProjectRoleID, cv1);
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
	
	public void addContactToDB(Contact contact)
	{
		if(doesContactExist(contact))
		{
			Log.i(WorkLoad.TAG,"contact does Exist BLAD!");
			//check the contact role here, if it is the same as the one already against it then do nothing
			if(hasContactRoleChanged(contact))
			{
				SQLiteDatabase db = this.getWritableDatabase();
				
				ContentValues cv1 = new ContentValues();
				cv1.put(ContactProjectRoleID, contact.getRole().getProjectRoleID());
				
				db.update(ContactsTable, cv1, ContactID+" = ? AND "+ContactProjectID+" = ?",new String[]{contact.getContactID(), contact.getProjectID()});
			}
			//else update the contact with the new role
		}
		else
		{
		ContentValues cv1 = new ContentValues();
		cv1.put(ContactID, contact.getContactID());
		Log.i(WorkLoad.TAG, "contact ID is "+contact.getContactID());
		cv1.put(ContactProjectID, contact.getProjectID());
		Log.i(WorkLoad.TAG, "project id JUST BEFORE ENTERING INTO DB IS "+contact.getProjectID());
		cv1.put(ContactProjectRoleID, contact.getRole().getProjectRoleID());
		getWritableDatabase().insert(ContactsTable,null,cv1);
		
		Log.i(WorkLoad.TAG, "Entered contact into DB with values name/projectID = "+contact.getName()+"/"+contact.getProjectID());
		}
	}
	
	public boolean hasContactRoleChanged(Contact contact)
	{
		boolean hasContactRoleChanged = false;
		
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery("SELECT * FROM "+ContactsTable+" WHERE "+ContactID+" = ? AND "+ContactProjectID+" = ?",new String[]{contact.getContactID(), contact.getProjectID()});
		cursor.moveToFirst();
		String currentRole = cursor.getString(3);
		String role = contact.getRole().getRoleDescription();
		if(currentRole.equalsIgnoreCase(contact.getRole().getRoleDescription()))
		{
			hasContactRoleChanged = false;
		}
		else
		{
			hasContactRoleChanged = true;
		}
	
		return hasContactRoleChanged;
	}
	
	public boolean doesContactExist(Contact contact)
	{
		boolean contactExists = false;
		Log.i(WorkLoad.TAG, "Contact id and project id are "+contact.getContactID()+" "+contact.getProjectID());
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery("SELECT * FROM "+ContactsTable+" WHERE "+ContactID+" = ? AND "+ContactProjectID+" = ?",new String[]{contact.getContactID(), contact.getProjectID()});
		cursor.moveToFirst();
		if(cursor.getCount() == 0)
		{
			contactExists = false;
		}
		else
		{
			contactExists = true;
		}
		return contactExists;
	}
	
	public List<String> getAllContactsForProject(String projectID)
	{
		Log.i(WorkLoad.TAG, "getting all contacts for project id - "+projectID);
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery("SELECT "+ContactID+" FROM "+ ContactsTable+" WHERE "+ContactProjectID+" =?",new String[]{projectID});
		List<String> contacts = new ArrayList<String>();
		cursor.moveToFirst();
		while(!cursor.isAfterLast())
		{
			contacts.add(cursor.getString(0));
			cursor.moveToNext();
		}
		cursor.close();
		return contacts;
		
	}

	public void addImageToProject(String picturePath, int projectID)
	{
		ContentValues cv1 = new ContentValues();
		cv1.put(ImageURL, picturePath);
		cv1.put(ImageProjectID, projectID);
		getWritableDatabase().insert(ImagesTable,null, cv1);
	}
	
	public ArrayList<String> getAllProjectImages(int imageProjectID)
	{
		SQLiteDatabase db = this.getReadableDatabase();
		Log.i(WorkLoad.TAG, "project id to get images is - "+imageProjectID);
		Cursor cursor = db.rawQuery("SELECT "+ImageURL+" FROM "+ ImagesTable+" WHERE "+ImageProjectID+" =?",new String[]{String.valueOf(imageProjectID)});
		ArrayList<String> imageURLs = new ArrayList<String>();
		cursor.moveToFirst();
		while(!cursor.isAfterLast())
		{
			imageURLs.add(cursor.getString(0));
			cursor.moveToNext();
		}
		cursor.close();
		db.close();
		return imageURLs;
	}

	public void deleteContact(Contact contact)
	{
		String contactID = contact.getContactID();
		String contactProjectID = contact.getProjectID();
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete(ContactsTable, ContactID+" = ? AND "+ContactProjectID+" = ?", new String[] {contactID,contactProjectID});
		
		Log.i(WorkLoad.TAG, "ToDoItem has been deleted");
		
	}

	
	public int getProjectContactID(Contact c)
	{
		Log.i(WorkLoad.TAG, "getting all contacts for project id - "+c.getProjectID()); 
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery("SELECT "+projectContactsID+" FROM "+ ContactsTable+" WHERE "+ContactProjectID+" =? AND "+ContactID+" =?",new String[]{c.getProjectID(), c.getContactID()});
		int pCID = 0;
		cursor.moveToFirst();
		while(!cursor.isAfterLast())
		{
			pCID = cursor.getInt(0);
			
		}
		cursor.close();
		return pCID;
		
		
	}

	public ArrayList<String> getAllProjectRoles(int projectID)
	{
		SQLiteDatabase db = this.getReadableDatabase();
		Log.i(WorkLoad.TAG, "project id to get images is - "+projectID);
		Cursor cursor = db.rawQuery("SELECT * FROM "+ ProjectRolesTable+" WHERE "+ProjectRole_ProjectID+" =?",new String[]{String.valueOf(projectID)});
		ArrayList<String> projectRoles = new ArrayList<String>();
		cursor.moveToFirst();
		while(!cursor.isAfterLast())
		{
			String rDescipt = cursor.getString(2);
			projectRoles.add(rDescipt);
			cursor.moveToNext();
		}
		cursor.close();
		db.close();
		return projectRoles;
	}
	
	public Role[] getAllGenericRoles()
	{
		SQLiteDatabase db = this.getReadableDatabase();
		Log.i(WorkLoad.TAG, "project id to get images is - "+projectID);
		Cursor cursor = db.rawQuery("SELECT * FROM "+ ProjectRolesTable+" WHERE "+ProjectRole_ProjectID+" = ?",new String[]{String.valueOf(0)});
		//ArrayList<Role> projectRoles = new ArrayList<Role>();
		Role[] projectRoles = new Role[cursor.getCount()];
		cursor.moveToFirst();
		int i=0;
		while(!cursor.isAfterLast())
		{
			
			int pRoleId = cursor.getInt(0);
			int pId = cursor.getInt(1);
			String rDescipt = cursor.getString(2);
			Role tmpRole = new Role(pRoleId,pId,rDescipt);
			projectRoles[i] = tmpRole;
			cursor.moveToNext();
			i++;
		}
		cursor.close();
		db.close();
		return projectRoles;
	}

	public String getContactProjectRoleDescription(int projectID,
			int contactID)
	{
	 
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery("SELECT "+ContactProjectRoleID +" FROM "+ ContactsTable+" WHERE "+ContactProjectID+" = ? AND "+ContactID+" = ?",new String[]{String.valueOf(projectID), String.valueOf(contactID)});
		int pCID = 0;
		cursor.moveToFirst();
		if(!cursor.isAfterLast())
		{
			pCID = cursor.getInt(0);
			
		}
		cursor.close();
		
		Cursor cursor1 = db.rawQuery("SELECT "+ProjectRole_Description +" FROM "+ ProjectRolesTable+" WHERE "+ProjectRoleID+" = ?",new String[]{String.valueOf(pCID)});
		String ProjectRoleDescription = "";
		cursor1.moveToFirst();
		if(!cursor.isAfterLast())
		{
			ProjectRoleDescription = cursor1.getString(0);
			
		}
		cursor1.close();
		
		return ProjectRoleDescription;
		
	}
	
	
	
	
	

}

package aston.cs3040.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

public class Project {

	

	private int id;
	private String projectName;
	public ArrayList<ToDoItem> toDoList = new ArrayList<ToDoItem>();
	private DatabaseHelper dbHelper = new DatabaseHelper(WorkLoad.getInstance().wAppContext);
	private ArrayList<Meeting> meetingsList;
	
	
	public Project()
	{
		initToDoList();
	}
	
	public Project(int id, String name)
	{
		this.id = id;
		this.projectName = name;
		initToDoList();
	}
	
//	public void initMeetingsForProject()
//	{
//		meetingsList = dbHelper
//	}
	
	public ArrayList<ToDoItem> initToDoList()
	{
		toDoList = (ArrayList<ToDoItem>) dbHelper.getAllToDoListItems(this.id);
		if (toDoList == null || toDoList.isEmpty()) {
			Log.i(WorkLoad.TAG, "DUDE!!!! ToDoList is null");
		}
		return toDoList;
	}
	
	public void updateProject()
	{
		initToDoList();
		
	}
	
	public void setProjectToDoList() {
		toDoList = (ArrayList<ToDoItem>) dbHelper.getAllToDoListItems(this.id);
	}
	
	public void addToDoItem(ToDoItem item)
	{
		toDoList.add(item);
	}
	
	public ToDoItem getToDoItem(int i)
	{
		ToDoItem tdi = null;
		for(ToDoItem item : toDoList)
		{
			if(item.getId() == i)
			{
				tdi = item;
			}
		}
		return tdi;
		
	}
	
	public String getName()
	{
		return this.projectName;
	}
	
	public void setName(String newName)
	{
		this.projectName = newName;
	}
	
	public int getID()
	{
		return this.id;
	}
	
	public void setID(int id)
	{
		this.id= id;
	}
	
	@Override
	public String toString() {
		return getName();
	}
	
	public ArrayList<ToDoItem> getToDoList()
	{
		return this.toDoList;
	}
	
	public ArrayList<Meeting> getMeetingsForProject()
	{
		return this.meetingsList;
	}
	
	public void setMeetingsForProject(List<Meeting> meetings)
	{
		this.meetingsList = (ArrayList<Meeting>)meetings;
	}
	
	public void createNewProjectMeeting(Meeting meeting)
	{
		this.meetingsList.add(meeting);
	}
	
	public Meeting getMeetingByID(int id)
	{
		Meeting meeting = null;
		for(Meeting m : meetingsList)
		{
			if(m.getID() == id)
			{
				meeting = m;
			}
		}
		return meeting;
	}

	
}

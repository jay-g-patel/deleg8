package aston.cs3040.model;

import java.util.Date;

import android.location.Location;

public class Meeting
{

	public int iD;
	public Date dateTime;
	public Location loc;
	public int projectID;
	public int toDoItemID;
	public String title;
	public String description;

	public Meeting(Date dateTime, Location loc)
	{
		this.dateTime = dateTime;
		this.loc = loc;
	}

	public Meeting()
	{

	}

	public int getID()
	{
		return this.iD;
	}

	public void setID(int iD)
	{
		this.iD = iD;
	}
	
	public Date getDateTime()
	{
		return dateTime;
	}

	public void setDateTime(Date dateTime)
	{
		this.dateTime = dateTime;
	}

	public Location getLoc()
	{
		return loc;
	}

	public void setLoc(Location loc)
	{
		this.loc = loc;
	}

	public int getProjectID()
	{
		return projectID;
	}

	public void setProjectID(int projectID)
	{
		this.projectID = projectID;
	}

	public int getToDoItemID()
	{
		return toDoItemID;
	}

	public void setToDoItemID(int toDoItemID)
	{
		this.toDoItemID = toDoItemID;
	}
	
	public String getTitle()
	{
		return this.title;
	}

	public void setTitle(String title)
	{
		this.title = title;
	}
	
	public String getDescription()
	{
		return this.description;
	}

	public void setDescription(String description)
	{
		this.description = description;
	}

}

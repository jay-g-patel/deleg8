package aston.cs3040.model;

import java.io.Serializable;
import java.util.Date;

public class ToDoItem implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -8073277412665335750L;
	public int id = 0;
	public String name = null;
	public boolean completed = false;
	public Date completionDate = null;
	public String description = null;
	public int projectID = 0;
	
	public ToDoItem()
	{
		this.name = "";
		this.completed = false;
		this.completionDate = new Date();
		this.description = "";
	}
	
	public ToDoItem(String name, Date completionDate, String description)
	{
		this.name = name;
		this.completionDate = completionDate;
		this.description = description;
		this.completed = false;
	}

	public ToDoItem(String name)
	{
		this.name = name;
		this.completionDate = new Date();
		this.description = "";
		this.completed = false;
	}
	
	public int getId()
	{
		return this.id;
	}
	
	public void setID(int newID)
	{
		this.id=newID;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isCompleted() {
		return completed;
	}

	public void setCompleted(boolean completed) {
		this.completed = completed;
	}

	public Date getCompletionDate() {
		return completionDate;
	}

	public void setCompletionDate(Date completionDate) {
		this.completionDate = completionDate;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	public int getProjectid()
	{
		return this.projectID;
	}
	
	public void setProjectID(int pid)
	{
		this.projectID = pid;
	}
	
	

}

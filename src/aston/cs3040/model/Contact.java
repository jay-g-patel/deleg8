package aston.cs3040.model;

import java.io.Serializable;

public class Contact
{

	private String projectID;
	private String contactID;
	private String name;
	private String number;
	private String email;
	
	public Contact(String id, String name, String number, String email, String proID)
	{
		this.setContactID(id);
		this.setName(name);
		this.setNumber(number);
		this.setEmail(email);
		this.setProjectID(proID);
	}
	
	public Contact(String name)
	{
		this.setName(name);
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getNumber()
	{
		return number;
	}

	public void setNumber(String number)
	{
		this.number = number;
	}

	public String getEmail()
	{
		return email;
	}

	public void setEmail(String email)
	{
		this.email = email;
	}

	public String getContactID()
	{
		return contactID;
	}

	public void setContactID(String contactID)
	{
		this.contactID = contactID;
	}

	public String getProjectID()
	{
		return projectID;
	}

	public void setProjectID(String projectID)
	{
		this.projectID = projectID;
	}
	
}

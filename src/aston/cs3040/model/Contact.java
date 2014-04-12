package aston.cs3040.model;

public class Contact
{

	private String contactID;
	private String name;
	private String number;
	private String email;
	
	public Contact(String id, String name, String number, String email)
	{
		this.setName(name);
		this.setNumber(number);
		this.setEmail(email);
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
	
}

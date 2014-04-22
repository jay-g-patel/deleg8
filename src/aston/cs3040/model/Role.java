package aston.cs3040.model;

public class Role
{

	private int projectRoleID = 0;
	private int projectID = 0;
	private String roleDescription = "";
	
	public Role()
	{
		
	}
	
	public Role(int projectRole, int projectid, String roleDescription)
	{
		this.projectRoleID = projectRole;
		this.projectID = projectid;
		this.roleDescription = roleDescription;
	}
	
	public int getProjectRoleID()
	{
		return projectRoleID;
	}
	public void setProjectRoleID(int projectRoleID)
	{
		this.projectRoleID = projectRoleID;
	}
	public int getProjectID()
	{
		return projectID;
	}
	public void setProjectID(int projectID)
	{
		this.projectID = projectID;
	}
	public String getRoleDescription()
	{
		return roleDescription;
	}
	public void setRoleDescription(String roleDescription)
	{
		this.roleDescription = roleDescription;
	}
	
}

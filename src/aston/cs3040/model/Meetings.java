package aston.cs3040.model;

import java.util.ArrayList;

import android.content.Context;

public class Meetings
{

	public Context wAppContext;
	private static Meetings sInstance;
	private ArrayList<Long> meetings;
	
	private Meetings(Context appContext)
	{
		this.wAppContext = appContext;
	}
	
	public static Meetings init(Context appContext) {
		if (sInstance == null) {
			sInstance = new Meetings(appContext);

		}
		return sInstance;

	}

	public static Meetings getInstance() {
		return sInstance;
	}
	
	//public List<long> getallMeetings()
	
	
	
}

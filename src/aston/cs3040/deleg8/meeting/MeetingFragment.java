package aston.cs3040.deleg8.meeting;

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import android.R.bool;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.CalendarContract.Calendars;
import android.provider.CalendarContract.Events;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import aston.cs3040.deleg8.R;
import aston.cs3040.deleg8.ToDoItemActivity;
import aston.cs3040.model.Meeting;
import aston.cs3040.model.WorkLoad;

public class MeetingFragment extends Fragment
{
	static Button start_date;
	static Button end_date;
	public Button start_time;
	public Button end_time;
	
	private int toDoItemID;
	private int projectID;
	
	private EditText mTitleET;
	private EditText mDescriptionET;

	private CheckBox allDayCheck; 
	
	private Button save;
	private Button cancel;
	
	View v;
	private OnDateSetListener startDatePickerListener;
	static Calendar cal = Calendar.getInstance();
	static int sDay = cal.get(Calendar.DAY_OF_MONTH);
	static int sMonth = cal.get(Calendar.MONTH);
	static int sYear = cal.get(Calendar.YEAR);
	public int sHour;
	public int sMinute;
	private OnTimeSetListener startTimePickerListener;
	
	static int eDay = cal.get(Calendar.DAY_OF_MONTH);
	static int eMonth = cal.get(Calendar.MONTH);
	static int eYear = cal.get(Calendar.YEAR);
	private int ehour;
	private int emin;
	private OnTimeSetListener endTimePickerListener;
	

	
	public void onCreate(Bundle savedInstanceState)
	{		
		projectID = getActivity().getIntent().getIntExtra("PROJECTID", 0);
		toDoItemID = getActivity().getIntent().getIntExtra("TODOITEMID", 0);
		super.onCreate(savedInstanceState);
		
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState){
		v = inflater.inflate(R.layout.activity_meeting,  parent, false);
		
		addSaveButtonListener();
		setStartDateOnClickListener();
		
		start_time = (Button)v.findViewById(R.id.start_time);
		start_time.setVisibility(View.VISIBLE);
		if(ehour==0 ||emin == 0)
		{
			sHour = 1;
			sMinute=00;
		}
		
		startTimePickerListener = new OnTimeSetListener()
		{
			
			@Override
			public void onTimeSet(TimePicker view, int hourOfDay, int minute)
			{
				// TODO Auto-generated method stub
				sHour = hourOfDay;
				sMinute = minute;
				start_time.setText(sHour+":"+sMinute);
				
			}
		};
		
		
		start_time.setOnClickListener(new View.OnClickListener() {
		

			@Override
			public void onClick(View v) {
			
				
				TimePickerDialog dialog = new TimePickerDialog(getActivity(), startTimePickerListener, sHour, sMinute,true);
				dialog.show();
			}
		});
		
		
		
		
			end_time = (Button)v.findViewById(R.id.end_time);
			end_time.setVisibility(View.VISIBLE);
			if(ehour==0 ||emin == 0)
			{
				ehour = 1;
				emin=00;
			}
			
			endTimePickerListener = new OnTimeSetListener()
			{
				
				@Override
				public void onTimeSet(TimePicker view, int hourOfDay, int minute)
				{
					// TODO Auto-generated method stub
					ehour = hourOfDay;
					emin = minute;
					end_time.setText(ehour+":"+emin);
				}
			};
			
			
			end_time.setOnClickListener(new View.OnClickListener() {
			

				@Override
				public void onClick(View v) {
				
					
					TimePickerDialog dialog = new TimePickerDialog(getActivity(), endTimePickerListener, ehour, emin,true);
					dialog.show();
				}
			});
		
	
			
		setEndDateOnClickListener();
		return v;
	}
	
	
	
	private void setStartDateOnClickListener()
	{
		start_date = (Button)v.findViewById(R.id.start_date);
		start_date.setVisibility(View.VISIBLE);
		start_date.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View view)
			{
				showStartDateDialogue(view);
			}
			
		});
	}
	
	public void showStartDateDialogue(View v)
	{
		DialogFragment dialogueFragment = new StartDatePicker();
		dialogueFragment.show(getFragmentManager(), "start_date_picker");
	}
	
	public static class StartDatePicker extends DialogFragment implements DatePickerDialog.OnDateSetListener{
	    @Override
	    public Dialog onCreateDialog(Bundle savedInstanceState) {
	        // TODO Auto-generated method stub
	        // Use the current date as the default date in the picker
	        DatePickerDialog dialog = new DatePickerDialog(getActivity(), this, sYear, sMonth, sDay);
			return dialog;

	    }
	    public void onDateSet(DatePicker view, int year, int monthOfYear,
	            int dayOfMonth) {
	        // TODO Auto-generated method stub
	        // Do something with the date chosen by the user
	        sYear = year;
	        sMonth = monthOfYear;
	        sDay = dayOfMonth;
	        updateStartDateDisplay(sDay, sMonth+1, sYear);
	    }
	} 
	
	public static void updateStartDateDisplay(int day, int month, int year)
	{
		start_date.setText(day+"/"+month+"/"+year);
	}
	////////// Start Time
	
	
	
	///////// end date
	private void setEndDateOnClickListener()
	{
		end_date = (Button)v.findViewById(R.id.end_date);
		end_date.setVisibility(View.VISIBLE);
		end_date.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View view)
			{
				showEndDateDialogue(view);
			}
			
		});
	}
	
	public void showEndDateDialogue(View v)
	{
		DialogFragment dialogueFragment = new EndDatePicker();
		dialogueFragment.show(getFragmentManager(), "end_date_picker");
	}
	
	public static class EndDatePicker extends DialogFragment implements DatePickerDialog.OnDateSetListener{
	    @Override
	    public Dialog onCreateDialog(Bundle savedInstanceState) {
	        // TODO Auto-generated method stub
	        // Use the current date as the default date in the picker
	        DatePickerDialog dialog = new DatePickerDialog(getActivity(), this, eYear, eMonth, eDay);
			return dialog;

	    }
	    public void onDateSet(DatePicker view, int year, int monthOfYear,
	            int dayOfMonth) {
	        // TODO Auto-generated method stub
	        // Do something with the date chosen by the user
	        eYear = year;
	        eMonth = monthOfYear;
	        eDay = dayOfMonth;
	        updateEndDateDisplay(eDay, eMonth+1, eYear);
	    }
	} 
	
	public static void updateEndDateDisplay(int day, int month, int year)
	{
		end_date.setText(day+"/"+month+"/"+year);
	}
	
	public void getCalendarDetails()
	{
		
	}
	
	
	public void addSaveButtonListener()
	{
		Log.i(WorkLoad.TAG, "Listener hit ");	
		
		save = (Button)v.findViewById(R.id.button_save);
		
		mTitleET = (EditText)v.findViewById(R.id.meeting_title_edittxt);
		mDescriptionET = (EditText)v.findViewById(R.id.meeting_description_edittxt);
		
		save.setVisibility(View.VISIBLE);
		save.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View view)
			{
				String mTitle = mTitleET.getText().toString();
				Log.i(WorkLoad.TAG, "title is "+ mTitleET.getText().toString());
				String mDescription = mDescriptionET.getText().toString();
				Log.i(WorkLoad.TAG, "description is "+mDescriptionET.getText().toString());
				validateEverything();
//				Meeting m = new Meeting();
//				m.setTitle(mTitle);
//				m.setDescription(mDescription);
//				m.setToDoItemID(toDoItemID);
//				m.setProjectID(projectID);
				Calendar scal = Calendar.getInstance();
				scal.set(sYear, sMonth, sDay, sHour, sMinute);
				Calendar ecal = Calendar.getInstance();
//				long stdvbhs = scal.getTimeInMillis();
				ecal.set(eYear, eMonth, eDay, ehour, emin);
				
				long startDT = scal.getTimeInMillis();
				long endDT = ecal.getTimeInMillis();
				TimeZone tz = TimeZone.getDefault();
				
				ContentResolver cr = getActivity().getContentResolver();
				ContentValues values = new ContentValues();
				
				values.put(Events.DTSTART, startDT);
				values.put(Events.DTEND, endDT);
				values.put(Events.TITLE, mTitle);
				values.put(Events.DESCRIPTION, mDescription);
				values.put(Events.CALENDAR_ID, WorkLoad.getInstance().getCALID());
				values.put(Events.EVENT_TIMEZONE, tz.getID());
				
				Uri uri = cr.insert(Events.CONTENT_URI, values);
				
				long eventID = Long.parseLong(uri.getLastPathSegment());
				Log.i(WorkLoad.getInstance().TAG, "Event id in meetings fragment has been returned as "+eventID);
				Log.i(WorkLoad.getInstance().TAG, " YEAR MONTH DAY ARE "+new Date(startDT));
				Log.i(WorkLoad.getInstance().TAG, "PROJECT AND TO DO ITEM ID IS " + projectID +" "+toDoItemID);
				WorkLoad.getInstance().addMeeting(eventID, projectID, toDoItemID);
				
				Intent i = new Intent(getActivity(), MeetingsListActivity.class);
				Log.i(WorkLoad.TAG, "Project ID = " + projectID);
				startActivity(i);
				
			}
		}
				);
	}
	

	
	private boolean validateEverything()
	{
	
		return true;
	}
	
}

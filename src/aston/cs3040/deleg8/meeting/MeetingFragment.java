package aston.cs3040.deleg8.meeting;

import java.util.Calendar;
import java.util.Date;

import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
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
	private int hour;
	private int min;
	private OnTimeSetListener endTimePickerListener;
	
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState){
		v = inflater.inflate(R.layout.activity_meeting,  parent, false);
		
		setStartDateOnClickListener();
		
		start_time = (Button)v.findViewById(R.id.start_time);
		start_time.setVisibility(View.VISIBLE);
		if(hour==0 ||min == 0)
		{
			hour = 1;
			min=00;
		}
		
		startTimePickerListener = new OnTimeSetListener()
		{
			
			@Override
			public void onTimeSet(TimePicker view, int hourOfDay, int minute)
			{
				// TODO Auto-generated method stub
				hour = hourOfDay;
				min = minute;
				start_time.setText(hour+":"+min);
			}
		};
		
		
		start_time.setOnClickListener(new View.OnClickListener() {
		

			@Override
			public void onClick(View v) {
			
				
				TimePickerDialog dialog = new TimePickerDialog(getActivity(), startTimePickerListener, hour, min,true);
				dialog.show();
			}
		});
		
		
		
		
			end_time = (Button)v.findViewById(R.id.end_time);
			end_time.setVisibility(View.VISIBLE);
			if(hour==0 ||min == 0)
			{
				hour = 1;
				min=00;
			}
			
			endTimePickerListener = new OnTimeSetListener()
			{
				
				@Override
				public void onTimeSet(TimePicker view, int hourOfDay, int minute)
				{
					// TODO Auto-generated method stub
					hour = hourOfDay;
					min = minute;
					end_time.setText(hour+":"+min);
				}
			};
			
			
			end_time.setOnClickListener(new View.OnClickListener() {
			

				@Override
				public void onClick(View v) {
				
					
					TimePickerDialog dialog = new TimePickerDialog(getActivity(), endTimePickerListener, hour, min,true);
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
	
	
	public void addSaveButtonListener()
	{
		Log.i(WorkLoad.TAG, "Listener hit ");	
		
		save = (Button)v.findViewById(R.id.button_save);
		save.setVisibility(View.VISIBLE);
		save.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View view)
			{
				validateEverything();
				
				
			}
		}
				);
			
		
		
		
	}
	
	private boolean validateEverything()
	{
	
		return true;
	}
	
}

package aston.cs3040.deleg8;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import aston.cs3040.model.Project;
import aston.cs3040.model.ToDoItem;
import aston.cs3040.model.WorkLoad;

public class EditableToDoItemFragment  extends Fragment{
	
	View v = null;
	Button addToDoItemButton = null;
	Button saveToDoItemButton = null;
	int projectID;
	ToDoItem toDoItem = null;
	EditText toDoName = null;
	
	static Button startDate_textView;
	
	
	private OnDateSetListener startDatePickerListener;
	static Calendar toDocal = Calendar.getInstance();
	static int toDosDay = toDocal.get(Calendar.DAY_OF_MONTH);
	static int toDosMonth = toDocal.get(Calendar.MONTH);
	static int toDosYear = toDocal.get(Calendar.YEAR);
	
	
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		toDoItem = (ToDoItem)getActivity().getIntent().getExtras().get("TODOITEM");
		if(toDoItem ==null)
		{
		projectID = getActivity().getIntent().getExtras().getInt("PID");
		Log.i(WorkLoad.TAG,"ProjectID = "+projectID);
		}
		else{
			projectID = toDoItem.getProjectid();
		}
		
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState){
		v = inflater.inflate(R.layout.editable_todoitem,  parent, false);
		toDoName = (EditText)v.findViewById(R.id.toDoItem_titleEditText);
		
		if(toDoItem == null)
		{
			addToDoItemButtonListener();
		}
		else
		{
			
			toDoName.setText(toDoItem.getName());
			addSaveButtonListener();
		}
		setStartDateOnClickListener();
		startDate_textView = (Button)v.findViewById(R.id.editToDoCompletionDate);
		if(toDoItem!=null && !toDoItem.getCompletionDate().equals(""))
		{
			startDate_textView.setText(toDoItem.getCompletionDate());
			Log.i(WorkLoad.TAG,"completion Date is "+toDoItem.getCompletionDate());
		}
		else
		{
			startDate_textView.setText("Set Completion Date");
		}
		
		return v;
	}
	
	public void addToDoItemButtonListener()
	{
		Log.i(WorkLoad.TAG, "Listener hit ");	
		
		addToDoItemButton = (Button)v.findViewById(R.id.add_toDoItem_button);
		addToDoItemButton.setVisibility(View.VISIBLE);
		addToDoItemButton.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View view)
			{
				ToDoItem tmpTDI= new ToDoItem();
				
				tmpTDI.setName(toDoName.getText().toString());
				tmpTDI.setProjectID(projectID);
				if(!startDate_textView.getText().toString().equalsIgnoreCase("Set Completion Date"))
				{
				tmpTDI.setCompletionDate(startDate_textView.getText().toString());
				}
				
				
				///// add project into workload here
				WorkLoad.getInstance().addNewToDoItem(tmpTDI);
				Log.i(WorkLoad.TAG, "toDoItem added ");	
				Intent i = new Intent(getActivity(), ToDoListActivity.class);
				i.putExtra("PID", projectID);

				getActivity().finish();
				startActivity(i);
				//Log.i(WorkLoad.TAG, "loading intent");
			
				
			}
		}
				);
			
	}
	
	public Date getCompletionDate()
	{
		Date date = new Date();
		try
		{
			date = new SimpleDateFormat("DDMMYYYY").parse(startDate_textView.getText().toString());
		}
		catch (ParseException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return date;
	}

	public void addSaveButtonListener()
	{
		Log.i(WorkLoad.TAG, "Listener hit ");	
		
		saveToDoItemButton = (Button)v.findViewById(R.id.save_toDoItem_button);
		saveToDoItemButton.setVisibility(View.VISIBLE);
		saveToDoItemButton.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View view)
			{
				toDoItem.setName(toDoName.getText().toString());
				toDoItem.setCompletionDate(startDate_textView.getText().toString());
				Log.i(WorkLoad.TAG,"completion date entered in form is "+toDoItem.getCompletionDate());
				///// add project into workload here
				WorkLoad.getInstance().updateToDoItem(toDoItem);
//				Log.i(WorkLoad.TAG, "toDoItem added ");	
				Intent i = new Intent(getActivity(), ToDoItemActivity.class);
				i.putExtra("PID", projectID);
				i.putExtra("TODOITEM", toDoItem);
				getActivity().finish();
				startActivity(i);
				//Log.i(WorkLoad.TAG, "loading intent");
			
				
			}
		}
				);
			
	}
	private void setStartDateOnClickListener()
	{
		startDate_textView = (Button)v.findViewById(R.id.editToDoCompletionDate);
		startDate_textView.setVisibility(View.VISIBLE);
		startDate_textView.setOnClickListener(new View.OnClickListener()
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
	public static void updateStartDateDisplay(int day, int month, int year)
	{
		
		startDate_textView.setText(day+"/"+month+"/"+year);
	}
	
	public static class StartDatePicker extends DialogFragment implements DatePickerDialog.OnDateSetListener{
	    @Override
	    public Dialog onCreateDialog(Bundle savedInstanceState) {
	        // TODO Auto-generated method stub
	        // Use the current date as the default date in the picker
	        DatePickerDialog dialog = new DatePickerDialog(getActivity(), this, toDosYear, toDosMonth, toDosDay);
			return dialog;

	    }
	    public void onDateSet(DatePicker view, int year, int monthOfYear,
	            int dayOfMonth) {
	        // TODO Auto-generated method stub
	        // Do something with the date chosen by the user
	    	toDosYear = year;
	    	toDosMonth = monthOfYear;
	    	toDosDay = dayOfMonth;
	        updateStartDateDisplay(toDosDay, toDosMonth+1, toDosYear);
	    }
	} 
	
	
}

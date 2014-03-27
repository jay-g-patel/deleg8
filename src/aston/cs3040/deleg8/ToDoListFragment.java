package aston.cs3040.deleg8;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemLongClickListener;
import aston.cs3040.model.DatabaseHelper;
import aston.cs3040.model.Project;
import aston.cs3040.model.ToDoItem;
import aston.cs3040.model.WorkLoad;

public class ToDoListFragment extends ListFragment
{

	public ArrayList<ToDoItem> toDoList = new ArrayList<ToDoItem>();
	private int projectID;
	private ToDoItem selectedToDoItem;

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		projectID = getActivity().getIntent().getIntExtra("PID", -1);
		toDoList = WorkLoad.getInstance().getProjectByProjectID(projectID)
				.getToDoList();

//		this.setListAdapter(new ArrayAdapter<ToDoItem>(getActivity(),
//				android.R.layout.simple_list_item_1, toDoList));
		ListAdapter adapter = new ToDoItemListAdapter(getActivity(), toDoList);
		this.setListAdapter(adapter);
	}

	@Override
	public void onListItemClick(ListView l, View v, int pos, long id)
	{
		Log.i(WorkLoad.TAG, "something has been clicked");
		ToDoItem tdi = (ToDoItem) (getListAdapter()).getItem(pos);
		Intent i = new Intent(getActivity(), ToDoItemActivity.class);
		Log.i(WorkLoad.TAG, "" + tdi.id + " has been clicked");
		i.putExtra("ToDoItemid", tdi.id);
		i.putExtra("PID", projectID);
		i.putExtra("TODOITEM", tdi);
		Log.i(WorkLoad.TAG, "Project ID = " + projectID);
		startActivity(i);
	}

	private class ToDoItemListAdapter extends ArrayAdapter<ToDoItem>
	{
		List<ToDoItem> items;

		public ToDoItemListAdapter(Activity a, ArrayList<ToDoItem> toDoItems)
		{
			super(a, 0, toDoItems);
			items = toDoItems;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent)
		{
			if (convertView == null)
			{
				convertView = getActivity().getLayoutInflater().inflate(
						R.layout.list_layout_todoitems, null);

			}
			// int tmp = position-1;
			selectedToDoItem = items.get(position);
			Log.i(WorkLoad.TAG,
					"toDoListFragm = adding to do list item at pos " + position
							+ " Item name = " + selectedToDoItem.getName());

			TextView titleTextView = (TextView) convertView
					.findViewById(R.id.toDoItem_titleTextView);
			titleTextView.setText(selectedToDoItem.getName());
			// Log.i(WorkLoad.TAG, "item name = " + selectedToDoItem.getName());

			getListView().setOnItemLongClickListener(
					new OnItemLongClickListener()
					{
						@Override
						public boolean onItemLongClick(AdapterView<?> list,
								View v, int pos, long id)
						{
							selectedToDoItem = (ToDoItem) list
									.getItemAtPosition(pos);
							Log.i(WorkLoad.TAG,
									"I'm gonna show the dialogue :)");
							showToDoDeleteConfirmAlert();
							Log.i(WorkLoad.TAG, "orrr maybe im not");
							return true;
						}

						private void showToDoDeleteConfirmAlert()
						{
							// TODO Auto-generated method stub
							Log.i(WorkLoad.TAG, "in the dialogue Method");
							AlertDialog.Builder confirmToDoDelete = new AlertDialog.Builder(
									getActivity());
							confirmToDoDelete
									.setMessage("Are you sure you want to delete "
											+ selectedToDoItem.getName() + "?");
							confirmToDoDelete.setCancelable(true);
							confirmToDoDelete.setPositiveButton(R.string.ok,
									new DialogInterface.OnClickListener()
									{

										@Override
										public void onClick(
												DialogInterface dialog,
												int which)
										{
											// This is what you need to do when
											// you press okay
											WorkLoad.getInstance()
													.deleteToDoItemByID(
															selectedToDoItem);
											Intent i = new Intent(
													getActivity(),
													ToDoListActivity.class);
											getActivity().finish();
											i.putExtra("PID", projectID);
											i.putExtra("REFRESH", true);
											startActivity(i);
										}
									});

							confirmToDoDelete.setNegativeButton(
									R.string.cancel,
									new DialogInterface.OnClickListener()
									{

										@Override
										public void onClick(
												DialogInterface dialog,
												int which)
										{
											// This is what to do when user hits
											// cancel

										}
									});

							confirmToDoDelete.show();
						}
					});

			return convertView;
		}
	}

}

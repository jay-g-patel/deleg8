package aston.cs3040.deleg8.contacts;

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
import android.widget.TextView;
import android.widget.AdapterView.OnItemLongClickListener;
import aston.cs3040.deleg8.ProjectListActivity;
import aston.cs3040.deleg8.R;
import aston.cs3040.model.Contact;
import aston.cs3040.model.Project;
import aston.cs3040.model.WorkLoad;

public class AppContactsListFragment extends ListFragment
{
	
	private ContactsArrayAdapter adapter;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		List <Contact> allContacts = WorkLoad.getInstance().getContacts();
		
//		this.setListAdapter(new ArrayAdapter<Project>(
//				getActivity(),
//				android.R.layout.simple_list_item_1,
//				allProj));
				
		adapter = new ContactsArrayAdapter(getActivity(), allContacts);
		this.setListAdapter(adapter);
		
	}
	
	private class ContactsArrayAdapter extends ArrayAdapter<Contact> {
		List<Contact> items;
		
		public ContactsArrayAdapter(Activity a, List<Contact> contacts) {
			super(a, 0, contacts);
			this.items = contacts;
		}
		
//		public void updateContactsList(){
//			List<Contact> updatedContactsList = WorkLoad.getInstance().getContacts();
//			items.clear();
//			items.addAll(updatedContactsList);
//			this.notifyDataSetChanged();
//		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			if (convertView == null) {
				convertView = getActivity().getLayoutInflater().inflate(
						R.layout.list_layout_project, null);
			}
			Contact contact = items.get(position);
			Log.i(WorkLoad.TAG, "adding item at pos " + position);

			TextView titleTextView = (TextView) convertView
					.findViewById(R.id.project_titleTextView);
			titleTextView.setText(contact.getName());
			
			
//			getListView().setOnItemLongClickListener(new OnItemLongClickListener() {
//				@Override
//				public boolean onItemLongClick(AdapterView<?>list, View v,int pos, long id){
//					projectToDelete = (Project)list.getItemAtPosition(pos);
//					Log.i(WorkLoad.TAG,"I'm gonna show the dialogue :)");
//					showDeleteConfirmAlert();
//					Log.i(WorkLoad.TAG,"orrr maybe im not");
//					return true;
//				}
//
//				private void showDeleteConfirmAlert() {
//					// TODO Auto-generated method stub
//					Log.i(WorkLoad.TAG,"in the dialogue Method");
//					AlertDialog.Builder confirmDelete = new AlertDialog.Builder(getActivity());
//					confirmDelete.setMessage("Are you sure you want to delete " + projectToDelete.getName()+"?");
//					confirmDelete.setCancelable(true);
//					confirmDelete.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
//						
//						@Override
//						public void onClick(DialogInterface dialog, int which) {
//							// This is what you need to do when you press okay
//							WorkLoad.getInstance().deleteProjectByID(projectToDelete);
//							Intent i = new Intent(getActivity(), ProjectListActivity.class);
//							getActivity().finish();
//							startActivity(i);
//						}
//					});
//					
//					confirmDelete.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
//						
//						@Override
//						public void onClick(DialogInterface dialog, int which) {
//							// This is what to do when user hits cancel
//							
//							
//						}
//					});
//					
//					confirmDelete.show();
//				}
//				});
			return convertView;

		}
		
		
	}
	
}

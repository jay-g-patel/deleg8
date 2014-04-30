package aston.cs3040.deleg8.contacts;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemLongClickListener;
import aston.cs3040.deleg8.R;
import aston.cs3040.model.Contact;
import aston.cs3040.model.WorkLoad;

public class ContactSelectorFragment extends ListFragment
{
	
	private ContactsArrayAdapter adapter;
	private int contactListProjectID = 0;
	private Contact contact = null;
	private List<Contact> selectedContactsList = new ArrayList<Contact>();

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		Log.i(WorkLoad.TAG, "menu is being created");
		inflater.inflate(R.menu.contact_selector, menu);
		super.onCreateOptionsMenu(menu,inflater);
	}
	
	@Override 
	public boolean onOptionsItemSelected(MenuItem item){
		//take the correct action for each action button clicked
		switch(item.getItemId()){
		case R.id.action_addSelectedContacts:
			Log.i(WorkLoad.TAG, "list details are "+selectedContactsList.toString());
			printOutContactsList();
			return true;
		case R.id.action_cancelSelectingContacts:
			return true;
		}
		return false;
	}
	
	private void printOutContactsList()
	{
		Log.i(WorkLoad.TAG, "list details are "+selectedContactsList.toString());
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		try{
		if(getActivity().getIntent().getIntExtra("PROJECTID",0) != 0)
		{
			contactListProjectID = getActivity().getIntent().getIntExtra("PROJECTID", 0);
			Log.i(WorkLoad.TAG, "project id in the app contacts list SELECTOR is "+contactListProjectID);
		}
		}catch(Exception e)
		{
			Log.i(WorkLoad.TAG, "m "+e.getMessage());
		}
		
		List <String> allContactsIDs = WorkLoad.getInstance().getContacts(Integer.toString(contactListProjectID));
		List<Contact> allContacts = new ArrayList<Contact>();
 		for(String cid : allContactsIDs)
		{
 			Contact tmpContact = createContactDetailsByID(cid);
 			allContacts.add(tmpContact);
		}
		
	
				
		adapter = new ContactsArrayAdapter(getActivity(), allContacts);
		this.setListAdapter(adapter);
		
	}
	
	@Override
	public void onResume()
	{
		super.onResume();
		adapter.updateContactsList();
	}
	
	private Contact createContactDetailsByID(String cid)
	{
		
			Contact c = null;
			Cursor contactCursor = null;
	    	Cursor emailCursor = null;
	    	String id = cid;
	    	try{
	    		
	    		Log.i(WorkLoad.TAG, "id is - "+id);
	    		contactCursor = getActivity().getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null , ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "=?", new String[] { id }, null);
	    		int nameIDX = contactCursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME);
	    		emailCursor = getActivity().getContentResolver().query(ContactsContract.CommonDataKinds.Email.CONTENT_URI, null, ContactsContract.CommonDataKinds.Email.CONTACT_ID+"=?", new String[]{id},  null);
	    		int emailIDX = emailCursor.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA);
	    		int numberIDX = contactCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DATA);
	    		String email = "";
	    		if(contactCursor.moveToFirst())
	    		{
	    			Log.i(WorkLoad.TAG, "IN THE CONTACTS SEARCH LOOPY");
	    			String name = contactCursor.getString(nameIDX);
	    			String number = contactCursor.getString(numberIDX);
	    			try{
	    			emailCursor = getActivity().getContentResolver().query(ContactsContract.CommonDataKinds.Email.CONTENT_URI, null, ContactsContract.CommonDataKinds.Email.CONTACT_ID+"=?", new String[]{id},  null);
	    			
	    			if(emailCursor.moveToFirst())
	    			{
	    				email = emailCursor.getString(emailIDX);
	    			}
	    			}
	    			catch(Exception e)
	    			{
	    				Log.i(WorkLoad.TAG, "Error with Email");
	    			}
	    			c = new Contact(id,name,number,email,Integer.toString(contactListProjectID));

	    		}
	    	}
	    	catch(Exception e)
	    	{
	    		Log.i(WorkLoad.TAG, "ERROR HAS OCCURED ON CONTACT SELECT");
	    	}
	    	return c;
	    }
	

	@Override
	public void onListItemClick(ListView l, View v, int position, long id){

		Log.i(WorkLoad.TAG, "item has been clicked");
		CheckBox selectedContact = (CheckBox) v.findViewById(R.id.ContactSelectorCheckbox);
		if(selectedContact.isChecked())
		{
			selectedContact.setChecked(false);
			Contact contactToAdd = (Contact)(getListAdapter()).getItem(position);
			selectedContactsList.remove(contactToAdd);
			Log.i(WorkLoad.TAG, "list details are "+selectedContactsList.toString());
		}
		else
		{
			selectedContact.setChecked(true);
			Contact contactToAdd = (Contact)(getListAdapter()).getItem(position);
			selectedContactsList.add(contactToAdd);
			Log.i(WorkLoad.TAG, "list details are "+selectedContactsList.toString());
		}
	}
	
	private class ContactsArrayAdapter extends ArrayAdapter<Contact> {
		List<Contact> items;
		
		public ContactsArrayAdapter(Activity a, List<Contact> contacts) {
			super(a, 0, contacts);
			this.items = contacts;
		}
		
		public void updateContactsList(){
			List<String> updatedContactsList = WorkLoad.getInstance().getContacts(Integer.toString(contactListProjectID));
			items.clear();
			List<Contact> allContacts = new ArrayList<Contact>();
	 		for(String cid : updatedContactsList)
			{
	 			Contact tmpContact = createContactDetailsByID(cid);
	 			allContacts.add(tmpContact);
			}
	 		items.addAll(allContacts);
			this.notifyDataSetChanged();
		}
		

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			if (convertView == null) {
				convertView = getActivity().getLayoutInflater().inflate(
						R.layout.list_layout_contact_chooser, null);
			}
			contact = items.get(position);
			Log.i(WorkLoad.TAG, "adding item at pos " + position);
			Log.i(WorkLoad.TAG, "item at pos "+position+ " is contact "+contact.getContactID());

			TextView titleTextView = (TextView) convertView
					.findViewById(R.id.contact_name_chooser_text);
			titleTextView.setText(contact.getName());
			setHasOptionsMenu(true);
			return convertView;

		}
		
		
		
	
		
	}
	
		
}

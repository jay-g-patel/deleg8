package aston.cs3040.deleg8.contacts;

import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import aston.cs3040.activity.SingleFragmentActivity;
import aston.cs3040.deleg8.ProjectActivity;
import aston.cs3040.deleg8.R;
import aston.cs3040.deleg8.R.layout;
import aston.cs3040.deleg8.R.menu;
import aston.cs3040.model.WorkLoad;

public class AppContactsListActivity extends SingleFragmentActivity
{
	String projectID;
	private static final int REQUEST_CONTACT = 8;

	@Override
	protected Fragment createFragment()
	{
		AppContactsListFragment f = new AppContactsListFragment();
		try{
		if(!getIntent().getStringExtra("PROJECTID").isEmpty())
		{
			projectID = getIntent().getStringExtra("PROJECTID");
			Log.i(WorkLoad.TAG, "project id in the app contacts activity list is "+projectID);
		}
		}
		catch(NullPointerException e)
		{
			Log.i(WorkLoad.TAG, "exception message "+e.getMessage());
		}
		return f;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.contact, menu);
		
		return super.onCreateOptionsMenu(menu);
	}
	
	@Override 
	public boolean onOptionsItemSelected(MenuItem item){
		//take the correct action for each action button clicked
		switch(item.getItemId()){
		case R.id.action_addContact:
			AddContactFromPhone();
			return true;
		}
		return false;
	}
	


	private void AddContactFromPhone()
	{
		Intent i = new Intent(Intent.ACTION_PICK,ContactsContract.Contacts.CONTENT_URI);
		
		startActivityForResult(i, REQUEST_CONTACT);
	}
	
	/**
	 * Receiving activity result method will be called after closing the camera
	 * */
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
	    if(requestCode == REQUEST_CONTACT){
	    	Cursor contactCursor = null;
	    	Cursor emailCursor = null;
	    	String id = "";
	    	try{
	    		Uri result = data.getData();
	    		id = result.getLastPathSegment();
	    		Log.i(WorkLoad.TAG, "contact id is "+ id);
	    		
	    		Log.i(WorkLoad.TAG, "id is - "+id);
	    		contactCursor = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null , ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "=?", new String[] { id }, null);
	    		int nameIDX = contactCursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME);
	    		emailCursor = getContentResolver().query(ContactsContract.CommonDataKinds.Email.CONTENT_URI, null, ContactsContract.CommonDataKinds.Email.CONTACT_ID+"=?", new String[]{id},  null);
	    		int emailIDX = emailCursor.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA);
	    		int numberIDX = contactCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DATA);
	    		String email = "";
	    		if(contactCursor.moveToFirst())
	    		{
	    			Log.i(WorkLoad.TAG, "IN THE CONTACTS SEARCH LOOPY");
	    			String name = contactCursor.getString(nameIDX);
	    			String number = contactCursor.getString(numberIDX);
	    			try{
	    			emailCursor = getContentResolver().query(ContactsContract.CommonDataKinds.Email.CONTENT_URI, null, ContactsContract.CommonDataKinds.Email.CONTACT_ID+"=?", new String[]{id},  null);
	    			
	    			if(emailCursor.moveToFirst())
	    			{
	    				email = emailCursor.getString(emailIDX);
	    			}
	    			}
	    			catch(Exception e)
	    			{
	    				Log.i(WorkLoad.TAG, "Error with Email");
	    			}
	    			Log.i(WorkLoad.TAG, "contact name = "+name);
	    			Log.i(WorkLoad.TAG, "contact number = "+number);
	    			Log.i(WorkLoad.TAG, "contact email = "+email);
	    			Intent i = new Intent(this, ContactActivity.class);
	    			i.putExtra("CONTACT_ID", id);
	    			i.putExtra("CONTACT_NAME", name);
	    			i.putExtra("CONTACT_NUMBER", number);
	    			i.putExtra("CONTACT_EMAIL", email);
	    			i.putExtra("SELECTED_FROM_PHONE_LIST", true);
	    			i.putExtra("PROJECTID", projectID);
	    			Log.i(WorkLoad.TAG, "project id before contact intent is "+projectID);
	    			i.putExtra("importContact", true);
	    			startActivity(i); 

	    		}
	    	}
	    	catch(Exception e)
	    	{
	    		Log.i(WorkLoad.TAG, "ERROR HAS OCCURED ON CONTACT SELECT");
	    	}
	    }
	}
	
}

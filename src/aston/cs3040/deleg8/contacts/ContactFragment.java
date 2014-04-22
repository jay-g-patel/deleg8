package aston.cs3040.deleg8.contacts;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.content.ContentProviderOperation;
import android.content.ContentProviderOperation.Builder;
import android.content.ContentUris;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.ContactsContract.RawContacts;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import aston.cs3040.deleg8.R;
import aston.cs3040.deleg8.ToDoListActivity;
import aston.cs3040.model.Contact;
import aston.cs3040.model.WorkLoad;

public class ContactFragment extends Fragment
{
	private View v;
	EditText contactName;
	EditText contactNumber;
	EditText contactEmailAddress;
	Button btnSave;
	Button btnEdit;
	String cID = "";
	String proID = "";
	boolean emailUpdate = false;
	boolean numberUpdate = false;

	public void onCreate(Bundle savedInstanceState)
	{		
		
		super.onCreate(savedInstanceState);
		if(!getActivity().getIntent().getStringExtra("CONTACT_ID").isEmpty())
		{
			cID = getActivity().getIntent().getStringExtra("CONTACT_ID");
		}
		if(getActivity().getIntent().getStringExtra("PROJECTID").isEmpty())
		{
			proID = getActivity().getIntent().getStringExtra("PROJECTID");
		}
		
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState){
		v = inflater.inflate(R.layout.activity_contact,  parent, false);
		boolean viewContact = getActivity().getIntent().getBooleanExtra("VIEW_CONTACT", false);
		boolean importContact = getActivity().getIntent().getBooleanExtra("importContact", false);
		Log.i(WorkLoad.TAG, "view contact is "+viewContact);
		Log.i(WorkLoad.TAG, "import contact is "+importContact);
		boolean fromPhoneContacts = getActivity().getIntent().getBooleanExtra("SELECTED_FROM_PHONE_LIST", false);
		contactName = (EditText)v.findViewById(R.id.contactName_editText);
		contactNumber = (EditText)v.findViewById(R.id.contactNumber_EditText);
		contactEmailAddress = (EditText)v.findViewById(R.id.contactEmail_EditText);
		btnSave = (Button)v.findViewById(R.id.btn_saveContact);
		btnEdit = (Button)v.findViewById(R.id.btn_EditContact);
		editContactButtonListener();
//		if(viewContact)
//		{
			btnSave.setVisibility(View.GONE);
			contactName.setEnabled(false);
			contactNumber.setEnabled(false);
			contactEmailAddress.setEnabled(false);
//		}
//		if(importContact)
//		{
			
			
//		}
		saveOnClickListener();
		
		
		contactName.setText(getActivity().getIntent().getStringExtra("CONTACT_NAME"));
		contactNumber.setText(getActivity().getIntent().getStringExtra("CONTACT_NUMBER"));
		contactEmailAddress.setText(getActivity().getIntent().getStringExtra("CONTACT_EMAIL"));
		
		
		if(getActivity().getIntent().getStringExtra("CONTACT_EMAIL").equals(""))
		{
		emailUpdate = true;	
		Log.i(WorkLoad.TAG, "email is  - "+getActivity().getIntent().getStringExtra("CONTACT_EMAIL"));
		}
		
		if(TextUtils.isEmpty(contactEmailAddress.getText().toString())) {
			btnSave.setEnabled(false);
		}
		
		if(getActivity().getIntent().getStringExtra("CONTACT_NUMBER").equals(""))
		{
			numberUpdate = true;	
		Log.i(WorkLoad.TAG, "email is  - "+getActivity().getIntent().getStringExtra("CONTACT_EMAIL"));
		}
		
		if(TextUtils.isEmpty(contactNumber.getText().toString())) {
			btnSave.setEnabled(false);
		}
		checkIfSaveable();
		contactEmailAddress.addTextChangedListener(new TextWatcher() {
		      @Override
		      public void afterTextChanged(Editable arg0) {
		         enableSaveIfReady();
		      }

		      @Override
		      public void beforeTextChanged(CharSequence s, int start, int count, int after) {
		      }

		      @Override
		      public void onTextChanged(CharSequence s, int start, int before, int count) {
		      }
		    });
		
		
		return v;
	}
	
	private void checkIfSaveable()
	{
		if(!TextUtils.isEmpty(contactEmailAddress.getText().toString())
				&& !TextUtils.isEmpty(contactEmailAddress.getText().toString()) &&
				!TextUtils.isEmpty(contactEmailAddress.getText().toString()))
		{
			btnSave.setVisibility(View.VISIBLE);
		}
		
	}

	public void enableAddEmailIfReady()
	{
	      btnEdit.setEnabled(true);
	}
	
	public void editContactButtonListener()
	{
		//Log.i(WorkLoad.TAG, "Listener hit ");	
		btnEdit.setVisibility(View.VISIBLE);
		btnEdit.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View view)
			{
				Intent i = new Intent(Intent.ACTION_EDIT); //ContactsContract.Intents.Insert.ACTION
				long l = Long.valueOf(cID);
				Uri contactURI = ContentUris.withAppendedId(ContactsContract.Contacts.CONTENT_URI, l);
				i.setData(contactURI);
				
				//startActivity(i);
				i.putExtra("finishActivityOnSaveCompleted", true);
				startActivityForResult(i, 2000);
			}
		}
				);
			
	}
	
	public void enableSaveIfReady()
	{
		boolean isReady =contactEmailAddress.getText().toString().length()>3;
		boolean isValidEamil = isEmailValid(contactEmailAddress.getText().toString());
		
	    if (isReady && isValidEamil) {
	      btnSave.setEnabled(true);
	   } else {
	      btnSave.setEnabled(false);
	    }
	}
	
	public boolean isEmailValid(String email)
    {
         String regExpn =
             "^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]{1}|[\\w-]{2,}))@"
                 +"((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                   +"[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
                   +"([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                   +"[0-9]{1,2}|25[0-5]|2[0-4][0-9])){1}|"
                   +"([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$";

     CharSequence inputStr = email;

     Pattern pattern = Pattern.compile(regExpn,Pattern.CASE_INSENSITIVE);
     Matcher matcher = pattern.matcher(inputStr);

     if(matcher.matches())
        return true;
     else
        return false;
}
	
	private void saveOnClickListener()
	{
		btnSave.setOnClickListener(new View.OnClickListener()
		{
			
			@Override
			public void onClick(View v)
			{
				String cName = contactName.getText().toString();
				String cNumber = contactNumber.getText().toString();
				String cEmail = contactEmailAddress.getText().toString();
				String cID = getActivity().getIntent().getStringExtra("CONTACT_ID");
				String projectID = getActivity().getIntent().getStringExtra("PROJECTID");
				
				Log.i(WorkLoad.TAG, "Project id before adding a new contact is "+projectID);
				
				
				Contact newContact = new Contact(cID, cName, cNumber, cEmail, projectID);
				Log.i(WorkLoad.TAG, "Created project ID is "+newContact.getProjectID());
				
				WorkLoad.getInstance().addContactToDB(newContact);
				Intent i = new Intent(getActivity(), AppContactsListActivity.class);
				i.putExtra("PROJECTID",projectID);
				startActivity(i);
				
				
			}
		});
	}
	
	/**
	 * Receiving activity result method will be called after closing the camera
	 * */
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		Log.i(WorkLoad.TAG, "Contact fragment has got result");
		Cursor contactCursor = null;
    	Cursor emailCursor = null;
    	String id = "";
    	try{
    		id = cID;
    		Log.i(WorkLoad.TAG, "contact id is "+ id);
    		
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
    			Log.i(WorkLoad.TAG, "contact name = "+name);
    			Log.i(WorkLoad.TAG, "contact number = "+number);
    			Log.i(WorkLoad.TAG, "contact email = "+email);
    			contactName.setText(name);
    			contactNumber.setText(number);
    			contactEmailAddress.setText(email);
    			checkIfSaveable();
//    			Intent i = new Intent(getActivity(), ContactActivity.class);
//    			i.putExtra("CONTACT_ID", id);
//    			i.putExtra("CONTACT_NAME", name);
//    			i.putExtra("CONTACT_NUMBER", number);
//    			i.putExtra("CONTACT_EMAIL", email);
//    			i.putExtra("SELECTED_FROM_PHONE_LIST", true);
//    			startActivity(i); 

    		}
    	}
    	catch(Exception e)
    	{
    		Log.i(WorkLoad.TAG, "ERROR HAS OCCURED ON CONTACT SELECT");
    	}
	}
	
}

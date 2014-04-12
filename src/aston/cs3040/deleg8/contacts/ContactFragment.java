package aston.cs3040.deleg8.contacts;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.content.Intent;
import android.os.Bundle;
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
import aston.cs3040.model.Contact;
import aston.cs3040.model.WorkLoad;

public class ContactFragment extends Fragment
{
	private View v;
	EditText contactName;
	EditText contactNumber;
	EditText contactEmailAddress;
	Button btnSave;

	public void onCreate(Bundle savedInstanceState)
	{		
		
		super.onCreate(savedInstanceState);
		
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState){
		v = inflater.inflate(R.layout.activity_contact,  parent, false);
		boolean fromPhoneContacts = getActivity().getIntent().getBooleanExtra("SELECTED_FROM_PHONE_LIST", false);
		contactName = (EditText)v.findViewById(R.id.contactName_editText);
		contactNumber = (EditText)v.findViewById(R.id.contactNumber_EditText);
		contactEmailAddress = (EditText)v.findViewById(R.id.contactEmail_EditText);
		btnSave = (Button)v.findViewById(R.id.btn_saveContact);
		saveOnClickListener();
		
		
		contactName.setText(getActivity().getIntent().getStringExtra("CONTACT_NAME"));
		contactNumber.setText(getActivity().getIntent().getStringExtra("CONTACT_NUMBER"));
		
		if(TextUtils.isEmpty(contactEmailAddress.getText().toString())) {
			btnSave.setEnabled(false);
		}
		
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
				
				Log.i(WorkLoad.TAG, "contact info is "+cName+ " "+cNumber+" "+cEmail);
				Contact newContact = new Contact(cID, cName, cNumber, cEmail);
				WorkLoad.getInstance().addContactToDB(newContact);
				
			}
		});
	}
	
}

package aston.cs3040.deleg8.contacts;

import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Toast;
import aston.cs3040.model.WorkLoad;

public class CustomOnItemSelectedListener implements OnItemSelectedListener
{

	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int pos,
			long arg3)
	{
		Toast.makeText(parent.getContext(), 
				"OnItemSelectedListener : " + parent.getItemAtPosition(pos).toString(),
				Toast.LENGTH_SHORT).show();
		

	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0)
	{
		// TODO Auto-generated method stub

	}

}

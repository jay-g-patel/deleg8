package aston.cs3040.deleg8.contacts;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v4.app.Fragment;
import android.support.v4.app.NavUtils;
import aston.cs3040.activity.SingleFragmentActivity;
import aston.cs3040.deleg8.R;
import aston.cs3040.deleg8.R.layout;
import aston.cs3040.deleg8.R.menu;

public class ContactActivity extends SingleFragmentActivity
{

	@Override
	protected Fragment createFragment()
	{
		ContactFragment f = new ContactFragment();
		return f;
	}

	/**
	 * Set up the {@link android.app.ActionBar}.
	 */
//	private void setupActionBar()
//	{
//
//		getActionBar().setDisplayHomeAsUpEnabled(true);
//
//	}
//
//	@Override
//	public boolean onCreateOptionsMenu(Menu menu)
//	{
//		// Inflate the menu; this adds items to the action bar if it is present.
//		getMenuInflater().inflate(R.menu.contact, menu);
//		return true;
//	}
//
//	@Override
//	public boolean onOptionsItemSelected(MenuItem item)
//	{
//		switch (item.getItemId())
//		{
//		case android.R.id.home:
//			// This ID represents the Home or Up button. In the case of this
//			// activity, the Up button is shown. Use NavUtils to allow users
//			// to navigate up one level in the application structure. For
//			// more details, see the Navigation pattern on Android Design:
//			//
//			// http://developer.android.com/design/patterns/navigation.html#up-vs-back
//			//
//			NavUtils.navigateUpFromSameTask(this);
//			return true;
//		}
//		return super.onOptionsItemSelected(item);
//	}
//
//	

}

package aston.cs3040.deleg8.contacts;

import android.os.Bundle;
import android.app.Activity;
import android.support.v4.app.Fragment;
import android.view.Menu;
import aston.cs3040.activity.SingleFragmentActivity;
import aston.cs3040.deleg8.R;
import aston.cs3040.deleg8.R.layout;
import aston.cs3040.deleg8.R.menu;

public class AppContactsListActivity extends SingleFragmentActivity
{

	@Override
	protected Fragment createFragment()
	{
		AppContactsListFragment f = new AppContactsListFragment();
		return f;
	}

}

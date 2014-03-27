package aston.cs3040.deleg8.meeting;


import android.support.v4.app.Fragment;
import aston.cs3040.activity.SingleFragmentActivity;

public class MeetingActivity extends SingleFragmentActivity
{

	@Override
	protected Fragment createFragment()
	{
		Fragment f = new MeetingFragment();
		// TODO Auto-generated method stub
		return f;
	}

	
}

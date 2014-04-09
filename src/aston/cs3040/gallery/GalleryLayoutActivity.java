package aston.cs3040.gallery;

import android.os.Bundle;
import android.app.Activity;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Menu;
import aston.cs3040.activity.SingleFragmentActivity;
import aston.cs3040.deleg8.R;
import aston.cs3040.deleg8.R.layout;
import aston.cs3040.deleg8.R.menu;
import aston.cs3040.model.WorkLoad;

public class GalleryLayoutActivity extends SingleFragmentActivity
{

	@Override
	protected Fragment createFragment()
	{	
		Log.i(WorkLoad.TAG, "Got to gallery activity");
	Fragment f = new GalleryLayoutFragment();
	// TODO Auto-generated method stub
	return f;
	}

}

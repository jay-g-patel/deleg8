package aston.cs3040.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import aston.cs3040.deleg8.R;

public abstract class SingleFragmentActivity extends FragmentActivity{

	protected abstract Fragment createFragment();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.empty_frame);
		
		FragmentManager fm = this.getSupportFragmentManager();
		Fragment fragment = fm.findFragmentById(R.id.fragmentContainer);
		if (fragment == null) {
			fragment = createFragment();
			fragment.setArguments(getIntent().getExtras());
			fm.beginTransaction().add(R.id.fragmentContainer, fragment).commit();
		}

	}
	
}

package aston.cs3040.deleg8;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import aston.cs3040.activity.SingleFragmentActivity;
import aston.cs3040.model.ToDoItem;

public class ToDoItemActivity extends SingleFragmentActivity {

	private ToDoItem tdi;
	
	
	@Override
	protected Fragment createFragment() {
		Fragment f = null;
		tdi = (ToDoItem) getIntent().getExtras().get("TODOITEM");
		boolean isNew = getIntent().getBooleanExtra("ISNEW", false);
		boolean editMode = getIntent().getBooleanExtra("MODE", false);
		
		if(isNew || editMode)
		{
			//is a new to do item
			f = new EditableToDoItemFragment();
		}
		else
		{
			f = new ToDoItemFragment();
		}
		return f;
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.project_actions, menu);
		
		return super.onCreateOptionsMenu(menu);
	}
	
	@Override 
	public boolean onOptionsItemSelected(MenuItem item){
		//take the correct action for each action button clicked
		switch(item.getItemId()){
		case R.id.action_editProject:
			EditProject();
			return true;
		}
		return false;
	}
	
	private void EditProject(){
		Intent i = new Intent(this,ToDoItemActivity.class);
		i.putExtra("MODE", true);
		i.putExtra("TODOITEM", tdi);
		this.finish();
		startActivity(i);
		
	}
	
	

}

package aston.cs3040.deleg8;

import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import aston.cs3040.model.Project;
import aston.cs3040.model.WorkLoad;

public class ProjectListFragment extends ListFragment {

	private WorkLoad workload;
	private Project projectToDelete;
	private ProjectArrayAdapter adapter;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (workload == null) {
			workload = WorkLoad.init(getActivity());
		} 
		List <Project> allProj = workload.getProjects();
		
//		this.setListAdapter(new ArrayAdapter<Project>(
//				getActivity(),
//				android.R.layout.simple_list_item_1,
//				allProj));
				
		adapter = new ProjectArrayAdapter(getActivity(), allProj);
		this.setListAdapter(adapter);
		
	}
	
	
		
		
	
	@Override
	public void onListItemClick(ListView l, View v, int position, long id){
		Project p = (Project)(getListAdapter()).getItem(position);
		Log.i(WorkLoad.TAG, p.getName()+" was clicked");
		//Start the Project activity
		Intent i = new Intent(getActivity(), ProjectActivity.class);
		i.putExtra("pos", position);
		i.putExtra("PROJECTID",p.getID());
		startActivity(i);
		Log.i(WorkLoad.TAG, "Project id = "+p.getID()+" and name = "+p.getName());
	}

	@Override
	public void onResume()
	{
		super.onResume();
		adapter.updateProjectList();
	}
	
	

	
	private class ProjectArrayAdapter extends ArrayAdapter<Project> {
		List<Project> items;
		
		public ProjectArrayAdapter(Activity a, List<Project> projects) {
			super(a, 0, projects);
			this.items = projects;
		}
		
		public void updateProjectList(){
			List<Project> updatedProjectsList = workload.getProjects();
			items.clear();
			items.addAll(updatedProjectsList);
			this.notifyDataSetChanged();
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			if (convertView == null) {
				convertView = getActivity().getLayoutInflater().inflate(
						R.layout.list_layout_project, null);
			}
			Project project = items.get(position);
			Log.i(WorkLoad.TAG, "adding item at pos " + position);

			TextView titleTextView = (TextView) convertView
					.findViewById(R.id.project_titleTextView);
			titleTextView.setText(project.getName());
			
			
			getListView().setOnItemLongClickListener(new OnItemLongClickListener() {
				@Override
				public boolean onItemLongClick(AdapterView<?>list, View v,int pos, long id){
					projectToDelete = (Project)list.getItemAtPosition(pos);
					Log.i(WorkLoad.TAG,"I'm gonna show the dialogue :)");
					showDeleteConfirmAlert();
					Log.i(WorkLoad.TAG,"orrr maybe im not");
					return true;
				}

				private void showDeleteConfirmAlert() {
					// TODO Auto-generated method stub
					Log.i(WorkLoad.TAG,"in the dialogue Method");
					AlertDialog.Builder confirmDelete = new AlertDialog.Builder(getActivity());
					confirmDelete.setMessage("Are you sure you want to delete " + projectToDelete.getName()+"?");
					confirmDelete.setCancelable(true);
					confirmDelete.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
						
						@Override
						public void onClick(DialogInterface dialog, int which) {
							// This is what you need to do when you press okay
							WorkLoad.getInstance().deleteProjectByID(projectToDelete);
							Intent i = new Intent(getActivity(), ProjectListActivity.class);
							getActivity().finish();
							startActivity(i);
						}
					});
					
					confirmDelete.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
						
						@Override
						public void onClick(DialogInterface dialog, int which) {
							// This is what to do when user hits cancel
							
							
						}
					});
					
					confirmDelete.show();
				}
				});
			return convertView;

		}
		
		
	}
	

}

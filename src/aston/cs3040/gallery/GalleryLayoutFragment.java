package aston.cs3040.gallery;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import aston.cs3040.deleg8.R;
import aston.cs3040.model.WorkLoad;

public class GalleryLayoutFragment extends Fragment
{
	View v;

	public void onCreate(Bundle savedInstanceState)
	{		
		Log.i(WorkLoad.TAG, "Got to gallery fragment");
		super.onCreate(savedInstanceState);
		
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState){
		v = inflater.inflate(R.layout.gallery_grid_view,  parent, false);
		GridView gridView = (GridView) v.findViewById(R.id.grid_view);
		 
        // Instance of ImageAdapter Class
		Log.i(WorkLoad.TAG, "setting image adapter now");
        gridView.setAdapter(new ImageAdapter(getActivity()));
		
		return v;
		
		
	}
	
}

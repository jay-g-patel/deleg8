	package aston.cs3040.deleg8.gallery;
	
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;

import java.util.ArrayList;
	
import android.app.Activity;
import android.util.Log;
import android.util.TypedValue;
import android.widget.Button;
import android.widget.GridView;
import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
	
import aston.cs3040.deleg8.R;
import aston.cs3040.deleg8.R.id;
import aston.cs3040.deleg8.R.layout;
import aston.cs3040.model.WorkLoad;
	
	public class GridViewActivity extends Activity {
		 
	    private Utils utils;
	    private ArrayList<String> imagePaths = new ArrayList<String>();
	    private GridViewImageAdapter adapter;
	    private GridView gridView;
	    private int columnWidth;
	 
	    @Override
	    protected void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.activity_grid_view);
	 
	        gridView = (GridView) findViewById(R.id.grid_view);
	 
	        utils = new Utils(this);
	 
	        // Initilizing Grid View
	        InitilizeGridLayout();
	 
	        int projectID = getIntent().getIntExtra("PID", 0);
	        // loading all image paths from SD card
	        imagePaths = WorkLoad.getInstance().getAllProjectImages(projectID);
	 
	        // Gridview adapter
	        adapter = new GridViewImageAdapter(GridViewActivity.this, imagePaths,
	                columnWidth);
	 
	        // setting grid view adapter
	        gridView.setAdapter(adapter);
	    }
	 
	    private void InitilizeGridLayout() {
	        Resources r = getResources();
	        float padding = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
	                AppConstant.GRID_PADDING, r.getDisplayMetrics());
	 
	        columnWidth = (int) ((utils.getScreenWidth() - ((AppConstant.NUM_OF_COLUMNS + 1) * padding)) / AppConstant.NUM_OF_COLUMNS);
	 
	        gridView.setNumColumns(AppConstant.NUM_OF_COLUMNS);
	        gridView.setColumnWidth(columnWidth);
	        gridView.setStretchMode(GridView.NO_STRETCH);
	        gridView.setPadding((int) padding, (int) padding, (int) padding,
	                (int) padding);
	        gridView.setHorizontalSpacing((int) padding);
	        gridView.setVerticalSpacing((int) padding);
	    }
	    
	    @Override
		public boolean onCreateOptionsMenu(Menu menu)
		{
			MenuInflater inflater = getMenuInflater();
			inflater.inflate(R.menu.contact, menu);
			
			return super.onCreateOptionsMenu(menu);
		}
		
		@Override 
		public boolean onOptionsItemSelected(MenuItem item){
			//take the correct action for each action button clicked
			switch(item.getItemId()){
			case R.id.action_addContact:
				AddPhotoFromPhoneGallery();
				return true;
			}
			return false;
		}
		
		public void AddPhotoFromPhoneGallery()
		{
			
				
					//Intent i = new Intent(getActivity(), GridViewActivity.class);
					//i.putExtra("TODOITEMID", toDoItem.getId());
					//i.putExtra("PROJECTID", getActivity().getIntent().getIntExtra("PID", 0));
					Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
				    photoPickerIntent.setType("image/*");
				    startActivityForResult(photoPickerIntent, 1);
		
		}
		
		@Override
		public void onActivityResult(int requestCode, int resultCode, Intent data) {
			 Uri selectedImage = data.getData();
             String[] filePathColumn = { MediaStore.Images.Media.DATA };
     
             Cursor cursor = getContentResolver().query(selectedImage,
                     filePathColumn, null, null, null);
             cursor.moveToFirst();
     
             int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
             String picturePath = cursor.getString(columnIndex);
             cursor.close();
             Log.i(WorkLoad.TAG,"Image path is "+picturePath);
             
             WorkLoad.getInstance().addImageToProject(picturePath,getIntent().getIntExtra("PID", 0));
             
             Intent i = new Intent(this, GridViewActivity.class);
				//i.putExtra("TODOITEMID", toDoItem.getId());
				i.putExtra("PID", getIntent().getIntExtra("PID", 0));
				startActivity(i);
		
		}
	 
	}

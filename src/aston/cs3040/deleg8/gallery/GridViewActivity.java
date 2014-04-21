	package aston.cs3040.deleg8.gallery;
	
import android.os.Bundle;
import java.util.ArrayList;
	
import android.app.Activity;
import android.util.TypedValue;
import android.widget.GridView;
import android.app.Activity;
import android.content.res.Resources;
import android.view.Menu;
	
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
	 
	}

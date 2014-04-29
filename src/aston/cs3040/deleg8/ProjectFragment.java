package aston.cs3040.deleg8;


import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import aston.cs3040.deleg8.contacts.AppContactsListActivity;
import aston.cs3040.deleg8.contacts.ContactActivity;
import aston.cs3040.deleg8.gallery.GridViewActivity;
import aston.cs3040.deleg8.gallery.GridViewImageAdapter;
import aston.cs3040.deleg8.meeting.MeetingActivity;
import aston.cs3040.deleg8.meeting.MeetingsListActivity;
import aston.cs3040.model.Project;
import aston.cs3040.model.WorkLoad;

public class ProjectFragment extends Fragment{

	private Project project;
	private static int pos;
	private Button viewToDoListBtn;
	private View v;
	private Button viewCalendar;
	private ImageView imgPreview;
	private Button btnCapturePicture;
	private Button goToGallery;
	private Button btnViewAppContacts;
	private Button btnViewAppGallery;
	
	
	//Activity Request Codes
	private static final int CAMERA_CAPTURE_IMAGE_REQUEST_CODE = 100;
//    private static final int CAMERA_CAPTURE_VIDEO_REQUEST_CODE = 200;
    public static final int MEDIA_TYPE_IMAGE = 1;
//    public static final int MEDIA_TYPE_VIDEO = 2;
	
    //Directoy name used to store images and Video - in the SD card Pictures/Deleg8
    private static final String IMAGE_DIRECTORY_NAME = "Deleg8";
    
   //file url to store image/video
    private Uri fileUri;
    
    
	
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		if(project == null){
			project = WorkLoad.getInstance().getProject(pos);
			
			
		}
		
	}
	
	public static ProjectFragment newInstance(int position)
	{
		pos = position;
		Bundle args = new Bundle();
		args.putInt("PROJECT_POSITION", pos);
		ProjectFragment projFrag = new ProjectFragment();
		projFrag.setArguments(args);
		return projFrag;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState){
		v = inflater.inflate(R.layout.activity_project,  parent, false);
		TextView projectTitleField = (TextView)v.findViewById(R.id.project_titleTextView1);
		//Button btn = (Button)v.findViewById(R.id.go_to_todolist);
		projectTitleField.setText(project.getName());	
		//Log.i(WorkLoad.TAG, "getting project name "+ project.getName());
		viewToDoListButtonListener();
		viewCalendarButtonListener();
		viewAppContactsButtonListener();
		//editProjectButtonListener();
		goToAppGalleryButtonListener();
		imgPreview = (ImageView) v.findViewById(R.id.imgPreview);
//        videoPreview = (VideoView) findViewById(R.id.videoPreview);
        btnCapturePicture = (Button) v.findViewById(R.id.btnCapturePicture);
//        btnRecordVideo = (Button) findViewById(R.id.btnRecordVideo);
		
        /**
         * Capture image button click event
         * */
        btnCapturePicture.setOnClickListener(new View.OnClickListener() {
 
            @Override
            public void onClick(View v) {
                // capture picture
                captureImage();
            }
        });
 
        /**
         * Record video button click event
         */
//        btnRecordVideo.setOnClickListener(new View.OnClickListener() {
// 
//            @Override
//            public void onClick(View v) {
//                // record video
//                recordVideo();
//            }
//        });
        
		return v;
		
	}
	
	

	private void goToAppGalleryButtonListener()
	{
		goToGallery = (Button)v.findViewById(R.id.btnViewAppImages);
		goToGallery.setVisibility(View.VISIBLE);
		goToGallery.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View view)
			{
				Log.i(WorkLoad.TAG, "app Gallery intent");
				Intent i = new Intent(getActivity(), GridViewActivity.class);
				//i.putExtra("TODOITEMID", toDoItem.getId());
				i.putExtra("PID", project.getID());
				Log.i(WorkLoad.TAG, "project id in gallery intent is "+project.getID());
				startActivity(i);
			}
		}
				);
	}


	

	/*
	 * Capturing Camera Image will lauch camera app requrest image capture
	 */
	private void captureImage() {
	    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
	 
	    fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE);
	 
	    intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
	 
	    // start the image capture Intent
	    startActivityForResult(intent, CAMERA_CAPTURE_IMAGE_REQUEST_CODE);
	}
	
	public void viewCalendarButtonListener()
	{
		//Log.i(WorkLoad.TAG, "Listener hit ");	
		
		viewCalendar = (Button)v.findViewById(R.id.viewMeetings);
		viewCalendar.setVisibility(View.VISIBLE);
		viewCalendar.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View view)
			{
				Intent i = new Intent(getActivity(), MeetingsListActivity.class);
				//i.putExtra("TODOITEMID", toDoItem.getId());
				i.putExtra("PROJECTID", project.getID());
			
				
				Log.i(WorkLoad.TAG, "projectID From project to meeting is - "+project.getID());
				startActivity(i);
			}
		}
				);
			
	}
	
	public void viewAppContactsButtonListener()
	{
		//Log.i(WorkLoad.TAG, "Listener hit ");	
		
		btnViewAppContacts = (Button)v.findViewById(R.id.btnViewAppContacts);
		btnViewAppContacts.setVisibility(View.VISIBLE);
		btnViewAppContacts.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View view)
			{
				Intent i = new Intent(getActivity(), AppContactsListActivity.class);
				i.putExtra("PROJECTID", Integer.toString(project.getID()));
				startActivity(i);
			}
		}
				);
			
	}
	
	public void viewToDoListButtonListener()
	{
		//Log.i(WorkLoad.TAG, "Listener hit ");	
		
		viewToDoListBtn = (Button)v.findViewById(R.id.go_to_todolist);
		viewToDoListBtn.setVisibility(View.VISIBLE);
		viewToDoListBtn.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View view)
			{
				int pid = project.getID();
				Intent i = new Intent(getActivity(), ToDoListActivity.class);
				i.putExtra("Position", pos);
				i.putExtra("PID", pid);
				Log.i(WorkLoad.TAG,"Position = "+pos+" PID = "+pid);
				
					startActivity(i);
			}
		}
				);
			
	}
	
	
	/*
     * Display image from a path to ImageView
     */
    private void previewCapturedImage() {
        try {
            // hide video preview
//            videoPreview.setVisibility(View.GONE);
 
        	Log.i(WorkLoad.TAG, fileUri.getPath());
            imgPreview.setVisibility(View.VISIBLE);
 
            // bimatp factory
            BitmapFactory.Options options = new BitmapFactory.Options();
 
            // downsizing image as it throws OutOfMemory Exception for larger
            // images
            options.inSampleSize = 8;
 
            final Bitmap bitmap = BitmapFactory.decodeFile(fileUri.getPath(),
                    options);
 
            imgPreview.setImageBitmap(bitmap);
            imgPreview.setOnClickListener(new View.OnClickListener()
			{
				
				@Override
				public void onClick(View v)
				{
					
					Log.i(WorkLoad.TAG, "file URI is " +fileUri.getPath());
					
				}
			});
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        
    }
	
	/**
	 * Receiving activity result method will be called after closing the camera
	 * */
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
	    // if the result is capturing Image
		Log.i(WorkLoad.TAG, "On  Activity Result");
	    if (requestCode == CAMERA_CAPTURE_IMAGE_REQUEST_CODE) {
	        if (resultCode == Activity.RESULT_OK) {
	            // successfully captured the image
	            // display it in image view
	            previewCapturedImage();
	        } else if (resultCode == Activity.RESULT_CANCELED) {
	            // user cancelled Image capture
	            Toast.makeText(getActivity(),
	                    "User cancelled image capture", Toast.LENGTH_SHORT)
	                    .show();
	        }
	        
	        	else {
	            // failed to capture image
	            Toast.makeText(getActivity(),
	                    "Sorry! Failed to capture image", Toast.LENGTH_SHORT)
	                    .show();
	        }
	    }
	    else
        {
        	
                Uri selectedImage = data.getData();
                String[] filePathColumn = { MediaStore.Images.Media.DATA };
        
                Cursor cursor = getActivity().getContentResolver().query(selectedImage,
                        filePathColumn, null, null, null);
                cursor.moveToFirst();
        
                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                String picturePath = cursor.getString(columnIndex);
                cursor.close();
                Log.i(WorkLoad.TAG,"Image path is "+picturePath);
                
                WorkLoad.getInstance().addImageToProject(picturePath,project.getID());
                
                Intent i = new Intent(getActivity(), GridViewActivity.class);
				//i.putExtra("TODOITEMID", toDoItem.getId());
				i.putExtra("PID", project.getID());
				startActivity(i);
        }
	    
	    
	}
	
	
	
	public Uri getOutputMediaFileUri(int type)
	{
		return Uri.fromFile(getOutputMediaFile(type));
		
	}
	
	private static File getOutputMediaFile(int type) {

		// External sdcard location
		File mediaStorageDir = new File(
				Environment
						.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
				IMAGE_DIRECTORY_NAME);
		
		Log.i(WorkLoad.TAG, "default Directory is "+ Environment.DIRECTORY_PICTURES);

		// Create the storage directory if it does not exist
		if (!mediaStorageDir.exists()) {
			if (!mediaStorageDir.mkdirs()) {
				Log.i(IMAGE_DIRECTORY_NAME, "Oops! Failed create "
						+ IMAGE_DIRECTORY_NAME + " directory");
				Log.i(WorkLoad.TAG, "directory did not get created "+ IMAGE_DIRECTORY_NAME);
				return null;
			}
		}

		// Create a media file name
		String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss",
				Locale.getDefault()).format(new Date());
		File mediaFile;
		if (type == MEDIA_TYPE_IMAGE) {
			mediaFile = new File(mediaStorageDir.getPath() + File.separator
					+ "IMG_" + timeStamp + ".jpg");
			
		} 
//		else if (type == MEDIA_TYPE_VIDEO) {
//			mediaFile = new File(mediaStorageDir.getPath() + File.separator
//					+ "VID_" + timeStamp + ".mp4");
//		} 
		else {
			return null;
		}

		
		return mediaFile;
	}
	
//	public void editProjectButtonListener()
//	{
//		//Log.i(WorkLoad.TAG, "Listener hit ");	
//		
//		editProjectBtn = (Button)v.findViewById(R.id.edit_project);
//		editProjectBtn.setVisibility(View.VISIBLE);
//		editProjectBtn.setOnClickListener(new View.OnClickListener()
//		{
//			@Override
//			public void onClick(View view)
//			{
//				Log.i(WorkLoad.TAG,"Edit Project Button has been Clicked");
//				Intent i = new Intent(getActivity(),ProjectActivity.class);
//				i.putExtra("PID", project.getID());
//				i.putExtra("MODE", true);
//				startActivity(i);
//			}
//		}
//				);
//			
//	}
//	
	
	
	
	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		// save file url in bundle as it will be null on scren orientation
		// changes
		outState.putParcelable("file_uri", fileUri);
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		if(savedInstanceState != null)
		{
		
		// get the file url
		fileUri = savedInstanceState.getParcelable("file_uri");
		}
	}

	
}

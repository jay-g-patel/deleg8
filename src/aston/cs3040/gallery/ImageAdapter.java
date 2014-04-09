package aston.cs3040.gallery;

import java.io.File;

import android.widget.BaseAdapter;
import android.content.Context;
import android.net.Uri;
import android.os.Environment;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import aston.cs3040.deleg8.R;

public class ImageAdapter extends BaseAdapter
{

	private Context mContext;
	
	File f =  new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),"Deleg8");
	File[] mThumbIds = f.listFiles();
	
	
	
    // Keep all Images in array
//    public Integer[] mThumbIds = {
//            R.drawable.ic_action_edit, R.drawable.ic_action_edit,
//            R.drawable.ic_action_new, R.drawable.ic_action_new,
//            R.drawable.ic_launcher, R.drawable.ic_launcher,
//    };
 
    // Constructor
    public ImageAdapter(Context c){
        mContext = c;
    }
 
//    public ImageAdapter(GalleryLayoutFragment galleryLayoutFragment)
//	{
//		// TODO Auto-generated constructor stub
//	}

	@Override
    public int getCount() {
        return mThumbIds.length;
    }
 
    @Override
    public Object getItem(int position) {
        return mThumbIds[position];
    }
 
    @Override
    public long getItemId(int position) {
        return 0;
    }
 
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView = new ImageView(mContext);
        imageView.setImageURI(Uri.fromFile(mThumbIds[position]));
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageView.setLayoutParams(new GridView.LayoutParams(70, 70));
        return imageView;
    }
	
}

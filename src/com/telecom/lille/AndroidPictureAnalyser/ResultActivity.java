package com.telecom.lille.AndroidPictureAnalyser;

import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.LoaderCallbackInterface;
import org.opencv.android.OpenCVLoader;

import android.app.ListActivity;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.example.tpandroid1.R;

public class ResultActivity extends ListActivity {

		final static String tag = ResultActivity.class.getName();
	
		Analyser analyser;
		Uri mainImage;
		private BaseLoaderCallback mLoaderCallback = new BaseLoaderCallback(this) {
	        @Override
	        public void onManagerConnected(int status) {
	            switch (status) {
	                case LoaderCallbackInterface.SUCCESS:
	                {
	                    Log.i(tag, "OpenCV loaded successfully");
	                    System.loadLibrary("native_activity");
	                } break;
	                default:
	                {
	                    super.onManagerConnected(status);
	                } break;
	            }
	        }
	    };
	    
	    public void CvNativeActivity() {
	        Log.i(tag, "Instantiated new " + this.getClass());
	    }

	    @Override
	    public void onResume()
	    {
	        super.onResume();
	        OpenCVLoader.initAsync(OpenCVLoader.OPENCV_VERSION_2_4_3, this, mLoaderCallback);
	    }
		
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		
		  String[] values = new String[] {"surf" , "sunset", "tiger"};
		  ListAdaptater listAdaptater = new ListAdaptater(this, values);
		  setListAdapter(listAdaptater);
		  mainImage = (Uri) getIntent().getExtras().get("chooseImage");
		  analyser = new Analyser(listAdaptater, this, mainImage);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.result, menu);
		return true;
	}
	
	 @Override
	 protected void onListItemClick(ListView l, View v, int position, long id) {
	        Toast.makeText(this, "Position : " + position, Toast.LENGTH_LONG).show();
	 }

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	
}

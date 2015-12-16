package com.telecom.lille.AndroidPictureAnalyser;

import android.app.ListActivity;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;

import com.example.tpandroid1.R;

public class ResultActivity extends ListActivity {

		final static String tag = ResultActivity.class.getName();
	
		Analyser analyser;
		Uri mainImage;
	
		/*private BaseLoaderCallback mLoaderCallback = new BaseLoaderCallback(this) {
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
*/	    
	    public void CvNativeActivity() {
	        Log.i(tag, "Instantiated new " + this.getClass());
	    }

	/*    @Override
	    public void onResume()
	    {
	        super.onResume();
	        OpenCVLoader.initAsync(OpenCVLoader.OPENCV_VERSION_2_4_3, this, mLoaderCallback);
	}   */  
		
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		//create list with all brand know by the system
		Picture brands[] = new Picture[10];
		brands[0] = new Picture(R.drawable.kfc, "kfc", "www.kfc.com", this);
		brands[1] = new Picture(R.drawable.auchan, "auchan", "www.auchan.com", this);
		brands[2] = new Picture(R.drawable.carrefour, "carrefour", "www.carrefour.com", this);
		brands[3] = new Picture(R.drawable.decathlon, "decathlon", "www.decathon.com", this);
		brands[4] = new Picture(R.drawable.hp, "hp", "www.hp.com", this);
		brands[5] = new Picture(R.drawable.mcdo, "mcdo", "www.mcdo.com", this);
		brands[6] = new Picture(R.drawable.nike, "nike", "www.nike.com", this);
		brands[7] = new Picture(R.drawable.quick, "quick", "www.quick.com", this);
		brands[8] = new Picture(R.drawable.starbucks, "starbucks", "www.starbucks.com", this);
		brands[9] = new Picture(R.drawable.tf1, "tf1", "www.tf1.com", this);
		
		ListAdaptater listAdaptater = new ListAdaptater(this, brands);
		setListAdapter(listAdaptater);
		mainImage = (Uri) getIntent().getExtras().get("chooseImage");
		analyser = new Analyser(listAdaptater, this, mainImage);
		analyser.compare(brands, this);
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

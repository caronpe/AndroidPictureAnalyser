package com.telecom.lille.AndroidPictureAnalyser;


import java.nio.channels.AlreadyConnectedException;

import com.example.tpandroid1.R;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Icon;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;

public class ChoosePictureActivity extends Activity implements OnClickListener {

	final static String tag = ChoosePictureActivity.class.getName();
	final int IMAGE_CAPTURE = 1;
	final int IMAGE_SELECT = 2;
	Button captureBtn;
	Button libraryBtn;
	Button analyseBtn;
	ImageView imageView;
	boolean imageAlreadyChoose;
	Uri uriChooseImage;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);	
		setContentView(R.layout.activity_main);
		captureBtn = (Button) findViewById(R.id.shootBtn);
		captureBtn.setOnClickListener(this);
		libraryBtn = (Button) findViewById(R.id.libBtn);
		libraryBtn.setOnClickListener(this);
		analyseBtn = (Button) findViewById(R.id.analyseBtn);
		analyseBtn.setOnClickListener(this);
		imageView = (ImageView) findViewById(R.id.imageView2);
		imageAlreadyChoose = false;
		//imageView.setImageURI((Uri)"@drawable/ic_launcher");
	
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
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

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		//Log.i(tag, "click on take picture button");
		if(arg0 == captureBtn) startCaptureActivity();
		if(arg0 == libraryBtn) startLibActivity();
		if(arg0 == analyseBtn) startAnalyseActivity();
			
	}
		
	private void startAnalyseActivity() {
		// TODO Auto-generated method stub
		if (uriChooseImage == null){
			return;
		}
		Intent analyse = new Intent(this, ResultActivity.class);
		analyse.putExtra("chooseImage", uriChooseImage);
		startActivity(analyse);
	}

	private void startLibActivity() {
		// TODO Auto-generated method stub
		Intent libInt = new Intent(Intent.ACTION_GET_CONTENT).setType("image/*");
		startActivityForResult(libInt, IMAGE_SELECT);
	}


	private void startCaptureActivity() {
		// TODO Auto-generated method stub
		Intent captureInt = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		startActivityForResult(captureInt, IMAGE_CAPTURE);
	}
	
	
	 @Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		imageAlreadyChoose = true;
		if(requestCode == IMAGE_CAPTURE && resultCode == RESULT_OK){
			Bundle extra = data.getExtras();
			Bitmap imageBitMap = (Bitmap) extra.get("data");
			imageView.setImageBitmap(imageBitMap);
			uriChooseImage = data.getData();
			
		}
		if(requestCode == IMAGE_SELECT && resultCode == RESULT_OK){
			
			uriChooseImage = data.getData();
			Log.i(tag, uriChooseImage.toString());
			imageView.setImageURI(uriChooseImage);
		}
		
	}
	 
	 
}

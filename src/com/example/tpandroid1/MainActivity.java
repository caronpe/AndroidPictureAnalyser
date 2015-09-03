package com.example.tpandroid1;


import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.hardware.Camera.PreviewCallback;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends Activity implements OnClickListener {

	final static String tag = MainActivity.class.getName();
	final int IMAGE_CAPTURE=1;
	Button captureBtn;
	ImageView imageView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);	
		setContentView(R.layout.activity_main);
		captureBtn = (Button) findViewById(R.id.shootBtn);
		captureBtn.setOnClickListener(this);
		imageView = (ImageView) findViewById(R.id.imageView2);
		
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
		startCaptureActivity();
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
		if(requestCode == IMAGE_CAPTURE && resultCode == RESULT_OK){
			Bundle extra = data.getExtras();
			Bitmap imageBitMap = (Bitmap) extra.get("data");
			imageView.setImageBitmap(imageBitMap);
		}
		
	}
	 
	 
}

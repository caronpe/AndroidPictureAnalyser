package com.telecom.lille.AndroidPictureAnalyser;


import java.io.File;
import java.io.FileNotFoundException;

import com.example.tpandroid1.R;

import android.app.Activity;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
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

			float rotation = getCameraPhotoOrientation(uriChooseImage);
			Bitmap raotatedImage = RotateBitmap(decodeSampledBitmapFromResource(uriChooseImage, 1200, 1200), rotation);
		
			imageView.setImageBitmap(raotatedImage);
		}
		
	}
	 
	public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
	    // Raw height and width of image
	    final int height = options.outHeight;
	    final int width = options.outWidth;
	    int inSampleSize = 1;
	
	    if (height > reqHeight || width > reqWidth) {
	
	        final int halfHeight = height / 2;
	        final int halfWidth = width / 2;
	
	        // Calculate the largest inSampleSize value that is a power of 2 and keeps both
	        // height and width larger than the requested height and width.
	        while ((halfHeight / inSampleSize) > reqHeight && (halfWidth / inSampleSize) > reqWidth) {
	            inSampleSize *= 2;
	        }
	    }
	    return inSampleSize;
	}
	public Bitmap decodeSampledBitmapFromResource(Uri img, int reqWidth, int reqHeight) {

		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;

		AssetFileDescriptor fileDescriptor = null;
		try {
			fileDescriptor = getContentResolver().openAssetFileDescriptor(img, "r");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		BitmapFactory.decodeFileDescriptor(fileDescriptor.getFileDescriptor(), null, options);

        // Decode with inSampleSize
        BitmapFactory.Options options2 = new BitmapFactory.Options();
        options2.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight );

		return BitmapFactory.decodeFileDescriptor(fileDescriptor.getFileDescriptor(), null, options2);

	}
	public String getPath(Uri uri){
        String[] projection = { MediaStore.Images.Media.DATA };
        Cursor cursor = getContentResolver().query(uri, projection, null, null, null);
        if (cursor == null) return null;
        int column_index =             cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String s=cursor.getString(column_index);
        cursor.close();
        return s;
    }

    public int getCameraPhotoOrientation(Uri imagePath){
        int rotate = 0;
        try {
            File imageFile = new File(getPath(imagePath));
            Log.i("TAG", String.valueOf(imageFile.exists()));

            ExifInterface exif = new ExifInterface(imageFile.getAbsolutePath());
            int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);

            switch (orientation) {
            case ExifInterface.ORIENTATION_ROTATE_270:
                rotate = 270;
                break;
            case ExifInterface.ORIENTATION_ROTATE_180:
                rotate = 180;
                break;
            case ExifInterface.ORIENTATION_ROTATE_90:
                rotate = 90;
                break;
            }

            Log.i("RotateImage", "Exif orientation: " + orientation);
            Log.i("RotateImage", "Rotate value: " + rotate);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rotate;
    }

    public static Bitmap RotateBitmap(Bitmap source, float angle){
          Matrix matrix = new Matrix();
          matrix.postRotate(angle);
          return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(), matrix, true);
    }
}

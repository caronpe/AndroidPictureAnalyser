package com.telecom.lille.AndroidPictureAnalyser;

import java.io.FileNotFoundException;
import java.io.IOException;

import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;

public class Analyser {
	
	ListAdaptater listAdaptater;
	Bitmap mainImage, comparable;
	ResultActivity resultActivity;
	
	public Analyser(ListAdaptater listAdaptater, ResultActivity resultActivity, Uri imageUri) throws FileNotFoundException, IOException{
		this.listAdaptater = listAdaptater;
		this.resultActivity = resultActivity;
		mainImage = MediaStore.Images.Media.getBitmap(resultActivity.getContentResolver(), imageUri);
	}
	
	
}

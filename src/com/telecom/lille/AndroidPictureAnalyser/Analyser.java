package com.telecom.lille.AndroidPictureAnalyser;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.opencv.android.Utils;
import org.opencv.core.Mat;

import com.example.tpandroid1.R;

import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;

public class Analyser {
	
	ListAdaptater listAdaptater;
	Bitmap mainImage, compareImage;
	Mat mainImageMat, compareImageMat;
	ResultActivity resultActivity;
	
	public Analyser(ListAdaptater listAdaptater, ResultActivity resultActivity, Uri imageUri) throws FileNotFoundException, IOException{
		this.listAdaptater = listAdaptater;
		this.resultActivity = resultActivity;
		//chargement des images à partir de l'URI de l'image
		mainImage = MediaStore.Images.Media.getBitmap(resultActivity.getContentResolver(), imageUri);
		compareImage = MediaStore.Images.Media.getBitmap(resultActivity.getContentResolver(), imageUri);
		this.mainImageMat = new Mat(); 
		this.compareImageMat = new Mat();
		Utils.bitmapToMat(mainImage, mainImageMat);
		Utils.bitmapToMat(compareImage, compareImageMat);
	}
	
	
}

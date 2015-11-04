package com.telecom.lille.AndroidPictureAnalyser;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.opencv.android.OpenCVLoader;
import org.opencv.android.Utils;
import org.opencv.core.Mat;
import org.opencv.core.MatOfDMatch;
import org.opencv.core.MatOfKeyPoint;
import org.opencv.features2d.DMatch;
import org.opencv.features2d.DescriptorExtractor;
import org.opencv.features2d.DescriptorMatcher;
import org.opencv.features2d.FeatureDetector;

import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;

public class Analyser {

	final static String tag = "COMPARAISON";
	ListAdaptater listAdaptater;
	Bitmap mainImage, compareImage;
	Mat mainImageMat, compareImageMat;
	ResultActivity resultActivity;
	
	static {
	    if (!OpenCVLoader.initDebug()) {
	        // Handle initialization error
	    }
	}
	

	public Analyser(ListAdaptater listAdaptater, ResultActivity resultActivity, Uri imageUri){
		this.listAdaptater = listAdaptater;
		this.resultActivity = resultActivity;
		//chargement des images à partir de l'URI de l'image
		try {
			mainImage = MediaStore.Images.Media.getBitmap(resultActivity.getContentResolver(), imageUri);
			compareImage = MediaStore.Images.Media.getBitmap(resultActivity.getContentResolver(), imageUri);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		this.mainImageMat = new Mat(); 
		this.compareImageMat = new Mat();
		Utils.bitmapToMat(mainImage, mainImageMat);
		Utils.bitmapToMat(compareImage, compareImageMat);
		Log.i("ANALYSER", "Image chargé!!!!!!!!!!!!!!!!!");
	}

	public void compare(){
		MatOfKeyPoint keyPointMain = new MatOfKeyPoint();
		MatOfKeyPoint keyPointComp = new MatOfKeyPoint();
		Mat DescriptorMain = new Mat();
		Mat DescriptorCompare = new Mat();

		//Definition of ORB keypoint detector and descriptor extractors
		FeatureDetector detector = FeatureDetector.create(FeatureDetector.ORB); 
		DescriptorExtractor extractor = DescriptorExtractor.create(DescriptorExtractor.ORB);

		//Detect keypoints
		detector.detect(mainImageMat, keyPointMain);
		detector.detect(compareImageMat, keyPointComp);  
		//Extract descriptors
		extractor.compute(mainImageMat, keyPointMain, DescriptorMain);
		extractor.compute(compareImageMat, keyPointComp, DescriptorCompare);

		//Definition of descriptor matcher
		DescriptorMatcher matcher = DescriptorMatcher.create(DescriptorMatcher.BRUTEFORCE_HAMMING);

		//Match points of two images
		MatOfDMatch matches = new MatOfDMatch();
		matcher.match(DescriptorMain,DescriptorCompare ,matches);

		Log.i(tag, "matches.size() : " + matches.size());
		Log.i(tag, "matches : " + matches);

		MatOfDMatch matchesFiltered = new MatOfDMatch();

		List<DMatch> matchesList = matches.toList();
		List<DMatch> bestMatches = new ArrayList<DMatch>();

		Double max_dist = 0.0;
		Double min_dist = 100.0;

		for (int i = 0; i < matchesList.size(); i++) {
			Double dist = (double) matchesList.get(i).distance;

			if (dist > 0)
				Log.i(tag, "dist : " + dist);

			if (dist < min_dist && dist != 0) {
				min_dist = dist;
			}

			if (dist > max_dist) {
				max_dist = dist;
			}

		}

		Log.i(tag, "max_dist : " + max_dist);
		Log.i(tag, "min_dist : " + min_dist);

		if (min_dist > 50) {
			Log.i(tag, "No match found, min_dist under minimum value");
			return;
		}

		double threshold = 3 * min_dist;
		double threshold2 = 2 * min_dist;

		if (threshold > 75) {
			threshold = 75;
		} else if (threshold2 >= max_dist) {
			threshold = min_dist * 1.1;
		} else if (threshold >= max_dist) {
			threshold = threshold2 * 1.4;
		}

		Log.i(tag, "Threshold : " + threshold);

		for (int i = 0; i < matchesList.size(); i++) {
			Double dist = (double) matchesList.get(i).distance;

			if (dist < threshold) {
				bestMatches.add(matches.toList().get(i));
				Log.i(tag, String.format(i + " best match added : %s", dist));
			}
		}

		matchesFiltered.fromList(bestMatches);

		Log.i("ANALYSER", "matchesFiltered.size() : " + matchesFiltered.size());

		if (matchesFiltered.rows() >= 1) {
			Log.i(tag, "match found");
		} else {
			Log.i(tag, "match not found");
		}
	}
	
}

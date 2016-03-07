package com.telecom.lille.AndroidPictureAnalyser;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.bytedeco.javacpp.opencv_core.Mat;
import org.opencv.android.OpenCVLoader;
import org.opencv.core.Size;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.content.CursorLoader;
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
	private String getRealPathFromURI(Uri contentUri, Context context) {
	    String[] proj = { MediaStore.Images.Media.DATA };
	    CursorLoader loader = new CursorLoader(context, contentUri, proj, null, null, null);
	    Cursor cursor = loader.loadInBackground();
	    int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
	    cursor.moveToFirst();
	    String result = cursor.getString(column_index);
	    cursor.close();
	    return result;
	}
	public Analyser(ListAdaptater listAdaptater, ResultActivity resultActivity, Uri imageUri){
		this.listAdaptater = listAdaptater;
		this.resultActivity = resultActivity;
		
		//load the choosing image
		mainImage = decodeSampledBitmapFromResource(imageUri, 70, 70);
		mainImageMat = new Mat();
		//this.mainImageMat = imread(getRealPathFromURI(imageUri,resultActivity)); 
	//	Utils.bitmapToMat(mainImage, mainImageMat);	
		Log.i("ANALYSER", "Image Principal chargée!!!!!!!!!!!!!!!!!");
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
	/*private void loadVocabulary(String vocabularyYml){
		if(vocabularyYml != null){
			try {			
				int startArray = vocabularyYml.indexOf("data:") + 7;				
				String tab = vocabularyYml.substring(startArray, (vocabularyYml.length()));
				List<Float> floatVocab = new LinkedList<Float>();
				floatVocab.addAll( tabStringtoFloat(tab) );
				//vocabulary = new Mat(50, 32, CvType.CV_32F);
				vocabulary = org.opencv.utils.Converters.vector_float_to_Mat(floatVocab);
			} catch (Exception e) {
				 vocabulary = null;
				 Log.e(tag, "Le parsing du fichier JSON a �chou�");
		}
		}
	}
	
	public static List<Float> tabStringtoFloat(String tab) {
		List<Float> tabReturn = null;
		if (tab != null) {
			String[] tabString = tab.split(",");
			tabReturn = new LinkedList<Float>();
			for (String s : tabString) {
				try {
					tabReturn.add(Float.valueOf(s.trim()));
				} catch (NumberFormatException e) {
					Log.e(tag, "Le parsing du fichier YML a �chou�");
					return null;
				}
			}
		}
		return tabReturn;
	}*/
	
	/**
	 * une conversion de l'image en bitmap
	 * @param img uri de l'image
	 * @param reqWidth
	 * @param reqHeight
	 * @return a BitMap description of the image
	 */
	public Bitmap decodeSampledBitmapFromResource(Uri img, int reqWidth, int reqHeight) {

		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		
		AssetFileDescriptor fileDescriptor = null;
		try {
			fileDescriptor = resultActivity.getContentResolver().openAssetFileDescriptor(img, "r");
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
	
	public void compare(Picture[] brand, Context context){
		for(int i = 0 ; i < brand.length ; i++){
			try {
				brand[i].setMatchSize(compare(brand[i].getUri(), context));
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public Size compare(Uri compareImg, Context context) throws FileNotFoundException, IOException{
	
		//compareImage = decodeSampledBitmapFromResource(compareImg, 70, 70);
		/*compareImage = MediaStore.Images.Media.getBitmap(context.getContentResolver(), compareImg);
		Log.i("ANALYSER", "Image à comparer chargée!" + compareImage);
		this.compareImageMat = new Mat();
		Utils.bitmapToMat(compareImage, compareImageMat);
		
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
		
		//get matching point list
		List<DMatch> matchesList = matches.toList();
		List<DMatch> bestMatches = new ArrayList<DMatch>();

		Double max_dist = 0.0;
		Double min_dist = 100.0;

		//determinated min and max dist in this compare
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
			return null;
		}

		//choose an appropriate threshold
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

		//get match with dist under threshold
		for (int i = 0; i < matchesList.size(); i++) {
			Double dist = (double) matchesList.get(i).distance;

			if (dist < threshold) {
				bestMatches.add(matches.toList().get(i));
				Log.i(tag, String.format(i + " best match added : %s", dist));
			}
		}

		MatOfDMatch matchesFiltered = new MatOfDMatch();
		matchesFiltered.fromList(bestMatches);

		Log.i("ANALYSER", "matchesFiltered.size() : " + matchesFiltered.size());

		if (matchesFiltered.rows() >= 1) {
			Log.i(tag, "match found");
		} else {
			Log.i(tag, "match not found");
		}
		
		if(matchesFiltered.size() == null){
			return new Size(0 , 0);
		}
		return matchesFiltered.size();
		*/
		return null;
	}
	
}

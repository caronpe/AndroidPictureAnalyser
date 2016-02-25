package com.telecom.lille.AndroidPictureAnalyser;

import java.net.URI;
import java.net.URISyntaxException;

import org.opencv.core.Size;

import com.example.tpandroid1.R;

import android.content.ContentResolver;
import android.content.Context;
import android.net.Uri;

public class Picture {

	private Uri uri;
	private int brandId;
	private String brand;
	private String website;
	private String matchingSize;
	
	public Picture(int brandID, String brand, String website, Context context) {
		super();
		try {
			this.uri = findUri(brandID, context);
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		this.brandId = brandID;
		this.brand = brand;
		this.website = website;
		this.matchingSize= new String();
	}
	
	public int getBrandId() {
		return brandId;
	}

	public void setBrandId(int brandId) {
		this.brandId = brandId;
	}

	/**
	 * Find URI link with a picture 
	 * @param brandID
	 * @param context
	 * @return
	 * @throws URISyntaxException
	 */
	public Uri findUri(int brandID, Context context) throws URISyntaxException{
		 Uri imageUri = Uri.parse(
				 ContentResolver.SCHEME_ANDROID_RESOURCE +
				 "://" + context.getResources().getResourcePackageName(brandID)
				 + '/' + context.getResources().getResourceTypeName(brandID)
				 + '/' + context.getResources().getResourceEntryName(brandID));
		 
		 return imageUri;
	}
	
	public Uri getUri() {
		return uri;
	}
	public void setUri(Uri uri) {
		this.uri = uri;
	}
	public String getBrand() {
		return brand;
	}
	public void setBrand(String brand) {
		this.brand = brand;
	}
	public String getWebsite() {
		return website;
	}
	public void setWebsite(String website) {
		this.website = website;
	}

	@Override
	public String toString() {
		return "Picture [uri=" + uri + ", brand=" + brand + ", website="
				+ website + "]";
	}

	public void setMatchSize(Size matchingSize) {
		// TODO Auto-generated method stub
		if(matchingSize != null){
			this.matchingSize = matchingSize.toString();
		}else{
			this.matchingSize = "Logo detected";
		}
	}

	public String getMatchingSize() {
		return matchingSize;
	}

	public boolean match() {
		return !matchingSize.equals("");
	}
}

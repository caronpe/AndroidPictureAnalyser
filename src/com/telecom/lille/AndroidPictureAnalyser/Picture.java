package com.telecom.lille.AndroidPictureAnalyser;

import java.net.URI;
import java.net.URISyntaxException;

import com.example.tpandroid1.R;

import android.content.ContentResolver;
import android.content.Context;
import android.net.Uri;

public class Picture {

	private URI uri;
	private int brandId;
	private String brand;
	private String website;
	
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
	public URI findUri(int brandID, Context context) throws URISyntaxException{
		 Uri imageUri = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE +
				 "://" + context.getResources().getResourcePackageName(brandID)
				 + '/' + context.getResources().getResourceTypeName(brandID) + '/' + context.getResources().getResourceEntryName(brandID) );
		 return new URI(imageUri.toString());
	}
	
	public URI getUri() {
		return uri;
	}
	public void setUri(URI uri) {
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
}

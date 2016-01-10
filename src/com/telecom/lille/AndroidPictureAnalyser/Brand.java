package com.telecom.lille.AndroidPictureAnalyser;

import android.util.Log;

public class Brand {
	
	private String name;
	private String url;
	private String classifier;
	
	public Brand(String name, String url, String classifier) {
		super();
		this.name = name;
		this.url = url;
		this.classifier = classifier;
		Log.i("BRANDS", name.concat(" correctly create"));
	}
}


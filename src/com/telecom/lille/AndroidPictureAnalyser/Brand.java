package com.telecom.lille.AndroidPictureAnalyser;

import java.util.ArrayList;

import android.util.Log;

public class Brand {
	
	private String name;
	private String url;
	private String classifier;
	private String imageURL[];
	
	public Brand(String name, String url, String classifier, ArrayList<String> listImagePath) {
		super();
		this.name = name;
		this.url = url;
		this.classifier = classifier;
		imageURL = new String[listImagePath.size()];
		int i = 0;
		for (String imageLink : listImagePath) {
			imageURL[i] = ChoosePictureActivity.serveurPath.concat("train-images/".concat(imageLink));
			i++;
		}
		Log.i("BRANDS", classifier);
		Log.i("BRANDS", name.concat(" correctly create"));
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getClassifier() {
		return classifier;
	}

	public void setClassifier(String classifier) {
		this.classifier = classifier;
	}
}


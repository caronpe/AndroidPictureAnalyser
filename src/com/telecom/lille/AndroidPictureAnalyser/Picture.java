package com.telecom.lille.AndroidPictureAnalyser;

import java.net.URI;

public class Picture {

	public URI uri;
	public String brand;
	public String website;
	
	public Picture(URI uri, String brand, String website) {
		super();
		this.uri = uri;
		this.brand = brand;
		this.website = website;
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

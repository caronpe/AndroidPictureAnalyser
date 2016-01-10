package com.telecom.lille.AndroidPictureAnalyser;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class URLdownloader {

		public static String URLdownload(String path){
			//download
			StringBuilder builder = new StringBuilder();
			String line;
			HttpURLConnection urlConnection;
			InputStream in;
			BufferedReader reader;
			try {
				URL url = new URL(path);
				urlConnection = (HttpURLConnection) url.openConnection();
				in = new BufferedInputStream(urlConnection.getInputStream());
				reader = new BufferedReader (new InputStreamReader(in));
				while((line = reader.readLine()) != null){
					builder.append((line + "\n"));	
				}
				reader.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return builder.toString();
		}
}

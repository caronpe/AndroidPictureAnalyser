package com.telecom.lille.AndroidPictureAnalyser;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.AsyncTask;

public class GetURL extends AsyncTask <URI, Integer, String>{

	/**
	 * Se connecte au serveur, recupére le JSON
	 */
	@Override
	protected String doInBackground(URI... uris) {
		//download
		/*HttpClient client = new DefaultHttpClient();
		HttpGet request = new HttpGet();*/
		StringBuilder builder = new StringBuilder();
		String line;
		
		try {
			//request.setURI(uris[0]);
			URL url = new URL(uris[0].toString());
			HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
			InputStream in = new BufferedInputStream(urlConnection.getInputStream());
		/*	//HttpResponse response = client.execute(request);
			//if(response.getStatusLine().getStatusCode() == 0){
			//	HttpEntity entity = response.getEntity();
			//	if(entity != null){*/
					BufferedReader reader = new BufferedReader (new InputStreamReader(in));
					while((line = reader.readLine()) != null){
						builder.append((line + "\n"));	
					}
					reader.close();
					/*		//	}
		//	}
			
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			*/
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//publishProgress(counter + 1);
		return builder.toString();
	}
	
	
	protected void onPostExecute(String JSONfile){
		//parsing du context + parsing Json
		List<Brand> brands = new LinkedList<Brand>();
		//StringJson est récupéré dans le httpget
		JSONObject root;
		try {
			root = new JSONObject (JSONfile);
	
		JSONArray jArray = root.getJSONArray("brands");
		for (int i=0; i<jArray.length() ; i++){
			JSONObject jobj = jArray.getJSONObject(i);
			Brand brand = new Brand(jobj.getString("brandname"), jobj.getString("url"), jobj.getString("classifier"));
			//TODO recup nom d'image
			brands.add(brand);
		}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	void onUpdateProgress (Integer...proress){
		//maj de linterface user (barre de chargement)
	}
}

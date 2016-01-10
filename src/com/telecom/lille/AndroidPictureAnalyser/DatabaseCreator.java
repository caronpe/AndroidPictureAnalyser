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

public class DatabaseCreator extends AsyncTask <String, Integer, String>{

	/**
	 * Se connecte au serveur, recupére le JSON
	 */
	@Override
	protected String doInBackground(String... sitePath) {
		//download JSON
		String JSONfile = URLdownloader.URLdownload(sitePath[0].concat("index.json"));		
		//parsing du context + parsing Json
		List<Brand> brands = new LinkedList<Brand>();
		//StringJson est récupéré dans le httpget
		JSONObject root;
		try {
			root = new JSONObject (JSONfile);
			JSONArray jArray = root.getJSONArray("brands");
			for (int i=0; i<jArray.length() ; i++){
				//vide le builder
				JSONObject jobj = jArray.getJSONObject(i);	
				//Création du chemin du classifier
				String classifierPath = "classifiers/";
				classifierPath.concat(jobj.getString("classifier"));
				//création de l'objet Brand
				Brand brand = new Brand(jobj.getString("brandname"), jobj.getString("url"), URLdownloader.URLdownload(sitePath[0].concat(classifierPath)));
				//TODO recup nom d'image
				brands.add(brand);
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		//publishProgress(counter + 1);
		return "";
	}


	protected void onPostExecute(String JSONfile){
	/*	//parsing du context + parsing Json
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
		}*/
	}

	void onUpdateProgress (Integer...proress){
		//maj de linterface user (barre de chargement)
	}
}

package com.telecom.lille.AndroidPictureAnalyser;

import com.example.tpandroid1.R;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;

public class ResultActivity extends ListActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		
		  String[] values = new String[] { "Device",
		            "Géo localisation", "Accéléromètre",
		            "Navigateur internet", "Dialogues", "Album photos",
		            "Connexion réseau", "Gestion des fichiers",
		            "Carnet de contacts" };

		  ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.rowlayout, values);
		  setListAdapter(adapter);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.result, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}

package com.telecom.lille.AndroidPictureAnalyser;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.tpandroid1.R;

public class ResultActivity extends ListActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		
		  String[] values = new String[] {"surf" , "sunset", "tiger"};

		  ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.rowlayout, values);
		  setListAdapter(adapter);
		  
		  ListAdaptater adaptateur = new ListAdaptater(this, values);
	      setListAdapter(adaptateur);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.result, menu);
		return true;
	}
	
	 @Override
	 protected void onListItemClick(ListView l, View v, int position, long id) {
	        Toast.makeText(this, "Position : " + position, Toast.LENGTH_LONG).show();
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

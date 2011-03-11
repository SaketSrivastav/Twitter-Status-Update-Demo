package com.android.saket.twitter;

import winterwell.jtwitter.Twitter;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class StatusUpdate extends Activity implements android.view.View.OnClickListener{

	private static final String TAG = StatusUpdate.class.getSimpleName();

	EditText mStatusMessage;
	Button mUpdate;
	SharedPreferences prefs;
	Context context = StatusUpdate.this;
	Twitter twitter;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		mUpdate = (Button)findViewById(R.id.btnUpdateStatus);
		mStatusMessage = (EditText)findViewById(R.id.editTextStatus);
		mUpdate.setOnClickListener(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu){
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menu, menu);
		return true;
	}

	// Called when menu item is selected //
	@Override
	public boolean onOptionsItemSelected(MenuItem item){

		switch(item.getItemId()){

		case R.id.prefs:
			// Launch Prefs activity
			Intent i = new Intent(StatusUpdate.this, Prefs.class);
			startActivity(i);
			Log.d(TAG, "MenuPrefs starting Prefs");
			break;

		}    
		return true;
	}

	@Override
	public void onClick(View v) {

		int id = v.getId();

		switch(id){
		case R.id.btnUpdateStatus:

			prefs = PreferenceManager.getDefaultSharedPreferences(StatusUpdate.this);

			String username = prefs.getString("username", "n/a");
			String password = prefs.getString("password", "n/a");
			Log.v(TAG, "In onClick");
			
			if((username == null) || (username.equals(""))){
				Log.v(TAG, "username == null");
				AlertDialog.Builder ad = new AlertDialog.Builder(context);
				ad.setNeutralButton("Ok", new OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.cancel();
					}
				});
				ad.setMessage("Username not provided in Preferences");
				ad.create();
				ad.show();

			}else if((password == null) || (password.equals(""))){
				Toast.makeText(context, "Password Missing", Toast.LENGTH_LONG).show();
			}else{
				try{
					Log.v(TAG, "creating twitter object");
					twitter = new Twitter(username, password);
					twitter.setAPIRootUrl("https://identi.ca/api");


					String statusMessage = mStatusMessage.getText().toString();
					if((statusMessage != null) && (statusMessage.length() != 0)){

						twitter.setStatus(statusMessage);
						Toast.makeText(context, "Status Posted Successfully", Toast.LENGTH_LONG).show();
						Log.v(TAG, "STATUS POSTED");
					}else{
						AlertDialog.Builder ad = new AlertDialog.Builder(context);
						ad.setNeutralButton("Ok", new OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog, int which) {
								dialog.cancel();
							}
						});
						ad.setMessage("Status Message Empty");
						ad.create();
						ad.show();
					}

				}catch (Exception e) {
					Log.e(TAG, e.getMessage());
					Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
					e.printStackTrace();
				}
			}
		}
	}

}
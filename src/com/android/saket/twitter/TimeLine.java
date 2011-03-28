/**
 * 
 */
package com.android.saket.twitter;

import java.util.ArrayList;
import java.util.List;

import winterwell.jtwitter.Twitter;
import winterwell.jtwitter.Twitter.Status;
import android.app.ListActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.ArrayAdapter;
import android.widget.Toast;

/**
 * @author saket
 *
 */
public class TimeLine extends ListActivity {
	
	Twitter twitter;
	SharedPreferences prefs;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		
		prefs = PreferenceManager.getDefaultSharedPreferences(TimeLine.this);
		String username = prefs.getString("username", "n/a");
		String password = prefs.getString("password", "n/a");
	
		try{
			
			twitter = new Twitter(username, password);
			twitter.setAPIRootUrl("https://identi.ca/api");
			
			List<Status> timeLineList = new ArrayList<Status>(); 
				
			timeLineList = twitter.getFriendsTimeline();
			
			setListAdapter(new ArrayAdapter<Status>(this,
					R.layout.timeline,
					timeLineList));
		}catch (Exception e) {
			e.printStackTrace();
			Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
		}
	}
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		Intent timeLine = new Intent(TimeLine.this, StatusUpdate.class);
		startActivity(timeLine);
		this.finish();
	}
}

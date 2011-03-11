/**
 * 
 */
package com.android.saket.twitter;

import android.os.Bundle;
import android.preference.PreferenceActivity;

/**
 * @author saket
 *
 */
public class Prefs extends PreferenceActivity {

	/* (non-Javadoc)
	 * @see android.preference.PreferenceActivity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.prefs);
	}
	
}

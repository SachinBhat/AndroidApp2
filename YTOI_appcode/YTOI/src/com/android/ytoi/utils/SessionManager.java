package com.android.ytoi.utils;

import java.util.HashMap;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.android.ytoi.Activity_Home;
import com.android.ytoi.Activity_Login;
import com.android.ytoi.webclass.UserInfo;

public class SessionManager {

	// Shared Preferences
	SharedPreferences pref;

	// Editor for Shared preferences
	Editor editor;
	Activity activity;
	// Context
	Context _context;

	// Shared pref mode
	int PRIVATE_MODE = 0;

	// Sharedpref file name
	private static final String PREF_NAME = "YTOI";

	// All Shared Preferences Keys
	private static final String IS_LOGIN = "IsLoggedIn";

	public static final String KEY_USERID = "userid";
	public static final String KEY_FIRSTNAME = "firstname";
	public static final String KEY_LASTNAME = "lastname";
	public static final String KEY_BIRTHDATE = "birthdate";
	public static final String KEY_STATUS = "status";
	public static final String KEY_STREET = "street";
	public static final String KEY_CITY = "city";
	public static final String KEY_STATE = "state";
	public static final String KEY_COUNTRY = "country";
	public static final String KEY_ZIPCODE = "zipcode";
	public static final String KEY_MOBILENO = "mobileNo";
	public static final String KEY_EMAIL = "email";
	public static final String KEY_GENDER = "gender";
	public static final String KEY_PROFILE_IMG = "profile_Image";

	public static final String KEY_PROFILE_THUMB = "profile_Thumb";

	// Constructor
	public SessionManager(Context context,Activity activity) {
		this._context = context;
		pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
		editor = pref.edit();
		this.activity=activity;
	}

	/**
	 * Create login session
	 * */
	public void createLoginSession(UserInfo user) {
		// Storing login value as TRUE
		editor.putBoolean(IS_LOGIN, true);
		editor.putString(KEY_USERID, user.user_id);
		editor.putString(KEY_FIRSTNAME, user.first_name);
		editor.putString(KEY_LASTNAME, user.last_name);
		editor.putString(KEY_BIRTHDATE, user.birth_date);
		editor.putString(KEY_STATUS, user.status);
		editor.putString(KEY_STREET, user.street);
		editor.putString(KEY_CITY, user.city);
		editor.putString(KEY_STATE, user.state);
		editor.putString(KEY_COUNTRY, user.country);
		editor.putString(KEY_ZIPCODE, user.zipcode);
		editor.putString(KEY_MOBILENO, user.mobile_number);
		editor.putString(KEY_EMAIL, user.email);
		editor.putString(KEY_GENDER, user.gender);
		editor.putString(KEY_PROFILE_IMG, user.profile_picture);
		editor.putString(KEY_PROFILE_THUMB, user.profile_thumb);
		// commit changes
		editor.commit();
	}

	/**
	 * Check login method wil check user login status If false it will redirect
	 * user to login page Else won't do anything
	 * */
	public void checkLogin() {
		if (this.isLoggedIn()) {
			Intent i = new Intent(_context, Activity_Home.class);
			i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			_context.startActivity(i);
			activity.finish();
		}

	}

	/**
	 * Get stored session data
	 * */
	public HashMap<String, String> getUserDetails() {
		HashMap<String, String> user = new HashMap<String, String>();
		user.put(KEY_USERID, pref.getString(KEY_USERID, null));
		user.put(KEY_FIRSTNAME, pref.getString(KEY_FIRSTNAME, null));
		user.put(KEY_LASTNAME, pref.getString(KEY_LASTNAME, null));
		user.put(KEY_BIRTHDATE, pref.getString(KEY_BIRTHDATE, null));
		user.put(KEY_STATUS, pref.getString(KEY_STATUS, null));
		user.put(KEY_STREET, pref.getString(KEY_STREET, null));
		user.put(KEY_CITY, pref.getString(KEY_CITY, null));
		user.put(KEY_STATE, pref.getString(KEY_STATE, null));
		user.put(KEY_COUNTRY, pref.getString(KEY_COUNTRY, null));
		user.put(KEY_ZIPCODE, pref.getString(KEY_ZIPCODE, null));
		user.put(KEY_MOBILENO, pref.getString(KEY_MOBILENO, null));
		user.put(KEY_EMAIL, pref.getString(KEY_EMAIL, null));
		user.put(KEY_GENDER, pref.getString(KEY_GENDER, null));
		user.put(KEY_PROFILE_IMG, pref.getString(KEY_PROFILE_IMG, null));
		user.put(KEY_PROFILE_THUMB, pref.getString(KEY_PROFILE_THUMB, null));

		// return user
		return user;
	}

	public void modifyInfornmation(UserInfo user) {
		// Storing login value as TRUE
		editor.putBoolean(IS_LOGIN, true);

		editor.putString(KEY_FIRSTNAME, user.first_name);
		editor.putString(KEY_LASTNAME, user.last_name);

		editor.putString(KEY_STATUS, user.status);
		editor.putString(KEY_STREET, user.street);
		editor.putString(KEY_CITY, user.city);
		editor.putString(KEY_STATE, user.state);
		editor.putString(KEY_COUNTRY, user.country);
		editor.putString(KEY_ZIPCODE, user.zipcode);

		editor.putString(KEY_GENDER, user.gender);

		// commit changes
		editor.commit();
	}


	/**
	 * Clear session details
	 * */
	public void logoutUser() {
		// Clearing all data from Shared Preferences
		editor.clear();
		editor.commit();

		// After logout redirect user to Loing Activity
		Intent i = new Intent(_context, Activity_Login.class);
		// Closing all the Activities
		i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

		// Add new Flag to start new Activity
		i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

		// Staring Login Activity
		_context.startActivity(i);
	}

	/**
	 * Quick check for login
	 * **/
	// Get Login State
	public boolean isLoggedIn() {
		return pref.getBoolean(IS_LOGIN, false);
	}
}

package com.android.ytoi.fragment;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.android.ytoi.R;
import com.android.ytoi.utils.Act_Fragment;
import com.android.ytoi.utils.ConstantValue;
import com.android.ytoi.utils.SessionManager;
import com.android.ytoi.web.HttpHelper_Fragment;
import com.android.ytoi.webclass.SettingInfo;

public class Fragment_Setting extends Act_Fragment {

	View rootView;
	Context context;
	TextView username,txt_changepassword;
	CheckBox soundCHK, locationCHK;
	String soundStr = "0", locationStr = "0";
	SettingInfo setting;
	public Fragment_Setting() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		rootView = inflater.inflate(R.layout.setting_main, container, false);
		context = rootView.getContext();
		init();

		soundCHK.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (((CheckBox) v).isChecked())
					soundStr = "1";
				else
					soundStr = "0";
				ChangeSetting(soundStr,locationStr);
			}
		});
		locationCHK.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (((CheckBox) v).isChecked())
					locationStr = "1";
				else
					locationStr = "0";
				ChangeSetting(soundStr,locationStr);				
			}
		});
		return rootView;
	}

	public void init() {
		username = (TextView) rootView
				.findViewById(R.id.setting_Textview_Username);
		soundCHK = (CheckBox) rootView
				.findViewById(R.id.setting_CheckBox_soundEffect);
		locationCHK = (CheckBox) rootView
				.findViewById(R.id.setting_CheckBox_location);
		username.setText(ConstantValue.user.get(SessionManager.KEY_FIRSTNAME)
				+ " " + ConstantValue.user.get(SessionManager.KEY_LASTNAME));

		txt_changepassword=(TextView) rootView
				.findViewById(R.id.setting_txt_changepassword);
		txt_changepassword.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				Fragment fragment = null;
				FragmentManager fragmentManager = getFragmentManager();
				android.app.FragmentTransaction fragmenttransaction = fragmentManager.beginTransaction(); 
				fragment = new Fragment_ChangePassword();
				String fragmentTagName = "changepassword";
				if (!fragmentTagName.equalsIgnoreCase("")) {

					if (fragment!=null) {
						if (fragment.isAdded()) {
							fragmenttransaction.show(fragment);
							fragmenttransaction.addToBackStack(null);
						} else {
							fragmenttransaction.add(R.id.profile_content_frame, fragment,fragmentTagName);
							fragmenttransaction.addToBackStack(fragmentTagName);
						}
						fragmenttransaction.commit();
					}
				}

			}
		});

		if (isOnline()) // connected
		{
			String uri = null;
			uri = String.format(ConstantValue.URL_ACSETTING_VIEW);
			uri = uri.replace(" ", "%20");
			Log.i("Following Info", uri);
			if (uri != null) {
				uri = uri.replace(" ", "%20");

				List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>(
						2);
				nameValuePair.add(new BasicNameValuePair("user_id",
						ConstantValue.user.get(SessionManager.KEY_USERID)));
				HttpHelper_Fragment httpHelper = new HttpHelper_Fragment(
						ConstantValue.REQUESTCODE_ACSETTING_VIEW,
						Fragment_Setting.this, "Loading...", nameValuePair);
				httpHelper.execute(uri);
			}

		} else {

			Toast.makeText(getActivity().getApplicationContext(),
					"Internet Connection Fail", Toast.LENGTH_SHORT).show();
		}

	}

	public void ChangeSetting(String sound,String location)
	{
		if (isOnline()) // connected
		{
			String uri = null;
			uri = String.format(ConstantValue.URL_ACSETTING_ADD);
			uri = uri.replace(" ", "%20");
			Log.i("Following Info", uri);
			if (uri != null) {
				uri = uri.replace(" ", "%20");

				List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>(
						2);
				nameValuePair.add(new BasicNameValuePair("user_id",
						ConstantValue.user.get(SessionManager.KEY_USERID)));
				nameValuePair.add(new BasicNameValuePair("sound_effect",
						sound));
				nameValuePair.add(new BasicNameValuePair("location",
						location));
				nameValuePair.add(new BasicNameValuePair("font_size",
						"12"));
				nameValuePair.add(new BasicNameValuePair("proxy",
						"12"));
				nameValuePair.add(new BasicNameValuePair("about",
						"testing"));
				HttpHelper_Fragment httpHelper = new HttpHelper_Fragment(
						ConstantValue.REQUESTCODE_ACSETTING_ADD,
						Fragment_Setting.this, "Loading...", nameValuePair);
				httpHelper.execute(uri);
			}

		} else {

			Toast.makeText(getActivity().getApplicationContext(),
					"Internet Connection Fail", Toast.LENGTH_SHORT).show();
		}

	}
	public void setBackApiResponse(int requestcode, Object obj1, Object obj2){
		if (requestcode == ConstantValue.REQUESTCODE_ACSETTING_ADD) {
			if (obj1.equals("1")) {
				Toast.makeText(context, obj2.toString(), Toast.LENGTH_SHORT)
				.show();
			} else
				Toast.makeText(context, obj2.toString(), Toast.LENGTH_SHORT)
				.show();
		}

		else if (requestcode == ConstantValue.REQUESTCODE_CHECKIN_ADD) {
			if (obj1.equals("1")) {
				Toast.makeText(context, obj2.toString(), Toast.LENGTH_SHORT)
				.show();
			} else
				Toast.makeText(context, obj2.toString(), Toast.LENGTH_SHORT)
				.show();
		}
	}
	public void setBackApiResponse(int requestcode, Object obj1, Object obj2,Object obj3) {


		if(requestcode == ConstantValue.REQUESTCODE_ACSETTING_VIEW)
		{
			if (obj1!=null) 
				if (obj1.equals("1")) {
					Toast.makeText(context, obj2.toString(), Toast.LENGTH_SHORT)
					.show();
					setting=(SettingInfo) obj3;
					if(setting.sound_Effect.equals("1"))
					{
						soundCHK.setChecked(true);
						soundStr = "1";
					}
					if(setting.location.equals("1"))
					{
						locationCHK.setChecked(true);
						locationStr = "1";
					}

				} else
					Toast.makeText(context, obj2.toString(), Toast.LENGTH_SHORT)
					.show();
		}
	}
}

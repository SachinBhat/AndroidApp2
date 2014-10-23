package com.android.ytoi.fragment;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;
import android.widget.Toast;

import com.android.ytoi.R;
import com.android.ytoi.utils.Act_Fragment;
import com.android.ytoi.utils.ConstantValue;
import com.android.ytoi.utils.SessionManager;
import com.android.ytoi.web.HttpHelper_Fragment;
import com.android.ytoi.webclass.UserInfo;

public class Fragment_EditProfile extends Act_Fragment implements
		OnClickListener {

	private View rootView;
	private Context context;
	private TextView txt_emailId;
	private EditText firstname, lastname, street, state, city, country, zipcode;
	private String firstnameStr, lastnameStr, streetStr, cityStr, stateStr, countryStr,
			zipcodeStr;
	private Button submit;
	private RadioGroup gender;
	private 	List<NameValuePair> nameValuePair;

	private String genderStr="m";
	private SessionManager session;

	public Fragment_EditProfile() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		rootView = inflater.inflate(R.layout.edit_profile_main, container,
				false);
		context = rootView.getContext();
		session = new SessionManager(getActivity().getApplicationContext(),getActivity());
		init();
		submit.setOnClickListener(this);
		gender.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				switch (checkedId) {
				case R.id.editProfile_RadioButton_Male:
					genderStr="m";
					break;
				case R.id.editProfile_RadioButton_Female:
					genderStr="f";
					break;
				default:
					break;
				}
			}
		});
		return rootView;
	}

	public void init() {
		firstname = (EditText) rootView
				.findViewById(R.id.editProfile_EditText_firstName);
		firstname.setText(ConstantValue.user.get(SessionManager.KEY_FIRSTNAME));
		lastname = (EditText) rootView
				.findViewById(R.id.editProfile_EditText_lastName);
		lastname.setText(ConstantValue.user.get(SessionManager.KEY_LASTNAME));
		street = (EditText) rootView
				.findViewById(R.id.editProfile_EditText_street);
		street.setText(ConstantValue.user.get(SessionManager.KEY_STREET));
		state = (EditText) rootView
				.findViewById(R.id.editProfile_EditText_State);
		state.setText(ConstantValue.user.get(SessionManager.KEY_STATE));
		city = (EditText) rootView.findViewById(R.id.editProfile_EditText_city);
		city.setText(ConstantValue.user.get(SessionManager.KEY_CITY));
		country = (EditText) rootView
				.findViewById(R.id.editProfile_EditText_country);
		country.setText(ConstantValue.user.get(SessionManager.KEY_COUNTRY));
		zipcode = (EditText) rootView
				.findViewById(R.id.editProfile_EditText_zipcode);
		zipcode.setText(ConstantValue.user.get(SessionManager.KEY_ZIPCODE));
		gender = (RadioGroup) rootView
				.findViewById(R.id.editProfile_RadioGroup_Gender);
		submit = (Button) rootView.findViewById(R.id.editProfile_Button_Submit);
		
		txt_emailId=(TextView) rootView
				.findViewById(R.id.editProfile_txt_emailid);
		txt_emailId.setText(ConstantValue.user.get(SessionManager.KEY_EMAIL));
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.editProfile_Button_Submit:
			firstnameStr = firstname.getText().toString().trim();
			lastnameStr = lastname.getText().toString().trim();
			streetStr = street.getText().toString().trim();
			cityStr = city.getText().toString().trim();
			stateStr = state.getText().toString().trim();
			countryStr = country.getText().toString().trim();
			zipcodeStr = zipcode.getText().toString().trim();
			
			if (!(firstnameStr.equals("") && lastnameStr.equals("")
					&& streetStr.equals("") && stateStr.equals("")
					&& countryStr.equals("") && zipcodeStr.equals("") && cityStr
						.equals(""))) {
				ConnectivityManager cm = (ConnectivityManager) context
						.getSystemService(Context.CONNECTIVITY_SERVICE);
				NetworkInfo netInfo = cm.getActiveNetworkInfo();
				if (netInfo != null && netInfo.isConnectedOrConnecting()
						&& cm.getActiveNetworkInfo().isAvailable()
						&& cm.getActiveNetworkInfo().isConnected()) {

					String uri = null;
					uri = String.format(ConstantValue.URL_REGISTRATION);
					uri = uri.replace(" ", "%20");
					Log.i("LoginInfo", uri);
					if (uri != null) {
						uri = uri.replace(" ", "%20");

						nameValuePair = new ArrayList<NameValuePair>(2);
						nameValuePair.add(new BasicNameValuePair("user_id",
								ConstantValue.user
										.get(SessionManager.KEY_USERID)));
						
						nameValuePair.add(new BasicNameValuePair("first_name",
								firstnameStr));
						nameValuePair.add(new BasicNameValuePair("last_name",
								lastnameStr));
						nameValuePair.add(new BasicNameValuePair("street",
								streetStr));
						nameValuePair.add(new BasicNameValuePair("city",
								cityStr));
						nameValuePair.add(new BasicNameValuePair("state",
								stateStr));
						nameValuePair.add(new BasicNameValuePair("country",
								countryStr));
						nameValuePair.add(new BasicNameValuePair("zipcode",
								zipcodeStr));
						nameValuePair.add(new BasicNameValuePair("gender",
								genderStr));
						nameValuePair.add(new BasicNameValuePair("email",
								txt_emailId.getText().toString()));
						
						Log.i("Parameter", nameValuePair.toString());
						HttpHelper_Fragment httpHelper = new HttpHelper_Fragment(
								ConstantValue.REQUESTCODE_REGISTRATION,
								Fragment_EditProfile.this,
								"Modify information...", nameValuePair);
						httpHelper.execute(uri);
					}

				} else {

					Toast.makeText(context.getApplicationContext(),
							"Internet Connection Fail", Toast.LENGTH_SHORT)
							.show();
				}
			} else {
				Toast.makeText(context.getApplicationContext(),
						"Must fill all information", Toast.LENGTH_SHORT).show();

			}

			break;

		default:
//			getFragmentManager().beginTransaction()
//					.remove(Fragment_EditProfile.modify_fragment).commit();
//			fragment = new Fragment_ViewProfile();
//			getFragmentManager().beginTransaction()
//					.replace(R.id.profile_content_frame, fragment).commit();
			break;
		}

	}

	public void setBackApiResponse(int requestcode, Object obj1, Object obj2,
			Object obj3) {

		if (requestcode == ConstantValue.REQUESTCODE_REGISTRATION) {

			if (obj1.equals("1")) {
				Toast.makeText(context, obj2.toString(), Toast.LENGTH_SHORT)
						.show();
				UserInfo userinfo = (UserInfo) obj3;
				
				session.modifyInfornmation(userinfo);
				ConstantValue.user = session.getUserDetails();


				Fragment fragment = null;
				FragmentManager fragmentManager = getFragmentManager();
				android.app.FragmentTransaction fragmenttransaction = fragmentManager.beginTransaction(); 
				fragment = new Fragment_ViewPersonProfile();
				String fragmentTagName = "viewpersonprofile";
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

			} else
				Toast.makeText(context, obj2.toString(), Toast.LENGTH_SHORT)
						.show();
		}
	}

}

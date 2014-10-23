package com.android.ytoi.fragment;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.ytoi.R;
import com.android.ytoi.utils.ConstantValue;
import com.android.ytoi.utils.SessionManager;
import com.android.ytoi.web.WebAPIRequest;

public class Fragment_ChangePassword extends Fragment implements
OnClickListener{

	private EditText oldPasswordEDT, newPasswordEDT, conNewpasswordEDT;
	private String oldpasswordSTR, newpasswordSTR, conpasswordSTR;
	private Button submitBTN;
	private Context context;
	private ProgressDialog progress;
	private String response;
	private WebAPIRequest webAPIRequest = new WebAPIRequest();
	private String success = null;
	private String message = null;
	private List<NameValuePair> nameValuePair;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.change_password, container, false);
		context = rootView.getContext();
		init(rootView);
		submitBTN.setOnClickListener(this);
		return rootView;
	}
	public void init(View rootView) {

		oldPasswordEDT = (EditText) rootView.findViewById(R.id.changePassword_EditText_oldPassword);
		newPasswordEDT = (EditText) rootView.findViewById(R.id.changePassword_EditText_newPassword);
		conNewpasswordEDT = (EditText) rootView.findViewById(R.id.changePassword_EditText_confNewPassword);
		submitBTN = (Button) rootView.findViewById(R.id.changePassword_Button_Submit);

	}

	@Override
	public void onClick(View v) {

		if (v.getId() == R.id.changePassword_Button_Submit) {

			oldpasswordSTR = oldPasswordEDT.getText().toString().trim();
			newpasswordSTR = newPasswordEDT.getText().toString().trim();
			conpasswordSTR = conNewpasswordEDT.getText().toString().trim();

			if (!(oldpasswordSTR.equals("") || newpasswordSTR.equals("") || conpasswordSTR
					.equals(""))) {

				if (newpasswordSTR.equals(conpasswordSTR)) {
					ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
					NetworkInfo netInfo = cm.getActiveNetworkInfo();
					if (netInfo != null && netInfo.isConnectedOrConnecting()&& cm.getActiveNetworkInfo().isAvailable()&& cm.getActiveNetworkInfo().isConnected()) 
					{

						String uri = null;
						uri = String.format(ConstantValue.URL_CHANGE_PASSWORD);
						uri = uri.replace(" ", "%20");
						Log.i("LoginInfo", uri);
						if (uri != null) {
							uri = uri.replace(" ", "%20");

							nameValuePair = new ArrayList<NameValuePair>(
									2);
							nameValuePair.add(new BasicNameValuePair("user_id",
									ConstantValue.user.get(SessionManager.KEY_USERID)));
							nameValuePair.add(new BasicNameValuePair(
									"old_password", oldpasswordSTR));
							nameValuePair.add(new BasicNameValuePair(
									"new_password", newpasswordSTR));
							AsyncTask<Void, Void, Void> task = new AsyncTask<Void, Void, Void>() {

								@Override
								protected void onPreExecute() {
									progress = new ProgressDialog(context);
									// progress.setTitle("Processing...");
									progress.setMessage("Loading...");
									progress.setCancelable(false);
									progress.setIndeterminate(true);
									progress.show();

								}

								@Override
								protected Void doInBackground(Void... arg0) {
									try {		
										response = webAPIRequest.performPost_String(ConstantValue.URL_CHANGE_PASSWORD, nameValuePair);
										System.out.println("Response :-" + response.toString());
										if (response != null) {
											Log.i("Response :==", response);
											try {

												JSONObject j_object = new JSONObject(response);
												String data = j_object.getString("data");
												System.out.println("Jobject value :-" + data);
												JSONObject dataobject = new JSONObject(data);
												success = dataobject.getString("Success");
												System.out.println("success value :" + success);
												if (success.equals("1")) {
													message = dataobject.getString("Message");

												} else {
													message = dataobject.getString("Message");
												}

											} catch (Exception e) {
												e.printStackTrace();
											}

										}
									} 
									catch (Exception e) {
										e.printStackTrace();
									}
									return null;
								}

								@Override
								protected void onPostExecute(Void result) {
									if(success.equals("1"))
									{
										Toast.makeText(context,message,Toast.LENGTH_SHORT).show();
										//  Intent intent = new Intent();

										//							                intent.setClass(getActivity(), Activity_Home.class);
										//							                startActivity(intent);
									}
									else
									{
										Toast.makeText(context,message,Toast.LENGTH_SHORT).show();
									}

									if (progress != null) {
										progress.dismiss();

									}
								}

							};
							task.execute((Void[]) null);
						}

					} else {

						Toast.makeText(context.getApplicationContext(),
								"Internet Connection Fail", Toast.LENGTH_SHORT)
								.show();
					}
				} else {
					Toast.makeText(context, "confirm password does not match",
							Toast.LENGTH_SHORT).show();
				}
			}

			else {
				Toast.makeText(context, "Must Field all information",
						Toast.LENGTH_SHORT).show();
			}

		}
	}

}

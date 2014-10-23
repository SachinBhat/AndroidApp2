package com.android.ytoi;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.ytoi.utils.Act_Main;
import com.android.ytoi.utils.ConstantValue;
import com.android.ytoi.web.HttpHelper;

public class Activity_ForgotPassword extends Act_Main {

	EditText emailEDT;
	Button submitBTN;
	Context context=this;
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.forgot_password);
		
		emailEDT=(EditText)findViewById(R.id.forgotpassword_EditText_email);
		
		submitBTN=(Button)findViewById(R.id.forgotpassword_Button_Submit);
		
		submitBTN.setOnClickListener(new OnClickListener() 
		{

			@Override
			public void onClick(View v) {
				if((!emailEDT.getText().toString().trim().equals("")) && ConstantValue.checkEmail(emailEDT.getText().toString().trim()))
				{
					if (isOnline()) // connected
					{
						String uri = null;
						uri = String.format(ConstantValue.URL_FORGOT_PASSWORD);
						uri = uri.replace(" ", "%20");
						Log.i("LoginInfo", uri);
						if (uri != null) {
							uri = uri.replace(" ", "%20");

							List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>(
									2);
							nameValuePair.add(new BasicNameValuePair(
									"email", emailEDT.getText().toString().trim()));

							HttpHelper httpHelper = new HttpHelper(
									ConstantValue.REQUESTCODE_FORGOT_PASSWORD,
									Activity_ForgotPassword.this, "Loading...",
									nameValuePair);
							httpHelper.execute(uri);
						}

					} else {

						Toast.makeText(getApplicationContext(),
								"Internet Connection Fail", Toast.LENGTH_SHORT)
								.show();
					}
				}
				else
				{
					Toast.makeText(getApplicationContext(),
							"Enter Valid Email Address", Toast.LENGTH_SHORT)
							.show();
				}
			}
		});
	}
	public void setBackApiResponse(int requestcode, Object obj1, Object obj2) {

		if (requestcode == ConstantValue.REQUESTCODE_FORGOT_PASSWORD) {
			if (obj1.equals("1")) {
				Toast.makeText(context, obj2.toString(), Toast.LENGTH_SHORT).show();
				startActivity(new Intent(context, Activity_Login.class));
				finish();
			} else
				Toast.makeText(context, obj2.toString(), Toast.LENGTH_SHORT).show();
		}
	}

}

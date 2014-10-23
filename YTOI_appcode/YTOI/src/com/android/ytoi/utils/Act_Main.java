package com.android.ytoi.utils;

import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;


public class Act_Main extends Activity
{


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//setContentView(R.layout.welcome);

	}
	public void setBackApiResponse(int requestcode, Object obj1) {
	}

	public void setBackApiResponse(int requestcode, Object obj1, Object obj2) {
	}
	public void setBackApiResponse(int requestcode, Object obj1, Object obj2,Object obj3) {
	}
	public void setBackApiResponse(int requestcode, Object obj1, Object obj2,Object obj3,Object obj4) {
	}
	public boolean isOnline()
	{
		ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo netInfo = cm.getActiveNetworkInfo();
		if (netInfo != null && netInfo.isConnectedOrConnecting()&& cm.getActiveNetworkInfo().isAvailable()&& cm.getActiveNetworkInfo().isConnected()) 
		{
			return true;
		}
		return false;
	}
	public static void isGoogleAvailable(final Handler handler) {
		new Thread() {
			private boolean hasGoogleResponded = false;

			@Override
			public void run() {
				new Thread() {
					@Override
					public void run() {
						HttpGet requestForTest = new HttpGet(
								"https://www.google.com/");
						try {
							new DefaultHttpClient().execute(requestForTest);
							hasGoogleResponded = true;
						} catch (Exception e) {
						}
					}
				}.start();

				try {
					int waited = 0;
					while (!hasGoogleResponded && (waited < 60000)) {
						sleep(100);
						if (!hasGoogleResponded) {
							waited += 100;
						}
					}
				} catch (InterruptedException e) {
				} finally {
					if (hasGoogleResponded) {
						handler.sendEmptyMessage(1);
					} else {
						handler.sendEmptyMessage(0);
					}
				}
			}
		}.start();
	}

}

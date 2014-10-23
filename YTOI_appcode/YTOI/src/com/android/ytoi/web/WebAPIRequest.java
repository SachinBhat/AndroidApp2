package com.android.ytoi.web;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.ByteArrayBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

import android.os.Bundle;
import android.util.Log;

import com.android.ytoi.utils.ConstantValue;

public class WebAPIRequest {

	// Return's String
	@SuppressWarnings("unchecked")
	public String performPost_String(String apiUrl, Object bundle) {
		String doc = null;
		try {

			HttpParams httpParameters = new BasicHttpParams();
			int timeoutConnection = 60 * 1000 * 1;
			HttpConnectionParams.setConnectionTimeout(httpParameters,
					timeoutConnection);
			int timeoutSocket = 60 * 1000 * 1;
			HttpConnectionParams.setSoTimeout(httpParameters, timeoutSocket);

			HttpClient client = new DefaultHttpClient();
			HttpPost httppost = new HttpPost(apiUrl);

			// httppost.setHeader(ConstantValue.WEB_API_KEY,
			// ConstantValue.WEB_API_KEY_VALUE);
			// httppost.setHeader("","");
			httppost.setEntity(new UrlEncodedFormEntity(
					(List<NameValuePair>) bundle));
			HttpResponse res = client.execute(httppost);
			InputStream data = res.getEntity().getContent();

			StringBuilder stringBuilder = new StringBuilder();
			int b;
			while ((b = data.read()) != -1) {
				stringBuilder.append((char) b);
			}

			doc = stringBuilder.toString();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return doc;
	}
	public String performPost_ByteArray(String apiUrl,Bundle bundle,byte [] bry1,byte [] bry2,int requestNumber)
	{
		String doc = null;
		try {


			HttpClient client = new DefaultHttpClient();
			HttpPost httppost = new HttpPost(apiUrl);


			MultipartEntity entity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);

			if (requestNumber == ConstantValue.REQUESTCODE_FEED_ADD) {
				entity.addPart("user_id",new StringBody(bundle.getString("user_id")));
				entity.addPart("location_name",new StringBody(bundle.getString("location_name")));
				entity.addPart("category_id",new StringBody(bundle.getString("category_id")));
				entity.addPart("feed_name",new StringBody(bundle.getString("feed_name")));
				entity.addPart("experiance",new StringBody(bundle.getString("experiance")));
				entity.addPart("longitude",new StringBody(bundle.getString("longitude")));
				entity.addPart("lattitude",new StringBody(bundle.getString("lattitude")));
				entity.addPart("date_time",new StringBody(bundle.getString("date_time")));
				entity.addPart("device_id",new StringBody(bundle.getString("device_id")));

			
			}
			if (requestNumber == ConstantValue.REQUESTCODE_REGISTRATION) {
			
				entity.addPart("email",new StringBody(bundle.getString("email")));
				Log.e("email", bundle.getString("email"));
				entity.addPart("first_name",new StringBody(bundle.getString("first_name")));
				Log.e("first_name", bundle.getString("first_name"));
				entity.addPart("last_name",new StringBody(bundle.getString("last_name")));
				Log.e("last_name",bundle.getString("last_name"));
				entity.addPart("birth_date",new StringBody(bundle.getString("birth_date")));
				Log.e("birth_date", bundle.getString("birth_date"));
				entity.addPart("mobile_number",new StringBody(bundle.getString("mobile_number")));
				Log.e("mobile_number",bundle.getString("mobile_number"));
				entity.addPart("street",new StringBody(bundle.getString("street")));
				Log.e("street", bundle.getString("street"));
				entity.addPart("city",new StringBody(bundle.getString("city")));
				Log.e("city", bundle.getString("city"));
				entity.addPart("state",new StringBody(bundle.getString("state")));
				Log.e("state", bundle.getString("state"));
				entity.addPart("zipcode",new StringBody(bundle.getString("zipcode")));
				Log.e("zipcode", bundle.getString("zipcode"));
				entity.addPart("country",new StringBody(bundle.getString("country")));
				Log.e("country",bundle.getString("country"));
				entity.addPart("gender",new StringBody(bundle.getString("gender")));
				Log.e("gender", bundle.getString("gender"));
				entity.addPart("fb_userid",new StringBody(bundle.getString("fb_userid")));
				Log.e("fb_userid",bundle.getString("fb_userid"));
				entity.addPart("gmail_userid",new StringBody(bundle.getString("gmail_userid")));
				Log.e("gmail_userid",bundle.getString("gmail_userid"));
				entity.addPart("password",new StringBody(bundle.getString("password")));
				Log.e("password",bundle.getString("password"));
				
			}
			long time= System.currentTimeMillis();

			if(bry1!=null && bry2!=null)
			{
				entity.addPart("user_image", new ByteArrayBody(bry1, "sun"+time+".jpg"));
				entity.addPart("emoticon_img", new ByteArrayBody(bry2, "sunj"+time+".jpg"));
				//httppost.setEntity(entity);

			}

			else if(bry2!=null)
			{
				entity.addPart("emoticon_img", new ByteArrayBody(bry2, "sunj.jpg"));
			//	httppost.setEntity(entity);
			}
			else if(bry1!=null)
			{
				entity.addPart("image", new ByteArrayBody(bry1, "sun.jpg"));
				
				//httppost.setEntity(entity);
			}
			else
			{
				Log.e("image", "null");
			}

			httppost.setEntity(entity);


			HttpResponse res = client.execute(httppost);
			InputStream data = res.getEntity().getContent();



			StringBuilder stringBuilder = new StringBuilder();
			int b;
			while ((b = data.read()) != -1)
			{
				stringBuilder.append((char) b);
			}

			doc = stringBuilder.toString();

		} catch (Exception e) {
			e.printStackTrace();
		} 
		return doc;
	}

	// Return's String

	public String performGet(String url) {
		String doc = null;
		try {
			HttpParams httpParameters = new BasicHttpParams();
			int timeoutConnection = 60 * 1000 * 1;
			HttpConnectionParams.setConnectionTimeout(httpParameters,
					timeoutConnection);
			int timeoutSocket = 60 * 1000 * 1;
			HttpConnectionParams.setSoTimeout(httpParameters, timeoutSocket);

			DefaultHttpClient client = new DefaultHttpClient();
			URI uri = new URI(url);
			HttpGet method = new HttpGet(uri);
			HttpResponse res = client.execute(method);
			InputStream data = res.getEntity().getContent();
			doc = convertStreamToString(data);
		} catch (ClientProtocolException e) {

			// e.printStackTrace();
		} catch (IOException e) {

			e.printStackTrace();
		} catch (URISyntaxException e) {

			// e.printStackTrace();
		}
		return doc;
	}

	public static String convertStreamToString(InputStream is)
			throws IOException {
		if (is != null) {
			StringBuilder sb = new StringBuilder();
			String line;
			try {
				BufferedReader reader = new BufferedReader(
						new InputStreamReader(is, "UTF-8"));
				while ((line = reader.readLine()) != null) {
					sb.append(line).append("\n");
				}
			} catch (Exception e) {

				e.printStackTrace();
			} finally {
				is.close();
			}
			return sb.toString();
		} else {
			return "false";
		}
	}
}

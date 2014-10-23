package com.sun.googleplace;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONObject;

import android.os.AsyncTask;
import android.util.Log;

import com.android.ytoi.utils.Act_Fragment;


public class HttpGooglePlace 
{

	private static final String SunGooglePlaceUrl="https://maps.googleapis.com/maps/api/place/nearbysearch/json?";
	static HttpURLConnection sunUrlConnection = null;
	private ArrayList<SunGooglePlaceData> aryLst_GooglePlaceDatas=new ArrayList<SunGooglePlaceData>();
	private String sunGoogleApiKey = null;
	private String sunGooglePlaceResponse = null;

	private Act_Fragment sunActivity = null;
	private double sunLatitude = 0;
	private double sunLongitude = 0;
	private String sunSensorType =null;
	private String sunFindTypes=null;
	private int sunRadius=0;

	public HttpGooglePlace(Act_Fragment sunActivity,String sunGoogleApiKey,double sunLatitude,double sunLongitude,String sunSensorType,String sunFindTypes,int sunRadius) 
	{

		this.sunActivity=sunActivity;
		this.sunGoogleApiKey=sunGoogleApiKey;
		this.sunLatitude=sunLatitude;
		this.sunLongitude=sunLongitude;
		this.sunSensorType=sunSensorType;
		this.sunRadius=sunRadius;
		this.sunFindTypes=sunFindTypes;

		Log.e("Calling Intent", "HttpGooglePlace");
		(new SunGooglePlacesTask()).execute(getGooglePlaceApiReuest());
	}

	public  void cancelRequest()
	{
		if (sunUrlConnection!=null) {
			sunUrlConnection.disconnect();
			sunUrlConnection=null;
		}

	}
	private String getGooglePlaceApiReuest() 
	{

		StringBuilder sunStringBuilder = new StringBuilder(SunGooglePlaceUrl);

		sunStringBuilder.append("location="+sunLatitude+","+sunLongitude);
		sunStringBuilder.append("&radius="+sunRadius);
		sunStringBuilder.append("&types="+sunFindTypes);
		sunStringBuilder.append("&sensor="+sunSensorType);
		sunStringBuilder.append("&key="+sunGoogleApiKey);

		return sunStringBuilder.toString();

	}


	private class SunGooglePlacesTask extends AsyncTask<String, Integer, String>{



		@Override
		protected String doInBackground(String... requesturl) {

			Log.e("Calling Intent", "SunGooglePlacesTask");
			Log.e("Request Nearest", requesturl[0]);
			InputStream sunIStream = null;
			URL sunGooglePlaceRequest = null;
			try
			{
				sunGooglePlaceRequest=new URL(requesturl[0]);

				sunUrlConnection = (HttpURLConnection) sunGooglePlaceRequest.openConnection();                

				sunUrlConnection.connect();                

				sunIStream = sunUrlConnection.getInputStream();

				BufferedReader br = new BufferedReader(new InputStreamReader(sunIStream));

				StringBuffer sunStringbuffer  = new StringBuffer();

				String sunLine = "";

				while( ( sunLine = br.readLine())  != null)
				{
					sunStringbuffer.append(sunLine);
				}

				sunGooglePlaceResponse = sunStringbuffer.toString();

				br.close();

			}
			catch(Exception e)
			{
				Log.d("Exception while downloading url", e.toString());
			}
			finally
			{
				try
				{
					if (sunIStream!=null) {
						sunIStream.close();
					}
					if (sunUrlConnection!=null) {
						sunUrlConnection.disconnect();
					}

				} 
				catch (IOException e) 
				{
					e.printStackTrace();
				}

			}
			return sunGooglePlaceResponse;
		}

		@Override
		protected void onPostExecute(String result)
		{			

			SunGooglePlacesParserTask parserTask = new SunGooglePlacesParserTask();

			parserTask.execute(result);

		}

	}

	private class SunGooglePlacesParserTask extends AsyncTask<String, Integer, List<HashMap<String,String>>>{

		JSONObject sunJObject=null;

		@Override
		protected List<HashMap<String,String>> doInBackground(String... sunJsonData) {

			List<HashMap<String, String>> sunListPlaces = null;			
			SunGooglePlaceJSONParser placeJsonParser = new SunGooglePlaceJSONParser();

			try{
				sunJObject = new JSONObject(sunJsonData[0]);

				sunListPlaces = placeJsonParser.parse(sunJObject);

			}catch(Exception e){
				Log.d("Exception",e.toString());
			}
			return sunListPlaces;
		}

		@Override
		protected void onPostExecute(List<HashMap<String,String>> list){			
			if (list!=null) {
				for(int i=0;i<list.size();i++){

					HashMap<String, String> sunMapPlace = list.get(i);

					aryLst_GooglePlaceDatas.add(new  SunGooglePlaceData(Double.parseDouble(sunMapPlace.get("lat")), Double.parseDouble(sunMapPlace.get("lng")), sunMapPlace.get("place_name"), sunMapPlace.get("vicinity")));
					Log.e("Name aryLst_GooglePlaceDatas", aryLst_GooglePlaceDatas.get(i).getName()+" Sun Name");
				}		
			}

			sunActivity.setBackApiResponse(aryLst_GooglePlaceDatas);
		}

	}
}

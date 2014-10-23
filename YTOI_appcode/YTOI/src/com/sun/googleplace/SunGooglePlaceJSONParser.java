package com.sun.googleplace;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class SunGooglePlaceJSONParser 
{
	
	public List<HashMap<String,String>> parse(JSONObject sunJObject)
	{		
		
		JSONArray sunJPlaces = null;
		try 
		{			
			sunJPlaces = sunJObject.getJSONArray("results");
		}
		catch (JSONException e) 
		{
			e.printStackTrace();
		}
		return getPlaces(sunJPlaces);
	}
	
	
	private List<HashMap<String, String>> getPlaces(JSONArray sunJPlaces)
	{
		
		int sunPlacesCount = sunJPlaces.length();
		List<HashMap<String, String>> sunPlacesList = new ArrayList<HashMap<String,String>>();
		HashMap<String, String> sunPlace = null;	

		for(int i=0; i<sunPlacesCount;i++)
		{
			try {
				sunPlace = getPlace((JSONObject)sunJPlaces.get(i));
				sunPlacesList.add(sunPlace);

			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		
		return sunPlacesList;
	}
	
	private HashMap<String, String> getPlace(JSONObject sunJPlace){

		HashMap<String, String> sunPlace = new HashMap<String, String>();
		String placeName = "-NA-";
		String vicinity="-NA-";
		String latitude="";
		String longitude="";
				
		
		try {
			if(!sunJPlace.isNull("name"))
			{
				placeName = sunJPlace.getString("name");
			}
			if(!sunJPlace.isNull("vicinity"))
			{
				vicinity = sunJPlace.getString("vicinity");
			}	
			
			latitude = sunJPlace.getJSONObject("geometry").getJSONObject("location").getString("lat");
			longitude = sunJPlace.getJSONObject("geometry").getJSONObject("location").getString("lng");			
			
			sunPlace.put("place_name", placeName);
			sunPlace.put("vicinity", vicinity);
			sunPlace.put("lat", latitude);
			sunPlace.put("lng", longitude);
			
			
		} 
		catch (JSONException e)
		{			
			e.printStackTrace();
		}		
		return sunPlace;
	}
}

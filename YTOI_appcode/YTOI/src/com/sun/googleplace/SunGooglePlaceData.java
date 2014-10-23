package com.sun.googleplace;


public class SunGooglePlaceData 
{
	public double sunLatitude =0;	            
	public double sunLonglatitude =0;
	public String sunName = null;
	public String  sunVicinity = null;

	public SunGooglePlaceData(double sunLatitude,double sunLonglatitude,String sunName,String sunVicinity)
	{

		this.sunLatitude=sunLatitude;
		this.sunLonglatitude=sunLonglatitude;
		this.sunName=sunName;
		this.sunVicinity=sunVicinity;

	}




	public double getLatitude() {
		return sunLatitude;

	}

	public void setLatitude(long sunLatitude) {
		this.sunLatitude = sunLatitude;

	}
	public double getLonglatitude() {
		return sunLonglatitude;

	}

	public void setLonglatitude(long sunLonglatitude) {
		this.sunLonglatitude = sunLonglatitude;

	}
	public String getName() {
		return sunName;
	}

	public void setName(String sunName) {
		this.sunName = sunName;

	}

	public String getVicinity() {
		return sunVicinity;

	}

	public void setVicinity(String sunVicinity) {
		this.sunVicinity = sunVicinity;

	}



}

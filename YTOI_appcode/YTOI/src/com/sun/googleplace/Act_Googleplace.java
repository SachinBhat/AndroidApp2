package com.sun.googleplace;

import android.app.Activity;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;


public class Act_Googleplace extends Activity implements LocationListener{

private double sunLongitude;
private double sunLatitude;

	//	private ArrayList<SunGooglePlaceData> aryLst_GooglePlaceDatas=new ArrayList<SunGooglePlaceData>();
//
//	private Spinner sunSprPlaceType;	
//
//	private ArrayAdapter<String> sunPlaceAdapter=null;
//
//	private String[] sunPlaceType=null;
//	private String[] sunPlaceTypeName=null;
//
//	private double sunLatitude=0;
//	private double sunLongitude=0;
//
//	private int sunRadius=145000;
//	private String sensorType="true";
//
//	public void onBackgroundResponse(ArrayList<SunGooglePlaceData> aryLst_GooglePlaceDatas)
//	{
//		this.aryLst_GooglePlaceDatas=aryLst_GooglePlaceDatas;
//
//		ListPlacesAdapter customAdapter = new ListPlacesAdapter(); 
//		ListView list =(ListView) findViewById(R.id.googleplace_list_places);
//		list.setAdapter(customAdapter);
//		
//	}
//	
//
//       
//	@Override
//	protected void onCreate(Bundle savedInstanceState) {
//		super.onCreate(savedInstanceState);
//
//		setContentView(R.layout.act__googleplace);
//
//		sunPlaceType = getResources().getStringArray(R.array.sunplace_type);
//
//		sunPlaceTypeName = getResources().getStringArray(R.array.sunplace_type_name);
//
//		sunPlaceAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, sunPlaceTypeName);
//
//		sunSprPlaceType = (Spinner) findViewById(R.id.googleplace_spr_placetype);
//
//		sunSprPlaceType.setAdapter(sunPlaceAdapter);
//
//		 
//		
//		
//		
//		Button btn=(Button) findViewById(R.id.googleplace_btn_find);
//		btn.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//				new HttpGooglePlace(Act_Googleplace.this, "AIzaSyDsCIERxSBDQqekAVXYgyoqVemA1lJWu44",sunLatitude, sunLongitude, sensorType,sunPlaceType[sunSprPlaceType.getSelectedItemPosition()], sunRadius);
//
//			}
//		});
//
//		findCurrentLattitudeLong();
//
//	}
//
//	class ListPlacesAdapter extends BaseAdapter {
//
//		@Override
//		public int getCount() {
//			return aryLst_GooglePlaceDatas.size();
//		}
//
//		@Override
//		public Object getItem(int position) {
//			return null;
//		}
//
//		@Override
//		public long getItemId(int position) {
//			return 0;
//		}
//
//		@Override
//		public View getView(int position, View convertView, ViewGroup parent) {
//
//			if (convertView == null) {
//
//				LayoutInflater imfltList;
//				imfltList = LayoutInflater.from(getApplicationContext());
//				convertView = imfltList.inflate(R.layout.lyt_listitem_place, null);
//
//			}
//
//			SunGooglePlaceData sungooglePlaceData = aryLst_GooglePlaceDatas.get(position);
//
//			if (sungooglePlaceData != null) {
//
//				TextView txt_Name = (TextView) convertView.findViewById(R.id.lyt_listitem_name);
//				TextView txt_Vcity = (TextView) convertView.findViewById(R.id.lyt_listitem_vcity);
//				TextView txt_latitude = (TextView) convertView.findViewById(R.id.lyt_listitem_latitude);
//				TextView txt_longitude = (TextView) convertView.findViewById(R.id.lyt_listitem_longlatitude);
//				
//				txt_Name.setText(sungooglePlaceData.getName());
//				txt_Vcity.setText(sungooglePlaceData.getVicinity());
//				txt_latitude.setText(sungooglePlaceData.getLatitude()+"");
//				txt_longitude.setText(sungooglePlaceData.getLonglatitude()+"");
//			}
//
//
//
//			return convertView;
//
//		}
//	}
//
//
//
//
	private void findCurrentLattitudeLong() 
	{

		LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

		Criteria criteria = new Criteria();

		String provider = locationManager.getBestProvider(criteria, true);

		Location location = locationManager.getLastKnownLocation(provider);

		if(location!=null)
		{
			onLocationChanged(location);
		}

		locationManager.requestLocationUpdates(provider, 20000, 0, this);
	}

	@Override
	public void onLocationChanged(Location location) {
		sunLatitude = location.getLatitude();
		sunLongitude = location.getLongitude();
		
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onProviderDisabled(String provider) {
		// TODO Auto-generated method stub
		
	}
}

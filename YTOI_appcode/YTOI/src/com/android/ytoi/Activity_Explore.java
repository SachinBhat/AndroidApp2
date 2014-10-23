package com.android.ytoi;

import java.io.File;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.TreeSet;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.ByteArrayBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.telephony.TelephonyManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.ytoi.imageloader.ImageLoader;
import com.android.ytoi.utils.Act_Main;
import com.android.ytoi.utils.ConstantValue;
import com.android.ytoi.utils.GPSTracker;
import com.android.ytoi.utils.ReUseMethod;
import com.android.ytoi.utils.SessionManager;
import com.android.ytoi.utils.Utills;
import com.android.ytoi.web.HttpHelper;
import com.android.ytoi.webclass.Category;
import com.android.ytoi.webclass.Category_Selected;
import com.android.ytoi.webclass.FeedInfo;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.slidingmenu.lib.SlidingMenu;

public class Activity_Explore extends Act_Main implements OnClickListener  {


	public SlidingMenu menu;
	private ListView mDrawerList;
	private ImageButton btn_Search;
	private TextView name, location, headeTXT;
	private MenuSliderAdapter menuSliderAdapter;
	private  ImageView homeIMG;
	private ImageView img_logoMain;
	private ImageView img_top_search;
	private  ImageView img_top_notification;
	private ImageView captureIMG;
	private ImageView img_slider_UserPhoto;
	private ImageView img_bottom_reco;
	private ImageView img_bottom_rate;
	private ImageView img_bottom_explore;
	private LinearLayout lin_bottom_bg;
	private Typeface type_BOLD, type_BOOK;
	private Context context = this;
	private View v;
	private ImageLoader imgloader=new ImageLoader(this,false);
	private static final int CAMERA_CAPTURE_IMAGE_REQUEST_CODE = 100;
	public static final int MEDIA_TYPE_IMAGE = 1;
	private static final int SELECT_PICTURE = 3;
	private static Uri fileUri;
	private static final String IMAGE_DIRECTORY_NAME = "YTOI";
	static ProgressDialog dialog = null;
	public static AutoCompleteTextView txtedt_Search;
	private ScrollView scroll_mapCategoryFeed;
	private FrameLayout scroll_map_transfernt;
	private GoogleMap googleMap;
	private LinearLayout lin_scrollHoriScrollCategory,lin_scrollFeed;
	private int id_slidepos;
	private HashMap<Integer, Category_Selected> hasmap_CategorySelect=new HashMap<Integer, Category_Selected>();
	private ArrayList<FeedInfo> arraylist_FeedInfo=new ArrayList<FeedInfo>();
	private ReUseMethod reusemethod;
	public static boolean GPS_ON=false;
	public static  ArrayList<Category> static_arraylist_Category_explorer =new ArrayList<Category> ();

	private int [] drwable_categoryId_unselected={R.drawable.explorer_category_books,R.drawable.explorer_category_food,R.drawable.explorer_category_lifestyle,R.drawable.explorer_category_movie,R.drawable.explorer_category_place};

	private int [] drwable_categoryId_selected={R.drawable.explorer_category_books_sel,R.drawable.explorer_category_food_sel,R.drawable.explorer_category_lifestyle_sel,R.drawable.explorer_category_movie_sel,R.drawable.explorer_category_place_sel};


	@SuppressWarnings("unchecked")
	public void setBackApiResponse(int requestcode, Object obj1, Object obj2,Object obj3) {
		System.gc();
		static_arraylist_Category_explorer=(ArrayList<Category>) obj3;

		if (requestcode == ConstantValue.REQUESTCODE_CATEGORY_EXPLORER_VIEW) 
		{

			if(obj1!=null)
			{

				setUIAfterCategory();
			}

		}
	}
	@SuppressWarnings("unchecked")
	private void setUIAfterCategory() {
		@SuppressWarnings("rawtypes")
		List adpter_autocomplete_list=new ArrayList<String>();

		for (int i = 0; i < Activity_Home.static_arraylist_FeedInfo.size(); i++) {

			adpter_autocomplete_list.add(Activity_Home.static_arraylist_FeedInfo.get(i).iteam_name);
		}
		LocationAutoArrayAdapter adpter_autocomplete = null;
		if (adpter_autocomplete_list!=null) 				
			if (adpter_autocomplete_list.size()>0) {

				if (adpter_autocomplete_list!=null) 				
					if (adpter_autocomplete_list.size()>0) {

						adpter_autocomplete = new LocationAutoArrayAdapter(context,adpter_autocomplete_list);
					}
			}

		//adpter_autocomplete=new AutoCompleteAdapter(adpter_autocomplete);
		if (adpter_autocomplete!=null) {
			txtedt_Search.setAdapter(adpter_autocomplete);
			txtedt_Search.invalidate();
			adpter_autocomplete.notifyDataSetChanged();
		}
		txtedt_Search.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {

				if (s.toString().equalsIgnoreCase("")) {
					setFeedOnUI(arraylist_FeedInfo);
					if (googleMap!=null) {
						googleMap.clear();
						try
						{
							initilizeMap1(arraylist_FeedInfo);
						} 
						catch (Exception e)
						{
							e.printStackTrace();
						}
					}
				}
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {

			}

			@Override
			public void afterTextChanged(Editable s) {

			}
		});


		btn_Search.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) 
			{
				ArrayList<FeedInfo> temp_arraylist=new ArrayList<FeedInfo>();

				ArrayList<String> strNames= new ArrayList<String>();

				for (int j = 0; j < hasmap_CategorySelect.size(); j++) 
				{
					if ( hasmap_CategorySelect.get(j).flag_selected)
					{
						strNames.add(hasmap_CategorySelect.get(j).category_name);
					}

				}


				if (strNames.size()==0) 
				{

					lin_scrollFeed.removeAllViews();

					for (int j = 0; j < arraylist_FeedInfo.size(); j++) 
					{
						String strfeedname2=arraylist_FeedInfo.get(j).iteam_name.toLowerCase();
						Log.e("edt_Search.getText().toString().toLowerCase()", txtedt_Search.getText().toString().toLowerCase());
						Log.e("strname2",strfeedname2);
						if (txtedt_Search.getText().toString().toLowerCase().equalsIgnoreCase(strfeedname2)) {

							temp_arraylist.add(arraylist_FeedInfo.get(j));
						}


					}
					setFeedOnUI(temp_arraylist);
					if (googleMap!=null) {
						googleMap.clear();
						try
						{
							initilizeMap1(temp_arraylist);
						} 
						catch (Exception e)
						{
							e.printStackTrace();
						}
					}

				}
				else
				{
					for (int j = 0; j < arraylist_FeedInfo.size(); j++) 
					{
						String strname2=arraylist_FeedInfo.get(j).category_name.toLowerCase();
						String strfeedname2=arraylist_FeedInfo.get(j).iteam_name.toLowerCase();

						if (strNames.contains(strname2)) {
							Log.e("edt_Search.getText().toString().toLowerCase()", txtedt_Search.getText().toString().toLowerCase());
							Log.e("strname2",strname2);

							if (txtedt_Search.getText().toString().toLowerCase().equalsIgnoreCase(strfeedname2)) {

								temp_arraylist.add(arraylist_FeedInfo.get(j));
							}

						}

					}

					lin_scrollFeed.removeAllViews();

					setFeedOnUI(temp_arraylist);

					if (googleMap!=null) {
						googleMap.clear();
						try
						{
							initilizeMap1(temp_arraylist);
						} 
						catch (Exception e)
						{
							e.printStackTrace();
						}
					}

				}

			}
		});

		for (int i = 0; i < Activity_Home.static_arraylist_FeedInfo.size(); i++) {

			if (Activity_Home.static_arraylist_FeedInfo.get(i).feed_image_BitmapDescriptor==null)
			{
				if (Activity_Home.static_arraylist_FeedInfo.get(i).feed_image_bitmap_thumb!=null) 
				{


					Bitmap  change = Bitmap.createScaledBitmap(Activity_Home.static_arraylist_FeedInfo.get(i).feed_image_bitmap_thumb, 76, 76, false);

					//					Bitmap border = BitmapFactory.decodeResource(getResources(),R.drawable.explor_imagebordar);
					//
					//					int width = border.getWidth();
					//
					//					int height = border.getHeight();
					//
					//					Canvas canvas = new Canvas(border);

					//Bitmap scaledBorder = Bitmap.createScaledBitmap(border,border.getWidth(),border.getHeight(), false);

					//canvas.drawBitmap(change,5,5,null);
					Activity_Home.static_arraylist_FeedInfo.get(i).feed_image_BitmapDescriptor=BitmapDescriptorFactory.fromBitmap(change);
				}
			}
		}
		arraylist_FeedInfo.addAll(Activity_Home.static_arraylist_FeedInfo);

		LinearLayout.LayoutParams lin_categoryParams=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT);
		lin_categoryParams.weight=1;

		LinearLayout.LayoutParams img_categoryparms = new LinearLayout.LayoutParams(
				LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);

		setFeedOnUI(arraylist_FeedInfo);

		try
		{
			initilizeMap(arraylist_FeedInfo);
		} 
		catch (Exception e)
		{
			e.printStackTrace();
		}
		lin_scrollHoriScrollCategory.setWeightSum(5);
		for (int i = 0; i < 5; i++)
		{

			lin_scrollHoriScrollCategory.setPadding(0, 0, 3, 0);


			LinearLayout lin_category=new LinearLayout(this);
			lin_category.setGravity(Gravity.CENTER);
			lin_category.setLayoutParams(lin_categoryParams);
			lin_category.setPadding(3, 0, 0, 0);

			final ImageView img_category = new ImageView(context);
			img_category.setId(i);
			img_category.setTag("false");
			img_category.setBackgroundResource(drwable_categoryId_unselected[i]);
			img_category.setLayoutParams(img_categoryparms);

			Category_Selected cat_sel=new Category_Selected();

			cat_sel.category_id=static_arraylist_Category_explorer.get(i).category_id;
			cat_sel.category_name=static_arraylist_Category_explorer.get(i).category_name;
			cat_sel.flag_selected=false;

			hasmap_CategorySelect.put(i,cat_sel);

			lin_category.addView(img_category);
			lin_scrollHoriScrollCategory.addView(lin_category);

			img_category.setOnClickListener(new OnClickListener() 
			{
				@Override
				public void onClick(View arg0) 
				{
					int position=arg0.getId();

					if (arg0.getTag().toString().equalsIgnoreCase("false")) 
					{
						arg0.setTag("true");

						hasmap_CategorySelect.get(position).flag_selected=true;

						img_category.setBackgroundResource(drwable_categoryId_selected[position]);
					}

					else
					{

						arg0.setTag("false");

						hasmap_CategorySelect.get(position).flag_selected=false;

						img_category.setBackgroundResource(drwable_categoryId_unselected[position]);

					}

					ArrayList<FeedInfo> temp_arraylist=new ArrayList<FeedInfo>();
					ArrayList<String> strNames= new ArrayList<String>();

					for (int j = 0; j < hasmap_CategorySelect.size(); j++) 
					{
						if ( hasmap_CategorySelect.get(j).flag_selected) {
							strNames.add(hasmap_CategorySelect.get(j).category_name);
						}

					}

					if (strNames.size()==0) 
					{
						lin_scrollFeed.removeAllViews();

						setFeedOnUI(arraylist_FeedInfo);
						if (googleMap!=null) {
							googleMap.clear();
							try
							{
								initilizeMap1(arraylist_FeedInfo);
							} 
							catch (Exception e)
							{
								e.printStackTrace();
							}
						}

					}
					else
					{
						for (int j = 0; j < arraylist_FeedInfo.size(); j++) 
						{
							String strname2=arraylist_FeedInfo.get(j).category_name;

							if (strNames.contains(strname2)) {
								temp_arraylist.add(arraylist_FeedInfo.get(j));
							}

						}

						lin_scrollFeed.removeAllViews();

						setFeedOnUI(temp_arraylist);

						if (googleMap!=null) {
							googleMap.clear();
							try
							{
								initilizeMap1(temp_arraylist);
							} 
							catch (Exception e)
							{
								e.printStackTrace();
							}
						}

					}

				}

			});

		}

		System.gc();

	}
	public class LocationAutoArrayAdapter extends ArrayAdapter<String> {
		private final Context context;
		private final List values;

		public LocationAutoArrayAdapter(Context context, List values) {
			super(context, R.layout.lyt_addfeed_locationauto, values);
			this.context = context;
			this.values = values;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			View rowView = inflater.inflate(R.layout.lyt_addfeed_locationauto, parent, false);
			TextView txt_autosearchdrop = (TextView) rowView.findViewById(R.id.lytaddfeed_txt_autosearch);
			txt_autosearchdrop.setText(values.get(position)+"");


			return rowView;
		}
	} 
	private class ViewHolder_Feed {
		ImageView img_feedimage;
		TextView txt_feedname;
	}
	private void setFeedOnUI(ArrayList<FeedInfo> arraylist_FeedInfo2) {




		type_BOOK = Typeface.createFromAsset(context.getAssets(),
				"fonts/Novecentowide-Book.otf");
		type_BOLD = Typeface.createFromAsset(context.getAssets(),
				"fonts/Novecentowide-Bold.otf");

		for (int i = 0; i < arraylist_FeedInfo2.size(); i++) 
		{
			ViewHolder_Feed holder_feed = null;

			final FeedInfo feed_info = arraylist_FeedInfo2.get(i);

			LayoutInflater mInflater = (LayoutInflater) context
					.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

			View View_feedInflet = mInflater.inflate(R.layout.lyt_feedcategory_row, null);

			holder_feed = new ViewHolder_Feed();

			holder_feed.txt_feedname = (TextView) View_feedInflet
					.findViewById(R.id.lyt_feedcategory_row_txt_name);

			holder_feed.img_feedimage = (ImageView) View_feedInflet
					.findViewById(R.id.lyt_feedcategory_row_img_image);


			holder_feed.txt_feedname.setTypeface(type_BOLD);
			holder_feed.txt_feedname.setText(feed_info.iteam_name);
			Log.e("feed_info.iteam_name", feed_info.iteam_name);

			ImageView view = new ImageView(context);
			view.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
					LayoutParams.MATCH_PARENT));
			view.setScaleType(ScaleType.FIT_XY);

			imgloader.DisplayImage(feed_info.feed_image_thumb,holder_feed.img_feedimage);

			View_feedInflet.setTag(holder_feed);

			lin_scrollFeed.addView(View_feedInflet);
		}

	}

	@Override
	protected void onPause() {
		Activity_Home.static_explorer_resume=false;
		super.onPause();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		if (android.os.Build.VERSION.SDK_INT > 9) {
			StrictMode.ThreadPolicy policy = 
					new StrictMode.ThreadPolicy.Builder().permitAll().build();      
			StrictMode.setThreadPolicy(policy);
		}

		GPS_ON=false;
		reusemethod=new ReUseMethod();


		setContentView(R.layout.explore_main);

		findIdFromXml();

		initilization();

		menuSliderSet();

		setOnClickLitinerView();

		setOnTouchLitinerView();

		//	static_arraylist_Category_explorer.addAll(Fragment_Category.static_arraylist_Category);

		gpsTrackerShow();




	}

	private void gpsTrackerShow()
	{
		if (isOnline()) 
		{
			GPSTracker gps = new GPSTracker(this);
			if(gps.canGetLocation())
			{
				int status = GooglePlayServicesUtil.isGooglePlayServicesAvailable(getApplicationContext());
				if(status == ConnectionResult.SUCCESS) 
				{
					Utills.CURRENT_LATTITUDE=gps.getLatitude();
					Utills.CURRENT_LONGITUDE=gps.getLongitude();


				}
				else
				{

					Utills.CURRENT_LATTITUDE=0.0;
					Utills.CURRENT_LONGITUDE=0.0;
				}

				Log.i("Utills.CURRENT_LATTITUDE", Utills.CURRENT_LATTITUDE+" lattitude");
				Log.i("Utills.CURRENT_LONGITUDE", Utills.CURRENT_LONGITUDE+" longlattitude");
				GPS_ON=true;

				//				if (static_arraylist_Category_explorer!=null) 
				//					if (static_arraylist_Category_explorer.size()>0) 
				//					{
				//						setUIAfterCategory();
				//					}
				//					else
				//						callapiCategory();
				//				else
				callapiCategory();
			}
			else
			{
				GPS_ON=false;
				gps.showSettingsAlert();
			}


		}
		else
		{
			GPS_ON=true;
			reusemethod.displayAlertDialog("EXPLORER","       "+ getResources().getString(R.string.request),
					getResources().getString(R.string.check_internet),getResources().getString(R.string.ok), null, 0,Activity_Explore.this);

		}

	}
	private void setOnTouchLitinerView() {

		scroll_map_transfernt.setOnTouchListener(new View.OnTouchListener() {


			@Override
			public boolean onTouch(View v, MotionEvent event) {

				int action = event.getAction();
				switch (action) {
				case MotionEvent.ACTION_DOWN:
					// Disallow ScrollView to intercept touch events.
					scroll_mapCategoryFeed.requestDisallowInterceptTouchEvent(true);
					// Disable touch on transparent view
					return false;

				case MotionEvent.ACTION_UP:
					// Allow ScrollView to intercept touch events.
					scroll_mapCategoryFeed.requestDisallowInterceptTouchEvent(false);
					return true;

				case MotionEvent.ACTION_MOVE:
					scroll_mapCategoryFeed.requestDisallowInterceptTouchEvent(true);
					return false;

				default: 
					return true;
				}   
			}
		});
	}
	private void setOnClickLitinerView() {
		homeIMG.setOnClickListener(this);
		img_logoMain.setOnClickListener(this);
		img_top_search.setOnClickListener(this);
		img_top_notification.setOnClickListener(this);
		img_bottom_reco.setOnClickListener(this);
		img_bottom_explore.setOnClickListener(this);
		img_bottom_rate.setOnClickListener(this);
	}

	private void menuSliderSet() 
	{

		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		v = inflater.inflate(R.layout.slidingmenu_main, null, true);

		mDrawerList = (ListView) v.findViewById(R.id.slidingmenu_Listview_list);

		name = (TextView) v.findViewById(R.id.slidingmenu_TextView_Username);

		location = (TextView) v
				.findViewById(R.id.slidingmenu_TextView_location);

		name.setText(ConstantValue.user.get(SessionManager.KEY_FIRSTNAME) + " "
				+ ConstantValue.user.get(SessionManager.KEY_LASTNAME));

		location.setText(ConstantValue.user.get(SessionManager.KEY_CITY));

		captureIMG = (ImageView) v
				.findViewById(R.id.slidingmenu_Imageview_cameracapture);

		img_slider_UserPhoto = (ImageView) v
				.findViewById(R.id.slidingmenu_Imageview_UserImage);
		img_slider_UserPhoto.setScaleType(ScaleType.FIT_XY);

		captureIMG.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (isDeviceSupportCamera()) {
					calldialog();

				}
			}
		});

		menu.setMode(SlidingMenu.LEFT);
		menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
		menu.setShadowWidthRes(R.dimen.shadow_width);

		menu.setBehindOffsetRes(R.dimen.slidingmenu_offset);
		menu.setFadeDegree(0.35f);
		menu.attachToActivity(this, SlidingMenu.SLIDING_CONTENT);
		menu.setMenu(v);
		menu.setSlidingEnabled(true);
		mDrawerList.setAdapter(menuSliderAdapter);

		mDrawerList.setOnItemClickListener(new DrawerItemClickListener());

		if (!ConstantValue.user.get(SessionManager.KEY_PROFILE_IMG).equals("")) {

			ImageView view = new ImageView(context);
			view.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
					LayoutParams.MATCH_PARENT));
			view.setScaleType(ScaleType.FIT_XY);

			imgloader.DisplayImage(ConstantValue.user.get(SessionManager.KEY_PROFILE_IMG),
					img_slider_UserPhoto);
		}
		else
		{
			img_slider_UserPhoto.setScaleType(ScaleType.FIT_XY);
			imgloader.DisplayImage(
					HttpHelper.static_profile_picture,
					img_slider_UserPhoto);
		}

	}

	private void callapiCategory() {

		if (isOnline()) // connected
		{
			String uri = null;
			uri = String.format(ConstantValue.URL_CATEGORY_VIEW);
			if (uri != null) {
				uri = uri.replace(" ", "%20");
				List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>(
						1);
				TelephonyManager telephonyManager = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);

				//				nameValuePair.add(new BasicNameValuePair("user_id",
				//						ConstantValue.user.get(SessionManager.KEY_USERID)));
				nameValuePair.add(new BasicNameValuePair("device_id",telephonyManager.getDeviceId()+""));
				Log.e("device_id Category View", telephonyManager.getDeviceId()+"");
				HttpHelper httpHelper = new HttpHelper(
						ConstantValue.REQUESTCODE_CATEGORY_EXPLORER_VIEW,
						this, "Loading...",
						nameValuePair);
				httpHelper.execute(uri);
			}

		} 
	}
	private void initilizeMap(ArrayList<FeedInfo> arry) {

		FrameLayout frm_maplayout=(FrameLayout) findViewById(R.id.explorer_framelay_fragmentmap);

		DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		int device_width = dm.widthPixels;
		int device_height = dm.heightPixels/2;

		LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams((int)device_width, (int)device_height);
		frm_maplayout.setLayoutParams(lp);

		if (googleMap == null)
		{

			googleMap = ((MapFragment) getFragmentManager().findFragmentById(
					R.id.explorer_fragment_map)).getMap();

			googleMap.getUiSettings().setZoomControlsEnabled(true);
			googleMap.setMyLocationEnabled(true);
			CameraPosition cameraPosition = new CameraPosition.Builder()
			.target(new LatLng(Utills.CURRENT_LATTITUDE, Utills.CURRENT_LONGITUDE))
			.zoom(10)
			.tilt(45)
			.build();


			googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

			for (int i = 0; i < arry.size(); i++) {

				googleMap.addMarker(new MarkerOptions().position(new LatLng(arry.get(i).lattitude,arry.get(i).longitude)).title(arry.get(i).iteam_name).snippet(arry.get(i).place_address).icon(arry.get(i).feed_image_BitmapDescriptor));

			}

			if (googleMap == null) {
				Toast.makeText(context, "Sorry! unable to create maps",
						Toast.LENGTH_SHORT).show();
			}
			//			scroll_mapCategoryFeed.setOnTouchListener(new OnTouchListener() {
			//				
			//				@Override
			//				public boolean onTouch(View v, MotionEvent event) {
			//					scroll_mapCategoryFeed.requestDisallowInterceptTouchEvent(false);
			//					return false;
			//				}
			//			});
			//			((SunMapFragment) getFragmentManager().findFragmentById(
			//					R.id.explorer_fragment_map)).setListener(new SunMapFragment.OnTouchListener() {
			//						@Override
			//						public void onTouch() {
			//							scroll_mapCategoryFeed.requestDisallowInterceptTouchEvent(true);
			//						}
			//					});
		}
	}
	private void initilizeMap1(ArrayList<FeedInfo> arry) {
		if (googleMap != null) {

			for (int i = 0; i < arry.size(); i++) {
				googleMap.addMarker(new MarkerOptions().position(new LatLng(arry.get(i).lattitude,arry.get(i).longitude)).title(arry.get(i).iteam_name).snippet(arry.get(i).place_address).icon(arry.get(i).feed_image_BitmapDescriptor));

			}

			if (googleMap == null) {
				Toast.makeText(context, "Sorry! unable to create maps",
						Toast.LENGTH_SHORT).show();
			}
		}
	}
	@Override
	protected void onResume() {
		img_bottom_rate.setBackgroundResource(R.drawable.bottom_rate);
		img_bottom_reco.setBackgroundResource(R.drawable.bottom_reco);
		img_bottom_explore.setBackgroundResource(R.drawable.bottom_explor);
		lin_bottom_bg.setBackgroundResource(R.drawable.bottom_bar);

		if (!GPS_ON) 
		{

			gpsTrackerShow();	

		}



		super.onResume();
	}

	public void initilization() {


		menu = new SlidingMenu(this);

		Activity_Home.static_explorer=true;

		menuSliderAdapter = new MenuSliderAdapter();

		for (int i = 0; i < Activity_Home.SlideMenu_Title.length; i++) 
		{
			menuSliderAdapter.addItem(Activity_Home.SlideMenu_Title[i]);
		}


		type_BOLD = Typeface.createFromAsset(getAssets(),
				"fonts/Novecentowide-Bold.otf");
		type_BOOK = Typeface.createFromAsset(getAssets(),
				"fonts/Novecentowide-Book.otf");
		headeTXT.setText(R.string.explore);

		set_TypeFace_Control();

	}

	public void set_TypeFace_Control() {
		headeTXT.setTypeface(type_BOLD);
	}

	private void findIdFromXml() {
		headeTXT = (TextView) findViewById(R.id.headermain_Textview_label);
		homeIMG = (ImageView) findViewById(R.id.headermain_Imageview_Menu);

		img_top_search = (ImageView) findViewById(R.id.headermain_img_search);
		img_top_notification = (ImageView) findViewById(R.id.headermain_img_notification);

		img_bottom_reco = (ImageView) findViewById(R.id.footermain_img_reco);
		img_bottom_rate = (ImageView) findViewById(R.id.footermain_img_rate);
		img_bottom_explore = (ImageView) findViewById(R.id.footermain_img_explore);
		lin_bottom_bg = (LinearLayout) findViewById(R.id.footermain_lin_bottom_bar);

		//scroll_mapCategoryFeed = (ScrollView) findViewById(R.id.explorer_scroll_mapcatefeed);
		lin_scrollFeed = (LinearLayout) findViewById(R.id.explorer_scroll_lin_feed);
		lin_scrollHoriScrollCategory = (LinearLayout) findViewById(R.id.explorer_scroll_horizontalscroll_lin_category);
		img_logoMain = (ImageView) 	findViewById(R.id.headermain_Imageview_icon);
		scroll_mapCategoryFeed = (ScrollView) findViewById(R.id.explorer_scroll_mapcatefeed);
		scroll_map_transfernt = (FrameLayout) findViewById(R.id.explorer_fragment_framelay_transfernt);

		txtedt_Search = (AutoCompleteTextView) findViewById(R.id.explore_autoedt_search);
		btn_Search = (ImageButton) findViewById(R.id.explore_btn_search);
		txtedt_Search.setHintTextColor(Color.BLACK);
		//	scroll_mapCategoryFeed.setScrollViewListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.headermain_Imageview_icon:

			Activity_Home.static_explorer=false;
			Activity_Home.fragment = null;
			Activity_Home.static_explorer=false;
			Activity_Home.selectItem_explorer=-1;
			Activity_Home.check_logoLogoutExplore=true;

			finish();

			break;
		case R.id.headermain_Imageview_Menu:
			System.out.println("Home call");
			((Activity_Explore.this)).menu.toggle();
			break;

		case R.id.headermain_img_search:
			id_slidepos=-2;
			intentCall();
			break;
		case R.id.headermain_img_notification:
			id_slidepos=9;
			intentCall();
			break;
		case R.id.footermain_img_reco:
			id_slidepos=11;
			intentCall();
			break;
		case R.id.footermain_img_rate:
			id_slidepos=8;
			intentCall();
			break;
		case R.id.footermain_img_explore:
			id_slidepos=-2;
			break;

		default:
			break;
		}
	}
	@Override
	public void onBackPressed() {
		Activity_Home.static_explorer=false;
		Activity_Home.selectItem_explorer=-2;
		if (!Activity_Home.check_categorysubmit)
		{

			img_bottom_rate.setBackgroundResource(R.drawable.bottom_rate);
			img_bottom_reco.setBackgroundResource(R.drawable.bottom_reco);
			img_bottom_explore.setBackgroundResource(R.drawable.bottom_explor);
			lin_bottom_bg.setBackgroundResource(R.drawable.bottom_bar);
		}
		else
		{
			Activity_Home.check_categorysubmit=false;
			img_bottom_rate.setBackgroundResource(R.drawable.bottom_rate2);
			img_bottom_reco.setBackgroundResource(R.drawable.bottom_reco2);
			img_bottom_explore.setBackgroundResource(R.drawable.bottom_explor2);
			lin_bottom_bg.setBackgroundResource(R.drawable.bottom_bar2);
		}

		finish();
	}
	public void intentCall() {

		if (id_slidepos==3) {
			Activity_Home.static_explorer=false;
			Activity_Home.fragment = null;
			Activity_Home.static_explorer=false;
			Activity_Home.selectItem_explorer=-1;
			Activity_Home.check_logoLogoutExplore=true;

			finish();
		}
		else
		{
			Activity_Home.selectItem_explorer=id_slidepos;
			finish();
		}

	}

	public class MenuSliderAdapter extends BaseAdapter {

		private static final int TYPE_ITEM = 0;
		private static final int TYPE_SEPARATOR = 1;
		private static final int TYPE_MAX_COUNT = TYPE_SEPARATOR + 1;

		private LayoutInflater mInflater;

		private TreeSet<Integer> mSeparatorsSet = new TreeSet<Integer>();

		public MenuSliderAdapter() {
			mInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		}

		public void addItem(final String item) {
			notifyDataSetChanged();
		}

		@Override
		public int getItemViewType(int position) {
			return mSeparatorsSet.contains(position) ? TYPE_SEPARATOR
					: TYPE_ITEM;
		}

		@Override
		public int getViewTypeCount() {
			return TYPE_MAX_COUNT;
		}

		public int getCount() {
			return Activity_Home.SlideMenu_Title.length;
		}

		public String getItem(int position) {
			return Activity_Home.SlideMenu_Title[position];
		}

		public long getItemId(int position) {
			return position;
		}

		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder holder = null;


			if (convertView == null) 
			{
				holder = new ViewHolder();
				convertView = mInflater.inflate(R.layout.slidingmenu_row, null);
				holder.textView = (TextView) convertView
						.findViewById(R.id.slidingmenu_Textview_name);
				holder.textView.setTypeface(type_BOLD);
				holder.list_layout = (LinearLayout) convertView
						.findViewById(R.id.slidingMenu_Linear);
				holder.textView.setText(Activity_Home.SlideMenu_Title[position]);
				//				holder.imageView = (ImageView) convertView
				//						.findViewById(R.id.slidingmenu_ImageView_icon);

				//				if (position == 0) 
				//				{
				//					holder.imageView
				//					.setImageResource(R.drawable.ic_action_add_person);
				//				}
				//				else if (position == 1) 
				//				{
				//					holder.imageView
				//					.setImageResource(R.drawable.ic_action_add_person);
				//				} 
				//				else if (position == 2) 
				//				{
				//					holder.imageView
				//					.setImageResource(R.drawable.ic_action_settings);
				//				} 
				//				else if (position == 3)
				//				{
				//					holder.imageView.setImageResource(R.drawable.home_icon);
				//				} 
				//				else if (position == 6) 
				//				{
				//					holder.imageView.setImageResource(R.drawable.ic_logout);
				//				}
				//				else if (position == 4) {
				//					holder.imageView.setImageResource(R.drawable.ic_logout);
				//				}
				//	holder.imageView.setVisibility(View.INVISIBLE);
				convertView.setTag(holder);
			}

			else {
				holder = (ViewHolder) convertView.getTag();
			}

			return convertView;
		}

	}

	private class DrawerItemClickListener implements
	ListView.OnItemClickListener {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			id_slidepos=position;

			intentCall();
		}
	}

	private static class ViewHolder {
		public TextView textView;
		public ImageView imageView;
		public LinearLayout list_layout;
	}

	public boolean isDeviceSupportCamera() {
		if (getApplicationContext().getPackageManager().hasSystemFeature(
				PackageManager.FEATURE_CAMERA)) {
			return true;
		} else {
			return false;
		}
	}

	private void captureImage() {
		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

		fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE);

		intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);

		startActivityForResult(intent, CAMERA_CAPTURE_IMAGE_REQUEST_CODE);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(resultCode ==RESULT_OK)
		{
			if (requestCode == CAMERA_CAPTURE_IMAGE_REQUEST_CODE) {

				previewCapturedImage();
			} 
			else if (requestCode == SELECT_PICTURE ) {
				final Uri selectedImageUri = data.getData();
				try {

					BitmapFactory.Options options = new BitmapFactory.Options();

					options.inSampleSize = ConstantValue.IMAGE_CAPTURE_OPTION;

					final Bitmap bitmap = BitmapFactory.decodeFile(
							getPath(selectedImageUri), options);

					img_slider_UserPhoto.setImageBitmap(bitmap);
					try {

						dialog = ProgressDialog.show(context, "",
								"Uploading file...", true);

						byte[] bry1 = Utills.bitmapTobyteArry(bitmap);
						performPost_ByteArray(bry1);


					} 
					catch (Exception e) {
						e.printStackTrace();
					}
				} catch (NullPointerException e) {
					e.printStackTrace();
				}

			}

		}
		else if (resultCode == RESULT_CANCELED) {
			// user cancelled Image capture
			Toast.makeText(getApplicationContext(),
					"User cancelled image capture", Toast.LENGTH_SHORT)
					.show();
		} else {
			// failed to capture image
			Toast.makeText(getApplicationContext(),
					"Sorry! Failed to capture image", Toast.LENGTH_SHORT)
					.show();
		}

	}

	private void previewCapturedImage() 
	{
		try
		{

			BitmapFactory.Options options = new BitmapFactory.Options();

			options.inSampleSize = ConstantValue.IMAGE_CAPTURE_OPTION;

			final Bitmap bitmap = BitmapFactory.decodeFile(fileUri.getPath(),
					options);

			img_slider_UserPhoto.setImageBitmap(bitmap);
			try 
			{
				dialog = ProgressDialog.show(context, "", "Uploading file...",
						true);

				byte[] bry1 = Utills.bitmapTobyteArry(bitmap);
				String Response=performPost_ByteArray(bry1);
				Log.e("Image Response", Response);

			}
			catch (Exception e) 
			{
				e.printStackTrace();
			}

		}
		catch (NullPointerException e)
		{
			e.printStackTrace();
		}
	}

	public Uri getOutputMediaFileUri(int type) {
		return Uri.fromFile(getOutputMediaFile(type));
	}

	private static File getOutputMediaFile(int type) {

		File mediaStorageDir = new File(
				Environment
				.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
				IMAGE_DIRECTORY_NAME);

		if (!mediaStorageDir.exists()) {
			if (!mediaStorageDir.mkdirs()) {
				Log.d(IMAGE_DIRECTORY_NAME, "Oops! Failed create "
						+ IMAGE_DIRECTORY_NAME + " directory");
				return null;
			}
		}

		String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss",
				Locale.getDefault()).format(new Date());
		File mediaFile;
		if (type == MEDIA_TYPE_IMAGE) {
			mediaFile = new File(mediaStorageDir.getPath() + File.separator
					+ "IMG_" + timeStamp + ".jpg");

		} else {
			return null;
		}

		return mediaFile;
	}

	public void calldialog() {

		final Dialog dialog = new Dialog(context);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.setContentView(R.layout.alterdialog);

		dialog.setCancelable(true);
		final Button btn_takephoto, btn_gallary;
		TextView txt_title;
		btn_takephoto = (Button) dialog.findViewById(R.id.alterdialog_btn_yesfirst);
		btn_gallary = (Button) dialog.findViewById(R.id.alterdialog_btn_nosecond);
		txt_title = (TextView) dialog.findViewById(R.id.alterdialog_txt_title);

		txt_title.setText(getResources().getString(R.string.alert_selectanyone));
		btn_gallary.setText(getResources().getString(R.string.alert_gallary));
		btn_takephoto.setText(getResources().getString(R.string.alert_takephoto));

		btn_takephoto.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				captureImage();
				dialog.dismiss();
			}
		});

		btn_gallary.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				dialog.dismiss();
				Intent intent = new Intent();
				intent.setType("image/*");
				intent.setAction(Intent.ACTION_GET_CONTENT);
				startActivityForResult(
						Intent.createChooser(intent, "Select Picture"),
						SELECT_PICTURE);


			}
		});

		dialog.show();
	}

	public String getPath(Uri uri) {

		String[] projection = { MediaStore.Images.Media.DATA };
		@SuppressWarnings("deprecation")
		Cursor cursor = managedQuery(uri, projection, null, null, null);
		int column_index = cursor
				.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
		cursor.moveToFirst();
		return cursor.getString(column_index);
	}


	public String performPost_ByteArray(byte [] bry1)
	{
		String apiUrl = ConstantValue.URL_IMAGE_VIEW+ConstantValue.user.get(SessionManager.KEY_USERID);

		String doc = null;

		try
		{
			HttpClient client = new DefaultHttpClient();
			HttpPost httppost = new HttpPost(apiUrl);

			MultipartEntity entity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);
			long time= System.currentTimeMillis();

			if(bry1!=null)
			{
				entity.addPart("image", new ByteArrayBody(bry1, "sun"+time+".jpg"));
				httppost.setEntity(entity);

			}

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
		dialog.dismiss();
		return doc;
	}

}

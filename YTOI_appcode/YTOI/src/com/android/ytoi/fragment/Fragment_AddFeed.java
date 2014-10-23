package com.android.ytoi.fragment;

import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.ytoi.Act_GetPhotoFrom_G_C;
import com.android.ytoi.Activity_Explore;
import com.android.ytoi.Activity_Home;
import com.android.ytoi.R;
import com.android.ytoi.rateark.ArcMenu;
import com.android.ytoi.utils.Act_Fragment;
import com.android.ytoi.utils.GPSTracker;
import com.android.ytoi.utils.Utills;
import com.facebook.LoggingBehavior;
import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.Session.StatusCallback;
import com.facebook.SessionState;
import com.facebook.Settings;
import com.facebook.model.GraphUser;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.plus.PlusShare;
import com.sun.googleplace.HttpGooglePlace;
import com.sun.googleplace.SunGooglePlaceData;

public class Fragment_AddFeed extends Act_Fragment{

	private View rootView;

	private Context context;

	private ImageView img_capture;

	public static TextView txt_categoryname,txt_fbshare,txt_googleshare;

	public static AutoCompleteTextView txtedt_location;

	public static AutoCompleteTextView txtedt_feedname;

	public static EditText edt_discription;

	//public static EditText edt_itemname;

	private String str_pathImage="",category_name="",category_id="";


	private int sunRadius=20000;

	private String sensorType="true";

	private ArrayList<SunGooglePlaceData> arraylist_SunGooglePlaceData;

	//public static ArrayAdapter<String> adpter_autocomplete;

	public static byte[] static_feedPhotoByte=null;

	public static Bitmap static_feedPhotoBitmap=null;

	public Fragment_AddFeed()
	{

	}
	private static final int[] ITEM_DRAWABLES = { R.drawable.rate_1,R.drawable.rate_2,R.drawable.rate_3,R.drawable.rate_4,R.drawable.rate_5,R.drawable.rate_6,R.drawable.rate_7, R.drawable.rate_8,
		R.drawable.rate_9, R.drawable.rate_10 };

	private Session.StatusCallback statusCallback ;
	protected Session session_fb;

	private String category_link;

	protected ImageView imageView_ratedisplay;

	public static HttpGooglePlace httpGooglePlace;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {



		rootView = inflater.inflate(R.layout.addfeed, container,false);

		context = rootView.getContext();

		init();

		statusCallback = new SessionStatusCallback();

		this.session_fb = createSession();

		Settings.addLoggingBehavior(LoggingBehavior.INCLUDE_ACCESS_TOKENS);


		category_id=getArguments().getString("category_id");
		category_name=getArguments().getString("category_name");
		category_link=getArguments().getString("category_link");

		Session session = Session.getActiveSession();

		if (session == null) {
			if (savedInstanceState != null) {
				session = Session.restoreSession(getActivity(), null, statusCallback, savedInstanceState);
			}
			if (session == null) {
				session = new Session(getActivity());
			}
			Session.setActiveSession(session);
			if (session.getState().equals(SessionState.CREATED_TOKEN_LOADED)) {
				session.openForPublish(new Session.OpenRequest(getActivity()).setCallback(statusCallback));
			}
		}

		txt_categoryname.setText(category_name);
		txt_categoryname.setTag(category_id);
		Activity_Home.check_categorysubmit=true;
		edt_discription.setHintTextColor(Color.BLACK);
		txtedt_location.setHintTextColor(Color.BLACK);
		txtedt_feedname.setHintTextColor(Color.BLACK);

		gpsTrackerShow();

		ArcMenu btn_arcMenu = (ArcMenu) rootView.findViewById(R.id.explorer_arc_menu);

		initArcMenu(btn_arcMenu, ITEM_DRAWABLES);

		return rootView;
	}
	private class SessionStatusCallback implements Session.StatusCallback {
		@Override
		public void call(Session session, SessionState state, Exception exception) {

			postPhoto();

			Log.e("SessionStatusCallback", "SessionStatusCallback");
		}
	}
	//	private void postPhoto() {
	//		if (hasPublishPermission()) {
	//			//	Bitmap image = BitmapFactory.decodeResource(this.getResources(), R.drawable.icon_check_in);
	//			Request request = Request.newUploadPhotoRequest(Session.getActiveSession(), captured_bitmap, new Request.Callback() {
	//				@Override
	//				public void onCompleted(Response response) {
	//					//     showPublishResult(getString(R.string.photo_post), response.getGraphObject(), response.getError());
	//					Log.e("response response", response+"  s");
	//				}
	//			});
	//			request.executeAsync();
	//		} 
	//	}
	private void postPhoto() {
		Log.e("Calling", "postPhoto");
		if (static_feedPhotoBitmap!=null) {


			if (hasPublishPermission()) {

				//Bitmap image = BitmapFactory.decodeResource(this.getResources(), R.drawable.ic_launcher);
				Request request = Request.newUploadPhotoRequest(Session.getActiveSession(), static_feedPhotoBitmap, new Request.Callback() {
					@Override
					public void onCompleted(Response response) {
						//   showPublishResult(getString(R.string.photo_post), response.getGraphObject(), response.getError());

						AlertDialog.Builder mBuilder = new AlertDialog.Builder(context);
						mBuilder.setTitle("     FaceBook Post   ");
						mBuilder.setMessage(" Photo successfully posted on Facebook   ");

						mBuilder.setPositiveButton("OK", new Dialog.OnClickListener() 
						{
							public void onClick(DialogInterface mDialogInterface, int mWhich)
							{
								mDialogInterface.dismiss();
								Activity_Home.check_categorysubmit=true;
							}

						});
						AlertDialog alertDialog = mBuilder.create();
						alertDialog.show();

						//						Log.e("response response", response+"  s");
					}
				});
				request.executeAsync();
				Log.e("Calling", "newUploadPhotoRequest");
			} 
			else
			{
				Log.e("Calling", "hasPublishPermission false");
			}
		}
	}
	private boolean hasPublishPermission() {
		Log.e("Calling", "hasPublishPermission");
		Session session = Session.getActiveSession();
		return session != null && session.getPermissions().contains("publish_actions");
	}

	@Override
	public void onStart() {
		super.onStart();
		Session.getActiveSession().addCallback(statusCallback);
	}

	@Override
	public void onStop() {
		super.onStop();
		Session.getActiveSession().removeCallback(statusCallback);
	}
	private void onClickRequest() {
		if (this.session_fb.isOpened()) {
			sendRequests();
		} else {
			StatusCallback callback = new StatusCallback() {
				public void call(Session session, SessionState state, Exception exception) {
					if (exception != null) {
						new AlertDialog.Builder(getActivity())
						.setTitle("Login Failed")
						.setMessage(exception.getMessage())
						.setPositiveButton("OK", null)
						.show();
						Fragment_AddFeed.this.session_fb = createSession();
					}
				}
			};
			this.session_fb.openForRead(new Session.OpenRequest(getActivity()).setCallback(callback));
		}
	}
	private Session createSession() {
		Session activeSession = Session.getActiveSession();
		if (activeSession == null || activeSession.getState().isClosed()) {
			activeSession = new Session.Builder(getActivity()).setApplicationId("640430639328182").build();
			Session.setActiveSession(activeSession);
		}
		return activeSession;
	}
	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		Session session = Session.getActiveSession();
		Session.saveSession(session, outState);
	}
	private void onClickLogout() {
		Session session = Session.getActiveSession();
		if (!session.isClosed()) {
			session.closeAndClearTokenInformation();
		}
	}
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (this.session_fb.onActivityResult(getActivity(), requestCode, resultCode, data) &&
				this.session_fb.getState().isOpened()) {
			sendRequests();
		}
		//	Session.getActiveSession().onActivityResult(getActivity(), requestCode, resultCode, data);
	}
	private void sendRequests() {

		final Session session = Session.getActiveSession();
		if (session != null && session.isOpened()) {
			Request request = Request.newMeRequest(session, new Request.GraphUserCallback() {
				@Override
				public void onCompleted(GraphUser user, Response response) {
					if (session == Session.getActiveSession()) {
						if (user != null) {
							postPhoto();
							//getProfileInformation(user);
						}   
					}   
				}   
			}); 
			Request.executeBatchAsync(request);
		}  
	}
	@Override
	public void onResume()
	{

		if (Act_GetPhotoFrom_G_C.FLAG_CAPTURED_IMAGE) {
			img_capture.setScaleType(ScaleType.FIT_XY);
			img_capture.setImageBitmap(static_feedPhotoBitmap);
			Act_GetPhotoFrom_G_C.FLAG_CAPTURED_IMAGE=false;

		}

		txt_categoryname.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

			}
		});
		img_capture.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				Intent intent=new Intent(context,Act_GetPhotoFrom_G_C.class);
				startActivity(intent);

				//calldialog();
			}
		});
		txt_googleshare.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				//				Intent shareIntent = ShareCompat.IntentBuilder.from(getActivity())
				//						.setText(edt_discription.getText()+"")
				//						.setType("image/jpg")
				//						.setStream(Uri.parse(pathImage))
				//						.getIntent()
				//						.setPackage("com.google.android.apps.plus");

				Intent shareIntent = new PlusShare.Builder(getActivity())
				.setType("text/plain")
				.setText("Welcome to the Google+ platform. with YTOI")
				.setContentUrl(Uri.parse(str_pathImage))
				.getIntent();
				startActivity(shareIntent);

				//				Intent intent = new Intent(Intent.ACTION_SEND);
				//				intent.setType("image/jpg");
				//				// uri looks like content://media/external/images/media/42
				//				intent.putExtra(Intent.EXTRA_STREAM, "http://i.stack.imgur.com/QcTKM.jpg");
				//				startActivity(Intent.createChooser(intent , "Share"));
			}
		});
		txt_fbshare.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				onClickRequest();
			}
		});

		ImageView img_rate=(ImageView) getActivity().findViewById(R.id.footermain_img_rate);
		img_rate.setBackgroundResource(R.drawable.bottom_rate2);
		ImageView img_reco=(ImageView) getActivity().findViewById(R.id.footermain_img_reco);
		img_reco.setBackgroundResource(R.drawable.bottom_reco2);
		ImageView img_exxplorer=(ImageView) getActivity().findViewById(R.id.footermain_img_explore);
		img_exxplorer.setBackgroundResource(R.drawable.bottom_explor2);
		LinearLayout lin_bottombar=(LinearLayout) getActivity().findViewById(R.id.footermain_lin_bottom_bar);
		lin_bottombar.setBackgroundResource(R.drawable.bottom_bar2);


		super.onResume();
	}


	public void init() {
		img_capture=(ImageView) rootView.findViewById(R.id.addfeed_img_cpture);
		txt_categoryname=(TextView) rootView.findViewById(R.id.addfeed_txt_categoryname);
		edt_discription=(EditText) rootView.findViewById(R.id.addfeed_edt_expiriance);
		txtedt_feedname=(AutoCompleteTextView) rootView.findViewById(R.id.addfeed_edt_itemname);
		txt_googleshare=(TextView) rootView.findViewById(R.id.addfeed_txt_googleshare);
		txt_fbshare=(TextView) rootView.findViewById(R.id.addfeed_txt_fbshare);
		imageView_ratedisplay=(ImageView) rootView.findViewById(R.id.addfeed_img_selectedrate);
		txtedt_location = (AutoCompleteTextView)rootView.findViewById(R.id.addfeed_txt_autolocation);


	}
	@SuppressWarnings("unchecked")
	public void setBackApiResponse(ArrayList<SunGooglePlaceData> obj1) {
		System.gc();
		if (obj1!=null) {
			if (Activity_Home.check_categorysubmit) {

				arraylist_SunGooglePlaceData=(ArrayList<SunGooglePlaceData>)obj1;
				List<String> list=new ArrayList<String>();

				for (int i = 0; i < arraylist_SunGooglePlaceData.size(); i++) 
				{
					list.add(arraylist_SunGooglePlaceData.get(i).getName());
					Log.e("List Name",list.get(i)+" s ");
				}

				if (list!=null) 				
					if (list.size()>0) 
					{
						LocationAutoArrayAdapter adpter_autocomplete = null;
						if (list!=null) 				
							if (list.size()>0) {

								adpter_autocomplete = new LocationAutoArrayAdapter(context,list);
							}



						if (adpter_autocomplete!=null) {
							txtedt_location.setAdapter(adpter_autocomplete);
							txtedt_location.invalidate();
							adpter_autocomplete.notifyDataSetChanged();
						}
					}

				Utills.CURRENT_LATTITUDE_KEY=Utills.CURRENT_LATTITUDE;
				Utills.CURRENT_LONGITUDE_KEY=Utills.CURRENT_LONGITUDE;

				txtedt_location.setOnItemClickListener(new OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> arg0, View arg1,
							int arg2, long arg3) 
					{
						Utills.CURRENT_LATTITUDE_KEY=arraylist_SunGooglePlaceData.get(arg2).getLatitude();
						Utills.CURRENT_LONGITUDE_KEY=arraylist_SunGooglePlaceData.get(arg2).getLonglatitude();
						Log.e("str_longitude", Utills.CURRENT_LATTITUDE_KEY+"");
						Log.e("str_lattitude", Utills.CURRENT_LONGITUDE_KEY+"");
						Log.e("str_longitude c", Utills.CURRENT_LATTITUDE+"");
						Log.e("str_lattitude c", Utills.CURRENT_LONGITUDE+"");
					}
				});

			}
		}
		@SuppressWarnings("rawtypes")
		List adpter_autocomplete_list=new ArrayList<String>();

		for (int i = 0; i < Activity_Home.static_arraylist_FeedInfo.size(); i++) {

			adpter_autocomplete_list.add(Activity_Home.static_arraylist_FeedInfo.get(i).iteam_name);
		}
		@SuppressWarnings("rawtypes")
		FeedAutoArrayAdapter adpter_autocomplete_item = null;
		if (adpter_autocomplete_list!=null) 				
			if (adpter_autocomplete_list.size()>0) {

				adpter_autocomplete_item = new FeedAutoArrayAdapter(context,adpter_autocomplete_list);
			}

		//adpter_autocomplete=new AutoCompleteAdapter(adpter_autocomplete);
		if (adpter_autocomplete_item!=null)
		{
			txtedt_feedname.setAdapter(adpter_autocomplete_item);
			txtedt_feedname.invalidate();
			adpter_autocomplete_item.notifyDataSetChanged();
		}

	}

	@Override
	public void onPause() {
		super.onPause();
	}


	public class FeedAutoArrayAdapter extends ArrayAdapter<String> {
		private final Context context;
		private final List values;

		public FeedAutoArrayAdapter(Context context, List values) {
			super(context, R.layout.lyt_addfeed_itemauto, values);
			this.context = context;
			this.values = values;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			View rowView = inflater.inflate(R.layout.lyt_addfeed_itemauto, parent, false);
			TextView txt_autosearchdrop = (TextView) rowView.findViewById(R.id.lytaddfeed_txt_autosearch);
			txt_autosearchdrop.setText(values.get(position)+"");


			return rowView;
		}
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

	private void gpsTrackerShow()
	{
		if (isOnline()) 
		{
			GPSTracker gps = new GPSTracker(context);
			if(gps.canGetLocation())
			{
				int status = GooglePlayServicesUtil.isGooglePlayServicesAvailable(context);
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
				Activity_Explore.GPS_ON=true;

				httpGooglePlace=new HttpGooglePlace(Fragment_AddFeed.this, "AIzaSyDsCIERxSBDQqekAVXYgyoqVemA1lJWu44",Utills.CURRENT_LATTITUDE, Utills.CURRENT_LONGITUDE, sensorType,category_link, sunRadius);

			}
			else
			{
				Activity_Explore.GPS_ON=false;
				gps.showSettingsAlert();
			}


		}
		else
		{
			Activity_Explore.GPS_ON=true;

		}

	}
	private void initArcMenu(final ArcMenu menu, final int[] itemDrawables) {

		final int itemCount = itemDrawables.length;
		for (int i = 0; i < itemCount; i++) {
			final ImageView item = new ImageView(context);
			item.setImageResource(itemDrawables[i]);
			item.setId(i);
			final int position = i;
			menu.addItem(item, new OnClickListener() {

				@Override
				public void onClick(View v) {
					imageView_ratedisplay.setImageResource(itemDrawables[item.getId()]);

				}
			});
		}
	}
}

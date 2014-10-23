package com.android.ytoi.fragment;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.ytoi.R;
import com.android.ytoi.adapter.FeedAdapter;
import com.android.ytoi.utils.Act_Fragment;
import com.android.ytoi.utils.ConstantValue;
import com.android.ytoi.utils.GPSTracker;
import com.android.ytoi.utils.SessionManager;
import com.android.ytoi.web.HttpHelper_Fragment;
import com.android.ytoi.webclass.FeedDetail;

public class Fragment_PlaceProfile extends Act_Fragment implements OnClickListener,OnItemClickListener{

	private ImageView checkInIMG;
	private ImageView locationIMG;
	private ImageView likeIMG;
	private ImageView userIMG;
	private TextView checkIn, header, checkStatus, like, user;
	private TextView userNameLV;

	private ListView feedList;
	private ArrayList<FeedDetail> arraylist_feedDetailInfo = new ArrayList<FeedDetail>();
	private FeedAdapter feeedAdapter;
	
	private Button feedBTN, mustTryBTN, expertsBTN;
	Context context;
	Typeface type_BOLD, type_BOOK;
	GPSTracker gps;

	public Fragment_PlaceProfile() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.home_main, container, false);
		context = rootView.getContext();
		
		checkInIMG = (ImageView) rootView
				.findViewById(R.id.homemain_ImageView_CheckIn);
		locationIMG = (ImageView) rootView
				.findViewById(R.id.homemain_ImageView_checkstatus);
		likeIMG = (ImageView) rootView
				.findViewById(R.id.homemain_ImageView_heart);
		userIMG = (ImageView) rootView
				.findViewById(R.id.homemain_ImageView_user);
		checkIn = (TextView) rootView
				.findViewById(R.id.homemain_Textview_CheckIn);
		header = (TextView) rootView
				.findViewById(R.id.homemain_Textview_Header);
		feedList = (ListView) rootView
				.findViewById(R.id.homemain_Listview_list);
		
		like = (TextView) rootView.findViewById(R.id.homemain_Textview_heart);
		user = (TextView) rootView.findViewById(R.id.homemain_Textview_user);
		checkStatus = (TextView) rootView
				.findViewById(R.id.homemain_Textview_checkstatus);

		feedBTN = (Button) rootView.findViewById(R.id.homemain_Button_feed);
		mustTryBTN = (Button) rootView
				.findViewById(R.id.homemain_Button_MustTry);
		expertsBTN = (Button) rootView
				.findViewById(R.id.homemain_Button_experts);
		type_BOLD = Typeface.createFromAsset(context.getAssets(),
				"fonts/Novecentowide-Bold.otf");
		type_BOOK = Typeface.createFromAsset(context.getAssets(),
				"fonts/Novecentowide-Book.otf");
		set_TypeFace_Control();

		checkInIMG.setOnClickListener(this);
		locationIMG.setOnClickListener(this);
		likeIMG.setOnClickListener(this);
		userIMG.setOnClickListener(this);
		feedBTN.setOnClickListener(this);
		mustTryBTN.setOnClickListener(this);
		expertsBTN.setOnClickListener(this);
		defaultFeed();	
		init();
		return rootView;
	}
	public void init()
	{
		if (isOnline()) // connected
		{
			String uri = null;
			uri = String.format(ConstantValue.URL_CHECKIN_ADD);
			uri = uri.replace(" ", "%20");
			Log.i("Following Info", uri);
			if (uri != null) {
				uri = uri.replace(" ", "%20");

				List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>(
						2);
				nameValuePair.add(new BasicNameValuePair("user_id",
						ConstantValue.user.get(SessionManager.KEY_USERID)));
				HttpHelper_Fragment httpHelper = new HttpHelper_Fragment(
						ConstantValue.REQUESTCODE_CHECKIN_ADD,
						Fragment_PlaceProfile.this, "Check In...", nameValuePair);
				httpHelper.execute(uri);
			}

		} else {

			Toast.makeText(getActivity().getApplicationContext(),
					"Internet Connection Fail", Toast.LENGTH_SHORT).show();
		}
	}
	public void defaultFeed()
	{ arraylist_feedDetailInfo = new ArrayList<FeedDetail>();
		FeedDetail feed=new FeedDetail();
		feed.feed_At="starbucks";
		feed.feed_Location="Mumbai";
		feed.feed_Rated="Devil's own";
		feed.feed_Time="2";
		feed.feed_UserName="Agni Kapoor";
		arraylist_feedDetailInfo.add(feed);
		feed_Feel();
	}
public void feed_Feel()
{
	feeedAdapter= new FeedAdapter(context,
			R.layout.feed_home_row, arraylist_feedDetailInfo);
	feedList.setAdapter(feeedAdapter);
	feedList.setOnItemClickListener(this);

}
	public void set_TypeFace_Control() {
		header.setTypeface(type_BOLD);
		feedBTN.setTypeface(type_BOOK);
		mustTryBTN.setTypeface(type_BOOK);
		expertsBTN.setTypeface(type_BOOK);
		checkIn.setTypeface(type_BOOK);
		like.setTypeface(type_BOOK);
		user.setTypeface(type_BOOK);
		checkStatus.setTypeface(type_BOOK);
	}

	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {

		case R.id.homemain_ImageView_CheckIn:
			gps = new GPSTracker(context);
			Toast.makeText(context, "Check In call", Toast.LENGTH_SHORT).show();
			if (gps.canGetLocation()) {

				double latitude = gps.getLatitude();
				double longitude = gps.getLongitude();

				// \n is for new line
				Toast.makeText(
						context,
						"Your Location is - \nLat: " + latitude + "\nLong: "
								+ longitude, Toast.LENGTH_LONG).show();
				if(latitude!=0 && longitude !=0)
				{
					if (isOnline()) // connected
					{
						String uri = null;
						uri = String.format(ConstantValue.URL_CHECKIN_ADD);
						uri = uri.replace(" ", "%20");
						Log.i("Following Info", uri);
						if (uri != null) {
							uri = uri.replace(" ", "%20");

							List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>(
									2);
							nameValuePair.add(new BasicNameValuePair("user_id",
									ConstantValue.user.get(SessionManager.KEY_USERID)));
							nameValuePair.add(new BasicNameValuePair("lattitude",
									String.valueOf(latitude)));
							nameValuePair.add(new BasicNameValuePair("longitude",
									String.valueOf(longitude)));
							nameValuePair.add(new BasicNameValuePair("place_id",
									"1"));
							HttpHelper_Fragment httpHelper = new HttpHelper_Fragment(
									ConstantValue.REQUESTCODE_CHECKIN_ADD,
									Fragment_PlaceProfile.this, "Check In...", nameValuePair);
							httpHelper.execute(uri);
						}

					} else {

						Toast.makeText(getActivity().getApplicationContext(),
								"Internet Connection Fail", Toast.LENGTH_SHORT).show();
					}
				}
			} else {
				// can't get location
				// GPS or Network is not enabled
				// Ask user to enable GPS/network in settings
				gps.showSettingsAlert();
			}
			break;
		case R.id.homemain_ImageView_checkstatus:
			Toast.makeText(context, "Check Status call", Toast.LENGTH_SHORT)
					.show();
			break;
		case R.id.homemain_ImageView_heart:
			Toast.makeText(context, "Like call", Toast.LENGTH_SHORT).show();
			break;
		case R.id.homemain_ImageView_user:
			Toast.makeText(context, "User call", Toast.LENGTH_SHORT).show();
			break;

		case R.id.homemain_Button_feed:
			Toast.makeText(context, "Feed call", Toast.LENGTH_SHORT).show();
//			feedBTN.setBackgroundResource(R.drawable.feed_bg_slt);
//			mustTryBTN.setBackgroundResource(R.drawable.feed_bg_center_normal);
//			expertsBTN.setBackgroundResource(R.drawable.feed_bg_unslt);
			defaultFeed();

			break;
		case R.id.homemain_Button_MustTry:
//			feedBTN.setBackgroundResource(R.drawable.feed_bg_unslt);
//			mustTryBTN
//					.setBackgroundResource(R.drawable.feed_bg_center_selected);
//			expertsBTN.setBackgroundResource(R.drawable.feed_bg_unslt);
//			Toast.makeText(context, "Must Try call", Toast.LENGTH_SHORT).show();
			defaultFeed();
			break;
		case R.id.homemain_Button_experts:
//			feedBTN.setBackgroundResource(R.drawable.feed_bg_unslt);
//			mustTryBTN.setBackgroundResource(R.drawable.feed_bg_center_normal);
//			expertsBTN.setBackgroundResource(R.drawable.feed_bg_slt);
			Toast.makeText(context, "Experts call", Toast.LENGTH_SHORT).show();
			defaultFeed();
			break;
		default:
			break;
		}
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
	switch (arg1.getId()) {
	case R.id.homemain_Listview_list:
		System.out.println("List click");
		userNameLV=(TextView)arg1.findViewById(R.id.feedhomerow_Textview_username);
		 userNameLV.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				System.out.println("User click");
				Toast.makeText(context,"User click", Toast.LENGTH_SHORT).show();
			}
		});
		break;

	default:
		break;
	}
	}


}

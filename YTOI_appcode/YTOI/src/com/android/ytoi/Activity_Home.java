package com.android.ytoi;

import java.io.File;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentManager.BackStackEntry;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
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
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.ytoi.adapter.HomeFeedAdapter;
import com.android.ytoi.fragment.Fragment_AddFeed;
import com.android.ytoi.fragment.Fragment_Category;
import com.android.ytoi.fragment.Fragment_EditProfile;
import com.android.ytoi.fragment.Fragment_Follower;
import com.android.ytoi.fragment.Fragment_Following;
import com.android.ytoi.fragment.Fragment_Notification;
import com.android.ytoi.fragment.Fragment_PlaceProfile;
import com.android.ytoi.fragment.Fragment_Recos;
import com.android.ytoi.fragment.Fragment_Setting;
import com.android.ytoi.fragment.Fragment_ViewPersonProfile;
import com.android.ytoi.imageloader.ImageLoader;
import com.android.ytoi.utils.Act_Main;
import com.android.ytoi.utils.ConstantValue;
import com.android.ytoi.utils.SessionManager;
import com.android.ytoi.utils.Utills;
import com.android.ytoi.web.HttpHelper;
import com.android.ytoi.webclass.Category;
import com.android.ytoi.webclass.FeedInfo;
import com.google.android.gms.plus.Plus;
import com.slidingmenu.lib.SlidingMenu;

public class Activity_Home extends Act_Main implements OnClickListener
{
	//public static Bitmap bitmap_ProfileImage=null;

	private Context context = this;
	private ImageLoader imgloader=new ImageLoader(this,false);
	public static Fragment fragment = null;
	public static FragmentManager fragmentManager ;
	public static boolean check_categorysubmit= false;
	public static boolean check_logoLogoutExplore= false;
	private SlidingMenu menu;

	private ListView list_sliderList;
	private ScrollView scroll_mainHome;
	private LinearLayout lin_mainHome;
	private MenuSliderAdapter menuSliderAdapter;

	private static final int CAMERA_CAPTURE_IMAGE_REQUEST_CODE = 100;

	public static final int MEDIA_TYPE_IMAGE = 1;
	private static final int SELECT_PICTURE = 3;
	public	static int selectItem_explorer=-1; 
	public static boolean static_explorer=false; 
	private  Uri fileUri;
	public static ArrayList<FeedInfo> static_arraylist_FeedInfo = new ArrayList<FeedInfo>();

	public static boolean static_explorer_resume=true;
	private static final String IMAGE_DIRECTORY_NAME = "YTOI";

	public final static String[] SlideMenu_Title = new String[] { "FOLLOWING",
		"FOLLOWERS", "SETTING", "HOME", "EDIT PROFILE", "LOG-OUT" };

	private  ImageView homeIMG;
	private  ImageView img_logoMain;
	private  ImageView img_top_search;
	private  ImageView img_top_notification;

	private ImageView captureIMG;
	private ImageView img_slider_UserPhoto;
	//private ImageView img_logoMain;
	private ImageView img_bottom_reco;
	private ImageView img_bottom_rate;
	private ImageView img_bottom_explore;
	private LinearLayout lin_bottom_bg;

	private TextView txt_slider_userName, txt_slider_location, headeTXT;
	private  ProgressDialog dialog = null;
	private Typeface type_BOLD, type_BOOK;
	private View view_slider;
	private HomeFeedAdapter homefeedAdapter;

	private SessionManager session;
	private String fragmentTagName;

	private String current_date_time;

	// After FeedView Http Api Call 
	@SuppressWarnings("unchecked")
	public void setBackApiResponse(int requestcode, Object obj1, Object obj2,Object obj3) {

		if(requestcode == ConstantValue.REQUESTCODE_FEED_VIEW)
		{
			if(obj1!=null)
				if (((String)obj1).equalsIgnoreCase("1")) 
				{
					Toast.makeText(context, obj2.toString(), Toast.LENGTH_SHORT)
					.show();
					static_arraylist_FeedInfo.clear();

					Fragment_Category.static_arraylist_Category.clear();
					Activity_Explore.static_arraylist_Category_explorer.clear();

					static_arraylist_FeedInfo=(ArrayList<FeedInfo>) obj3;

					if (ConstantValue.user.get(SessionManager.KEY_PROFILE_IMG).equalsIgnoreCase("")) 
					{

						img_slider_UserPhoto.setScaleType(ScaleType.FIT_XY);

						imgloader.DisplayImage(
								HttpHelper.static_profile_picture,
								img_slider_UserPhoto);

					}

					setMainHomeFeedUI();


				} else
					Toast.makeText(context, obj2.toString(), Toast.LENGTH_SHORT)
					.show();
		}


	}

	// After Feed Add Http Api Call
	public void setBackApiResponse(int requestcode, Object obj1, Object obj2) {


		if(requestcode == ConstantValue.REQUESTCODE_FEED_ADD)
		{

			if(Fragment_AddFeed.httpGooglePlace!=null)
			{
				Fragment_AddFeed.httpGooglePlace.cancelRequest();
				Fragment_AddFeed.httpGooglePlace=null;
			}


			if(obj1!=null)
			{

				for (int i = 0; i < fragmentManager.getBackStackEntryCount(); i++) 
				{

					fragmentManager.popBackStack();

				}

				check_categorysubmit=false;

				img_bottom_rate.setBackgroundResource(R.drawable.bottom_rate);

				img_bottom_reco.setBackgroundResource(R.drawable.bottom_reco);

				img_bottom_explore.setBackgroundResource(R.drawable.bottom_explor);

				lin_bottom_bg.setBackgroundResource(R.drawable.bottom_bar);

				feedViewApiCall();
			}
		}

	}

	// After Feed Add Http Api Call
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		if (android.os.Build.VERSION.SDK_INT > 9)
		{

			StrictMode.ThreadPolicy policy = 
					new StrictMode.ThreadPolicy.Builder().permitAll().build();      
			StrictMode.setThreadPolicy(policy);

		}

		setContentView(R.layout.fragment);

		DisplayMetrics dm = new DisplayMetrics();

		getWindowManager().getDefaultDisplay().getMetrics(dm);

		ConstantValue.DEVICE_WIDTH = dm.widthPixels;

		ConstantValue.DEVICE_HEIGHT = dm.heightPixels;

		getCurrentDateTime();

		System.gc();

		findIdFromXml();

		initalization();

		set_TypeFace_Control();

		menuSliderMethod();

		setClickListener();

		feedViewApiCall();

		setUserImage();

	}

	@Override
	protected void onResume() {

		txt_slider_userName.setText(ConstantValue.user.get(SessionManager.KEY_FIRSTNAME) + " "
				+ ConstantValue.user.get(SessionManager.KEY_LASTNAME));
		txt_slider_location.setText(ConstantValue.user.get(SessionManager.KEY_CITY));

		if(static_explorer)
		{
			Log.e("AFTER EXPLORER", "AFTER EXPLORER");
			selectItem(selectItem_explorer);
		}
		if (check_logoLogoutExplore) {
			check_logoLogoutExplore=false;
			Log.e("getFragmentManager().getBackStackEntryCount()", Activity_Home.fragmentManager.getBackStackEntryCount()+"");
			for (int i = 0; i < Activity_Home.fragmentManager.getBackStackEntryCount(); i++) {

				Activity_Home.fragmentManager.popBackStack();

			}
		}

		super.onResume();
	}

	@Override
	public void onClick(View v) 
	{
		switch (v.getId()) 
		{
		case R.id.headermain_Imageview_icon:

			for (int i = 0; i < fragmentManager.getBackStackEntryCount(); i++) 
			{

				fragmentManager.popBackStack();

			}

			fragment = null;

			headeTXT.setText("HOME");

			static_explorer=false;

			check_categorysubmit=false;

			selectItem_explorer=-1;

			break;

		case R.id.headermain_Imageview_Menu:
			((Activity_Home.this)).menu.toggle();
			break;

		case R.id.headermain_img_search:
			Toast.makeText(context, "Search call", Toast.LENGTH_SHORT).show();
			break;
		case R.id.headermain_img_notification:
			selectItem(9);
			break;
		case R.id.footermain_img_reco:
			selectItem(11);
			break;
		case R.id.footermain_img_rate:
			selectItem(8);
			break;
		case R.id.footermain_img_explore:
			selectItem(7);
			break;

		default:
			break;
		}
	}

	// selectItem Fragment Number
	public void selectItem(int position)
	{

		if (position!=8) {
			Fragment_AddFeed.static_feedPhotoByte=null;
			Fragment_AddFeed.static_feedPhotoBitmap=null;
			if(Fragment_AddFeed.httpGooglePlace!=null)
			{
				Fragment_AddFeed.httpGooglePlace.cancelRequest();
				Fragment_AddFeed.httpGooglePlace=null;
			}
			check_categorysubmit=false;
			img_bottom_rate.setBackgroundResource(R.drawable.bottom_rate);
			img_bottom_reco.setBackgroundResource(R.drawable.bottom_reco);
			img_bottom_explore.setBackgroundResource(R.drawable.bottom_explor);
			lin_bottom_bg.setBackgroundResource(R.drawable.bottom_bar);
		}

		fragment=null;

		android.app.FragmentTransaction fragmenttransaction = fragmentManager.beginTransaction(); 

		switch (position)

		{
		case 0:

			headeTXT.setText(R.string.following);

			fragmentTagName=getResources().getString(R.string.following);

			if (selectItem_explorer!=0 || static_explorer==true) 
				fragment = new Fragment_Following();

			selectItem_explorer=0;

			break;

		case 1:


			headeTXT.setText(R.string.followers);

			fragmentTagName=getResources().getString(R.string.followers);

			if (selectItem_explorer!=1 || static_explorer==true) 
				fragment = new Fragment_Follower();

			selectItem_explorer=1;

			break;

		case 2:


			fragmentTagName=getResources().getString(R.string.setting);

			headeTXT.setText(R.string.setting);

			if (selectItem_explorer!=2 || static_explorer==true) 
				fragment = new Fragment_Setting();

			selectItem_explorer=2;

			break;

		case 3:

			for (int i = 0; i < fragmentManager.getBackStackEntryCount(); i++) {

				fragmentManager.popBackStack();

			}
			fragment = null;
			static_explorer=false;
			selectItem_explorer=-1;

			break;

		case 4:

			check_categorysubmit=false;

			fragmentTagName=getResources().getString(R.string.editprofile);
			headeTXT.setText(R.string.editprofile);
			if (selectItem_explorer!=4 || static_explorer==true) 
				fragment = new Fragment_EditProfile();
			selectItem_explorer=4;

			break;

		case 5:


			selectItem_explorer=5;

			fragmentTagName="";

			SharedPreferences Pref_google = getSharedPreferences(ConstantValue.PRE_GOOGLE_NAME, 0);
			SharedPreferences.Editor edt_google = Pref_google.edit();
			edt_google.clear();
			edt_google.putBoolean(ConstantValue.PRE_GOOGLELOGIN_FLAG,true);
			edt_google.commit();

			session.logoutUser();

			if (Activity_Login.mGoogleApiClient!=null) 

				if (Activity_Login.mGoogleApiClient.isConnected())
				{
					Plus.AccountApi.clearDefaultAccount(Activity_Login.mGoogleApiClient);
					Activity_Login.mGoogleApiClient.disconnect();
					Activity_Login.mGoogleApiClient.connect();
				}


			Activity_Login.check_Login=true;

			finish();

			break;

		case 7:

			headeTXT.setText(R.string.explore);
			if (selectItem_explorer!=7 || static_explorer==true) 
				startActivity(new Intent(context, Activity_Explore.class));
			fragmentTagName="";
			selectItem_explorer=7;
			break;
		case 8:
			if(	Activity_Home.check_categorysubmit)
			{

				callApiFragmentFeed();
			}
			else
			{
				check_categorysubmit=false;
				img_bottom_rate.setBackgroundResource(R.drawable.bottom_rate);
				img_bottom_reco.setBackgroundResource(R.drawable.bottom_reco);
				img_bottom_explore.setBackgroundResource(R.drawable.bottom_explor);
				lin_bottom_bg.setBackgroundResource(R.drawable.bottom_bar);

				headeTXT.setText("");
				fragmentTagName=getResources().getString(R.string.category_fragment);
				if (selectItem_explorer!=8 || static_explorer==true) 
					fragment = new Fragment_Category();
				selectItem_explorer=8;



			}

			break;
		case 9:
			fragmentTagName=getResources().getString(R.string.notifications);
			headeTXT.setText(R.string.notifications);
			if (selectItem_explorer!=9 || static_explorer==true) 
				fragment = new Fragment_Notification();
			selectItem_explorer=9;
			break;

		case 10:
			fragmentTagName=getResources().getString(R.string.profile);
			headeTXT.setText(R.string.profile);
			if (selectItem_explorer!=10 || static_explorer==true) 
				fragment = new Fragment_ViewPersonProfile();
			selectItem_explorer=10;
			break;

		case 11:
			fragmentTagName=getResources().getString(R.string.recos);
			headeTXT.setText(R.string.recos);
			if (selectItem_explorer!=11 || static_explorer==true)
				fragment = new Fragment_Recos();
			selectItem_explorer=11;

			break;
		default:
			break;
		}
		static_explorer=false;
		Log.e("selectItem_explorer", selectItem_explorer+"");
		if (selectItem_explorer!=7 && selectItem_explorer!=5 && selectItem_explorer!=-2) {


			if (!fragmentTagName.equalsIgnoreCase("")) {

				if (fragment!=null) {


					if (fragment.isAdded()) {
						fragmenttransaction.show(fragment);
						fragmenttransaction.addToBackStack(null);


					} else {
						fragmenttransaction.add(R.id.profile_content_frame, fragment,fragmentTagName);
						fragmenttransaction.addToBackStack(fragmentTagName);
					}
					fragmenttransaction.commit();
				}
			}
		}
	}

	private void setMainHomeFeedUI() 
	{
		LayoutInflater lyt_homefeed = (LayoutInflater) context
				.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

		ViewHolder_MainHomeFeed holder = null;
		lin_mainHome.removeAllViews();
		for (int j = 0; j < static_arraylist_FeedInfo.size(); j++)
		{
			View view_homefeed = null ;

			final FeedInfo feedinfo = static_arraylist_FeedInfo.get(j);

			view_homefeed = lyt_homefeed.inflate(R.layout.lyt_homefeeddisply,null);

			holder = new ViewHolder_MainHomeFeed();

			holder.txt_address = (TextView) view_homefeed
					.findViewById(R.id.lyt_homefeeddisply_txt_address);
			holder.txt_feedname = (TextView) view_homefeed
					.findViewById(R.id.lyt_homefeeddisply_txt_feedname);
			holder.txt_placename = (TextView) view_homefeed
					.findViewById(R.id.lyt_homefeeddisply_txt_placename);
			holder.txt_username = (TextView) view_homefeed
					.findViewById(R.id.lyt_homefeeddisply_txt_username);
			holder.txt_postdate = (TextView) view_homefeed
					.findViewById(R.id.lyt_homefeeddisply_txt_postdate);
			holder.txt_expiriance = (TextView) view_homefeed
					.findViewById(R.id.lyt_homefeeddisply_txt_expiriance);
			holder.txt_approvedcount= (TextView) view_homefeed
					.findViewById(R.id.lyt_homefeeddisply_txt_approved);
			holder.txt_commentscount= (TextView) view_homefeed
					.findViewById(R.id.lyt_homefeeddisply_txt_comment);
			holder.img_approvedcount= (ImageView) view_homefeed
					.findViewById(R.id.lyt_homefeeddisply_img_approved);
			holder.img_commentcount= (ImageView) view_homefeed
					.findViewById(R.id.lyt_homefeeddisply_img_comment);
			holder.img_feedimage = (ImageView) view_homefeed
					.findViewById(R.id.lyt_homefeeddisply_img_feedimage);
			holder.img_feedimage.setScaleType(ScaleType.FIT_XY);
			holder.img_rateimage = (ImageView) view_homefeed
					.findViewById(R.id.lyt_homefeeddisply_img_rateimage);
			holder.img_userimage = (ImageView) view_homefeed
					.findViewById(R.id.lyt_homefeeddisply_img_userimage);
			holder.lin_addComment=(LinearLayout) view_homefeed.findViewById(R.id.lyt_homefeed_lin_comments);

			holder.txt_expiriance.setTypeface(type_BOLD);
			holder.txt_feedname.setTypeface(type_BOLD);
			holder.txt_placename.setTypeface(type_BOLD);
			holder.txt_username.setTypeface(type_BOOK);
			holder.txt_postdate.setTypeface(type_BOOK);
			holder.txt_address.setTypeface(type_BOOK);
			holder.txt_commentscount.setTypeface(type_BOOK);
			holder.txt_approvedcount.setTypeface(type_BOOK);



			view_homefeed.setTag(holder);

			holder.txt_expiriance.setText(feedinfo.experiance);
			holder.txt_feedname.setText(feedinfo.category_name);
			holder.txt_placename.setText(feedinfo.location_name);
			holder.txt_postdate.setText(feedinfo.date_time);
			holder.txt_username.setText(feedinfo.user_name);
			holder.txt_address.setText(feedinfo.location_name);

			holder.txt_approvedcount.setText(feedinfo.num_of_approve+"");
			holder.txt_commentscount.setText(feedinfo.num_of_comments+"");

			holder.lin_addComment.removeAllViews();

			for (int i = 0; i < feedinfo.arrylist_commentInfo.size(); i++) {


				TextView txt_username=new TextView(this);
				txt_username.setText(feedinfo.arrylist_commentInfo.get(i).user_name);
				txt_username.setTextColor(getResources().getColor(R.color.line_color));
				txt_username.setTypeface(type_BOOK);
				txt_username.setTextSize(11);

				TextView txt_coment=new TextView(this);
				txt_coment.setTextColor(Color.BLACK);
				txt_coment.setPadding(5, 0,0,0);
				txt_coment.setText(feedinfo.arrylist_commentInfo.get(i).comment);
				txt_coment.setTypeface(type_BOOK);
				txt_coment.setTextSize(11);

				LinearLayout lin_coment=new LinearLayout(this);
				lin_coment.setPadding(32, 0,0,0);
				lin_coment.addView(txt_username);
				lin_coment.addView(txt_coment);

				holder.lin_addComment.addView(lin_coment);
			}



			if (!feedinfo.rate_image.equalsIgnoreCase("")) 
				imgloader.DisplayImage(feedinfo.rate_image, holder.img_rateimage);

			if (!feedinfo.feed_image.equalsIgnoreCase("")) 
				imgloader.DisplayImage(feedinfo.feed_image, holder.img_feedimage);

			if (feedinfo.profile_picture_bitmap!=null) 
			{
				holder.img_userimage.setImageBitmap(feedinfo.profile_picture_bitmap);
			}

			holder.img_commentcount.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {

					addCommentDialog(feedinfo.feed_id);
				}
			});

			holder.img_approvedcount.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {

					setApiApprovFeed(feedinfo.feed_id);
				}
			});
			holder.txt_placename.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {

					//					fragmentTagName=;
					//					headeTXT.setText(R.string.home);
					//					if (selectItem_explorer!=3 || static_explorer==true) 
					//						fragment = new Fragment_Home();
					//					selectItem_explorer=3;

					selectItem_explorer=-2;
					check_categorysubmit=false;
					Fragment fragment = null;
					FragmentManager fragmentManager = getFragmentManager();
					android.app.FragmentTransaction fragmenttransaction = fragmentManager.beginTransaction(); 
					fragment = new Fragment_PlaceProfile();

					String fragmentTagName =getResources().getString(R.string.place_profile);
					if (!fragmentTagName.equalsIgnoreCase("")) {

						if (fragment!=null) {
							if (fragment.isAdded()) {
								fragmenttransaction.show(fragment);
								fragmenttransaction.addToBackStack(null);
							} else {
								fragmenttransaction.add(R.id.profile_content_frame, fragment,fragmentTagName);
								fragmenttransaction.addToBackStack(fragmentTagName);
							}
							fragmenttransaction.commit();
						}
					}

				}
			});

			lin_mainHome.addView(view_homefeed);
		}

	}


	@Override
	public void onBackPressed() {
		if(Fragment_AddFeed.httpGooglePlace!=null)
		{
			Fragment_AddFeed.httpGooglePlace.cancelRequest();
			Fragment_AddFeed.httpGooglePlace=null;
		}
		if (fragmentManager.getBackStackEntryCount()==0) {


			final Dialog dialog = new Dialog(context);
			dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
			dialog.setContentView(R.layout.alterdialog);

			dialog.setCancelable(true);
			final Button btn_yes, btn_no;
			TextView txt_title;
			btn_yes = (Button) dialog.findViewById(R.id.alterdialog_btn_yesfirst);
			btn_no = (Button) dialog.findViewById(R.id.alterdialog_btn_nosecond);
			txt_title = (TextView) dialog.findViewById(R.id.alterdialog_txt_title);

			txt_title.setText(getResources().getString(R.string.alert_exit));
			btn_yes.setText(getResources().getString(R.string.alert_yes));
			btn_no.setText(getResources().getString(R.string.alert_no));

			btn_yes.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					dialog.dismiss();
					finish();
				}
			});

			btn_no.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					dialog.dismiss();
				}
			});

			dialog.show();
		}
		else
		{
			if ( fragmentManager.getBackStackEntryCount()==1) {
				fragmentTagName="";
				fragment=null;
				selectItem_explorer=-1;
				headeTXT.setText("HOME");
			}
			else if ( fragmentManager.getBackStackEntryCount()>1) 
			{
				check_categorysubmit=false;
				img_bottom_rate.setBackgroundResource(R.drawable.bottom_rate);
				img_bottom_reco.setBackgroundResource(R.drawable.bottom_reco);
				img_bottom_explore.setBackgroundResource(R.drawable.bottom_explor);
				lin_bottom_bg.setBackgroundResource(R.drawable.bottom_bar);

				BackStackEntry backstackentry=	fragmentManager.getBackStackEntryAt(fragmentManager.getBackStackEntryCount()-2);
				String lastfragmentName=backstackentry.getName();
				headeTXT.setText(lastfragmentName);

				if (lastfragmentName!=null) {


					if (lastfragmentName.equalsIgnoreCase("addfeed")) {
						check_categorysubmit=true;
						selectItem_explorer=8;
						img_bottom_rate.setBackgroundResource(R.drawable.bottom_rate2);
						img_bottom_reco.setBackgroundResource(R.drawable.bottom_reco2);
						img_bottom_explore.setBackgroundResource(R.drawable.bottom_explor2);
						lin_bottom_bg.setBackgroundResource(R.drawable.bottom_bar2);

					}
					else if (lastfragmentName.equalsIgnoreCase(getResources().getString(R.string.recos))) {
						selectItem_explorer=11;


					}
					else if (lastfragmentName.equalsIgnoreCase(getResources().getString(R.string.profile))) {
						selectItem_explorer=10;


					}
					else if (lastfragmentName.equalsIgnoreCase(getResources().getString(R.string.notifications))) {
						selectItem_explorer=9;


					}
					else if (lastfragmentName.equalsIgnoreCase(getResources().getString(R.string.category_fragment))) {
						selectItem_explorer=8;
						headeTXT.setText("");

					}
					else if (lastfragmentName.equalsIgnoreCase(getResources().getString(R.string.editprofile))) {
						selectItem_explorer=4;


					}
					else if (lastfragmentName.equalsIgnoreCase(getResources().getString(R.string.home))) {
						selectItem_explorer=3;



					}
					else if (lastfragmentName.equalsIgnoreCase(getResources().getString(R.string.setting))) {
						selectItem_explorer=2;

					}
					else if (lastfragmentName.equalsIgnoreCase(getResources().getString(R.string.followers))) {
						selectItem_explorer=1;

					}
					else if (lastfragmentName.equalsIgnoreCase(getResources().getString(R.string.following))) {
						selectItem_explorer=0;
					}
				}

			}
			else
			{

			}

			//			if (!check_categorysubmit) {
			//				img_bottom_rate.setBackgroundResource(R.drawable.bottom_rate);
			//				img_bottom_reco.setBackgroundResource(R.drawable.bottom_reco);
			//				img_bottom_explore.setBackgroundResource(R.drawable.bottom_explor);
			//				lin_bottom_bg.setBackgroundResource(R.drawable.bottom_bar);
			//			}
			//			else
			//			{
			//				check_categorysubmit=false;
			//				img_bottom_rate.setBackgroundResource(R.drawable.bottom_rate2);
			//				img_bottom_reco.setBackgroundResource(R.drawable.bottom_reco2);
			//				img_bottom_explore.setBackgroundResource(R.drawable.bottom_explor2);
			//				lin_bottom_bg.setBackgroundResource(R.drawable.bottom_bar2);
			//			}
			super.onBackPressed();
		}

	}
	private void addCommentDialog(final String feed_id) 
	{
		View mView = View.inflate(this, R.layout.pop_dialogcomment, null);

		final EditText  edt_comment = ((EditText) mView.findViewById(R.id.pop_dialogcomment_edt_comment));

		final InputMethodManager mInputMethodManager = (InputMethodManager) this
				.getSystemService(Context.INPUT_METHOD_SERVICE);
		mInputMethodManager.restartInput(mView);

		AlertDialog.Builder mBuilder = new AlertDialog.Builder(this);
		mBuilder.setTitle("ENTER COMMENT");

		mBuilder.setPositiveButton("save", new Dialog.OnClickListener() 
		{
			public void onClick(DialogInterface mDialogInterface, int mWhich)
			{
				String commentString = edt_comment.getText().toString().trim();

				if (commentString.length() > 0) 
				{

					String uri = null;
					uri = String.format(ConstantValue.URL_COMMENT_ADD);
					if (uri != null)
					{
						uri = uri.replace(" ", "%20");

						ArrayList<NameValuePair> nameValuePair = new ArrayList<NameValuePair>(2);
						nameValuePair.add(new BasicNameValuePair("user_id",
								ConstantValue.user
								.get(SessionManager.KEY_USERID)));

						nameValuePair.add(new BasicNameValuePair("feed_id",
								feed_id));
						nameValuePair.add(new BasicNameValuePair("comment",
								commentString));

						Log.i("Parameter", nameValuePair.toString());
						HttpHelper httpHelper = new HttpHelper(ConstantValue.REQUESTCODE_COMMENT_ADD,
								Activity_Home.this,"Comments...", nameValuePair);
						httpHelper.execute(uri);
					}

				}
				mInputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
				mDialogInterface.dismiss();
			}

		});
		mBuilder.setView(mView);
		mBuilder.show();

		if (mInputMethodManager != null) {
			mInputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
		}
	}
	public void set_TypeFace_Control() {
		headeTXT.setTypeface(type_BOLD);
	}
	public void initalization() {

		fragment = null;

		static_explorer=false;
		check_categorysubmit=false;
		selectItem_explorer=-1;
		fragmentManager = getFragmentManager();
		type_BOLD = Typeface.createFromAsset(getAssets(),
				"fonts/Novecentowide-Bold.otf");
		type_BOOK = Typeface.createFromAsset(getAssets(),
				"fonts/Novecentowide-Book.otf");

		session = new SessionManager(getApplicationContext(),this);

		ConstantValue.user = session.getUserDetails();


		scroll_mainHome.setVisibility(View.GONE);

		scroll_mainHome.setVisibility(View.VISIBLE);


	}
	private void setUserImage() 
	{
		if (!ConstantValue.user.get(SessionManager.KEY_PROFILE_IMG).equals("")) {



			img_slider_UserPhoto.setScaleType(ScaleType.FIT_XY);

			imgloader.DisplayImage(
					ConstantValue.user.get(SessionManager.KEY_PROFILE_IMG),
					img_slider_UserPhoto);
		}

	}
	private void feedViewApiCall() 
	{

		if (isOnline()) // connected
		{
			String uri = null;

			uri = String.format(ConstantValue.URL_FEED_VIEW);



			if (uri != null) 
			{
				uri = uri.replace(" ", "%20");

				List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>(
						2);
				TelephonyManager telephonyManager = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);

				nameValuePair.add(new BasicNameValuePair("user_id",
						ConstantValue.user.get(SessionManager.KEY_USERID)));
				nameValuePair.add(new BasicNameValuePair("date_time",current_date_time));
				nameValuePair.add(new BasicNameValuePair("device_width",ConstantValue.DEVICE_WIDTH+""));
				nameValuePair.add(new BasicNameValuePair("device_height",ConstantValue.DEVICE_HEIGHT+""));
				nameValuePair.add(new BasicNameValuePair("device_id",telephonyManager.getDeviceId()+""));
				Log.e("nameValuePair feed View", current_date_time);
				Log.e("device_height feed View", ConstantValue.DEVICE_WIDTH+"");
				Log.e("device_width feed View", ConstantValue.DEVICE_HEIGHT+"");
				Log.e("device_id feed View", telephonyManager.getDeviceId()+"");


				HttpHelper httpHelper = new HttpHelper(
						ConstantValue.REQUESTCODE_FEED_VIEW,this, "Loading...",nameValuePair);
				httpHelper.execute(uri);
			}

		} else {

			Toast.makeText(getApplicationContext(),
					"Internet Connection Fail", Toast.LENGTH_SHORT).show();
		}

	}
	private void callApiFragmentFeed()
	{
		if (isOnline()) // connected
		{
			final String category_id = Fragment_AddFeed.txt_categoryname.getTag().toString();
			final String str_expiriance = Fragment_AddFeed.edt_discription.getText().toString();
			final String str_itemname = Fragment_AddFeed.txtedt_feedname.getText().toString();
			final String str_location_name = Fragment_AddFeed.txtedt_location.getText().toString();
			final String str_longitude=Utills.CURRENT_LONGITUDE_KEY+"";
			final String str_lattitude=Utills.CURRENT_LATTITUDE_KEY+"";
			//			final String str_longitude=Utills.CURRENT_LONGITUDE+"";
			//			final String str_lattitude=Utills.CURRENT_LATTITUDE+"";

			Log.e("callApiFragmentFeed", "true");
			if (Fragment_AddFeed.static_feedPhotoByte!=null  && !str_itemname.equalsIgnoreCase("") && !str_location_name.equalsIgnoreCase("")) 
			{

				AlertDialog.Builder mBuilder = new AlertDialog.Builder(this);
				mBuilder.setTitle("     Conformation   ");
				mBuilder.setMessage(" Do you Want to upload Feed..?  ");

				mBuilder.setPositiveButton("OK", new Dialog.OnClickListener() 
				{
					public void onClick(DialogInterface mDialogInterface, int mWhich)
					{
						mDialogInterface.dismiss();
						getCurrentDateTime();
						selectItem_explorer=-2;
						fragmentTagName="";
						fragment=null;
						Activity_Home.check_categorysubmit=false;


						String uri = null;
						uri = String.format(ConstantValue.URL_FEED_ADD);
						if (uri != null) {
							uri = uri.replace(" ", "%20");

							//							str_longitude = Utills.CURRENT_LATTITUDE+"";
							//							 str_longitude = Utills.CURRENT_LONGITUDE+"";

							Log.e("str_longitude", str_longitude);
							Log.e("str_lattitude", str_lattitude);

							Bundle nameValuePair = new Bundle();
							Log.e("userid", ConstantValue.user.get(SessionManager.KEY_USERID)+"");

							nameValuePair.putString("user_id",ConstantValue.user.get(SessionManager.KEY_USERID));
							nameValuePair.putString("location_name",str_location_name);
							nameValuePair.putString("category_id",category_id);
							nameValuePair.putString("feed_name",str_itemname);
							nameValuePair.putString("experiance",str_expiriance);
							nameValuePair.putString("longitude",str_longitude);
							nameValuePair.putString("lattitude",str_lattitude);
							nameValuePair.putString("date_time",current_date_time);
							TelephonyManager telephonyManager = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
							nameValuePair.putString("device_id",telephonyManager.getDeviceId()+"");

							Log.e("device_id feed add", telephonyManager.getDeviceId()+"");
							Log.e("str_location_name", str_location_name);
							Log.e("str_itemname", str_itemname);
							Log.e("category_id", category_id);
							Log.e("str_expiriance", str_expiriance);
							Log.e("current_date_time", current_date_time);

							HttpHelper httpHelper = new HttpHelper(
									ConstantValue.REQUESTCODE_FEED_ADD,
									Activity_Home.this, "Loading...",
									nameValuePair,Fragment_AddFeed.static_feedPhotoByte);
							httpHelper.execute(uri);
						}
					}

				});
				mBuilder.setNegativeButton("Cancel", new Dialog.OnClickListener() 
				{
					public void onClick(DialogInterface mDialogInterface, int mWhich)
					{
						mDialogInterface.dismiss();
					}

				});
				AlertDialog alertDialog = mBuilder.create();

				// show it
				alertDialog.show();




			}
			else
			{
				Log.e("Alert Feed", "openAlert Feed");

				AlertDialog.Builder mBuilder = new AlertDialog.Builder(this);
				mBuilder.setTitle("     ERROR   ");
				mBuilder.setMessage(" Please Fillup All Field   ");

				mBuilder.setPositiveButton("OK", new Dialog.OnClickListener() 
				{
					public void onClick(DialogInterface mDialogInterface, int mWhich)
					{
						mDialogInterface.dismiss();
					}

				});
				AlertDialog alertDialog = mBuilder.create();

				// show it
				alertDialog.show();
			}
		}
		else
		{
			Log.e("Alert Feed", "openAlert Feed");

			AlertDialog.Builder mBuilder = new AlertDialog.Builder(this);
			mBuilder.setTitle("     ERROR   ");
			mBuilder.setMessage(" Please Check Your Internet Connection   ");

			mBuilder.setPositiveButton("OK", new Dialog.OnClickListener() 
			{
				public void onClick(DialogInterface mDialogInterface, int mWhich)
				{
					mDialogInterface.dismiss();
				}

			});
			AlertDialog alertDialog = mBuilder.create();

			// show it
			alertDialog.show();
		}
	}
	private void setApiApprovFeed(final String feed_id) 
	{

		String uri = null;
		uri = String.format(ConstantValue.URL_APPROVE_ADD);
		if (uri != null)
		{
			uri = uri.replace(" ", "%20");

			ArrayList<NameValuePair> nameValuePair = new ArrayList<NameValuePair>(2);
			nameValuePair.add(new BasicNameValuePair("user_id",
					ConstantValue.user
					.get(SessionManager.KEY_USERID)));

			nameValuePair.add(new BasicNameValuePair("feed_id",
					feed_id));

			Log.i("Parameter", nameValuePair.toString());
			HttpHelper httpHelper = new HttpHelper(ConstantValue.REQUESTCODE_APPROVE_VIEW,
					this,"Comments...", nameValuePair);
			httpHelper.execute(uri);
		}
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
					img_slider_UserPhoto.setScaleType(ScaleType.FIT_XY);
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
		} else {
		}

	}


	private void menuSliderMethod()
	{
		menu = new SlidingMenu(this);

		menuSliderAdapter = new MenuSliderAdapter();

		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);


		for (int i = 0; i < Activity_Home.SlideMenu_Title.length; i++) {
			menuSliderAdapter.addItem(SlideMenu_Title[i]);
		}

		view_slider = inflater.inflate(R.layout.slidingmenu_main, null, true);

		list_sliderList = (ListView) view_slider.findViewById(R.id.slidingmenu_Listview_list);
		txt_slider_userName = (TextView) view_slider.findViewById(R.id.slidingmenu_TextView_Username);
		txt_slider_location = (TextView) view_slider.findViewById(R.id.slidingmenu_TextView_location);

		txt_slider_userName.setText(ConstantValue.user.get(SessionManager.KEY_FIRSTNAME) + " "
				+ ConstantValue.user.get(SessionManager.KEY_LASTNAME));
		txt_slider_location.setText(ConstantValue.user.get(SessionManager.KEY_CITY));

		captureIMG = (ImageView) view_slider.findViewById(R.id.slidingmenu_Imageview_cameracapture);

		img_slider_UserPhoto = (ImageView) view_slider.findViewById(R.id.slidingmenu_Imageview_UserImage);


		captureIMG.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				calldialogSelectImage();
			}
		});


		menu.setMode(SlidingMenu.LEFT);
		menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
		menu.setShadowWidthRes(R.dimen.shadow_width);

		menu.setBehindOffsetRes(R.dimen.slidingmenu_offset);
		menu.setFadeDegree(0.35f);
		menu.attachToActivity(this, SlidingMenu.SLIDING_CONTENT);
		menu.setMenu(view_slider);
		menu.setSlidingEnabled(true);

		list_sliderList.setAdapter(menuSliderAdapter);

		list_sliderList.setOnItemClickListener(new DrawerItemClickListener());
	}
	private void findIdFromXml() 
	{
		scroll_mainHome = (ScrollView)findViewById(R.id.fragment_scroll_mainhomefeed);
		lin_mainHome = (LinearLayout)findViewById(R.id.fragment_scroll_lin_mainhomefeed);
		headeTXT = (TextView) findViewById(R.id.headermain_Textview_label);
		homeIMG = (ImageView) findViewById(R.id.headermain_Imageview_Menu);
		img_logoMain = (ImageView) 	findViewById(R.id.headermain_Imageview_icon);
		img_top_search = (ImageView) findViewById(R.id.headermain_img_search);
		img_top_notification = (ImageView) findViewById(R.id.headermain_img_notification);
		img_bottom_reco = (ImageView) findViewById(R.id.footermain_img_reco);
		img_bottom_rate = (ImageView) findViewById(R.id.footermain_img_rate);
		img_bottom_explore = (ImageView) findViewById(R.id.footermain_img_explore);
		lin_bottom_bg = (LinearLayout) findViewById(R.id.footermain_lin_bottom_bar);
	}
	private void setClickListener() 
	{
		homeIMG.setOnClickListener(this);
		img_logoMain.setOnClickListener(this);
		img_top_search.setOnClickListener(this);
		img_top_notification.setOnClickListener(this);
		img_bottom_reco.setOnClickListener(this);
		img_bottom_explore.setOnClickListener(this);
		img_bottom_rate.setOnClickListener(this);
	}

	private class MenuSliderAdapter extends BaseAdapter {

		private static final int TYPE_ITEM = 0;
		private static final int TYPE_SEPARATOR = 1;
		private static final int TYPE_MAX_COUNT = TYPE_SEPARATOR + 1;

		private ArrayList<String> mData = new ArrayList<String>();
		private LayoutInflater mInflater;

		private TreeSet<Integer> mSeparatorsSet = new TreeSet<Integer>();

		public MenuSliderAdapter() {
			mInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		}

		public void addItem(final String item) {
			mData.add(item);
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
			return mData.size();
		}

		public String getItem(int position) {
			return mData.get(position);
		}

		public long getItemId(int position) {
			return position;
		}

		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder_SliderAdpter holder = null;


			if (convertView == null)
			{

				holder = new ViewHolder_SliderAdpter();

				convertView = mInflater.inflate(R.layout.slidingmenu_row, null);

				holder.textView = (TextView) convertView
						.findViewById(R.id.slidingmenu_Textview_name);
				holder.textView.setTypeface(type_BOLD);
				holder.list_layout = (LinearLayout) convertView
						.findViewById(R.id.slidingMenu_Linear);
				holder.textView.setText(mData.get(position));
				convertView.setTag(holder);

			}

			else 
			{
				holder = (ViewHolder_SliderAdpter) convertView.getTag();
			}

			return convertView;
		}

	}
	private void getCurrentDateTime() {
		Calendar c = Calendar.getInstance(); 
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		current_date_time = formatter.format(c.getTime());
	}

	private String getPath(Uri uri) {

		String[] projection = { MediaStore.Images.Media.DATA };
		@SuppressWarnings("deprecation")
		Cursor cursor = managedQuery(uri, projection, null, null, null);
		int column_index = cursor
				.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
		cursor.moveToFirst();
		return cursor.getString(column_index);
	}
	private Uri getOutputMediaFileUri(int type) {
		return Uri.fromFile(getOutputMediaFile(type));
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

	private void previewCapturedImage() 
	{
		try
		{

			BitmapFactory.Options options = new BitmapFactory.Options();

			options.inSampleSize = ConstantValue.IMAGE_CAPTURE_OPTION;

			final Bitmap bitmap = BitmapFactory.decodeFile(fileUri.getPath(),
					options);

			img_slider_UserPhoto.setImageBitmap(bitmap);
			img_slider_UserPhoto.setScaleType(ScaleType.FIT_XY);
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
	private void calldialogSelectImage() {

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
	private String performPost_ByteArray(byte [] bry1)
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
	private class DrawerItemClickListener implements

	ListView.OnItemClickListener {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			selectItem(position);
			menu.toggle();
		}
	}
	private static class ViewHolder_SliderAdpter {
		public TextView textView;
		public ImageView imageView;
		public LinearLayout list_layout;
	}

	private static class ViewHolder_MainHomeFeed {
		public TextView txt_address;

		ImageView img_userimage, img_feedimage,img_rateimage,img_commentcount,img_approvedcount;

		TextView txt_username,txt_expiriance,txt_postdate,txt_feedname,txt_placename,
		txt_approvedcount,txt_commentscount;

		LinearLayout lin_addComment;

	}
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);

		outState.putParcelable("file_uri", fileUri);
	}

	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		super.onRestoreInstanceState(savedInstanceState);

		fileUri = savedInstanceState.getParcelable("file_uri");
	}
	@Override
	public void onDestroy() {
		super.onDestroy();
		System.gc();
	}
}

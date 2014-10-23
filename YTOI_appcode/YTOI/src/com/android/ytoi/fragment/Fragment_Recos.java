package com.android.ytoi.fragment;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.ytoi.Activity_Home;
import com.android.ytoi.R;
import com.android.ytoi.adapter.FollowerAdapter;
import com.android.ytoi.imageloader.ImageLoader;
import com.android.ytoi.utils.Act_Fragment;
import com.android.ytoi.utils.ConstantValue;
import com.android.ytoi.utils.SessionManager;
import com.android.ytoi.web.HttpHelper_Fragment;
import com.android.ytoi.webclass.FeedInfo;

public class Fragment_Recos extends Act_Fragment implements OnClickListener{

	View rootView;
	Context context;
	private Typeface type_BOLD, type_BOOK;
	ListView list_recos;
	FollowerAdapter adapter;
	private String current_date_time;
	private LinearLayout scroll_lin_mainRecos;
	private ScrollView scroll_mainRecos;
	private ImageLoader imgloader;
	public Fragment_Recos() {
	}

	
//	@SuppressWarnings("unchecked")
//	public void setBackApiResponse(int requestcode, Object obj1, Object obj2,Object obj3) {
//
//		if(requestcode == ConstantValue.REQUESTCODE_FEED_VIEW)
//		{
//			if(obj1!=null)
//				if (((String)obj1).equalsIgnoreCase("1")) 
//				{
//					Toast.makeText(context, obj2.toString(), Toast.LENGTH_SHORT)
//					.show();
//					static_arraylist_FeedInfo.clear();
//					static_arraylist_Category.clear();
//					static_arraylist_FeedInfo=(ArrayList<FeedInfo>) obj3;
//
//					if (ConstantValue.user.get(SessionManager.KEY_PROFILE_IMG).equalsIgnoreCase("")) 
//					{
//
//						img_slider_UserPhoto.setScaleType(ScaleType.FIT_XY);
//
//						imgloader.DisplayImage(
//								HttpHelper.static_profile_picture,
//								img_slider_UserPhoto);
//
//					}
//
//					setMainHomeFeedUI();
//
//
//				} else
//					Toast.makeText(context, obj2.toString(), Toast.LENGTH_SHORT)
//					.show();
//		}
//
//
//	}
	private static class ViewHolder_MainHomeFeed {
		public TextView txt_address;

		ImageView img_userimage, img_feedimage,img_rateimage,img_commentcount,img_approvedcount;

		TextView txt_username,txt_expiriance,txt_postdate,txt_feedname,txt_placename,
		txt_approvedcount,txt_commentscount;

		LinearLayout lin_addComment;

	}
	private void setMainHomeFeedUI() 
	{
		LayoutInflater lyt_homefeed = (LayoutInflater) context
				.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

		ViewHolder_MainHomeFeed holder = null;
		scroll_lin_mainRecos.removeAllViews();

		for (int j = 0; j < Activity_Home.static_arraylist_FeedInfo.size(); j++)
		{
			View view_homefeed = null ;

			final FeedInfo feedinfo = Activity_Home.static_arraylist_FeedInfo.get(j);

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


				TextView txt_username=new TextView(context);
				txt_username.setText(feedinfo.arrylist_commentInfo.get(i).user_name);
				txt_username.setTextColor(getResources().getColor(R.color.line_color));
				txt_username.setTypeface(type_BOOK);
				txt_username.setTextSize(11);

				TextView txt_coment=new TextView(context);
				txt_coment.setTextColor(Color.BLACK);
				txt_coment.setPadding(5, 0,0,0);
				txt_coment.setText(feedinfo.arrylist_commentInfo.get(i).comment);
				txt_coment.setTypeface(type_BOOK);
				txt_coment.setTextSize(11);

				LinearLayout lin_coment=new LinearLayout(context);
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

					//addCommentDialog(feedinfo.feed_id);
				}
			});

			holder.img_approvedcount.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {

					//setApiApprovFeed(feedinfo.feed_id);
				}
			});
		

			scroll_lin_mainRecos.addView(view_homefeed);
		}
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		rootView = inflater.inflate(R.layout.recos, container,
				false);
		
		context = rootView.getContext();
		
		type_BOLD = Typeface.createFromAsset(getActivity().getAssets(),
				"fonts/Novecentowide-Bold.otf");
		type_BOOK = Typeface.createFromAsset(getActivity().getAssets(),
				"fonts/Novecentowide-Book.otf");
		imgloader=new ImageLoader(context,false);
		findIdfromXml();
		initialization();
		

		setMainHomeFeedUI();
		
		
		//feedViewApiCall();
		return rootView;
	}
	private void feedViewApiCall() 
	{

		if (isOnline()) // connected
		{
			String uri = null;

			uri = String.format(ConstantValue.URL_FEED_VIEW);

		
			DisplayMetrics dm = new DisplayMetrics();
			getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
			int device_width = dm.widthPixels;
			int device_height = dm.heightPixels;

			if (uri != null) 
			{
				uri = uri.replace(" ", "%20");

				List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>(
						2);
				TelephonyManager telephonyManager = (TelephonyManager)getActivity().getSystemService(Context.TELEPHONY_SERVICE);

				nameValuePair.add(new BasicNameValuePair("user_id",
						ConstantValue.user.get(SessionManager.KEY_USERID)));
				nameValuePair.add(new BasicNameValuePair("date_time",current_date_time));
				nameValuePair.add(new BasicNameValuePair("device_width",device_width+""));
				nameValuePair.add(new BasicNameValuePair("device_height",device_height+""));
				nameValuePair.add(new BasicNameValuePair("device_id",telephonyManager.getDeviceId()+""));
				Log.e("nameValuePair feed View", current_date_time);
				Log.e("device_height feed View", device_height+"");
				Log.e("device_width feed View", device_width+"");
				Log.e("device_id feed View", telephonyManager.getDeviceId()+"");


				HttpHelper_Fragment httpHelper_fragment = new HttpHelper_Fragment(
						ConstantValue.REQUESTCODE_FEED_VIEW,Fragment_Recos.this, "Loading...",nameValuePair);
				httpHelper_fragment.execute(uri);
			}

		} else {

			Toast.makeText(context,
					"Internet Connection Fail", Toast.LENGTH_SHORT).show();
		}

	}
	private void findIdfromXml() 
	{
		scroll_mainRecos = (ScrollView)rootView.findViewById(R.id.recos_scroll_recosdesply);
		scroll_lin_mainRecos = (LinearLayout)rootView.findViewById(R.id.recos_scroll_lin_recosdesply);
	}

	public void initialization() {

	}
	@Override
	public void onClick(View arg0) {

	}
	
	private void getCurrentDateTime() {
		Calendar c = Calendar.getInstance(); 
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		current_date_time = formatter.format(c.getTime());
	}
}


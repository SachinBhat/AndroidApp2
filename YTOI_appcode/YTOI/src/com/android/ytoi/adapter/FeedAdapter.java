package com.android.ytoi.adapter;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.ytoi.R;
import com.android.ytoi.webclass.FeedDetail;


public class FeedAdapter extends ArrayAdapter<FeedDetail> {

	private Context context;
	Typeface type_BOOK,type_BOLD;

	public FeedAdapter(Context asyncTask, int resourceId, List<FeedDetail> items) {
		super(asyncTask, resourceId, items);
		this.context = asyncTask;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		type_BOOK = Typeface.createFromAsset(context.getAssets(),
				"fonts/Novecentowide-Book.otf");
		type_BOLD = Typeface.createFromAsset(context.getAssets(),
				"fonts/Novecentowide-Bold.otf");
		final FeedDetail rowItem = getItem(position);
		System.out.println("Row item :-" + rowItem.toString());
		LayoutInflater mInflater = (LayoutInflater) context
				.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.feed_home_row, null);
			holder = new ViewHolder();
			holder.userNameTextview = (TextView) convertView
					.findViewById(R.id.feedhomerow_Textview_username);
			holder.rateTextview = (TextView) convertView
					.findViewById(R.id.feedhomerow_Textview_rate);
			holder.toTextview = (TextView) convertView
					.findViewById(R.id.feedhomerow_Textview_to);
			holder.atTextview = (TextView) convertView
					.findViewById(R.id.feedhomerow_Textview_at);
			holder.hoursTextview = (TextView) convertView
					.findViewById(R.id.feedhomerow_Textview_hours);
			holder.hoursTxtTextview = (TextView) convertView
					.findViewById(R.id.feedhomerow_Textview_hoursTXT);
			holder.nearTextview = (TextView) convertView
					.findViewById(R.id.feedhomerow_Textview_near);
			holder.locationTextview = (TextView) convertView
					.findViewById(R.id.feedhomerow_Textview_location);
			holder.placeTextview = (TextView) convertView
					.findViewById(R.id.feedhomerow_Textview_place);

			holder.userNameTextview.setTypeface(type_BOLD);
			holder.rateTextview.setTypeface(type_BOLD);
			holder.toTextview.setTypeface(type_BOLD);
			holder.atTextview.setTypeface(type_BOLD);
			holder.placeTextview.setTypeface(type_BOLD);
			
			holder.hoursTextview.setTypeface(type_BOOK);
			holder.hoursTxtTextview.setTypeface(type_BOOK);
			holder.nearTextview.setTypeface(type_BOOK);
			holder.locationTextview.setTypeface(type_BOOK);
			
			
			
			holder.mainImageview = (RelativeLayout) convertView
					.findViewById(R.id.feedhomerow_RelativeLayout_bg);
			holder.userImageView = (ImageView) convertView
					.findViewById(R.id.feedhomerow_Imageview_userphoto);
			holder.statusImageview = (ImageView) convertView
					.findViewById(R.id.feedhomerow_Imageview_statusImg);
			holder.commentImageview = (ImageView) convertView
					.findViewById(R.id.feedhomerow_Imageview_comment);
			holder.likeImageview = (ImageView) convertView
					.findViewById(R.id.feedhomerow_Imageview_like);

			convertView.setTag(holder);
		} else

			holder = (ViewHolder) convertView.getTag();

		System.out.println("Detail :-" + rowItem.feed_UserName);
		holder.userNameTextview.setText(rowItem.feed_UserName);
		holder.toTextview.setText(rowItem.feed_Rated);
		holder.placeTextview.setText(rowItem.feed_At);
		holder.hoursTextview.setText(rowItem.feed_Time);
		holder.locationTextview.setText(rowItem.feed_Location);
		
//		ImageView view = new ImageView(context);
//		view.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
//				LayoutParams.MATCH_PARENT));
//		// view.setScaleType(ScaleType.FIT_XY);
//
//		ImageLoader im_loader = new ImageLoader(context);
//		System.out.println("Image url :-" + rowItem.msg_userImg);
//		im_loader.DisplayImage(rowItem.msg_userImg, holder.userImageView, 200,
//				false);
	
		return convertView;
	}

	/* private view holder class */
	private class ViewHolder {
		ImageView  userImageView, statusImageview,
				commentImageview, likeImageview;
		RelativeLayout mainImageview;
		TextView userNameTextview, rateTextview, toTextview, atTextview,
				placeTextview, hoursTextview, hoursTxtTextview, nearTextview,
				locationTextview;
	}
}

package com.android.ytoi.adapter;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.ytoi.R;
import com.android.ytoi.webclass.ProfileListInfo;

public class ProfileListAdapter extends ArrayAdapter<ProfileListInfo> {

	private Context context;
	Typeface type_BOOK, type_BOLD;

	public ProfileListAdapter(Context asyncTask, int resourceId,
			List<ProfileListInfo> items) {
		super(asyncTask, resourceId, items);
		this.context = asyncTask;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		type_BOOK = Typeface.createFromAsset(context.getAssets(),
				"fonts/Novecentowide-Book.otf");
		type_BOLD = Typeface.createFromAsset(context.getAssets(),
				"fonts/Novecentowide-Bold.otf");
		final ProfileListInfo rowItem = getItem(position);
		System.out.println("Row item :-" + rowItem.toString());
		LayoutInflater mInflater = (LayoutInflater) context
				.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.personalprofile_row, null);
			holder = new ViewHolder();
			holder.userNameTextview = (TextView) convertView
					.findViewById(R.id.personalprofile_Textview_username);
			holder.placeInfoTextview = (TextView) convertView
					.findViewById(R.id.personalprofile_Textview_placeInfo);
			holder.messageTextview = (TextView) convertView
					.findViewById(R.id.personalprofile_Textview_message);

			holder.hoursTextview = (TextView) convertView
					.findViewById(R.id.personalprofile_Textview_hours);
			holder.hoursTxtTextview = (TextView) convertView
					.findViewById(R.id.personalprofile_Textview_HoursInfo);
			holder.nearTextview = (TextView) convertView
					.findViewById(R.id.personalprofile_Textview_near);
			holder.locationTextview = (TextView) convertView
					.findViewById(R.id.personalprofile_Textview_location);
			holder.placeNameTextview = (TextView) convertView
					.findViewById(R.id.personalprofile_Textview_place);

			holder.userNameTextview.setTypeface(type_BOLD);
			holder.placeInfoTextview.setTypeface(type_BOLD);
			holder.messageTextview.setTypeface(type_BOLD);
			holder.placeNameTextview.setTypeface(type_BOOK);

			holder.hoursTextview.setTypeface(type_BOOK);
			holder.hoursTxtTextview.setTypeface(type_BOOK);
			holder.nearTextview.setTypeface(type_BOOK);
			holder.locationTextview.setTypeface(type_BOOK);

			holder.userImageView = (ImageView) convertView
					.findViewById(R.id.personalprofile_Imageview_userimage);
			holder.statusImageview = (ImageView) convertView
					.findViewById(R.id.personalProfile_Textview_status);
			holder.commentImageview = (ImageView) convertView
					.findViewById(R.id.personalprofile_Imageview_comment);
			holder.main_bg = (ImageView) convertView
					.findViewById(R.id.personalprofile_Imageview_bg);

			convertView.setTag(holder);
		} else

			holder = (ViewHolder) convertView.getTag();

		System.out.println("Detail :-" + rowItem.plInfo_UserName);
		holder.userNameTextview.setText(rowItem.plInfo_UserName);
		holder.placeInfoTextview.setText(rowItem.plInfo_PlaceInfo);
		holder.messageTextview.setText(rowItem.plInfo_Message);
		holder.placeNameTextview.setText(rowItem.plInfo_Place);
		holder.hoursTextview.setText(rowItem.plInfo_Time);
		holder.locationTextview.setText(rowItem.plInfo_Location);

		holder.userNameTextview.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				Toast.makeText(context,"User  call",Toast.LENGTH_SHORT).show();
//				Intent i=new Intent(context,Activity_Home.class);
//				i.putExtra("id","2");
//				context.startActivity(i);
			}
		});
		holder.placeInfoTextview.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				Toast.makeText(context,"Cafe click",Toast.LENGTH_SHORT).show();
//				Intent i=new Intent(context,Activity_Home.class);
//				i.putExtra("id","3");
//				context.startActivity(i);
			}
		});
		holder.commentImageview.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				
				Toast.makeText(context,"Comment click",Toast.LENGTH_SHORT).show();
			}
		});
		// ImageView view = new ImageView(context);
		// view.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
		// LayoutParams.MATCH_PARENT));
		// // view.setScaleType(ScaleType.FIT_XY);
		//
		// ImageLoader im_loader = new ImageLoader(context);
		// System.out.println("Image url :-" + rowItem.msg_userImg);
		// im_loader.DisplayImage(rowItem.msg_userImg, holder.userImageView,
		// 200,
		// false);

		return convertView;
	}

	/* private view holder class */
	private class ViewHolder {
		ImageView userImageView, statusImageview, commentImageview, main_bg;

		TextView userNameTextview, placeInfoTextview, messageTextview,
				hoursTextview, hoursTxtTextview, nearTextview,
				locationTextview, placeNameTextview;
	}
}

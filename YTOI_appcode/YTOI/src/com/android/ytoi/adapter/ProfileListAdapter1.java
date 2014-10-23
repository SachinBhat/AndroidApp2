package com.android.ytoi.adapter;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.ytoi.R;
import com.android.ytoi.utils.ConstantValue;
import com.android.ytoi.utils.SessionManager;
import com.android.ytoi.webclass.ProfileListInfo;

public class ProfileListAdapter1 extends ArrayAdapter<ProfileListInfo> {

	private Context context;
	Typeface type_BOOK, type_BOLD;
   
	public ProfileListAdapter1(Context asyncTask, int resourceId,
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
			convertView = mInflater.inflate(R.layout.personalprofile_row1, null);
			holder = new ViewHolder();
			holder.userNameTextview = (TextView) convertView
					.findViewById(R.id.personalprofile_Textview_username);
//			holder.placeInfoTextview = (TextView) convertView
//					.findViewById(R.id.personalprofile_Textview_placeInfo);
			holder.messageTextview = (TextView) convertView
					.findViewById(R.id.personalprofile_Textview_message);

			holder.hoursTextview = (TextView) convertView
					.findViewById(R.id.personalprofile_Textview_hours);
			holder.hoursTxtTextview = (TextView) convertView
					.findViewById(R.id.personalprofile_Textview_HoursInfo);
//			holder.nearTextview = (TextView) convertView
//					.findViewById(R.id.personalprofile_Textview_near);
//			holder.locationTextview = (TextView) convertView
//					.findViewById(R.id.personalprofile_Textview_location);
//			holder.placeNameTextview = (TextView) convertView
//					.findViewById(R.id.personalprofile_Textview_place);
//
			holder.userNameTextview.setTypeface(type_BOLD);

//			holder.messageTextview.setTypeface(type_BOLD);
//			holder.placeNameTextview.setTypeface(type_BOOK);
//
			holder.hoursTextview.setTypeface(type_BOOK);
			holder.hoursTxtTextview.setTypeface(type_BOOK);
//			holder.nearTextview.setTypeface(type_BOOK);
//			holder.locationTextview.setTypeface(type_BOOK);
//
			holder.userImageView = (ImageView) convertView
					.findViewById(R.id.personalprofile_Imageview_userimage);
//			holder.statusImageview = (ImageView) convertView
//					.findViewById(R.id.personalProfile_Textview_status);
//			holder.commentImageview = (ImageView) convertView
//					.findViewById(R.id.personalprofile_Imageview_comment);
//			holder.main_bg = (ImageView) convertView
//					.findViewById(R.id.personalprofile_Imageview_bg);

			convertView.setTag(holder);
		} else

			holder = (ViewHolder) convertView.getTag();

//		System.out.println("Detail :-" + rowItem.plInfo_UserName);
		holder.userNameTextview.setText(rowItem.plInfo_UserName);
		

	
		if (!ConstantValue.user.get(SessionManager.KEY_PROFILE_IMG).equals(""))
		{
		 Bitmap myBitmap ;
		 try {
		        URL url = new URL(ConstantValue.user.get(SessionManager.KEY_PROFILE_IMG));
		        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		        connection.setDoInput(true);
		        connection.connect();
		        InputStream input = connection.getInputStream();
		         myBitmap = BitmapFactory.decodeStream(input);
		
		    } catch (IOException e) {
		        e.printStackTrace();
		        return null;
		    }
		  Bitmap resized = Bitmap.createScaledBitmap(myBitmap, 100, 100, true);
		    Bitmap conv_bm = ConstantValue.getRoundedRectBitmap(resized, 100);
		    holder.userImageView.setImageBitmap(conv_bm);
		}
	  
//		holder.placeInfoTextview.setText(rowItem.plInfo_PlaceInfo);
		holder.messageTextview.setText(rowItem.plInfo_Message);
//		holder.placeNameTextview.setText(rowItem.plInfo_Place);
		holder.hoursTextview.setText(rowItem.plInfo_Time);
//		holder.locationTextview.setText(rowItem.plInfo_Location);
//
//		holder.userNameTextview.setOnClickListener(new OnClickListener() {
//			
//			@Override
//			public void onClick(View arg0) {
//				Toast.makeText(context,"User  call",Toast.LENGTH_SHORT).show();
////				Intent i=new Intent(context,Activity_Home.class);
////				i.putExtra("id","2");
////				context.startActivity(i);
//			}
//		});
//		holder.placeInfoTextview.setOnClickListener(new OnClickListener() {
//			
//			@Override
//			public void onClick(View arg0) {
//				Toast.makeText(context,"Cafe click",Toast.LENGTH_SHORT).show();
////				Intent i=new Intent(context,Activity_Home.class);
////				i.putExtra("id","3");
////				context.startActivity(i);
//			}
//		});
//		holder.commentImageview.setOnClickListener(new OnClickListener() {
//			
//			@Override
//			public void onClick(View arg0) {
//				
//				Toast.makeText(context,"Comment click",Toast.LENGTH_SHORT).show();
//			}
//		});
//		// ImageView view = new ImageView(context);
//		// view.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
//		// LayoutParams.MATCH_PARENT));
//		// // view.setScaleType(ScaleType.FIT_XY);
//		//
//		// ImageLoader im_loader = new ImageLoader(context);
//		// System.out.println("Image url :-" + rowItem.msg_userImg);
//		// im_loader.DisplayImage(rowItem.msg_userImg, holder.userImageView,
//		// 200,
//		// false);

		return convertView;
	}

	/* private view holder class */
	private class ViewHolder {
		ImageView userImageView;

		TextView userNameTextview,  messageTextview,
				hoursTextview, hoursTxtTextview ;
	}
	
}

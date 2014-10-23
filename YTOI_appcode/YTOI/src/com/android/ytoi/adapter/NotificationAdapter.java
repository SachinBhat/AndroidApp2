package com.android.ytoi.adapter;

import java.util.List;

import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.TextView;

import com.android.ytoi.R;
import com.android.ytoi.imageloader.ImageLoader;
import com.android.ytoi.webclass.NotificationInfo;



public class NotificationAdapter extends  ArrayAdapter<NotificationInfo> {
	Typeface type_BOOK;
	private Context context;

	public NotificationAdapter(Context asyncTask, int resourceId,
			List<NotificationInfo> items) {
		super(asyncTask, resourceId, items);
		this.context = asyncTask;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		type_BOOK = Typeface.createFromAsset(context.getAssets(),
				"fonts/Novecentowide-Book.otf");
		final NotificationInfo rowItem = getItem(position);
		System.out.println("Row item :-"+rowItem.toString());
		LayoutInflater mInflater = (LayoutInflater) context
				.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.notification_row, null);
			holder = new ViewHolder();
			holder.messageTextview = (TextView) convertView
					.findViewById(R.id.notification_TextView_name);
			//holder.messageTextview.setTypeface(type_BOOK);
			holder.userImageView = (ImageView) convertView
					.findViewById(R.id.notification_Imageview_UserImage);
			holder.userPhotoView = (ImageView) convertView
					.findViewById(R.id.notification_ImageView_photo);
			convertView.setTag(holder);
		} else

			holder = (ViewHolder) convertView.getTag();

		System.out.println("DEtail :-"+rowItem.nt_message);
		holder.messageTextview.setText(rowItem.nt_message);

		ImageView view = new ImageView(context);
		view.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.MATCH_PARENT));
		view.setScaleType(ScaleType.FIT_XY);

		ImageLoader im_loader= new ImageLoader(context, false);
		System.out.println("Image url :-"+rowItem.nt_userImg);
		im_loader.DisplayImage(rowItem.nt_userImg,holder.userImageView);


		view.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.MATCH_PARENT));
		view.setScaleType(ScaleType.FIT_XY);

		ImageLoader im_loader1= new ImageLoader(context, false);
		System.out.println("Image url :-"+rowItem.nt_img);
		im_loader1.DisplayImage(rowItem.nt_img,holder.userPhotoView);

		return convertView;
	}

	/* private view holder class */
	private class ViewHolder {
		ImageView userImageView,userPhotoView;
		TextView messageTextview;
	}
}

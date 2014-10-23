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
import com.android.ytoi.webclass.FollowerInfo;

public class FollowerAdapter extends ArrayAdapter<FollowerInfo> {

	private Context context;
	Typeface type_BOOK, type_BOLD;

	public FollowerAdapter(Context asyncTask, int resourceId,
			List<FollowerInfo> items) {
		super(asyncTask, resourceId, items);
		this.context = asyncTask;
	}

	public View getView(int position, View convertView, ViewGroup parent)
	{
		ViewHolder holder = null;
		type_BOOK = Typeface.createFromAsset(context.getAssets(),
				"fonts/Novecentowide-Book.otf");
		type_BOLD = Typeface.createFromAsset(context.getAssets(),
				"fonts/Novecentowide-Bold.otf");
		final FollowerInfo rowItem = getItem(position);
		System.out.println("Row item :-" + rowItem.toString());
		LayoutInflater mInflater = (LayoutInflater) context
				.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.follower_row, null);
			holder = new ViewHolder();
			holder.userNameTextview = (TextView) convertView
					.findViewById(R.id.follower_TextView_name);
			holder.userImageView = (ImageView) convertView
					.findViewById(R.id.follower_Imageview_UserImage);

			holder.userNameTextview.setTypeface(type_BOLD);

			convertView.setTag(holder);
		} else

			holder = (ViewHolder) convertView.getTag();

		System.out.println("Detail :-" + rowItem.first_name);
		holder.userNameTextview.setText(rowItem.first_name + " "
				+ rowItem.last_name);

		ImageView view = new ImageView(context);
		
		view.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.MATCH_PARENT));
		
		view.setScaleType(ScaleType.FIT_XY);

		ImageLoader im_loader = new ImageLoader(context, false);

		im_loader.DisplayImage(rowItem.user_Image, holder.userImageView);
		System.out.println("Image url :-" + rowItem.user_Image);
		return convertView;
	}

	/* private view holder class */
	private class ViewHolder
	{
		ImageView userImageView;

		TextView userNameTextview;
	}
}

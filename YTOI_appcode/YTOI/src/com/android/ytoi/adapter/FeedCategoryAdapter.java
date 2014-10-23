package com.android.ytoi.adapter;

import java.util.ArrayList;

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
import com.android.ytoi.webclass.FeedInfo;

public class FeedCategoryAdapter extends ArrayAdapter<FeedInfo> {

	private Context context;
	Typeface type_BOOK, type_BOLD;

	public FeedCategoryAdapter(Context context, int resourceId,
			ArrayList<FeedInfo> feed_info) {
		super(context, resourceId, feed_info);
		this.context = context;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		
		ViewHolder holder = null;
	
		type_BOOK = Typeface.createFromAsset(context.getAssets(),
				"fonts/Novecentowide-Book.otf");
		type_BOLD = Typeface.createFromAsset(context.getAssets(),
				"fonts/Novecentowide-Bold.otf");
		
		final FeedInfo feed_info = getItem(position);
	
		LayoutInflater mInflater = (LayoutInflater) context
				.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
		
		if (convertView == null)
		{
			convertView = mInflater.inflate(R.layout.lyt_feedcategory_row, null);
		
			holder = new ViewHolder();
			
			holder.txt_feedname = (TextView) convertView
					.findViewById(R.id.lyt_feedcategory_row_txt_name);
			
			holder.img_feedimage = (ImageView) convertView
					.findViewById(R.id.lyt_feedcategory_row_img_image);

			holder.txt_feedname.setTypeface(type_BOLD);

			convertView.setTag(holder);
		
		} 
		else

			holder = (ViewHolder) convertView.getTag();

		holder.txt_feedname.setText(feed_info.iteam_name);

		ImageView view = new ImageView(context);
		view.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.MATCH_PARENT));
		view.setScaleType(ScaleType.FIT_XY);

		ImageLoader im_loader = new ImageLoader(context, false);

		im_loader.DisplayImage(feed_info.feed_image_thumb,holder.img_feedimage);
		
		return convertView;
	}

	/* private view holder class */
	private class ViewHolder {
		ImageView img_feedimage;
		TextView txt_feedname;
	}
}

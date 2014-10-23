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
import android.widget.TextView;

import com.android.ytoi.R;
import com.android.ytoi.imageloader.ImageLoader;
import com.android.ytoi.webclass.MessageInfo;

public class MessageAdapter extends  ArrayAdapter<MessageInfo>{
	Typeface type_BOOK;
private Context context;
	
	public MessageAdapter(Context asyncTask, int resourceId,
			List<MessageInfo> items) {
		super(asyncTask, resourceId, items);
		this.context = asyncTask;
	}
	public View getView(int position, View convertView, ViewGroup parent) {
		 ViewHolder holder = null;
			type_BOOK = Typeface.createFromAsset(context.getAssets(),
					"fonts/Novecentowide-Book.otf");
		final MessageInfo rowItem = getItem(position);
		System.out.println("Row item :-"+rowItem.toString());
		LayoutInflater mInflater = (LayoutInflater) context
				.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.message_row, null);
			holder = new ViewHolder();
			holder.messageTextview = (TextView) convertView
					.findViewById(R.id.message_TextView_message);
			//holder.messageTextview.setTypeface(type_BOOK);
			holder.userImageView = (ImageView) convertView
					.findViewById(R.id.message_Imageview_UserImage);
			holder.userNameTextview = (TextView) convertView
					.findViewById(R.id.message_TextView_name);
			holder.userNameTextview.setTypeface(type_BOOK);
			convertView.setTag(holder);
		} else
			
			holder = (ViewHolder) convertView.getTag();
		
		System.out.println("Detail :-"+rowItem.msg_message);
		holder.messageTextview.setText(rowItem.msg_message);
		holder.userNameTextview.setText(rowItem.msg_Usename);		
		 ImageView view = new ImageView(context);
		    view.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
		    LayoutParams.MATCH_PARENT));
			//view.setScaleType(ScaleType.FIT_XY);
			
			ImageLoader im_loader= new ImageLoader(context, false);
			System.out.println("Image url :-"+rowItem.msg_userImg);
			im_loader.DisplayImage(rowItem.msg_userImg,holder.userImageView);
						  
		return convertView;
	}
	
	/* private view holder class */
	private class ViewHolder {
		ImageView userImageView;
		TextView messageTextview,userNameTextview;
	}
}

package com.android.ytoi.adapter;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.ytoi.Activity_Home;
import com.android.ytoi.R;
import com.android.ytoi.imageloader.ImageLoader;
import com.android.ytoi.utils.ConstantValue;
import com.android.ytoi.utils.SessionManager;
import com.android.ytoi.web.HttpHelper;
import com.android.ytoi.webclass.FeedInfo;


public class HomeFeedAdapter extends ArrayAdapter<FeedInfo> {

	private Context context;
	Typeface type_BOOK, type_BOLD;
	private Activity_Home act_home;

	public HomeFeedAdapter(Context asyncTask, int resourceId, List<FeedInfo> items,Activity_Home act_home) {
		super(asyncTask, resourceId, items);
		this.context = asyncTask;
		this.act_home=act_home;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		type_BOOK = Typeface.createFromAsset(context.getAssets(),
				"fonts/Novecentowide-Book.otf");
		type_BOLD = Typeface.createFromAsset(context.getAssets(),
				"fonts/Novecentowide-Bold.otf");
		final FeedInfo rowItem = getItem(position);
		LayoutInflater mInflater = (LayoutInflater) context
				.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.lyt_homefeeddisply, null);
			holder = new ViewHolder();



			holder.txt_address = (TextView) convertView
					.findViewById(R.id.lyt_homefeeddisply_txt_address);
			holder.txt_feedname = (TextView) convertView
					.findViewById(R.id.lyt_homefeeddisply_txt_feedname);
			holder.txt_placename = (TextView) convertView
					.findViewById(R.id.lyt_homefeeddisply_txt_placename);
			holder.txt_username = (TextView) convertView
					.findViewById(R.id.lyt_homefeeddisply_txt_username);
			holder.txt_postdate = (TextView) convertView
					.findViewById(R.id.lyt_homefeeddisply_txt_postdate);
			holder.txt_expiriance = (TextView) convertView
					.findViewById(R.id.lyt_homefeeddisply_txt_expiriance);
			holder.txt_approvedcount= (TextView) convertView
					.findViewById(R.id.lyt_homefeeddisply_txt_approved);
			holder.txt_commentscount= (TextView) convertView
					.findViewById(R.id.lyt_homefeeddisply_txt_comment);
			holder.img_approvedcount= (ImageView) convertView
					.findViewById(R.id.lyt_homefeeddisply_img_approved);
			holder.img_commentcount= (ImageView) convertView
					.findViewById(R.id.lyt_homefeeddisply_img_comment);


			holder.txt_expiriance.setTypeface(type_BOLD);
			holder.txt_feedname.setTypeface(type_BOLD);
			holder.txt_placename.setTypeface(type_BOLD);
			holder.txt_username.setTypeface(type_BOOK);
			holder.txt_postdate.setTypeface(type_BOOK);
			holder.txt_address.setTypeface(type_BOOK);
			holder.txt_commentscount.setTypeface(type_BOOK);
			holder.txt_approvedcount.setTypeface(type_BOOK);


			holder.img_feedimage = (ImageView) convertView
					.findViewById(R.id.lyt_homefeeddisply_img_feedimage);
			holder.img_rateimage = (ImageView) convertView
					.findViewById(R.id.lyt_homefeeddisply_img_rateimage);
			holder.img_userimage = (ImageView) convertView
					.findViewById(R.id.lyt_homefeeddisply_img_userimage);
			holder.lin_addComment=(LinearLayout) convertView.findViewById(R.id.lyt_homefeed_lin_comments);
			convertView.setTag(holder);
		} 
		else
		{
			holder = (ViewHolder) convertView.getTag();
		}
		holder.txt_expiriance.setText(rowItem.experiance);
		holder.txt_feedname.setText(rowItem.category_name);
		holder.txt_placename.setText(rowItem.location_name);
		holder.txt_postdate.setText(rowItem.date_time);
		holder.txt_username.setText(rowItem.user_name);
		holder.txt_address.setText(rowItem.location_name);

		holder.txt_approvedcount.setText(rowItem.num_of_approve+"");
		holder.txt_commentscount.setText(rowItem.num_of_comments+"");

		holder.lin_addComment.removeAllViews();

		for (int i = 0; i < rowItem.arrylist_commentInfo.size(); i++) {

			
			
			
			TextView txt_username=new TextView(act_home);
			txt_username.setText(rowItem.arrylist_commentInfo.get(i).user_name);
			txt_username.setTextColor(act_home.getResources().getColor(R.color.line_color));
			txt_username.setTypeface(type_BOOK);
			txt_username.setTextSize(11);
			
			TextView txt_coment=new TextView(act_home);
			txt_coment.setTextColor(Color.BLACK);
			txt_coment.setPadding(5, 0,0,0);
			txt_coment.setText(rowItem.arrylist_commentInfo.get(i).comment);
			txt_coment.setTypeface(type_BOOK);
			txt_coment.setTextSize(11);
			
			LinearLayout lin_coment=new LinearLayout(act_home);
			lin_coment.setPadding(32, 0,0,0);
			lin_coment.addView(txt_username);
			lin_coment.addView(txt_coment);
			
			holder.lin_addComment.addView(lin_coment);
		}

		ImageLoader im_loader = new ImageLoader(context, false);

		if (!rowItem.rate_image.equalsIgnoreCase("")) 
			im_loader.DisplayImage(rowItem.rate_image, holder.img_rateimage);

		if (rowItem.profile_picture_bitmap!=null) 
		{
			holder.img_userimage.setImageBitmap(rowItem.profile_picture_bitmap);
		}
		if (rowItem.feed_image_bitmap!=null) 
		{
			holder.img_feedimage.setImageBitmap(rowItem.feed_image_bitmap);
		}

		holder.img_commentcount.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				addCommentDialog(rowItem.feed_id);
			}
		});

		holder.img_approvedcount.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				setApprovFeed(rowItem.feed_id);
			}
		});

		return convertView;
	}

	/* private view holder class */
	private class ViewHolder {
		public TextView txt_address;

		ImageView img_userimage, img_feedimage,img_rateimage,img_commentcount,img_approvedcount;

		TextView txt_username,txt_expiriance,txt_postdate,txt_feedname,txt_placename,
		txt_approvedcount,txt_commentscount;

		LinearLayout lin_addComment;

	}

	private void addCommentDialog(final String feed_id) 
	{
		View mView = View.inflate(act_home, R.layout.pop_dialogcomment, null);

		final EditText  edt_comment = ((EditText) mView.findViewById(R.id.pop_dialogcomment_edt_comment));

		final InputMethodManager mInputMethodManager = (InputMethodManager) act_home
				.getSystemService(Context.INPUT_METHOD_SERVICE);
		mInputMethodManager.restartInput(mView);

		AlertDialog.Builder mBuilder = new AlertDialog.Builder(act_home);
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
								act_home,"Comments...", nameValuePair);
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
	private void setApprovFeed(final String feed_id) 
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
					act_home,"Comments...", nameValuePair);
			httpHelper.execute(uri);
		}
	}
}

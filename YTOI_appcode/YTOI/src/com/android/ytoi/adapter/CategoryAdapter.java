package com.android.ytoi.adapter;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.android.ytoi.R;
import com.android.ytoi.webclass.Category;

public class CategoryAdapter extends ArrayAdapter<Category> {

	private Context context;
	Typeface type_BOOK, type_BOLD;

	public CategoryAdapter(Context asyncTask, int resourceId,
			ArrayList<Category> arraylist_Category) {
		super(asyncTask, resourceId, arraylist_Category);
		this.context = asyncTask;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		type_BOOK = Typeface.createFromAsset(context.getAssets(),
				"fonts/Novecentowide-Book.otf");
		type_BOLD = Typeface.createFromAsset(context.getAssets(),
				"fonts/Novecentowide-Bold.otf");
		
		final Category rowItem = getItem(position);
		LayoutInflater mInflater = (LayoutInflater) context
				.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.lyt_category_row, null);
			holder = new ViewHolder();
			
			holder.txt_categoryname = (TextView) convertView
					.findViewById(R.id.lyt_category_txt_name);
			holder.txt_categoryname.setTypeface(type_BOOK);
			holder.txt_categoryname.setTextColor(context.getResources().getColor(R.color.category_list_color));
			
			convertView.setTag(holder);
		} else

			holder = (ViewHolder) convertView.getTag();

		holder.txt_categoryname.setText(rowItem.category_name);

		return convertView;
	}

	/* private view holder class */
	private class ViewHolder {

		TextView txt_categoryname;
	}
}

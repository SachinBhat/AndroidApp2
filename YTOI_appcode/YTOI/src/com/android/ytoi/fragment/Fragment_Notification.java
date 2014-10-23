package com.android.ytoi.fragment;

import java.util.ArrayList;

import android.app.Fragment;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ListView;

import com.android.ytoi.R;
import com.android.ytoi.adapter.NotificationAdapter;
import com.android.ytoi.webclass.NotificationInfo;

public class Fragment_Notification extends Fragment implements OnClickListener{

	Context context;
	ListView notificationList;
	private ArrayList<NotificationInfo> arraylist_notificationInfo = new ArrayList<NotificationInfo>();
	private NotificationAdapter notificationAdapter;
	Typeface  type_BOOK;
	public Fragment_Notification() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.message_main, container, false);
		context = rootView.getContext();
		init(rootView);
		notificationAdapter= new NotificationAdapter(context,
				R.layout.notification_row, arraylist_notificationInfo);
		notificationList.setAdapter(notificationAdapter);
		return rootView;
	}
	public void init(View rootView)
	{
		notificationList=(ListView)rootView.findViewById(R.id.message_listView_List);
		NotificationInfo notification=new NotificationInfo();
		notification.nt_message="Agni Kapoor agrees with your take on Devil's Own at Starbuck";
		notification.nt_userImg="";
		notification.nt_img="";
		arraylist_notificationInfo.add(notification);
		type_BOOK = Typeface.createFromAsset(context.getAssets(),
				"fonts/Novecentowide-Book.otf");
		
	}
	
	@Override
	public void onClick(View arg0) {
	
	}
}

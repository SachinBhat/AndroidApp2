package com.android.ytoi.fragment;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.android.ytoi.R;
import com.android.ytoi.adapter.FollowerAdapter;
import com.android.ytoi.utils.Act_Fragment;
import com.android.ytoi.utils.ConstantValue;
import com.android.ytoi.utils.SessionManager;
import com.android.ytoi.web.HttpHelper_Fragment;
import com.android.ytoi.webclass.FollowerInfo;

public class Fragment_Follower extends Act_Fragment implements OnClickListener{

	View rootView;
	Context context;

	ListView followerList;
	FollowerAdapter adapter;
	public Fragment_Follower() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		rootView = inflater.inflate(R.layout.message_main, container,
				false);
		context = rootView.getContext();
		init();
		return rootView;
	}
	public void init() {
		followerList=(ListView)rootView.findViewById(R.id.message_listView_List);
		if (isOnline()) // connected
		{
			String uri = null;
			uri = String.format(ConstantValue.URL_FOLLOWER_VIEW);
			uri = uri.replace(" ", "%20");
			Log.i("Following Info", uri);
			if (uri != null) {
				uri = uri.replace(" ", "%20");

				List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>(
						2);
				nameValuePair.add(new BasicNameValuePair("user_id",
						ConstantValue.user.get(SessionManager.KEY_USERID)));			
				HttpHelper_Fragment httpHelper = new HttpHelper_Fragment(
						ConstantValue.REQUESTCODE_FOLLOWER_VIEW,
						Fragment_Follower.this, "Loading...",
						nameValuePair);
				httpHelper.execute(uri);
			}


		} else {

			Toast.makeText(getActivity().getApplicationContext(),
					"Internet Connection Fail", Toast.LENGTH_SHORT)
					.show();
		}
	}
	@Override
	public void onClick(View arg0) {

	}
	public void setBackApiResponse(int requestcode, Object obj1, Object obj2,Object obj3) {

		@SuppressWarnings("unchecked")
		ArrayList<FollowerInfo> arraylist_followerListInfo =(ArrayList<FollowerInfo>) obj3;
		if (requestcode == ConstantValue.REQUESTCODE_FOLLOWER_VIEW) {
			if (obj1!=null) {
				if (obj1.equals("1")) {
					Toast.makeText(context, obj2.toString(), Toast.LENGTH_SHORT)
					.show();
					System.out.println("Follower list is :-"+arraylist_followerListInfo);
					adapter = new FollowerAdapter(getActivity(), R.layout.follower_row,
							arraylist_followerListInfo);
					followerList.setAdapter(adapter);
				} else
					Toast.makeText(context, obj2.toString(), Toast.LENGTH_SHORT)
					.show();
			}
		}
	}

}

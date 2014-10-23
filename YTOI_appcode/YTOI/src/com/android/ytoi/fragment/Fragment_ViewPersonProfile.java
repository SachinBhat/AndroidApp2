package com.android.ytoi.fragment;

import java.util.ArrayList;

import android.app.ActionBar.LayoutParams;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.ListView;
import android.widget.TextView;

import com.android.ytoi.R;
import com.android.ytoi.adapter.ProfileListAdapter1;
import com.android.ytoi.imageloader.ImageLoader;
import com.android.ytoi.utils.ConstantValue;
import com.android.ytoi.utils.SessionManager;
import com.android.ytoi.webclass.ProfileListInfo;


public class Fragment_ViewPersonProfile extends Fragment implements OnClickListener {

	View rootView;
	Context context;
	TextView userNameTXT, locationTXT, followerTXT, followingTXT, 
			followerCoutTXT, followingCountTXT;
	Typeface type_BOLD, type_BOOK;
	ListView personalprofile_list;
	ImageView userIMG,editProfileIMG;
	private ArrayList<ProfileListInfo> arraylist_profileListInfo = new ArrayList<ProfileListInfo>();
	private ProfileListAdapter1 profileAdapter;
	FragmentManager fragmentManager;
	String backStateName;
	
	public Fragment_ViewPersonProfile() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		rootView = inflater.inflate(R.layout.view_personprofile, container,
				false);
		context = rootView.getContext();
		init();
		defaultFeed();
		editProfileIMG.setOnClickListener(this);
		return rootView;
	}

	public void defaultFeed() {
		arraylist_profileListInfo = new ArrayList<ProfileListInfo>();
		ProfileListInfo profile = new ProfileListInfo();
		profile.plInfo_PlaceInfo = "CAFE COFEE DAY";
		profile.plInfo_Location = "Mumbai";
		profile.plInfo_Place = "Devil's own";
		profile.plInfo_Time = "2";
		profile.plInfo_UserName = "SANKALP";
		profile.plInfo_Message = "BEST COFEE I HAD ";
		arraylist_profileListInfo.add(profile);
		ProfileListInfo profile1 = new ProfileListInfo();
		profile1.plInfo_PlaceInfo = "CAFE COFEE SHOP";
		profile1.plInfo_Location = "AHMEDABAD";
		profile1.plInfo_Place = "Devil's own";
		profile1.plInfo_Time = "2";
		profile1.plInfo_UserName = "VISHAL";
		profile1.plInfo_Message = "BEST COFEE ";
		arraylist_profileListInfo.add(profile1);
		feed_Feel();
	}


	public void feed_Feel() {
		profileAdapter = new ProfileListAdapter1(context,
				R.layout.personalprofile_row1, arraylist_profileListInfo);
		personalprofile_list.setAdapter(profileAdapter);
		// personalprofile_list.setOnItemClickListener(this);

	}

	public void init() {
		userNameTXT = (TextView) rootView
				.findViewById(R.id.personalprofile_TextView_username);
		userNameTXT.setText(ConstantValue.user.get(SessionManager.KEY_FIRSTNAME) + " "
				+ ConstantValue.user.get(SessionManager.KEY_LASTNAME));
		locationTXT = (TextView) rootView
				.findViewById(R.id.personalprofile_TextView_location);
		followerTXT = (TextView) rootView
				.findViewById(R.id.personalprofile_TextView_follower);
		followingTXT = (TextView) rootView
				.findViewById(R.id.personalprofile_TextView_following);
		
		followerCoutTXT = (TextView) rootView
				.findViewById(R.id.personalprofile_TextView_follower_count);
		followingCountTXT = (TextView) rootView
				.findViewById(R.id.personalprofile_TextView_following_count);
		
		userIMG=(ImageView)rootView.findViewById(R.id.personalprofile_ImageView_userImage);
		editProfileIMG=(ImageView)rootView.findViewById(R.id.personalprofile_ImageView_editProfile);
		if (!ConstantValue.user.get(SessionManager.KEY_PROFILE_IMG).equals("")) {
			ImageView view = new ImageView(context);
			view.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
					LayoutParams.MATCH_PARENT));
			view.setScaleType(ScaleType.FIT_XY);

			ImageLoader im_loader = new ImageLoader(context, false);
			System.out.println("Image url :-"
					+ ConstantValue.user.get(SessionManager.KEY_PROFILE_IMG));

			im_loader.DisplayImage(ConstantValue.user.get(SessionManager.KEY_PROFILE_IMG),
					userIMG);
		}
		personalprofile_list = (ListView) rootView
				.findViewById(R.id.personalprofile_Listview_list);
		locationTXT.setText(ConstantValue.user.get(SessionManager.KEY_CITY));
		type_BOLD = Typeface.createFromAsset(context.getAssets(),
				"fonts/Novecentowide-Bold.otf");
		type_BOOK = Typeface.createFromAsset(context.getAssets(),
				"fonts/Novecentowide-Book.otf");
		set_TypeFace_Control();
	}

	
	public void set_TypeFace_Control() {
		userNameTXT.setTypeface(type_BOLD);
		locationTXT.setTypeface(type_BOOK);
		
		followerTXT.setTypeface(type_BOOK);
		followingTXT.setTypeface(type_BOOK);
		followerCoutTXT.setTypeface(type_BOOK);
		followingCountTXT.setTypeface(type_BOOK);
		
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.personalprofile_ImageView_editProfile:
			
			Fragment fragment = null;
			FragmentManager fragmentManager = getFragmentManager();
			android.app.FragmentTransaction fragmenttransaction = fragmentManager.beginTransaction(); 
			fragment = new Fragment_EditProfile();
			String fragmentTagName = "editprofile";
			if (!fragmentTagName.equalsIgnoreCase("")) {

				if (fragment!=null) {


					if (fragment.isAdded()) {
						fragmenttransaction.show(fragment);
						fragmenttransaction.addToBackStack(null);
					} else {
						fragmenttransaction.add(R.id.profile_content_frame, fragment,fragmentTagName);
						fragmenttransaction.addToBackStack(fragmentTagName);
					}
					fragmenttransaction.commit();
				}
			}
			break;
		default:
			break;
		}
	}
	
	
}

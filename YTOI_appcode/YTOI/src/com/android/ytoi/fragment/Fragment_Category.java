package com.android.ytoi.fragment;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.android.ytoi.Activity_Home;
import com.android.ytoi.R;
import com.android.ytoi.adapter.CategoryAdapter;
import com.android.ytoi.utils.Act_Fragment;
import com.android.ytoi.utils.ConstantValue;
import com.android.ytoi.web.HttpHelper_Fragment;
import com.android.ytoi.webclass.Category;

public class Fragment_Category extends Act_Fragment implements OnClickListener{

	private View rootView;
	private Context context;
	public static  ArrayList<Category> static_arraylist_Category =new ArrayList<Category> ();
	private  Bundle bundle_arrgument=new Bundle();

	private ListView list_Category;
	private CategoryAdapter adapter_category;
	private TextView txt_categorTitle;


	public Fragment_Category() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		rootView = inflater.inflate(R.layout.rate_main, container,
				false);
		context = rootView.getContext();
		init();

		list_Category.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {

				bundle_arrgument.clear();
				adapter_category.notifyDataSetChanged();
				((TextView)arg1).setTextColor(getResources().getColor(R.color.category_list_color_sel));
				bundle_arrgument.putString("category_name", static_arraylist_Category.get(arg2).category_name);
				bundle_arrgument.putString("category_id", static_arraylist_Category.get(arg2).category_id);
				bundle_arrgument.putString("category_link", static_arraylist_Category.get(arg2).category_link);

				Fragment fragment = null;
				FragmentManager fragmentManager = getFragmentManager();
				android.app.FragmentTransaction fragmenttransaction = fragmentManager.beginTransaction(); 
				fragment = new Fragment_AddFeed();

				String fragmentTagName = "addfeed";
				if (!fragmentTagName.equalsIgnoreCase("")) {

					if (fragment!=null) {
						fragment.setArguments(bundle_arrgument);
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

			}



		});
		return rootView;
	}

	@Override
	public void onResume() {
		Log.e("Category Resume", "true");
		ImageView img_rate=(ImageView) getActivity().findViewById(R.id.footermain_img_rate);
		img_rate.setBackgroundResource(R.drawable.bottom_rate);
		ImageView img_reco=(ImageView) getActivity().findViewById(R.id.footermain_img_reco);
		img_reco.setBackgroundResource(R.drawable.bottom_reco);
		ImageView img_exxplorer=(ImageView) getActivity().findViewById(R.id.footermain_img_explore);
		img_exxplorer.setBackgroundResource(R.drawable.bottom_explor);
		LinearLayout lin_bottombar=(LinearLayout) getActivity().findViewById(R.id.footermain_lin_bottom_bar);
		lin_bottombar.setBackgroundResource(R.drawable.bottom_bar);

		super.onResume();
	}
	public void init() {
		list_Category=(ListView)rootView.findViewById(R.id.rateMain_Listview_category);
		txt_categorTitle=(TextView)rootView.findViewById(R.id.rateMain_Textview_category);
		Typeface type_BOLD = Typeface.createFromAsset(context.getAssets(),
				"fonts/Novecentowide-Bold.otf");
		txt_categorTitle.setTypeface(type_BOLD);

		//		if (static_arraylist_Category.size()>0) {
		//			adapter_category = new CategoryAdapter(getActivity(), R.layout.lyt_category_row,
		//					static_arraylist_Category);
		//			list_Category.setAdapter(adapter_category);
		//		}
		//		else
		//		{
		if (isOnline()) // connected
		{
			String uri = null;
			uri = String.format(ConstantValue.URL_CATEGORY_VIEW);


			if (uri != null) {
				uri = uri.replace(" ", "%20");

				List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>(
						1);
				TelephonyManager telephonyManager = (TelephonyManager)getActivity().getSystemService(Context.TELEPHONY_SERVICE);

				//				nameValuePair.add(new BasicNameValuePair("user_id",
				//						ConstantValue.user.get(SessionManager.KEY_USERID)));
				nameValuePair.add(new BasicNameValuePair("device_id",telephonyManager.getDeviceId()+""));
				Log.e("device_id Category View", telephonyManager.getDeviceId()+"");

				HttpHelper_Fragment httpHelper = new HttpHelper_Fragment(
						ConstantValue.REQUESTCODE_CATEGORY_VIEW,
						Fragment_Category.this, "Loading...",
						null);
				httpHelper.execute(uri);



			}

			//}
		} 
	}
	@Override
	public void onClick(View arg0) {

	}
	@SuppressWarnings("unchecked")
	public void setBackApiResponse(int requestcode, Object obj1, Object obj2,Object obj3) {

		static_arraylist_Category=(ArrayList<Category>) obj3;
		if (requestcode == ConstantValue.REQUESTCODE_CATEGORY_VIEW) {
			if (obj1!=null)
				if (obj1.equals("1")) {
					adapter_category = new CategoryAdapter(getActivity(), R.layout.lyt_category_row,
							static_arraylist_Category);
					list_Category.setAdapter(adapter_category);
				} 

		}
	}





}

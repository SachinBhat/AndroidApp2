package com.android.ytoi.web;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.entity.BufferedHttpEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import com.android.ytoi.Activity_Home;
import com.android.ytoi.utils.Act_Fragment;
import com.android.ytoi.utils.ConstantValue;
import com.android.ytoi.webclass.Category;
import com.android.ytoi.webclass.CommentInfo;
import com.android.ytoi.webclass.FeedInfo;
import com.android.ytoi.webclass.FollowerInfo;
import com.android.ytoi.webclass.SettingInfo;
import com.android.ytoi.webclass.UserInfo;

public class HttpHelper_Fragment extends AsyncTask<String, Integer, Long> {

	Act_Fragment act_main;
	private ProgressDialog dialog;
	private int requestNumber;
	private String loadingMessage;
	List<NameValuePair> parameter;
	private String success = null;
	private String message = null;
	private String response;
	private WebAPIRequest webAPIRequest = new WebAPIRequest();
	private ArrayList<FollowerInfo> arraylist_followerListInfo = new ArrayList<FollowerInfo>();
	private ArrayList<Category> arraylist_Category = new ArrayList<Category>();
	private ArrayList<FeedInfo> arraylist_FeedInfo = new ArrayList<FeedInfo>();
	private UserInfo userinfo;
	private SettingInfo setting;
	private byte[] byt_image1;
	private Bundle bundle;

	public HttpHelper_Fragment(int request_num, Act_Fragment activity,
			String msg)

	{
		act_main = activity;
		requestNumber = request_num;
		dialog = new ProgressDialog(act_main.getActivity());
		dialog.setCancelable(false);
		loadingMessage = msg;
	}

	public HttpHelper_Fragment(int request_num, Act_Fragment activity,
			String msg, List<NameValuePair> parameter)

	{
		act_main = activity;
		requestNumber = request_num;
		dialog = new ProgressDialog(act_main.getActivity());
		dialog.setCancelable(false);
		loadingMessage = msg;
		this.parameter = parameter;
	}
	public HttpHelper_Fragment(int request_num, Act_Fragment activity,
			String msg, Bundle bundle,byte[] byt_image1)

	{
		act_main = activity;
		requestNumber = request_num;
		dialog = new ProgressDialog(act_main.getActivity());
		dialog.setCancelable(false);
		loadingMessage = msg;
		this.bundle = bundle;
		this.byt_image1=byt_image1;
	}
	protected void onPreExecute() {

		dialog.setMessage(loadingMessage);
		dialog.show();
	}

	@Override
	protected Long doInBackground(String... value) {
		if (requestNumber == ConstantValue.REQUESTCODE_REGISTRATION) {

			Log.i("Request REQUESTCODE_REGISTRATION","======================");
			Log.i("Request",value[0]);


			response = webAPIRequest.performPost_String(value[0],parameter);

			if (response != null) 
			{
				Log.i("Response REQUESTCODE_REGISTRATION","======================");
				Log.i("Response ",response);


				try {

					JSONObject j_object_main = new JSONObject(response);

					JSONObject j_object_data = j_object_main
							.optJSONObject("data");

					success = j_object_data.optString("Success");
					message = j_object_data.optString("Message");

					if (success.equals("1")) 
					{

						JSONObject j_object_AllDetails = j_object_data
								.optJSONObject("AllDetails");

						userinfo=new UserInfo();

						userinfo.user_id = j_object_AllDetails
								.optString("user_id");
						userinfo.city = j_object_AllDetails
								.optString("first_name");
						userinfo.country = j_object_AllDetails
								.optString("last_name");
						userinfo.email = j_object_AllDetails
								.optString("email");
						userinfo.password = j_object_AllDetails
								.optString("password");
						userinfo.mobile_number = j_object_AllDetails
								.optString("mobile_number");
						userinfo.feed = j_object_AllDetails
								.optString("feed");
						userinfo.option_box = j_object_AllDetails
								.optString("option_box");
						userinfo.reached_from = j_object_AllDetails
								.optString("reached_from");
						userinfo.birth_date = j_object_AllDetails
								.optString("birth_date");
						userinfo.gender = j_object_AllDetails
								.optString("gender");
						userinfo.street = j_object_AllDetails
								.optString("street");
						userinfo.city = j_object_AllDetails
								.optString("city");
						userinfo.state = j_object_AllDetails
								.optString("state");
						userinfo.country = j_object_AllDetails
								.optString("country");
						userinfo.zipcode = j_object_AllDetails
								.optString("zipcode");
						userinfo.fb_userid = j_object_AllDetails
								.optString("fb_userid");
						userinfo.gmail_userid = j_object_AllDetails
								.optString("gmail_userid");

					} 

				} catch (Exception e) {
					e.printStackTrace();
				}

			}

		}  else if (requestNumber == ConstantValue.REQUESTCODE_FOLLOWER_VIEW) {
			String request = value[0];
			response = webAPIRequest.performPost_String(request, parameter);
			if (response != null) {
				Log.i("Response :==", response);
				try {

					JSONObject j_object = new JSONObject(response);
					String data = j_object.optString("data");
					JSONObject dataobject = new JSONObject(data);
					success = dataobject.optString("Success");
					if (success.equals("1")) {
						message = dataobject.optString("Message");

						JSONArray followerINfo = dataobject
								.optJSONArray("allDetails");
						for (int i = 0; i < followerINfo.length(); i++) {
							JSONObject jObject = followerINfo.optJSONObject(i);
							FollowerInfo follower = new FollowerInfo();

							follower.follower_Id = jObject
									.optString("follower_id");
							follower.user_Id = jObject.optString("user_id");
							follower.user_Image = jObject
									.optString("profile_picture");
							follower.user_Image_Thumb = jObject
									.optString("profile_picture");
							follower.first_name = jObject
									.optString("first_name");
							follower.last_name = jObject.optString("last_name");
							arraylist_followerListInfo.add(follower);
						}

					} else {
						message = dataobject.optString("Message");
					}

				} catch (Exception e) {
					e.printStackTrace();
				}

			}

		} else if (requestNumber == ConstantValue.REQUESTCODE_FOLLOWING_VIEW) {
			String request = value[0];
			response = webAPIRequest.performPost_String(request, parameter);
			if (response != null) {
				Log.i("Response :==", response);
				try {

					JSONObject j_object = new JSONObject(response);
					String data = j_object.optString("data");
					JSONObject dataobject = new JSONObject(data);
					success = dataobject.optString("Success");
					if (success.equals("1")) {
						message = dataobject.optString("Message");

						JSONArray followerINfo = dataobject
								.optJSONArray("allDetails");
						for (int i = 0; i < followerINfo.length(); i++) {
							JSONObject jObject = followerINfo.optJSONObject(i);
							FollowerInfo follower = new FollowerInfo();

							follower.follower_Id = jObject
									.optString("following_id");
							follower.user_Id = jObject.optString("user_id");
							follower.user_Image = jObject
									.optString("profile_picture");
							follower.user_Image_Thumb = jObject
									.optString("profile_picture");
							follower.first_name = jObject
									.optString("first_name");
							follower.last_name = jObject.optString("last_name");
							arraylist_followerListInfo.add(follower);
						}

					} else {
						message = dataobject.optString("Message");
					}

				} catch (Exception e) {
					e.printStackTrace();
				}

			}

		}
		else if (requestNumber == ConstantValue.REQUESTCODE_FEED_VIEW) 
		{
			Log.i("Request REQUESTCODE_FEED_VIEW","======================");
			Log.i("Request",value[0]);


			response = webAPIRequest.performPost_String(value[0],parameter);


			if (response != null) 
			{
				Log.i("Response REQUESTCODE_FEED_VIEW","======================");
				Log.i("Response ",response);

				try
				{

					JSONObject j_object_main = new JSONObject(response);

					JSONObject j_object_data = j_object_main
							.optJSONObject("data");

					success = j_object_data.optString("Success");
					message = j_object_data.optString("Message");
					if (success.equals("1")) 
					{
						JSONArray itemArray = j_object_data
								.optJSONArray("allDetails");

						for (int i = 0; i < itemArray.length(); i++)
						{
							JSONObject jObject = itemArray.optJSONObject(i);

							FeedInfo feed = new FeedInfo();

							feed.user_id = jObject.optString("user_id");
							feed.iteam_name = jObject.optString("iteam_name");
							feed.category_name = jObject.optString("category_name");
							feed.lattitude = jObject.optDouble("lattitude");
							feed.longitude = jObject.optDouble("longitude");
							feed.location_name = jObject.optString("location_name");
							feed.experiance = jObject.optString("experiance");
							feed.rate = jObject.optString("rate");
							feed.rate_image = jObject.optString("rate_image");
							feed.feed_image = jObject.optString("feed_image");
							feed.feed_image_thumb = jObject.optString("feed_image_thumb");
							feed.user_name = jObject.optString("user_name");
							feed.profile_picture = jObject.optString("profile_picture");
							feed.num_of_comments = jObject.optInt("num_of_comments");
							feed.num_of_approve = jObject.optInt("num_of_approve");
							feed.feed_id = jObject.optString("feed_id");
							feed.date_time = jObject.optString("date_time");

							JSONArray jArray_comments= jObject.optJSONArray("comments");

							for (int j = 0; j < jArray_comments.length(); j++) {
								CommentInfo commentInfo=new CommentInfo();
								JSONObject jobj_comment=jArray_comments.optJSONObject(j);
								commentInfo.comment=jobj_comment.optString("comment");
								commentInfo.comment_Id=jobj_comment.optString("id");
								commentInfo.user_name=jobj_comment.optString("user_name");

								feed.arrylist_commentInfo.add(commentInfo);
							}



							//							if (feed.feed_image!=null) 
							//								if (!feed.feed_image.equalsIgnoreCase("")) 
							//									try {
							//										URL url = new URL(feed.feed_image);
							//										HttpGet httpRequest = null;
							//
							//										httpRequest = new HttpGet(url.toURI());
							//
							//										HttpClient httpclient = new DefaultHttpClient();
							//										HttpResponse response = (HttpResponse) httpclient
							//												.execute(httpRequest);
							//
							//										HttpEntity entity = response.getEntity();
							//										BufferedHttpEntity b_entity = new BufferedHttpEntity(entity);
							//										InputStream input = b_entity.getContent();
							//
							//										feed.feed_image_bitmap = BitmapFactory.decodeStream(input);
							//
							//
							//									}
							//							catch (Exception ex) {
							//
							//							}
							if (feed.profile_picture!=null) 
								if (!feed.profile_picture.equalsIgnoreCase(""))
								{
									Bitmap myBitmap = null ;
									try {
										URL url = new URL(feed.profile_picture);
										HttpURLConnection connection = (HttpURLConnection) url.openConnection();
										connection.setDoInput(true);
										connection.connect();
										InputStream input = connection.getInputStream();
										myBitmap = BitmapFactory.decodeStream(input);

									} catch (IOException e) {
										e.printStackTrace();
									}
									Bitmap resized = Bitmap.createScaledBitmap(myBitmap, 100, 100, true);
									feed.profile_picture_bitmap = ConstantValue.getRoundedRectBitmap(resized, 100);

								}

							arraylist_FeedInfo.add(feed);
						}
					} 

				} catch (Exception e) {
					e.printStackTrace();
				}

			}

		}


		else if (requestNumber == ConstantValue.REQUESTCODE_ACSETTING_ADD) {
			String request = value[0];
			response = webAPIRequest.performPost_String(request, parameter);
			if (response != null) {
				Log.i("Response :==", response);
				try {

					JSONObject j_object = new JSONObject(response);
					String data = j_object.optString("data");
					JSONObject dataobject = new JSONObject(data);
					success = dataobject.optString("Success");
					if (success.equals("1")) {
						message = dataobject.optString("Message");

					} else {
						message = dataobject.optString("Message");
					}

				} catch (Exception e) {
					e.printStackTrace();
				}

			}

		}else if (requestNumber == ConstantValue.REQUESTCODE_ACSETTING_VIEW) {
			String request = value[0];
			response = webAPIRequest.performPost_String(request, parameter);
			if (response != null) {
				Log.i("Response :==", response);
				try {

					JSONObject j_object = new JSONObject(response);
					String data = j_object.optString("data");
					JSONObject dataobject = new JSONObject(data);
					success = dataobject.optString("Success");
					if (success.equals("1")) {
						message = dataobject.optString("Message");
						JSONArray followerINfo = dataobject
								.optJSONArray("allDetails");

						JSONObject jObject = followerINfo.optJSONObject(0);
						setting=new SettingInfo();
						setting.sound_Effect = jObject
								.optString("sound_effect");
						setting.proxy = jObject.optString("user_id");
						setting.location = jObject
								.optString("location");
						setting.font_Size = jObject
								.optString("font_size");
						setting.about = jObject
								.optString("about");	


					} else {
						message = dataobject.optString("Message");
					}

				} catch (Exception e) {
					e.printStackTrace();
				}

			}

		}
		else if (requestNumber == ConstantValue.REQUESTCODE_CHECKIN_ADD) {
			String request = value[0];
			response = webAPIRequest.performPost_String(request, parameter);
			if (response != null) {
				Log.i("Response :==", response);
				try {

					JSONObject j_object = new JSONObject(response);
					String data = j_object.optString("data");
					JSONObject dataobject = new JSONObject(data);
					success = dataobject.optString("Success");
					if (success.equals("1")) {
						message = dataobject.optString("Message");

					} else {
						message = dataobject.optString("Message");
					}

				} catch (Exception e) {
					e.printStackTrace();
				}

			}

		}
		else if (requestNumber == ConstantValue.REQUESTCODE_CATEGORY_VIEW) {


			Log.i("Request REQUESTCODE_CATEGORY_VIEW","======================");
			Log.i("Request",value[0]);

			response = webAPIRequest.performGet(value[0]);


			if (response != null) 
			{
				Log.i("Response REQUESTCODE_CATEGORY_VIEW","======================");
				Log.i("Response ",response);

				try 
				{
					JSONObject j_object_main = new JSONObject(response);

					JSONObject j_object_data = j_object_main
							.optJSONObject("data");

					success = j_object_data.optString("Success");
					message = j_object_data.optString("Message");

					if (success.equals("1")) {

						JSONArray jsonarray=j_object_data.optJSONArray("allCategory");

						for (int i = 0; i < jsonarray.length(); i++) {

							JSONObject categoryJObj=jsonarray.optJSONObject(i);

							Category category=	new Category();

							category.category_id =	categoryJObj.optString("category_id");
							category.category_name = categoryJObj.optString("category_name");
							//category.category_image = categoryJObj.optString("category_image");
							category.category_link = categoryJObj.optString("category_link");
							//category.selected_image = categoryJObj.optString("selected_image");

							//							URL url = new URL(category.selected_image);
							//							HttpGet httpRequest = null;
							//
							//							httpRequest = new HttpGet(url.toURI());
							//
							//							HttpClient httpclient = new DefaultHttpClient();
							//							HttpResponse response = (HttpResponse) httpclient
							//									.execute(httpRequest);
							//
							//							HttpEntity entity = response.getEntity();
							//							BufferedHttpEntity b_entity = new BufferedHttpEntity(entity);
							//							InputStream input = b_entity.getContent();
							//
							//							category.selected_image_bimap = BitmapFactory.decodeStream(input);


							arraylist_Category.add(category);
						}

						//						for (int i = 0; i < Activity_Home.static_arraylist_FeedInfo.size(); i++) {
						//
						//							if (Activity_Home.static_arraylist_FeedInfo.get(i).feed_image_thumb!=null) 
						//								if (!Activity_Home.static_arraylist_FeedInfo.get(i).feed_image_thumb.equalsIgnoreCase("")) 
						//								{
						//									URL url = new URL(Activity_Home.static_arraylist_FeedInfo.get(i).feed_image_thumb);
						//									HttpGet httpRequest = null;
						//
						//									httpRequest = new HttpGet(url.toURI());
						//
						//									HttpClient httpclient = new DefaultHttpClient();
						//									HttpResponse response = (HttpResponse) httpclient
						//											.execute(httpRequest);
						//
						//									HttpEntity entity = response.getEntity();
						//									BufferedHttpEntity b_entity = new BufferedHttpEntity(entity);
						//									InputStream input = b_entity.getContent();
						//
						//									Activity_Home.static_arraylist_FeedInfo.get(i).feed_image_bitmap_thumb = BitmapFactory.decodeStream(input);
						//
						//
						//									//									Bitmap  change = Bitmap.createScaledBitmap(Activity_Home.static_arraylist_FeedInfo.get(i).feed_image_bitmap_thumb, 76, 76, false);
						//									//
						//									//									Bitmap border = BitmapFactory.decodeResource(act_main.getActivity().getResources(),R.drawable.explor_imagebordar);
						//									//								
						//									//									int width = border.getWidth();
						//									//								
						//									//									int height = border.getHeight();
						//									//
						//									//									Canvas canvas = new Canvas(border);
						//									//								
						//									//									//Bitmap scaledBorder = Bitmap.createScaledBitmap(border,border.getWidth(),border.getHeight(), false);
						//									//								
						//									//									canvas.drawBitmap(change,5,5,null);
						//									//									Activity_Home.static_arraylist_FeedInfo.get(i).feed_image_BitmapDescriptor=BitmapDescriptorFactory.fromBitmap(border);
						//
						//								}
						//						}

					} else {
					}

				} catch (Exception e) {
					e.printStackTrace();
				}

			}

		}
		return null;
	}

	protected void onPostExecute(Long result) {
		if (requestNumber == ConstantValue.REQUESTCODE_REGISTRATION) {
			act_main.setBackApiResponse(ConstantValue.REQUESTCODE_REGISTRATION,
					success, message, userinfo);
		} else if (requestNumber == ConstantValue.REQUESTCODE_FOLLOWER_VIEW) {
			act_main.setBackApiResponse(
					ConstantValue.REQUESTCODE_FOLLOWER_VIEW, success, message,
					arraylist_followerListInfo);
		} else if (requestNumber == ConstantValue.REQUESTCODE_FOLLOWING_VIEW) {
			act_main.setBackApiResponse(
					ConstantValue.REQUESTCODE_FOLLOWING_VIEW, success, message,
					arraylist_followerListInfo);
		}
		else if (requestNumber == ConstantValue.REQUESTCODE_ACSETTING_ADD) {
			act_main.setBackApiResponse(
					ConstantValue.REQUESTCODE_ACSETTING_ADD, success, message);
		}
		else if (requestNumber == ConstantValue.REQUESTCODE_ACSETTING_VIEW) {
			act_main.setBackApiResponse(
					ConstantValue.REQUESTCODE_ACSETTING_VIEW, success, message,setting);
		}
		else if (requestNumber == ConstantValue.REQUESTCODE_CHECKIN_ADD) {
			act_main.setBackApiResponse(ConstantValue.REQUESTCODE_CHECKIN_ADD,
					success, message);
		}
		else if (requestNumber == ConstantValue.REQUESTCODE_FEED_VIEW) {
			act_main.setBackApiResponse(ConstantValue.REQUESTCODE_FEED_VIEW,
					success, message, arraylist_FeedInfo);
		}
		else if (requestNumber == ConstantValue.REQUESTCODE_CATEGORY_VIEW) {
			act_main.setBackApiResponse(ConstantValue.REQUESTCODE_CATEGORY_VIEW,
					success, message,arraylist_Category);
		}
		if (dialog != null) {
			if (dialog.isShowing()) {
				dialog.dismiss();
			}

		}
	}
}

package com.android.ytoi.webclass;

import java.util.ArrayList;

import android.graphics.Bitmap;

import com.google.android.gms.maps.model.BitmapDescriptor;

public class FeedInfo
{
	
	public String user_id = "";
	public String iteam_name="";
	public String category_name="";
	public Double lattitude=0.0;
	public Double longitude=0.0;
	public String location_name="";
	public String experiance="";
	public String place_address="";
	public String feed_id="";
	public String rate="";
	public String rate_image="";
	public String feed_image="";
	public Bitmap feed_image_bitmap=null;
	public Bitmap feed_image_bitmap_thumb=null;
	public BitmapDescriptor feed_image_BitmapDescriptor=null;
	public Bitmap profile_picture_bitmap=null;
	public String feed_image_thumb="";
	public String user_name="";
	public int num_of_comments=0;
	public int num_of_approve=0;
	public String profile_picture="";
	public ArrayList<CommentInfo> arrylist_commentInfo=new ArrayList<CommentInfo>();
	public String date_time="";
	
}

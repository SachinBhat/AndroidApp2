package com.android.ytoi.utils;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import android.graphics.Bitmap;



public class Utills {


//	public static int MAX_ThumbImage_SIZE = 125;
//	public static int MAX_Image_SIZE = 320;
	// MESSAGE WEB
	public static double CURRENT_LATTITUDE=0.0;
	public static double  CURRENT_LONGITUDE=0.0;
	
	public static double CURRENT_LATTITUDE_KEY=0.0;
	public static double  CURRENT_LONGITUDE_KEY=0.0;
	
	public static String MESSAGE_FRIENDS_ID[];

	 public static void CopyStream(InputStream is, OutputStream os)
	    {
	        final int buffer_size=1024;
	        try
	        {
	            byte[] bytes=new byte[buffer_size];
	            for(;;)
	            {
	              int count=is.read(bytes, 0, buffer_size);
	              if(count==-1)
	                  break;
	              os.write(bytes, 0, count);
	            }
	        }
	        catch(Exception ex){}
	    }
		public static byte[] bitmapTobyteArry(Bitmap bitmap)
		{
			ByteArrayOutputStream stream = new ByteArrayOutputStream();
			bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
			return stream.toByteArray();
		}

}


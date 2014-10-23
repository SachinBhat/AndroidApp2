package com.android.ytoi.utils;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.os.Build;
import android.view.inputmethod.InputMethodManager;

import com.android.ytoi.webclass.UserInfo;

public class ConstantValue {

	// public final static String URL_BASE= "http://71.185.65.7/ws/";

	public final static String URL_BASE = "http://www.sevenstarinfotech.com/projects/demo/ytoi/API/";
	// public final static String IMAGE_URL_BASE = "http://71.185.65.7/";
	private static final String VALID_EMAIL = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
	private static final String VALID_PASSWORD = "(?=.*[0-9])(?=.*[a-zA-Z])([a-zA-Z0-9]+)";
	private static final String VALID_MOBILE = "^[0-9]{10}$";
	public static String rPrinter[], kPrinter[];
	public static boolean flag = false;
	public static final int IMAGE_CAPTURE_OPTION = 3;
	public static UserInfo userinfo;
	public static int DEVICE_WIDTH = 480;
	public static int DEVICE_HEIGHT = 800;
	// get user data from session
	public static HashMap<String, String> user;
	// public final static String WEB_API_KEY = "app-key";
	// public final static String WEB_API_KEY_VALUE = "1234";

	//Notification
	/**
	 * Tag used on log messages.
	 */
	// Google project id
	public static final String SENDER_ID = "942519382075"; 
	// Your Facebook APP ID

	static final String TAG = "YTOI GCM";
	static final String DISPLAY_MESSAGE_ACTION =
			"com.android.ytoi.DISPLAY_MESSAGE";

	static final String EXTRA_MESSAGE = "message";

	/**
	 * Notifies UI to display a message.
	 * <p>
	 * This method is defined in the common helper because it's used both by
	 * the UI and the background service.
	 *
	 * @param context application's context.
	 * @param message message to be displayed.
	 */
	public static void displayMessage(Context context, String message) {
		Intent intent = new Intent(DISPLAY_MESSAGE_ACTION);
		intent.putExtra(EXTRA_MESSAGE, message);
		context.sendBroadcast(intent);
	}

	public final static String PRE_GOOGLE_NAME="com.ytoi.google";
	public final static int PRE_GOOGLE_MOD=0;
	public final static String PRE_GOOGLELOGIN_FLAG="isGoogleLogin";
	
	
	

	public static String FILENAME = "YTOI_data";

	// GET Login
	public final static int REQUESTCODE_LOGIN_PASSWORD = 111;
	public final static String URL_LOGIN_PASSWORD = URL_BASE + "login.php?";

	// GET Change Password
	// http://www.sevenstarinfotech.com/projects/demo/ytoi/API/change_password.php?user_id=value&old_password=value&new_password=value
	public final static int REQUESTCODE_CHANGE_PASSWORD = 112;
	public final static String URL_CHANGE_PASSWORD = URL_BASE
			+ "change_password.php?";

	// GET forgot Password
	//
	public final static int REQUESTCODE_FORGOT_PASSWORD = 113;
	public final static String URL_FORGOT_PASSWORD = URL_BASE
			+ "forget_password.php?";

	// GET Registration
	//
	public final static int REQUESTCODE_REGISTRATION = 114;
	public final static String URL_REGISTRATION = URL_BASE
			+ "registration.php?";

	// GET Feed add
	//http://www.sevenstarinfotech.com/projects/demo/ytoi/API/feed_add.php?feed_name=value&feed_link=value
	public final static int REQUESTCODE_FEED_ADD = 115;
	public final static String URL_FEED_ADD = URL_BASE + "feed_add.php?";

	// GET Feed
	//http://www.sevenstarinfotech.com/projects/demo/ytoi/API/feed_view.php?user_id=1
	public final static int REQUESTCODE_FEED_VIEW = 116;
	public final static String URL_FEED_VIEW = URL_BASE + "feed_view.php?";


	// GET Comment add
	//http://www.sevenstarinfotech.com/projects/demo/ytoi/API/comment_add.php?comment=value&raputation_point=value
	public final static int REQUESTCODE_COMMENT_ADD = 117;
	public final static String URL_COMMENT_ADD = URL_BASE + "comments_add.php?";

//	//http://www.sevenstarinfotech.com/projects/demo/ytoi/API/comments_add.php?user_id=1&feed_id=5&comment=This%20is%20test%20comment
//	public final static int REQUESTCODE_COMMENT_VIEW = 126;
//	public final static String URL_COMMENT_VIEW = URL_BASE + "comment_view.php?";


	// http://www.sevenstarinfotech.com/projects/demo/ytoi/API/feed_approve.php?feed_id=1&user_id=1
	public final static int REQUESTCODE_APPROVE_VIEW = 118;
	public final static String URL_APPROVE_ADD = URL_BASE + "feed_approve.php?";
	
		
	// GET Followers View
	//http://www.sevenstarinfotech.com/projects/demo/ytoi/API/followers_view.php?user_id=1
	public final static int REQUESTCODE_FOLLOWER_VIEW = 119;
	public final static String URL_FOLLOWER_VIEW = URL_BASE
			+ "followers_view.php?";



	// GET Following View
	//http://www.sevenstarinfotech.com/projects/demo/ytoi/API/following_view.php?user_id=1
	public final static int REQUESTCODE_FOLLOWING_VIEW = 120;
	public final static String URL_FOLLOWING_VIEW = URL_BASE
			+ "following_view.php?";


	// GET Setting add
	//http://www.sevenstarinfotech.com/projects/demo/ytoi/API/accountSetting_add.php?user_id=1&sound_effect=0&font_size=12&proxy=14&location=Thaltej&about=testing
	public final static int REQUESTCODE_ACSETTING_ADD = 121;
	public final static String URL_ACSETTING_ADD = URL_BASE
			+ "accountSetting_add.php";

	// GET Setting View
	//http://www.sevenstarinfotech.com/projects/demo/ytoi/API/accountSetting_view.php?user_id=1
	public final static int REQUESTCODE_ACSETTING_VIEW = 122;
	public final static String URL_ACSETTING_VIEW = URL_BASE
			+ "accountSetting_view.php?";


	// GET Check in add
	public final static int REQUESTCODE_CHECKIN_ADD = 123;
	public final static String URL_CHECKIN_ADD = URL_BASE
			+ "check_in_add.php?";


	// GET Category View
	//http://www.sevenstarinfotech.com/projects/demo/ytoi/API/followers_view.php?user_id=1
	public final static int REQUESTCODE_CATEGORY_VIEW = 124;
	public final static int REQUESTCODE_CATEGORY_EXPLORER_VIEW = 125;
	public final static String URL_CATEGORY_VIEW = URL_BASE
			+ "category_view.php?";

	public final static int REQUESTCODE_IMAGERREGISTRATION_VIEW = 126;
	public final static String URL_IMAGE_VIEW = URL_BASE
			+ "image_upload.php?user_id=";

	
	
	public static boolean checkEmail(String d) {
		Pattern pattern = Pattern.compile(VALID_EMAIL);
		Matcher matcher = pattern.matcher(d);

		return matcher.matches();

	}

	public boolean checkPassword(String f) {
		Pattern pattern = Pattern.compile(VALID_PASSWORD);
		Matcher matcher = pattern.matcher(f);

		return matcher.matches();

	}

	public boolean checkMobile(String e) {
		Pattern pattern = Pattern.compile(VALID_MOBILE);
		Matcher matcher = pattern.matcher(e);

		return matcher.matches();

	}

	public static void hideSoftKeyboard(Activity activity) {
		InputMethodManager inputMethodManager = (InputMethodManager) activity
				.getSystemService(Activity.INPUT_METHOD_SERVICE);
		inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus()
				.getWindowToken(), 0);
	}

	public static String getCurrentDate() {
		long date = System.currentTimeMillis();

		SimpleDateFormat sdf = new SimpleDateFormat("MMMMdd,yyyy,");
		return sdf.format(date);
	}

	public static boolean isHoneycomb() {
		// Can use static final constants like HONEYCOMB, declared in later
		// versions
		// of the OS since they are inlined at compile time. This is guaranteed
		// behavior.
		return Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB;
	}

	public static boolean isTablet(Context context) {
		return (context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) >= Configuration.SCREENLAYOUT_SIZE_LARGE;
	}

	public static boolean isHoneycombTablet(Context context) {
		return isHoneycomb() && isTablet(context);
	}

	public static Bitmap getRoundedRectBitmap(Bitmap bitmap, int pixels) {
		Bitmap result = null;
		try {
			result = Bitmap.createBitmap(200, 200, Bitmap.Config.ARGB_8888);
			Canvas canvas = new Canvas(result);

			int color = 0xff424242;
			Paint paint = new Paint();
			Rect rect = new Rect(0, 0, 200, 200);

			paint.setAntiAlias(true);
			canvas.drawARGB(0, 0, 0, 0);
			paint.setColor(color);
			canvas.drawCircle(50, 50, 50, paint);
			paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
			canvas.drawBitmap(bitmap, rect, rect, paint);

		} catch (NullPointerException e) {
		} catch (OutOfMemoryError o) {
		}
		return result;
	}


}

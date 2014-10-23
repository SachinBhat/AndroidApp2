package com.android.ytoi;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
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
import org.apache.http.message.BasicNameValuePair;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender.SendIntentException;
import android.content.SharedPreferences.Editor;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.ytoi.utils.Act_Main;
import com.android.ytoi.utils.ConstantValue;
import com.android.ytoi.utils.SessionManager;
import com.android.ytoi.web.HttpHelper;
import com.android.ytoi.webclass.UserInfo;
import com.facebook.AppEventsLogger;
import com.facebook.LoggingBehavior;
import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.Session.StatusCallback;
import com.facebook.SessionState;
import com.facebook.Settings;
import com.facebook.android.Facebook;
import com.facebook.model.GraphUser;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.plus.People;
import com.google.android.gms.plus.People.LoadPeopleResult;
import com.google.android.gms.plus.Plus;
import com.google.android.gms.plus.model.people.Person;

public class Activity_Login extends Act_Main implements
ConnectionCallbacks, OnConnectionFailedListener,
ResultCallback<People.LoadPeopleResult>, OnClickListener {

	private Button signUpBTN, signInBTN, fbLoginBTN,btn_googleplus;
	static final String PENDING_REQUEST_BUNDLE_KEY = "com.facebook.samples.graphapi:PendingRequest";
	private EditText usernameEDT, passwordEDT;

	private TextView forgotPassTXT;

	private Context context = this;
	public static boolean check_Login =false;
	private String usernameSTR, passwordSTR;

	public final static String TOKEN = "access_token";
	public final static String EXPIRES = "expires_in";
	public static String KEY = "ytoiPrefs";
	public final static String[] PERMISSIONS = new String[] { "email",
		"publish_stream","publish_actions", "user_birthday", "user_photos"};

	@SuppressWarnings("unused")

	private static final int REQUEST_CODE_RESOLVE_ERR = 9000;

	private Typeface type_BOLD, type_BOOK;

	private UserInfo userInfo=new UserInfo();

	protected Session session_fb;
	boolean pendingRequest;


	private static final String TAG = "android-plus-quickstart";
	private static final int STATE_DEFAULT = 0;
	private static final int STATE_SIGN_IN = 1;
	private static final int STATE_IN_PROGRESS = 2;
	private static final int RC_SIGN_IN = 0;
	private static final int DIALOG_PLAY_SERVICES_ERROR = 0;
	private static final String SAVED_PROGRESS = "sign_in_progress";
	public static GoogleApiClient mGoogleApiClient;
	private int mSignInProgress;
	private PendingIntent mSignInIntent;
	private int mSignInError;
	private SessionManager session;




	@Override
	protected void onResume() {
		super.onResume();

		AppEventsLogger.activateApp(this, "640430639328182");
	}
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login_main);

		session = new SessionManager(getApplicationContext(),this);

		session.checkLogin();

		init();

		signUpBTN.setOnClickListener(this);
		signInBTN.setOnClickListener(this);
		fbLoginBTN.setOnClickListener(this);
		btn_googleplus.setOnClickListener(this);
		forgotPassTXT.setOnClickListener(this);

		this.session_fb = createSession();

		Settings.addLoggingBehavior(LoggingBehavior.INCLUDE_ACCESS_TOKENS);


		if (savedInstanceState != null) {
			mSignInProgress = savedInstanceState
					.getInt(SAVED_PROGRESS, STATE_DEFAULT);
		}

		mGoogleApiClient = buildGoogleApiClient();
	}






	public void init() 
	{
		signUpBTN = (Button) findViewById(R.id.loginmain_Button_SignUp);

		signInBTN = (Button) findViewById(R.id.loginmain_Button_SignIn);

		fbLoginBTN = (Button) findViewById(R.id.loginmain_Button_Facebook);

		btn_googleplus = (Button) findViewById(R.id.loginmain_Button_GooglePlus);

		usernameEDT = (EditText) findViewById(R.id.loginmain_Edittext_Username);

		passwordEDT = (EditText) findViewById(R.id.loginmain_Edittext_Password);

		forgotPassTXT = (TextView) findViewById(R.id.loginmain_Textview_forgotPass);

		type_BOLD = Typeface.createFromAsset(getAssets(),
				"fonts/Novecentowide-Bold.otf");

		type_BOOK = Typeface.createFromAsset(getAssets(),
				"fonts/Novecentowide-Book.otf");

		set_TypeFace_Control();
	}

	public void set_TypeFace_Control() {
		signInBTN.setTypeface(type_BOLD);
		signUpBTN.setTypeface(type_BOLD);
		usernameEDT.setTypeface(type_BOOK);
		passwordEDT.setTypeface(type_BOOK);
	}


	@Override
	public void onClick(View v) 
	{
		switch (v.getId()) 
		{

		case R.id.loginmain_Button_SignUp:
			startActivity(new Intent(context, Activity_Registration.class));
			break;

		case R.id.loginmain_Button_SignIn:
			if (!(usernameEDT.getText().toString().equals("") && passwordEDT
					.getText().toString().equals(""))) {
				usernameSTR = usernameEDT.getText().toString();
				passwordSTR = passwordEDT.getText().toString();
				if (ConstantValue.checkEmail(usernameSTR)) {
					if (isOnline()) // connected
					{
						String uri = null;
						uri = String.format(ConstantValue.URL_LOGIN_PASSWORD);
						uri = uri.replace(" ", "%20");
						Log.i("LoginInfo", uri);
						if (uri != null) {
							uri = uri.replace(" ", "%20");

							List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>(
									2);
							nameValuePair.add(new BasicNameValuePair("email",
									usernameSTR));
							nameValuePair.add(new BasicNameValuePair(
									"password", passwordSTR));

							HttpHelper httpHelper = new HttpHelper(
									ConstantValue.REQUESTCODE_LOGIN_PASSWORD,
									Activity_Login.this, "Loading...",
									nameValuePair);
							httpHelper.execute(uri);
						}

					} else {

						Toast.makeText(getApplicationContext(),
								"Internet Connection Fail", Toast.LENGTH_SHORT)
								.show();
					}

				}
				else {
					Toast.makeText(context, "Enter valid Email",
							Toast.LENGTH_SHORT).show();
				}

			}
			else
				Toast.makeText(context, "Must fill all information",
						Toast.LENGTH_SHORT).show();
			break;

		case R.id.loginmain_Button_Facebook:


			onClickRequest();

			break;


		case R.id.loginmain_Button_GooglePlus:
			if (!mGoogleApiClient.isConnecting()) 
				resolveSignInError();

			break;


		case R.id.loginmain_Textview_forgotPass:
			System.out.println("Forgot Password call");
			startActivity(new Intent(context, Activity_ForgotPassword.class));
			break;

		default:
			break;

		}
	}

	@SuppressWarnings("deprecation")
	public boolean saveCredentials(Facebook facebook) 
	{

		Editor editor = getSharedPreferences(KEY, Context.MODE_PRIVATE).edit();

		editor.putString(TOKEN, facebook.getAccessToken());
		editor.putLong(EXPIRES, facebook.getAccessExpires());
		return editor.commit();
	}

	public void getProfileInformation(GraphUser user) {

		try
		{

			final String f_id = user.getId();

			final String f_name = user.getFirstName();

			final String l_name = user.getLastName();

			final String username = user.getUsername();

			final String email = user.getProperty("email").toString();

			final String gender = user.getProperty("gender").toString();

			//final String city = user.getInnerJSONObject().getString("hometown")+" "+user.getInnerJSONObject().getString("city");

			//final String location = user.getInnerJSONObject().getString("hometown")+" "+user.getInnerJSONObject().getString("city");

			final String birthday = user.getBirthday();

			Log.e("f_id", f_id +" s");
			Log.e("f_name", f_name+" s");
			Log.e("l_name", l_name+" s");
			Log.e("username", username+" s");
			Log.e("email", email+" s");
			Log.e("gender", gender+" s");
			Log.e("birthday", birthday+" s");
			//Log.e("city", city+" s");

			userInfo.birth_date = birthday;
			//String address[] = city.split(",");
			//	userInfo.city = address[0];
			//	userInfo.country = address[1];
			userInfo.email = email;
			userInfo.first_name = f_name;
			userInfo.last_name = l_name;
			userInfo.gender = gender;
			userInfo.mobile_number = "";
			userInfo.profile_picture = "http://graph.facebook.com/"
					+ f_id + "/picture?type=small";
			userInfo.state = "";
			userInfo.status = "";
			userInfo.street = "";
			userInfo.fb_userid = f_id;
			userInfo.gmail_userid = "";
			userInfo.profile_picture = "";
			userInfo.user_id = "";

			userInfo.zipcode = "";
			registration();
		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		super.onRestoreInstanceState(savedInstanceState);

		pendingRequest = savedInstanceState.getBoolean(PENDING_REQUEST_BUNDLE_KEY, pendingRequest);
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putInt(SAVED_PROGRESS, mSignInProgress);
		outState.putBoolean(PENDING_REQUEST_BUNDLE_KEY, pendingRequest);
	}
	@Override
	protected void onActivityResult(int requestCode, int responseCode,
			Intent data) {

		super.onActivityResult(requestCode, responseCode, data);
		if (requestCode == RC_SIGN_IN)
		{
			if (responseCode == RESULT_OK) {
				mSignInProgress = STATE_SIGN_IN;
			} else {
				mSignInProgress = STATE_DEFAULT;
			}

			if (!mGoogleApiClient.isConnecting()) {
				mGoogleApiClient.connect();
			}
		} else {
			if (this.session_fb.onActivityResult(this, requestCode, responseCode, data) &&
					pendingRequest &&
					this.session_fb.getState().isOpened()) {
				sendRequests();
			}
		}

	}


	public void setBackApiResponse(int requestcode, Object obj1, Object obj2,
			Object obj3) {

		if (requestcode == ConstantValue.REQUESTCODE_LOGIN_PASSWORD || requestcode == ConstantValue.REQUESTCODE_REGISTRATION) {
			if (((String)obj1).equalsIgnoreCase("1")) {

				if (requestcode == ConstantValue.REQUESTCODE_REGISTRATION) {
					String temp_profile_picture=userInfo.profile_picture;
					userInfo = (UserInfo) obj3;
					userInfo.profile_picture=temp_profile_picture;
				}
				else
					userInfo = (UserInfo) obj3;

				session.createLoginSession(userInfo);

				startActivity(new Intent(context, Activity_Home.class));
				finish();

			} else
				Toast.makeText(context, obj2.toString(), Toast.LENGTH_SHORT)
				.show();
		}
		else
		{

		}
	}

	public void registration() {
		if (isOnline()) // connected
		{
			String uri = null;
			uri = String.format(ConstantValue.URL_REGISTRATION);
			if (uri != null) {
				uri = uri.replace(" ", "%20");

				Bundle bundel = new Bundle();

				bundel.putString("email", userInfo.email);
				bundel.putString("first_name", userInfo.first_name);
				bundel.putString("last_name", userInfo.last_name);
				bundel.putString("birth_date", userInfo.birth_date);
				bundel.putString("mobile_number", userInfo.mobile_number);
				bundel.putString("street", userInfo.street);
				bundel.putString("city", userInfo.city);
				bundel.putString("state", userInfo.state);
				bundel.putString("country", userInfo.country);
				bundel.putString("zipcode", userInfo.zipcode);
				bundel.putString("gender", userInfo.gender);
				bundel.putString("fb_userid", userInfo.fb_userid);
				bundel.putString("gmail_userid", userInfo.gmail_userid);
				bundel.putString("password","");

				byte[] userimage_bytearry=null;
				if (userInfo.profile_picture!=null) 
					if (!userInfo.profile_picture.equalsIgnoreCase("")) 
						try {
							URL url = new URL(userInfo.profile_picture);
							Log.e("userInfo.profile_picture", userInfo.profile_picture);
							HttpGet httpRequest = null;

							httpRequest = new HttpGet(url.toURI());

							HttpClient httpclient = new DefaultHttpClient();
							HttpResponse response = (HttpResponse) httpclient
									.execute(httpRequest);

							HttpEntity entity = response.getEntity();
							BufferedHttpEntity b_entity = new BufferedHttpEntity(entity);
							InputStream input = b_entity.getContent();

							ByteArrayOutputStream buffer = new ByteArrayOutputStream();

							int nRead;
							byte[] data = new byte[45384];

							while ((nRead = input.read(data, 0, data.length)) != -1) {
								buffer.write(data, 0, nRead);
							}

							buffer.flush();

							userimage_bytearry = buffer.toByteArray();


						}
				catch (Exception ex) 
				{
//					HttpHelper httpHelper = new HttpHelper(
//							ConstantValue.REQUESTCODE_REGISTRATION,
//							Activity_Login.this, "Loading...", bundel,userimage_bytearry);
//					httpHelper.execute(uri);
					ex.getStackTrace();
				}
				HttpHelper httpHelper = new HttpHelper(
						ConstantValue.REQUESTCODE_REGISTRATION,
						Activity_Login.this, "Loading...", bundel,userimage_bytearry);
				httpHelper.execute(uri);

				//				List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>(
				//						2);
				//
				//				nameValuePair.add(new BasicNameValuePair("email", userInfo.email));
				//				nameValuePair.add(new BasicNameValuePair("first_name",
				//						userInfo.first_name));
				//				nameValuePair.add(new BasicNameValuePair("last_name",
				//						userInfo.last_name));
				//				nameValuePair.add(new BasicNameValuePair("birth_date",
				//						userInfo.birth_date));
				//				nameValuePair.add(new BasicNameValuePair("mobile_number",
				//						userInfo.mobile_number));
				//				nameValuePair.add(new BasicNameValuePair("street", userInfo.street));
				//				nameValuePair.add(new BasicNameValuePair("city", userInfo.city));
				//				nameValuePair.add(new BasicNameValuePair("state", userInfo.state));
				//				nameValuePair
				//				.add(new BasicNameValuePair("country", userInfo.country));
				//				nameValuePair
				//				.add(new BasicNameValuePair("zipcode", userInfo.zipcode));
				//				nameValuePair.add(new BasicNameValuePair("gender", userInfo.gender));
				//				nameValuePair.add(new BasicNameValuePair("file", userInfo.profile_picture));
				//				nameValuePair.add(new BasicNameValuePair("fb_userid",
				//						userInfo.fb_userid));
				//				nameValuePair.add(new BasicNameValuePair("gmail_userid",
				//						userInfo.gmail_userid));
				//				HttpHelper httpHelper = new HttpHelper(
				//						ConstantValue.REQUESTCODE_REGISTRATION,
				//						Activity_Login.this, "Loading...", nameValuePair);
				//				httpHelper.execute(uri);


			}

		}
	}


	private Session createSession() {
		Session activeSession = Session.getActiveSession();
		if (activeSession == null || activeSession.getState().isClosed()) {
			activeSession = new Session.Builder(this).setApplicationId("640430639328182").build();
			Session.setActiveSession(activeSession);
		}
		return activeSession;
	}
	private void onClickRequest() {
		if (this.session_fb.isOpened()) {
			sendRequests();
		} else {
			StatusCallback callback = new StatusCallback() {
				public void call(Session session, SessionState state, Exception exception) {
					if (exception != null) {
						new AlertDialog.Builder(Activity_Login.this)
						.setTitle("Login Failed")
						.setMessage(exception.getMessage())
						.setPositiveButton("OK", null)
						.show();
						Activity_Login.this.session_fb = createSession();
					}
				}
			};
			pendingRequest = true;
			this.session_fb.openForRead(new Session.OpenRequest(this).setCallback(callback));
		}
	}
	private void sendRequests() {

		final Session session = Session.getActiveSession();
		if (session != null && session.isOpened()) {
			Request request = Request.newMeRequest(session, new Request.GraphUserCallback() {
				@Override
				public void onCompleted(GraphUser user, Response response) {
					if (session == Session.getActiveSession()) {
						if (user != null) {
							getProfileInformation(user);
						}   
					}   
				}   
			}); 
			Request.executeBatchAsync(request);
		}  
	}
	@Override
	public void onConnectionFailed(ConnectionResult result) {
		if (mSignInProgress != STATE_IN_PROGRESS) {
			mSignInIntent = result.getResolution();
			mSignInError = result.getErrorCode();

			if (mSignInProgress == STATE_SIGN_IN) {
				resolveSignInError();
			}
		}

	}
	@Override
	public void onResult(LoadPeopleResult peopleData) {
	}
	@Override
	public void onConnected(Bundle arg0) {

		Person person = Plus.PeopleApi.getCurrentPerson(mGoogleApiClient);

		userInfo.first_name = person.getName().getGivenName();
		userInfo.last_name = person.getName().getFamilyName();
		userInfo.email = Plus.AccountApi.getAccountName(mGoogleApiClient);
		userInfo.birth_date = "";
		userInfo.gender = person.getGender() + "";
		userInfo.gmail_userid = person.getId();
		userInfo.fb_userid="";
		userInfo.city="";
		userInfo.country="";
		userInfo.mobile_number="";
		userInfo.profile_thumb="";
		userInfo.user_id="";
		userInfo.zipcode="";
		userInfo.profile_picture = person.getImage().getUrl() + "";
		userInfo.state = "";
		userInfo.status = "";
		userInfo.street = "";

		Plus.PeopleApi.loadVisible(mGoogleApiClient, null)
		.setResultCallback(this);

		mSignInProgress = STATE_DEFAULT;

		registration();
	}

	private GoogleApiClient buildGoogleApiClient() {
		return new GoogleApiClient.Builder(this)
		.addConnectionCallbacks(this)
		.addOnConnectionFailedListener(this)
		.addApi(Plus.API, null)
		.addScope(Plus.SCOPE_PLUS_LOGIN)
		.build();
	}

	@SuppressWarnings("deprecation")
	private void resolveSignInError() {
		if (mSignInIntent != null) {

			try
			{
				mSignInProgress = STATE_IN_PROGRESS;
				startIntentSenderForResult(mSignInIntent.getIntentSender(),
						RC_SIGN_IN, null, 0, 0, 0);
			} 
			catch (SendIntentException e) 
			{
				mSignInProgress = STATE_SIGN_IN;
				mGoogleApiClient.connect();
			}
		} 
		else 
		{
			showDialog(DIALOG_PLAY_SERVICES_ERROR);
		}  
	}

	@SuppressWarnings("deprecation")
	@Override
	protected Dialog onCreateDialog(int id) {
		switch(id) {
		case DIALOG_PLAY_SERVICES_ERROR:
			if (GooglePlayServicesUtil.isUserRecoverableError(mSignInError)) {
				return GooglePlayServicesUtil.getErrorDialog(
						mSignInError,
						this,
						RC_SIGN_IN, 
						new DialogInterface.OnCancelListener() {
							@Override
							public void onCancel(DialogInterface dialog) {
								mSignInProgress = STATE_DEFAULT;
							}
						});
			} else {
				return new AlertDialog.Builder(this)
				.setMessage(R.string.play_services_error)
				.setPositiveButton(R.string.close,
						new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						Log.e(TAG, "Google Play services error could not be "
								+ "resolved: " + mSignInError);
						mSignInProgress = STATE_DEFAULT;
					}
				}).create();
			}
		default:
			return super.onCreateDialog(id);
		}
	}

	public void clearCookies(){
		CookieSyncManager.createInstance(this);
		CookieManager cookieManager = CookieManager.getInstance();
		cookieManager.removeAllCookie();
	}

	@Override
	public void onConnectionSuspended(int cause) {
		mGoogleApiClient.connect();
	}
	@Override
	protected void onStart() {
		super.onStart();
		mGoogleApiClient.connect();
	}

	@Override
	protected void onStop() {
		super.onStop();

		if (mGoogleApiClient.isConnected()) {
			//mGoogleApiClient.disconnect();
		}
	}
	
}

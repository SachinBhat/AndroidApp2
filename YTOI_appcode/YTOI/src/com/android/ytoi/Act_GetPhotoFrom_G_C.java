package com.android.ytoi;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.hardware.Camera.PictureCallback;
import android.net.Uri;
import android.os.Bundle;
import android.view.Display;
import android.view.Surface;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import android.widget.Toast;

import com.android.ytoi.fragment.Fragment_AddFeed;
import com.android.ytoi.utils.ImageTools;
import com.android.ytoi.utils.Utills;

public class Act_GetPhotoFrom_G_C extends Activity	
{

	private static final int SELECT_PICTURE = 0;
	private static final int PIC_CROP = 1;
	private static boolean CHECk_FRAMEHOLDER_ADD = false;
	private static boolean CHECk_FLASHLIGHT_ON_OFF = false;
	private Camera obj_Camera;
	private ShowCamera showCamera;
	private FrameLayout frameLay_preview;
	private Button btn_clickSnp;
	public static boolean FLAG_CAPTURED_IMAGE = false;
	private Bitmap bitmap_pictureCallback=null;
	private static int Camera_camId = Camera.CameraInfo.CAMERA_FACING_BACK;

	public static Camera isCameraAvailiable(int camera_id)
	{
		Camera object = null;

		try
		{
			object = Camera.open(); 
		}

		catch (Exception e)
		{

		}

		return object; 
	}



	private PictureCallback pictureCallback = new PictureCallback() 
	{

		@Override
		public void onPictureTaken(byte[] data, Camera camera)
		{
			btn_clickSnp.setEnabled(false);
			bitmap_pictureCallback = ImageTools.toBitmap(data);

			if(bitmap_pictureCallback==null)
			{

				Toast.makeText(getApplicationContext(), "not taken", Toast.LENGTH_SHORT).show();
			}

			else
			{
				Display display = getWindowManager().getDefaultDisplay();
				int rotation = 0;
				switch (display.getRotation()) {
				case Surface.ROTATION_0: // This is display orientation
					rotation = 90;
					break;
				case Surface.ROTATION_90:
					rotation = 0;
					break;
				case Surface.ROTATION_180:
					rotation = 270;
					break;
				case Surface.ROTATION_270:
					rotation = 180;
					break;
				}

				//				if (ConstantValue.DEVICE_WIDTH<500) {
				//					bitmap_pictureCallback = ImageTools.rotate(bitmap_pictureCallback, rotation,ConstantValue.DEVICE_WIDTH,ConstantValue.DEVICE_WIDTH);
				//					//bitmap_pictureCallback = Bitmap.createScaledBitmap(bitmap_pictureCallback, ConstantValue.DEVICE_WIDTH-53, ConstantValue.DEVICE_WIDTH-53, true);
				//				}
				//				else if(ConstantValue.DEVICE_WIDTH<800)
				//				{
				//					bitmap_pictureCallback = ImageTools.rotate(bitmap_pictureCallback, rotation,ConstantValue.DEVICE_WIDTH-20,ConstantValue.DEVICE_WIDTH-20);
				//
				//				}
				//				else

				if (bitmap_pictureCallback.getHeight()>bitmap_pictureCallback.getWidth()) {
					bitmap_pictureCallback = ImageTools.rotate(bitmap_pictureCallback, rotation,bitmap_pictureCallback.getWidth());
				}
				else
				{
					bitmap_pictureCallback = ImageTools.rotate(bitmap_pictureCallback, rotation,bitmap_pictureCallback.getHeight());

				}


				bitmap_pictureCallback = ImageTools.adjustedContrast(bitmap_pictureCallback, 15);

				//bitmap_pictureCallback = ImageTools.changeBitmapContrastBrightness(bitmap_pictureCallback, 1,1);

				//				img_showImage.setImageBitmap(bitmap_pictureCallback);
				//
				//				frameLay_preview.setVisibility(View.GONE);
				//				
				//				btn_clickSnp.setVisibility(View.GONE);
				//				
				//				btn_save.setVisibility(View.VISIBLE);

				FLAG_CAPTURED_IMAGE=true;
				if (obj_Camera!=null)
					obj_Camera.release();
				if (bitmap_pictureCallback!=null)
				{
					Fragment_AddFeed.static_feedPhotoBitmap=bitmap_pictureCallback;
					Fragment_AddFeed.static_feedPhotoByte = Utills.bitmapTobyteArry(bitmap_pictureCallback);
				}
				Activity_Home.check_categorysubmit=true;
				finish();

			}

			if (obj_Camera!=null)
				obj_Camera.release();
		}
	};



	@Override
	public void onBackPressed() 
	{

		FLAG_CAPTURED_IMAGE=false;
		if (obj_Camera!=null)
			obj_Camera.release();
		finish();

		super.onBackPressed();

	};

	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);

		setContentView(R.layout.capture_img_gallery);

		findIDfromXml();

		CHECk_FRAMEHOLDER_ADD=false;

		if(Fragment_AddFeed.httpGooglePlace!=null)
		{

			Fragment_AddFeed.httpGooglePlace.cancelRequest();

			Fragment_AddFeed.httpGooglePlace=null;

		}

		if(cameraInitialize(Camera_camId))
		{
			CHECk_FRAMEHOLDER_ADD=true;

			@SuppressWarnings("deprecation")

			FrameLayout.LayoutParams layParms=new LayoutParams(FrameLayout.LayoutParams.FILL_PARENT,FrameLayout.LayoutParams.FILL_PARENT);

			frameLay_preview.addView(showCamera,layParms);
		}


	}
	private boolean cameraInitialize(int camera_id)
	{
		//stopCamera();

		obj_Camera = isCameraAvailiable(camera_id);
		if (obj_Camera!=null) {

			showCamera = new ShowCamera(this, obj_Camera);
			return true;
		}
		else
			return false;
	}

	private void findIDfromXml() 
	{
		btn_clickSnp = (Button) findViewById(R.id.capture_img_gallery_btn_capture);
		frameLay_preview = (FrameLayout) findViewById(R.id.camera_preview);

	}

	public void btnClickViewOnPhoto(View view)
	{
		int view_id =view.getId();

		switch (view_id)
		{
		case R.id.capture_img_gallery_btn_gallary:

			if (obj_Camera!=null)
				obj_Camera.release();

			Intent intent = new Intent();

			intent.setType("image/*");

			intent.setAction(Intent.ACTION_GET_CONTENT);

			startActivityForResult(
					Intent.createChooser(intent, "Select Picture"),
					SELECT_PICTURE);



			break;

		case R.id.capture_img_gallery_btn_flashlight:


			if (isCameraLedflashAvalable()) {
				if (CHECk_FLASHLIGHT_ON_OFF) {

					CHECk_FLASHLIGHT_ON_OFF=false;
					cameraLedflashOff();

					if (frameLay_preview.getChildCount()>0) 
						frameLay_preview.removeViewAt(0);
					CHECk_FRAMEHOLDER_ADD=false;
					if(cameraInitialize(Camera_camId))
					{
						frameLay_preview.addView(showCamera);
						CHECk_FRAMEHOLDER_ADD=true;
					}
				}
				else
				{
					cameraLedflashOn();
					CHECk_FLASHLIGHT_ON_OFF=true;
				}

			}

			break;
			//		case R.id.capture_img_gallery_btn_save:
			//
			//			FLAG_CAPTURED_IMAGE=true;
			//			if (obj_Camera!=null)
			//				obj_Camera.release();
			//			if (bitmap_pictureCallback!=null)
			//			{
			//				Fragment_AddFeed.static_feedPhotoBitmap=bitmap_pictureCallback;
			//				Fragment_AddFeed.static_feedPhotoByte = Utills.bitmapTobyteArry(bitmap_pictureCallback);
			//			}
			//			Activity_Home.check_categorysubmit=true;
			//			finish();
			//
			//
			//
			//			break;

		case R.id.capture_img_gallery_btn_capture:

			if(obj_Camera!=null)
				obj_Camera.takePicture(null, null, pictureCallback);

			break;

		case R.id.capture_img_gallery_btn_cancel:
			Activity_Home.check_categorysubmit=true;
			//			if (Camera.getNumberOfCameras() > 1 && Camera_camId ==0) 
			//				Camera_camId=Camera.CameraInfo.CAMERA_FACING_FRONT;
			//			else 
			//				Camera_camId=Camera.CameraInfo.CAMERA_FACING_BACK;
			//
			//			if (frameLay_preview.getChildCount()>0) 
			//				frameLay_preview.removeViewAt(0);
			//			CHECk_FRAMEHOLDER_ADD=false;
			//			if(cameraInitialize(Camera_camId))
			//			{
			//				frameLay_preview.addView(showCamera);
			//				CHECk_FRAMEHOLDER_ADD=true;
			//			}

			FLAG_CAPTURED_IMAGE=false;
			if (obj_Camera!=null)
				obj_Camera.release();
			finish();

			break;

		default:
			break;
		}
	}
	private void performCrop(Uri picUri) {
		try {

			Intent cropIntent = new Intent("com.android.camera.action.CROP");
			cropIntent.setDataAndType(picUri, "image/*");
			cropIntent.putExtra("crop", "true");
			cropIntent.putExtra("aspectX", 1);
			cropIntent.putExtra("aspectY", 1);
			cropIntent.putExtra("outputX", 128);
			cropIntent.putExtra("outputY", 128);
			cropIntent.putExtra("return-data", true);
			startActivityForResult(cropIntent, PIC_CROP);
		}
		catch (ActivityNotFoundException anfe) {
			String errorMessage = "Whoops - your device doesn't support the crop action!";
			Toast toast = Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT);
			toast.show();
		}
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		if (frameLay_preview.getChildCount()>0) 
		{

			if (CHECk_FRAMEHOLDER_ADD)
			{
				frameLay_preview.removeViewAt(0);
				CHECk_FRAMEHOLDER_ADD=false;
			}
		}

		if (resultCode==RESULT_OK) {


			if (requestCode==SELECT_PICTURE)
			{
				if (data.getData()!=null) {
					performCrop(data.getData());
				}
				else
				{
					if(cameraInitialize(Camera_camId))
					{
						frameLay_preview.addView(showCamera);
						CHECk_FRAMEHOLDER_ADD=true;
					}
				}
			}
			else if (requestCode == PIC_CROP) 
			{
				if (data != null) {
					Bundle extras = data.getExtras();
					bitmap_pictureCallback = extras.getParcelable("data");
					setBitmapSolved();
				}
				else
				{
					if(cameraInitialize(Camera_camId))
					{
						CHECk_FRAMEHOLDER_ADD=true;
						frameLay_preview.addView(showCamera);
					}
				}
			}
			else
			{
				if(cameraInitialize(Camera_camId))
				{
					CHECk_FRAMEHOLDER_ADD=true;
					frameLay_preview.addView(showCamera);
				}
			}
		} 
		else
		{
			if(cameraInitialize(Camera_camId))
			{
				frameLay_preview.addView(showCamera);
				CHECk_FRAMEHOLDER_ADD=true;
			}
		}
	}

	private void setBitmapSolved()
	{
		try
		{

			if (obj_Camera!=null)
				obj_Camera.release();

			//			img_showImage.setImageBitmap(bitmap_pictureCallback);
			//			frameLay_preview.setVisibility(View.GONE);
			//			btn_clickSnp.setVisibility(View.GONE);
			//			btn_save.setVisibility(View.VISIBLE);

			FLAG_CAPTURED_IMAGE=true;
			if (obj_Camera!=null)
				obj_Camera.release();
			if (bitmap_pictureCallback!=null)
			{
				Fragment_AddFeed.static_feedPhotoBitmap=bitmap_pictureCallback;
				Fragment_AddFeed.static_feedPhotoByte = Utills.bitmapTobyteArry(bitmap_pictureCallback);
			}

			Activity_Home.check_categorysubmit=true;

			finish();

		} 

		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	void cameraLedflashOn() {
		if (obj_Camera!=null) {

			Parameters params = obj_Camera.getParameters();
			params.setFlashMode(Parameters.FLASH_MODE_TORCH);
			obj_Camera.setParameters(params);
			obj_Camera.startPreview();
		}
		//		obj_Camera.autoFocus(new AutoFocusCallback() {
		//			public void onAutoFocus(boolean success, Camera camera) {
		//
		//			}
		//		});
	}
	boolean isCameraLedflashAvalable() 
	{
		return this.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH);
	}
	void isCameraLedflashOnOff() 
	{
		//	return this.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH);
	}
	void cameraLedflashOff() 
	{
		if (obj_Camera!=null) {
			obj_Camera.stopPreview();
			obj_Camera.release();
		}
	}
}
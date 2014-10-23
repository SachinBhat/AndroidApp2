package com.android.ytoi;

import android.content.Context;
import android.hardware.Camera;
import android.util.Log;
import android.view.Display;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.WindowManager;
import android.widget.Toast;

import com.android.ytoi.utils.ConstantValue;

public class ShowCamera  extends SurfaceView implements SurfaceHolder.Callback {
	private SurfaceHolder holdMe;
	private Camera theCamera;
	private Context context;

	public ShowCamera(Context context,Camera camera) {
		super(context);
		this.context=context;
		theCamera = camera;
		holdMe = getHolder();
		holdMe.addCallback(this);
	}
	@Override
	public void surfaceChanged(SurfaceHolder arg0, int arg1, int width, int height) {
		stopPreview();
		initPreview(arg0);
		startPreview();
	}
	private void stopPreview() {
		if (theCamera != null) {
			theCamera.stopPreview();
		}
	}
	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		initPreview(holder);
		//			theCamera.setPreviewDisplay(holder);
		//			theCamera.startPreview(); 
	}
	private void startPreview() {
		if (theCamera != null) {
			theCamera.startPreview();
		}
	}
	@Override
	public void surfaceDestroyed(SurfaceHolder arg0) {
	}
	private void initPreview(SurfaceHolder holder) {
		if (theCamera != null && holder.getSurface() != null) {
			try {
				theCamera.setPreviewDisplay(holder);
			} catch (Throwable t) {
				Log.e("PreviewDemo-surfaceCallback",
						"Exception in setPreviewDisplay()", t);
				Toast.makeText(getContext(), t.getMessage(),
						Toast.LENGTH_LONG).show();
			}
			int width,  height;
			try {
				if (ConstantValue.DEVICE_WIDTH<500) {
					width=ConstantValue.DEVICE_WIDTH;
					height=ConstantValue.DEVICE_HEIGHT;
				}
				else if(ConstantValue.DEVICE_WIDTH<800)
				{
					width=ConstantValue.DEVICE_WIDTH;
					height=ConstantValue.DEVICE_HEIGHT;

				}
				else
				{
					width=ConstantValue.DEVICE_WIDTH;
					height=ConstantValue.DEVICE_HEIGHT;
				}

				if(theCamera != null){
					Camera.Parameters parameters=theCamera.getParameters();


					WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);

					Display display = windowManager.getDefaultDisplay();

					if(display.getRotation() == Surface.ROTATION_0)
					{
						parameters.setPreviewSize(height, width);                           
						theCamera.setDisplayOrientation(90);
					}

					if(display.getRotation() == Surface.ROTATION_90)
					{
						parameters.setPreviewSize(width, height);                           
					}

					if(display.getRotation() == Surface.ROTATION_180)
					{
						parameters.setPreviewSize(height, width);               
					}

					if(display.getRotation() == Surface.ROTATION_270)
					{
						parameters.setPreviewSize(width, height);
						theCamera.setDisplayOrientation(180);
					}

					
					theCamera.setParameters(parameters);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

	
}
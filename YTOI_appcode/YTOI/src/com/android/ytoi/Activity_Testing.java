package com.android.ytoi;

import java.io.ByteArrayOutputStream;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ImageFormat;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.YuvImage;
import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.hardware.Camera.PreviewCallback;
import android.hardware.Camera.Size;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.Toast;

public class Activity_Testing extends SurfaceView implements SurfaceHolder.Callback {

    private SurfaceHolder mHolder;
    private Camera mCamera;
    public Camera.Parameters mParameters;
    private byte[] mBuffer;
    private Activity mActivity;

    // this constructor used when requested as an XML resource
    public Activity_Testing(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public Activity_Testing(Context context) {
        super(context);
        init(); 
    }

    public Camera getCamera() {
        return this.mCamera;
    }

    @SuppressWarnings("deprecation")
	public void init() {
        // Install a SurfaceHolder.Callback so we get notified when the
        // underlying surface is created and destroyed.
        mHolder = getHolder();
        mHolder.addCallback(this);
        mHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
    }

    public Bitmap getPic(int x, int y, int width, int height) {
        System.gc();
        Bitmap b = null;
        Size s = mParameters.getPreviewSize();

        YuvImage yuvimage = new YuvImage(mBuffer, ImageFormat.NV21, s.width,
                s.height, null);

        ByteArrayOutputStream outStream = new ByteArrayOutputStream();

        yuvimage.compressToJpeg(new Rect(x, y, width, height), 100, outStream); // make
                                                                                // JPG

        b = BitmapFactory.decodeByteArray(outStream.toByteArray(), 0,
                outStream.size()); // decode JPG
        Bitmap mBitmap = null;
        if (b != null) {
            Matrix mtrxFreeze = new Matrix();
            mtrxFreeze.postRotate(90);
            mBitmap = Bitmap.createBitmap(b, 0, 0, b.getWidth(), b.getHeight(),
                    mtrxFreeze, true);
        } else {
        }
        yuvimage = null;
        outStream = null;
        System.gc();
        return b = mBitmap;
    }

    private void updateBufferSize() {
        mBuffer = null;
        System.gc();
        int h = mCamera.getParameters().getPreviewSize().height;
        int w = mCamera.getParameters().getPreviewSize().width;
        int bitsPerPixel = ImageFormat.getBitsPerPixel(mCamera.getParameters()
                .getPreviewFormat());
        mBuffer = new byte[w * h * bitsPerPixel / 8];
    }

    public void surfaceCreated(SurfaceHolder holder) {
        try {
            mCamera = Camera.open();
        } catch (RuntimeException exception) {
            Toast.makeText(getContext(), "Camera broken, quitting :(",
                    Toast.LENGTH_LONG).show();
            //Logger.writeLOG(EnforcementConstants.LOG, "" + exception.toString());
        }

        try {
            mCamera.setPreviewDisplay(holder);
            updateBufferSize();
            mCamera.addCallbackBuffer(mBuffer); // where we'll store the image
                                                // data
            mCamera.setPreviewCallbackWithBuffer(new PreviewCallback() {
                public synchronized void onPreviewFrame(byte[] data, Camera c) {
                    if (mCamera != null) {
                        // there was a race condition when
                        mCamera.addCallbackBuffer(mBuffer); // it was consumed
                    }
                }
            });
            mCamera.setDisplayOrientation(90);
        } catch (Exception exception) {
            //Logger.writeLOG(EnforcementConstants.LOG, "" + exception.toString());
            if (mCamera != null) {
                mCamera.release();
                mCamera = null;
            }
        }
    }

    public void surfaceDestroyed(SurfaceHolder holder) {
        // Surface will be destroyed when we return, so stop the preview.
        // Because the CameraDevice object is not a shared resource, it's very
        // important to release it when the activity is paused.
        // Log.i(TAG,"SurfaceDestroyed being called");
        mCamera.stopPreview();
        mCamera.release();
        mCamera = null;
    }

    // FYI: not called for each frame of the camera preview
    // gets called on my phone when keyboard is slid out
    // requesting landscape orientation prevents this from being called as
    // camera tilts

    public void surfaceChanged(SurfaceHolder holder, int format, int w, int h) {

        try {
            setParameters(mCamera);
        } catch (Exception e) {

            //Logger.writeLOG(EnforcementConstants.LOG, "" + e.toString());

        }
        updateBufferSize(); // then use them to calculate
        mCamera.startPreview();
    }

    /**
     * 
     */
    public void setParameters(Camera mCamera) {
        mParameters = mCamera.getParameters();
        mParameters.set("orientation", "portrait");

        mParameters.setPreviewSize(
                mCamera.getParameters().getPreviewSize().width, mCamera
                        .getParameters().getPreviewSize().height);
        List<?> focus = mParameters.getSupportedFocusModes();
        if (focus != null
                && focus.contains(android.hardware.Camera.Parameters.FOCUS_MODE_AUTO)) {
            mParameters.setFocusMode(Parameters.FOCUS_MODE_AUTO);
        }

        List<?> whiteMode = mParameters.getSupportedWhiteBalance();
        if (whiteMode != null
                && whiteMode
                        .contains(android.hardware.Camera.Parameters.WHITE_BALANCE_AUTO)) {
            mParameters.setWhiteBalance(Parameters.WHITE_BALANCE_AUTO);
        }

        List<String> flashModes = mParameters.getSupportedFlashModes();

        if (flashModes
                .contains(android.hardware.Camera.Parameters.FLASH_MODE_AUTO)) {
            mParameters.setFlashMode(Parameters.FLASH_MODE_AUTO);
        }

        mCamera.setParameters(mParameters); // apply the changes
    }

    public Parameters getCameraParameters() {
        return mCamera.getParameters();
    }

    public void setFlash(boolean flash) {
        if (flash) {
            mParameters.setFlashMode(Parameters.FLASH_MODE_TORCH);
            mCamera.setParameters(mParameters);
        } else {
            mParameters.setFlashMode(Parameters.FLASH_MODE_OFF);
            mCamera.setParameters(mParameters);
        }
    }

    /**
     * @return the mActivity
     */

    public Activity getActivity() {
        return mActivity;
    }

    /**
     * @param mActivity
     *            the mActivity to set
     */
    public void setActivity(Activity mActivity) {
        this.mActivity = mActivity;
    }
}

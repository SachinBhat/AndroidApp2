

package com.android.ytoi.rateark;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationSet;
import android.view.animation.Interpolator;
import android.view.animation.LayoutAnimationController;
import android.view.animation.LinearInterpolator;
import android.view.animation.OvershootInterpolator;
import android.view.animation.RotateAnimation;

import com.android.ytoi.R;
import com.android.ytoi.utils.ConstantValue;

/**
 * A Layout that arranges its children around its center. The arc can be set by
 * calling {@link #setArc(float, float) setArc()}. You can override the method
 * {@link #onMeasure(int, int) onMeasure()}, otherwise it is always
 * WRAP_CONTENT.
 * 
 * @author Capricorn
 * 
 */
public class ArcLayout extends ViewGroup {
	/**
	 * children will be set the same size.
	 */
	private int mChildSize;

	private int mChildPadding = 5;

	private int mLayoutPadding = 10;

	public static final float DEFAULT_FROM_DEGREES = 0.0f;

	public static final float DEFAULT_TO_DEGREES = 180.0f;

	private float mFromDegrees = DEFAULT_FROM_DEGREES;

	private float mToDegrees = DEFAULT_TO_DEGREES;



	/* the distance between the layout's center and any child's center */
	private int mRadius;

	private boolean mExpanded = false;

	public ArcLayout(Context context) {
		super(context);
	}

	public ArcLayout(Context context, AttributeSet attrs) {
		super(context, attrs);

		if (attrs != null) {
			TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.ArcLayout, 0, 0);
			mFromDegrees = a.getFloat(R.styleable.ArcLayout_fromDegrees, DEFAULT_FROM_DEGREES);
			mToDegrees = a.getFloat(R.styleable.ArcLayout_toDegrees, DEFAULT_TO_DEGREES);
			mChildSize = Math.max(a.getDimensionPixelSize(R.styleable.ArcLayout_childSize, 0), 0);

			a.recycle();
		}
	}

	private static int computeRadius(final float arcDegrees, final int childCount, final int childSize,
			final int childPadding, final int minRadius) 
	{
		if (childCount < 2) 
		{

			return minRadius;

		}

		final float perDegrees = arcDegrees / (childCount - 1);
		final float perHalfDegrees = perDegrees / 2;
		final int perSize = childSize + childPadding;

		final int radius = (int) ((perSize / 2) / Math.sin(Math.toRadians(perHalfDegrees)));

		return Math.max(radius, minRadius);
	}

	private static Rect computeChildFrame(final int centerX, final int centerY, final int radius, final float degrees,
			final int size) {

		final double childCenterX = centerX + radius * Math.cos(Math.toRadians(degrees));
		final double childCenterY = centerY + radius * Math.sin(Math.toRadians(degrees));

		return new Rect((int) (childCenterX - size / 2), (int) (childCenterY - size / 2),
				(int) (childCenterX + size / 2), (int) (childCenterY + size / 2));
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

		int min_radius = 100;

		if (ConstantValue.DEVICE_WIDTH<=320) {
			min_radius=115;	
		}
		else if (ConstantValue.DEVICE_WIDTH<=380) {
			min_radius=140;	
		}
		else if (ConstantValue.DEVICE_WIDTH<=440) {
			min_radius=165;	
		}
		else if (ConstantValue.DEVICE_WIDTH<=500) {
			min_radius=190;	
		}
		else if (ConstantValue.DEVICE_WIDTH<=560) {
			min_radius=215;	
		}
		else if (ConstantValue.DEVICE_WIDTH<=620) {
			min_radius=240;	
		}
		else if (ConstantValue.DEVICE_WIDTH<=680) {
			min_radius=265;	
		}
		else if (ConstantValue.DEVICE_WIDTH<=740) {
			min_radius=290;	
		}
		else if (ConstantValue.DEVICE_WIDTH<=800) {
			min_radius=315;	
		}
		else if (ConstantValue.DEVICE_WIDTH<=860) {
			min_radius=340;	
		}
		else if (ConstantValue.DEVICE_WIDTH<920) {
			min_radius=365;	
		}
		else if (ConstantValue.DEVICE_WIDTH<980) {
			min_radius=390;	
		}
		else if (ConstantValue.DEVICE_WIDTH<1040) {
			min_radius=415;	
		}
		else if (ConstantValue.DEVICE_WIDTH<1080) {
			min_radius=440;	
		}
		else if (ConstantValue.DEVICE_WIDTH<1140) {
			min_radius=465;	
		}
		else if (ConstantValue.DEVICE_WIDTH<1220) {
			min_radius=490;	
		}
		else if (ConstantValue.DEVICE_WIDTH<1280) {
			min_radius=515;	
		}
		else
		{
			min_radius=540;	
		}



		final int radius = mRadius = computeRadius(Math.abs(mToDegrees - mFromDegrees), getChildCount(), mChildSize,
				mChildPadding, min_radius);
		final int size = radius * 2 + mChildSize + mChildPadding + mLayoutPadding * 2;

		setMeasuredDimension(size, size);

		final int count = getChildCount();
		for (int i = 0; i < count; i++) {
			getChildAt(i).measure(MeasureSpec.makeMeasureSpec(mChildSize, MeasureSpec.EXACTLY),
					MeasureSpec.makeMeasureSpec(mChildSize, MeasureSpec.EXACTLY));
		}
	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		final int centerX = getWidth() / 2;
		final int centerY = getHeight() / 2;
		final int radius = mExpanded ? mRadius : 0;

		final int childCount = getChildCount();
		final float perDegrees = (mToDegrees - mFromDegrees) / (childCount - 1);

		float degrees = mFromDegrees;
		for (int i = 0; i < childCount; i++) {
			Rect frame = computeChildFrame(centerX, centerY, radius, degrees, mChildSize);
			degrees += perDegrees;
			getChildAt(i).layout(frame.left, frame.top, frame.right, frame.bottom);
		}
	}

	/**
	 * refers to {@link LayoutAnimationController#getDelayForView(View view)}
	 */
	private static long computeStartOffset(final int childCount, final boolean expanded, final int index,
			final float delayPercent, final long duration, Interpolator interpolator) {
		final float delay = delayPercent * duration;
		final long viewDelay = (long) (getTransformedIndex(expanded, childCount, index) * delay);
		final float totalDelay = delay * childCount;

		float normalizedDelay = viewDelay / totalDelay;
		normalizedDelay = interpolator.getInterpolation(normalizedDelay);

		return (long) (normalizedDelay * totalDelay);
	}

	private static int getTransformedIndex(final boolean expanded, final int count, final int index) {
		if (expanded) {
			return count - 1 - index;
		}

		return index;
	}

	private static Animation createExpandAnimation(float fromXDelta, float toXDelta, float fromYDelta, float toYDelta,
			long startOffset, long duration, Interpolator interpolator) {
		Animation animation = new RotateAndTranslateAnimation(0, toXDelta, 0, toYDelta, 0, 720);
		animation.setStartOffset(startOffset);
		animation.setDuration(duration);
		animation.setInterpolator(interpolator);
		animation.setFillAfter(true);

		return animation;
	}

	private static Animation createShrinkAnimation(float fromXDelta, float toXDelta, float fromYDelta, float toYDelta,
			long startOffset, long duration, Interpolator interpolator) {
		AnimationSet animationSet = new AnimationSet(false);
		animationSet.setFillAfter(true);

		final long preDuration = duration / 2;
		Animation rotateAnimation = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f,
				Animation.RELATIVE_TO_SELF, 0.5f);
		rotateAnimation.setStartOffset(startOffset);
		rotateAnimation.setDuration(preDuration);
		rotateAnimation.setInterpolator(new LinearInterpolator());
		rotateAnimation.setFillAfter(true);

		animationSet.addAnimation(rotateAnimation);

		Animation translateAnimation = new RotateAndTranslateAnimation(0, toXDelta, 0, toYDelta, 360, 720);
		translateAnimation.setStartOffset(startOffset + preDuration);
		translateAnimation.setDuration(duration - preDuration);
		translateAnimation.setInterpolator(interpolator);
		translateAnimation.setFillAfter(true);

		animationSet.addAnimation(translateAnimation);

		return animationSet;
	}

	private void bindChildAnimation(final View child, final int index, final long duration) {
		final boolean expanded = mExpanded;
		final int centerX = getWidth() / 2;
		final int centerY = getHeight() / 2;
		final int radius = expanded ? 0 : mRadius;

		final int childCount = getChildCount();
		final float perDegrees = (mToDegrees - mFromDegrees) / (childCount - 1);
		Rect frame = computeChildFrame(centerX, centerY, radius, mFromDegrees + index * perDegrees, mChildSize);

		final int toXDelta = frame.left - child.getLeft();
		final int toYDelta = frame.top - child.getTop();

		Interpolator interpolator = mExpanded ? new AccelerateInterpolator() : new OvershootInterpolator(1.5f);
		final long startOffset = computeStartOffset(childCount, mExpanded, index, 0.1f, duration, interpolator);

		Animation animation = mExpanded ? createShrinkAnimation(0, toXDelta, 0, toYDelta, startOffset, duration,
				interpolator) : createExpandAnimation(0, toXDelta, 0, toYDelta, startOffset, duration, interpolator);

		final boolean isLast = getTransformedIndex(expanded, childCount, index) == childCount - 1;
		animation.setAnimationListener(new AnimationListener() {

			@Override
			public void onAnimationStart(Animation animation) {

			}

			@Override
			public void onAnimationRepeat(Animation animation) {

			}

			@Override
			public void onAnimationEnd(Animation animation) {
				if (isLast) {
					postDelayed(new Runnable() {

						@Override
						public void run() {
							onAllAnimationsEnd();
						}
					}, 0);
				}
			}
		});

		child.setAnimation(animation);
	}

	public boolean isExpanded() {
		return mExpanded;
	}

	public void setArc(float fromDegrees, float toDegrees) {
		if (mFromDegrees == fromDegrees && mToDegrees == toDegrees) {
			return;
		}

		mFromDegrees = fromDegrees;
		mToDegrees = toDegrees;

		requestLayout();
	}

	public void setChildSize(int size) {
		if (mChildSize == size || size < 0) {
			return;
		}

		mChildSize = 24;

		if (ConstantValue.DEVICE_WIDTH<=320) {
			mChildSize = 30;
		}
		else if (ConstantValue.DEVICE_WIDTH<=380) {
			mChildSize = 36;
		}
		else if (ConstantValue.DEVICE_WIDTH<=440) {
			mChildSize = 42;	
		}
		else if (ConstantValue.DEVICE_WIDTH<=500) {
			mChildSize = 48;
		}
		else if (ConstantValue.DEVICE_WIDTH<=560) {
			mChildSize = 54;	
		}
		else if (ConstantValue.DEVICE_WIDTH<=620) {
			mChildSize = 60;	
		}
		else if (ConstantValue.DEVICE_WIDTH<=680) {
			mChildSize = 66;	
		}
		else if (ConstantValue.DEVICE_WIDTH<=740) {
			mChildSize = 72;
		}
		else if (ConstantValue.DEVICE_WIDTH<=800) {
			mChildSize = 78;	
		}
		else if (ConstantValue.DEVICE_WIDTH<=860) {
			mChildSize = 84;	
		}
		else if (ConstantValue.DEVICE_WIDTH<920) {
			mChildSize = 90;	
		}
		else if (ConstantValue.DEVICE_WIDTH<980) {
			mChildSize = 96;
		}
		else if (ConstantValue.DEVICE_WIDTH<1040) {
			mChildSize = 102;	
		}
		else if (ConstantValue.DEVICE_WIDTH<1080) {
			mChildSize = 108;
		}
		else if (ConstantValue.DEVICE_WIDTH<1140) {
			mChildSize = 114;
		}
		else if (ConstantValue.DEVICE_WIDTH<1220) {
			mChildSize = 120;
		}
		else if (ConstantValue.DEVICE_WIDTH<1280) {
			mChildSize = 126;
		}
		else
		{
			mChildSize = 132;	
		}
		requestLayout();
	}

	public int getChildSize() {

		if (ConstantValue.DEVICE_WIDTH<=320) 
		{
			return 43;
		}
		else if (ConstantValue.DEVICE_WIDTH<=380) {
			return 52;
		}
		else if (ConstantValue.DEVICE_WIDTH<=440) {
			return 61;	
		}
		else if (ConstantValue.DEVICE_WIDTH<=500) {
			return 70;
		}
		else if (ConstantValue.DEVICE_WIDTH<=560) {
			return 79;	
		}
		else if (ConstantValue.DEVICE_WIDTH<=620) {
			return 88;	
		}
		else if (ConstantValue.DEVICE_WIDTH<=680) {
			return 97;	
		}
		else if (ConstantValue.DEVICE_WIDTH<=740) {
			return 105;
		}
		else if (ConstantValue.DEVICE_WIDTH<=800) {
			return 114;	
		}
		else if (ConstantValue.DEVICE_WIDTH<=860) {
			return 123;	
		}
		else if (ConstantValue.DEVICE_WIDTH<920) {
			return 132;	
		}
		else if (ConstantValue.DEVICE_WIDTH<980) {
			return 141;
		}
		else if (ConstantValue.DEVICE_WIDTH<1040) {
			return 150;	
		}
		else if (ConstantValue.DEVICE_WIDTH<1080) {
			return 159;
		}
		else if (ConstantValue.DEVICE_WIDTH<1140) {
			return 168;
		}
		else if (ConstantValue.DEVICE_WIDTH<1220) {
			return 177;
		}
		else if (ConstantValue.DEVICE_WIDTH<1280) {
			return 186;
		}
		else
		{
			return 195;	
		}
	}

	/**
	 * switch between expansion and shrinkage
	 * 
	 * @param showAnimation
	 */
	public void switchState(final boolean showAnimation) {
		if (showAnimation) {
			final int childCount = getChildCount();
			for (int i = 0; i < childCount; i++) {
				bindChildAnimation(getChildAt(i), i, 300);
			}
		}

		mExpanded = !mExpanded;

		if (!showAnimation) {
			requestLayout();
		}

		invalidate();
	}

	private void onAllAnimationsEnd() {
		final int childCount = getChildCount();
		for (int i = 0; i < childCount; i++) {
			getChildAt(i).clearAnimation();
		}

		requestLayout();
	}

}

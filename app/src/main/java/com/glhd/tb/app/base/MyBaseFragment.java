package com.glhd.tb.app.base;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public abstract class MyBaseFragment  extends Fragment {
	public String TAG;
	public int width, height;
	/**
	 * 布局文件对应的id
	 */
	public abstract int initViewId();

	public abstract void initView();


	private int mLayoutId;
	public View rootView;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		width = getActivity().getWindowManager().getDefaultDisplay().getWidth();
		height =  getActivity().getWindowManager().getDefaultDisplay().getHeight();
		TAG = this.getClass().getSimpleName();
		EventBus.getDefault().register(this);
	}

	@Override
	public void onStart() {
		super.onStart();

	}

	@Subscribe(threadMode = ThreadMode.MAIN)
	public void setEvent(Activity event) {


	}
	public void setContentView(int id) {
		this.mLayoutId = id;
	}

 

	@Override
	public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		initView();
	}
	public void startActivity(Class<?> cls) {
		if (cls == null)
			return;
		Intent intent = new Intent(getContext(), cls);
		super.startActivity(intent);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		int id = initViewId();
		rootView = inflater.inflate(id, container, false);
		return rootView;
	}


	public void log(String log) {
		Log.i(TAG, log);
	}
	public View findViewById(int id){
		return  rootView.findViewById(id);
	}


	private Fragment currentFragment;//当前显示的fragment

	public void replace(int id, Fragment f) {
		if (f == currentFragment)
			return;
		FragmentTransaction mft = ((BaseActivity)getContext()).getSupportFragmentManager().beginTransaction();
		if (currentFragment != null)
			mft.hide(currentFragment);
		if (f.isAdded()) {
			mft.show(f);
		} else {
			mft.add(id, f);
		}
		mft.commitAllowingStateLoss();
		currentFragment = f;
	}
	public int myY;


	public void setMyY(int myY) {
		this.myY = myY;
	}

	public int getMyY() {
		return myY;
	}

	public void refresh(final View view) {
		myY=0;
		MotionEvent e = MotionEvent.obtain(SystemClock.uptimeMillis(), SystemClock.uptimeMillis(), MotionEvent.ACTION_DOWN, width / 2, 0, 0);
		view.dispatchTouchEvent(e);

		ObjectAnimator animator = ObjectAnimator.ofInt(this, "myY", 0, 800);
		animator.setDuration(500);
		animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
			@Override
			public void onAnimationUpdate(ValueAnimator animation) {
				MotionEvent e = MotionEvent.obtain(SystemClock.uptimeMillis(),
						SystemClock.uptimeMillis(), MotionEvent.ACTION_MOVE,
						width / 2, myY, 0);
				view.dispatchTouchEvent(e);
			}
		});
		animator.addListener(new Animator.AnimatorListener() {
			@Override
			public void onAnimationStart(Animator animation) {

			}

			@Override
			public void onAnimationEnd(Animator animation) {
				MotionEvent e = MotionEvent.obtain(SystemClock.uptimeMillis(),SystemClock.uptimeMillis(), MotionEvent.ACTION_UP, width / 2, 800, 0);
				view.dispatchTouchEvent(e);
			}

			@Override
			public void onAnimationCancel(Animator animation) {

			}

			@Override
			public void onAnimationRepeat(Animator animation) {

			}
		});
		animator.start();


	}
}

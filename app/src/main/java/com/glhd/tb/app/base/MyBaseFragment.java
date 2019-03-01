package com.glhd.tb.app.base;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public abstract class MyBaseFragment  extends Fragment {
	public String TAG;

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
	    
}

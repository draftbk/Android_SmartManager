package com.example.apple1.smartmanager2.fragment;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.apple1.smartmanager2.R;


public class MyRepairListFragment extends Fragment {
	public Context context;
	public Activity activity;
	private View view;
	private View mMainView;
	@Override
	/**
	 * 初始化操作
	 */

	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		context = getActivity();
		activity = getActivity();

	}

	/**
	 * 界面初始化
	 */
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		view = inflater.inflate(R.layout.fragment_my_repair, container,
				false);

		return view;
	}
}

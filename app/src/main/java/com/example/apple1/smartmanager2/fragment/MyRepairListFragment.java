package com.example.apple1.smartmanager2.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.apple1.smartmanager2.R;


public class MyRepairListFragment extends Fragment {
	private View mMainView;
	private TextView tv;
	private Button btn;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		LayoutInflater inflater = getActivity().getLayoutInflater();
		mMainView = inflater.inflate(R.layout.fragment_my_repair, (ViewGroup)getActivity().findViewById(R.id.viewpager), false);
		
		tv = (TextView)mMainView.findViewById(R.id.tv1);
		
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		ViewGroup p = (ViewGroup) mMainView.getParent(); 
        if (p != null) { 
            p.removeAllViewsInLayout();
        } 
		
		return mMainView;
	}


}

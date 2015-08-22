package com.example.apple1.smartmanager2.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.apple1.smartmanager2.R;


public class AllRepairListFragment extends Fragment {
	private View mMainView;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		
		LayoutInflater inflater = getActivity().getLayoutInflater();
		mMainView = inflater.inflate(R.layout.fragment_all_repair, (ViewGroup)getActivity().findViewById(R.id.viewpager), false);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub

		
		ViewGroup p = (ViewGroup) mMainView.getParent(); 
        if (p != null) { 
            p.removeAllViewsInLayout(); 

        } 
		
		return mMainView;
	}
	

}

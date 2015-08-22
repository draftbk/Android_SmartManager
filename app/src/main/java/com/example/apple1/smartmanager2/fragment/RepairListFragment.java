package com.example.apple1.smartmanager2.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.example.apple1.smartmanager2.R;

import java.util.ArrayList;

/**
 * Created by draft on 2015/7/21.
 */
public class RepairListFragment extends Fragment {
    /**
     * 上下文对象
     */
    public Context context;
    public Activity activity;
    private ViewPager m_vp;
    private MyRepairListFragment myRepair;
    private AllRepairListFragment allRepair;
    //页面列表
    private ArrayList<Fragment> fragmentList;

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
        View view = inflater.inflate(R.layout.fragment_repair_list, container,
                false);
        m_vp = (ViewPager)view.findViewById(R.id.viewpager);

        myRepair = new MyRepairListFragment();
        allRepair = new AllRepairListFragment();

        fragmentList = new ArrayList<Fragment>();
        fragmentList.add(myRepair);
        fragmentList.add(allRepair);

        m_vp.setAdapter(new MyViewPagerAdapter(getFragmentManager()));

        return view;
    }

    //ViewPager的Adapter

    public class MyViewPagerAdapter extends FragmentPagerAdapter {
        public MyViewPagerAdapter(FragmentManager fm) {
            super(fm);
            // TODO Auto-generated constructor stub
        }

        @Override
        public Fragment getItem(int arg0) {
            return fragmentList.get(arg0);
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }



    }
}
package com.example.apple1.smartmanager2.fragment;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Switch;


import com.example.apple1.smartmanager2.Application.ManagerData;
import com.example.apple1.smartmanager2.R;
import com.example.apple1.smartmanager2.tools.SlidingMenu;

import java.util.ArrayList;

/**
 * Created by draft on 2015/7/21.
 */
public class RepairListFragment extends Fragment implements View.OnClickListener {
    /**
     * 上下文对象
     */
    public Context context;
    public Activity activity;
    private Button myButton,allButton;
    private ImageButton menuButton;
    private SlidingMenu mLeftMenu;

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

        //设置初始界面
        setface();
        //初始化控件
        myButton=(Button)view.findViewById(R.id.btn_my);
        allButton=(Button)view.findViewById(R.id.btn_all);
        menuButton=(ImageButton)view.findViewById(R.id.button_menu);
        mLeftMenu = (SlidingMenu) activity.findViewById(R.id.menu);
        //设置监听
        setOnClick();



        return view;
    }

    private void setOnClick() {
        myButton.setOnClickListener(this);
        allButton.setOnClickListener(this);
        menuButton.setOnClickListener(this);
    }



    private void setface() {

        Fragment fragment;
        FragmentManager fragmentManager = getChildFragmentManager();
        fragment = new MyRepairListFragment();
        fragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, fragment)
                .commit();
    }

    @Override
    public void onClick(View v) {

        Fragment fragment;
        FragmentManager fragmentManager = getChildFragmentManager();
        switch (v.getId()){

            case R.id.btn_my:
                myButton.setBackgroundResource(R.drawable.my_chosen);
                myButton.setTextColor(0xffffffff);
                allButton.setTextColor(0xff00b7ee);
                allButton.setBackgroundResource(R.drawable.all_unchosen);
                fragment = new MyRepairListFragment();
                fragmentManager.beginTransaction()
                        .replace(R.id.fragmentContainer, fragment)
                        .commit();
                break;

            case R.id.btn_all:
                myButton.setBackgroundResource(R.drawable.my_unchosen);
                myButton.setTextColor(0xff00b7ee);
                allButton.setTextColor(0xffffffff);
                allButton.setBackgroundResource(R.drawable.all_chosen);
                fragment = new AllRepairListFragment();
                fragmentManager.beginTransaction()
                        .replace(R.id.fragmentContainer, fragment)
                        .commit();
                break;

            case R.id.button_menu:
                mLeftMenu.toggle();
                break;


        }

    }
}
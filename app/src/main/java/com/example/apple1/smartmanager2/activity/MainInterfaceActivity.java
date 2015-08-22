package com.example.apple1.smartmanager2.activity;

import android.content.Intent;
import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.TextureView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import com.example.apple1.smartmanager2.Application.ManagerData;
import com.example.apple1.smartmanager2.R;
import com.example.apple1.smartmanager2.fragment.RepairListFragment;
import com.example.apple1.smartmanager2.fragment.RepairRecordFragment;
import com.example.apple1.smartmanager2.fragment.SettingFragment;
import com.example.apple1.smartmanager2.tools.SlidingMenu;

import org.w3c.dom.Text;

public class MainInterfaceActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView itemText1;
    private Button  itemButton2, itemButton3, itemButton4, itemButton5;
    private SlidingMenu mLeftMenu;
    private ManagerData managerData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_interface);
        //获取Application
        managerData= (ManagerData) getApplication();
        //设置初始界面
        setface();
        //初始化控件
        init();
        //设置监听
        setOnClick();
        //测试Application
        Toast.makeText(MainInterfaceActivity.this, managerData.getManagerId(),
                Toast.LENGTH_SHORT).show();

    }

    private void setface() {
        Fragment fragment;
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragment = new RepairListFragment();
        fragmentManager.beginTransaction()
                .replace(R.id.container, fragment)
                .commit();
    }


    private void init() {
        itemText1 = (TextView) findViewById(R.id.item_text_1);
        itemButton2 = (Button) findViewById(R.id.item_button_2);
        itemButton3 = (Button) findViewById(R.id.item_button_3);
        itemButton4 = (Button) findViewById(R.id.item_button_4);
        itemButton5 = (Button) findViewById(R.id.item_button_5);
        mLeftMenu = (SlidingMenu) findViewById(R.id.menu);
    }

    private void setOnClick() {
        itemButton2.setOnClickListener(this);
        itemButton3.setOnClickListener(this);
        itemButton4.setOnClickListener(this);
        itemButton5.setOnClickListener(this);
    }

    public void toggle() {
        mLeftMenu.toggle();
    }

    @Override
    public void onClick(View v) {
        Fragment fragment;
        FragmentManager fragmentManager = getSupportFragmentManager();
        switch (v.getId()) {
            case R.id.item_button_2:
                Toast.makeText(MainInterfaceActivity.this, itemButton2.getText(),
                        Toast.LENGTH_SHORT).show();
                fragment = new RepairListFragment();
                fragmentManager.beginTransaction()
                        .replace(R.id.container, fragment)
                        .commit();
                toggle();
                break;
            case R.id.item_button_3:
                Toast.makeText(MainInterfaceActivity.this, itemButton3.getText(),
                        Toast.LENGTH_SHORT).show();
                fragment = new RepairRecordFragment();
                fragmentManager.beginTransaction()
                        .replace(R.id.container, fragment)
                        .commit();
                toggle();
                break;
            case R.id.item_button_4:
                Toast.makeText(MainInterfaceActivity.this, itemButton4.getText(),
                        Toast.LENGTH_SHORT).show();
                fragment = new SettingFragment();
                fragmentManager.beginTransaction()
                        .replace(R.id.container, fragment)
                        .commit();
                toggle();
                break;
            case R.id.item_button_5:
                Intent intent =new Intent(MainInterfaceActivity.this,LoginActivity.class);
                startActivity(intent);
                finish();
                break;

        }



    }

}
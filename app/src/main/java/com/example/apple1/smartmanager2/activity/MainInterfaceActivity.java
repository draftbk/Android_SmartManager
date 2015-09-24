package com.example.apple1.smartmanager2.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.apple1.smartmanager2.Application.Application;
import com.example.apple1.smartmanager2.Application.ManagerData;
import com.example.apple1.smartmanager2.R;
import com.example.apple1.smartmanager2.fragment.RepairListFragment;
import com.example.apple1.smartmanager2.fragment.RepairRecordFragment;
import com.example.apple1.smartmanager2.fragment.SettingFragment;
import com.example.apple1.smartmanager2.net.GetCount;
import com.example.apple1.smartmanager2.net.GetPicture;
import com.example.apple1.smartmanager2.tools.SlidingMenu;
import com.example.apple1.smartmanager2.tools.pictureChangeToRound;

import org.json.JSONException;

public class MainInterfaceActivity extends FragmentActivity implements View.OnClickListener {
    private TextView itemText1;
    private ImageView headImage;
    private Button itemButton2, itemButton3, itemButton4, itemButton5;
    private SlidingMenu mLeftMenu;
    private ManagerData managerData;
    private Handler hanGetImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_interface);
        new GetCount(new GetCount.SuccessCallBack() {
            @Override
            public void onSuccess(String result) throws JSONException {
                Application.INT_LAST = Integer.parseInt(result);

            }
        });
        //获取Application
        managerData = (ManagerData) getApplication();
        //设置初始界面
        setface();
        //初始化控件
        init();
        //设置头像
        GetPicture getPicture = new GetPicture(hanGetImage, managerData.getImagePath());
        getPicture.start();
        //设置监听
        setOnClick();

        GetNotification();

    }

    private void setface() {
        Fragment fragment;
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragment = new RepairListFragment();
        fragmentManager.beginTransaction().replace(R.id.container, fragment).commit();
    }


    private void init() {
        itemText1 = (TextView) findViewById(R.id.item_text_1);
        headImage = (ImageView) findViewById(R.id.img1);
        itemButton2 = (Button) findViewById(R.id.item_button_2);
        itemButton3 = (Button) findViewById(R.id.item_button_3);
        itemButton4 = (Button) findViewById(R.id.item_button_4);
        itemButton5 = (Button) findViewById(R.id.item_button_5);
        mLeftMenu = (SlidingMenu) findViewById(R.id.menu);
        itemText1.setText(managerData.getNickName());
        hanGetImage = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                // TODO Auto-generated method stub
                Bitmap bitmap = (Bitmap) msg.obj;

                //把图片变圆
                pictureChangeToRound pictureChangeToRound = new pictureChangeToRound();
                bitmap = pictureChangeToRound.toRoundBitmap(bitmap);
                super.handleMessage(msg);
                headImage.setImageBitmap(bitmap);

            }
        };

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
                fragment = new RepairListFragment();
                fragmentManager.beginTransaction().replace(R.id.container, fragment).commit();
                toggle();
                break;
            case R.id.item_button_3:

                fragment = new RepairRecordFragment();
                fragmentManager.beginTransaction().replace(R.id.container, fragment).commit();
                toggle();
                break;
            case R.id.item_button_4:

                fragment = new SettingFragment();
                fragmentManager.beginTransaction().replace(R.id.container, fragment).commit();
                toggle();
                break;
            case R.id.item_button_5:
                //取消自动登陆
                SharedPreferences.Editor editor = getSharedPreferences("LandedJuage", MODE_PRIVATE).edit();
                editor.putBoolean("AutoLanded", false);
                editor.commit();
                //跳转
                Intent intent = new Intent(MainInterfaceActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
                break;

        }


    }

    /**
     * 后台开启服务
     */
    void GetNotification() {
        /*Intent i = new Intent();
        i.setAction("com.example.service");
        startService(i);*/
        Intent mIntent = new Intent();
        mIntent.setAction("com.example.service");//你定义的service的action
        mIntent.setPackage(getPackageName());//这里你需要设置你应用的包名
        startService(mIntent);
    }
}
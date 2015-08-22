package com.example.apple1.smartmanager2.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.example.apple1.smartmanager2.R;


/**
 * Created by draft on 2015/7/16.
 */
public class SettingActivity extends Activity {
    ImageButton buttonBack;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        //初始化
        init();
       // 添加监听
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //跳转到主界面
                Intent intent=new Intent(SettingActivity.this,MainInterfaceActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }

    //初始化的方法
    private void init() {
        buttonBack=(ImageButton)findViewById(R.id.button_back);
    }
}
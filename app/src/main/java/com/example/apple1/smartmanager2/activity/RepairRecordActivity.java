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
public class RepairRecordActivity extends Activity{
    ImageButton buttonBack;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repair_record);
        //初始化返回按钮
        buttonBack=(ImageButton)findViewById(R.id.button_back);
        //设置监听
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //返回到RepairListActivity
                Intent intent = new Intent(RepairRecordActivity.this, MainInterfaceActivity.class);
                startActivity(intent);
                finish();
            }
        });

        

    }
}

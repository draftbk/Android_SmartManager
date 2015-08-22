package com.example.apple1.smartmanager2.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.os.Handler;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.apple1.smartmanager2.R;
import com.example.apple1.smartmanager2.net.GetPicture;
import com.example.apple1.smartmanager2.net.NetThread;
import com.example.apple1.smartmanager2.tools.AutoString;

import org.json.JSONException;
import org.json.JSONObject;


/**
 * Created by draft on 2015/7/20.
 */
public class EvaluationActivity extends Activity implements View.OnClickListener {
    private TextView textGrade=null,textSuggestion=null;
    private ImageButton btnBack;
    private String id;
    private String url="http://1.smartprotecter.sinaapp.com/sm/service_id03.php";
    private Handler han;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evaluation);
        Intent intent=getIntent();
        id=intent.getStringExtra("s_id");
        init();
        getMessage();
        btnBack.setOnClickListener(this);
    }

    private void getMessage() {
        AutoString autoString=new AutoString("s_id",id);
        String params=autoString.getResult();
        Log.d("test","params"+"............."+params);
        NetThread nt=new NetThread(han,url,params);
        nt.start();
    }



    private void init() {
        textGrade=(TextView)findViewById(R.id.text_grade);
        textSuggestion=(TextView)findViewById(R.id.text_suggestion);
        btnBack=(ImageButton)findViewById(R.id.button_back);
        han = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                String Jsmess =(String) msg.obj;
                Log.d("test","Jsmess"+Jsmess);
                super.handleMessage(msg);
                if (Jsmess.equals("1")){
                    Toast.makeText(EvaluationActivity.this, "连接失败", Toast.LENGTH_SHORT).show();
                }else {
                    try {

                        JSONObject temp = new JSONObject(Jsmess);
                        String score = temp.getString("score");
                        textGrade.setText(score);
                        String advice =temp.getString("advice");
                        textSuggestion.setText(advice);


                    } catch (JSONException e) {
                        // TODO Auto-generated catch block
                        Log.d("test","出错了");
                        e.printStackTrace();
                    }
                }
            }



        };
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button_back:
                finish();
                break;
            default:
                break;
        }
    }
}

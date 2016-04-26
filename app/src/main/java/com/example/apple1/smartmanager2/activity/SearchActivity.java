package com.example.apple1.smartmanager2.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import android.speech.SpeechRecognizer;
import android.util.Log;
import android.view.View;
import android.widget.Button;


import com.example.apple1.smartmanager2.R;
import com.iflytek.cloud.RecognizerListener;
import com.iflytek.cloud.RecognizerResult;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechUtility;
import com.iflytek.cloud.ui.RecognizerDialog;
import com.iflytek.cloud.ui.RecognizerDialogListener;


/**
 * Created by slf on 16/3/7.
 */
public class SearchActivity extends Activity implements View.OnClickListener {

    private Button btn;
    private RecognizerDialog iatDialog;
    Context context=SearchActivity.this;
    RecognizerDialogListener mRecoListener;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        SpeechUtility.createUtility(context, SpeechConstant.APPID + "=56e007dd");
        //1.创建SpeechRecognizer对象，第二个参数：本地听写时传InitListener
        init();
        iatDialog = new RecognizerDialog(this,null); //2.设置听写参数,同上节
        //3.设置回调接口
        iatDialog.setListener(mRecoListener);
    }

    private void init() {
        btn= (Button) findViewById(R.id.btn_test);
        btn.setOnClickListener(this);
     //听写监听器
        mRecoListener = new RecognizerDialogListener(){
            //听写结果回调接口(返回Json格式结果，用户可参见附录12.1)；
            //一般情况下会通过onResults接口多次返回结果，完整的识别内容是多次结果的累加；
            //关于解析Json的代码可参见MscDemo中JsonParser类；
           //isLast等于true时会话结束。
            public void onResult(RecognizerResult results, boolean isLast) {
                Log.d("Result:", results.getResultString());

            }

            //会话发生错误回调接口
            public void onError(SpeechError error) {
                error.getPlainDescription(true); //获取错误码描述
            }

        };

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_test:
                iatDialog.show();
                break;
        }
    }
}

package com.example.apple1.smartmanager2.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;


import com.example.apple1.smartmanager2.Application.ManagerData;
import com.example.apple1.smartmanager2.R;
import com.example.apple1.smartmanager2.net.GetPicture;
import com.example.apple1.smartmanager2.net.NetThread;
import com.example.apple1.smartmanager2.tools.AutoString;
import com.example.apple1.smartmanager2.tools.DoubleClickJuage;
import com.example.apple1.smartmanager2.tools.EncryptMD5;
import com.example.apple1.smartmanager2.tools.PictureChangeToRound;

import org.json.JSONException;
import org.json.JSONObject;


/**
 * Created by draft on 2015/7/12.
 */
public class LoginActivity extends Activity {
    private ManagerData managerData;
    private  Button buttonLogin;
    private ImageView imageViewId;
    private ImageView imageViewHead;
    private EditText editId,editPassword;
    private String url="http://1.smartprotecter.sinaapp.com/sm/login_m.php";
    private String urlImagePath="http://1.smartprotecter.sinaapp.com/sm/icon_m.php";
    private String id,password;
    private Handler han;
    private String managerId,managerPhone,imagePath,nickName;
    private Handler hanGetImage;
    private Handler hanGetUrl;
//    private List<NameValuePair> params;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //初始化
        init();

        editId.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence text, int start, int before, int count) {
                //text  输入框中改变后的字符串信息
                //start 输入框中改变后的字符串的起始位置
                //before 输入框中改变前的字符串的位置 默认为0
                //count 输入框中改变后的一共输入字符串的数量
                Log.d("test1","1");
            }

            @Override
            public void beforeTextChanged(CharSequence text, int start, int count, int after) {
                //text  输入框中改变前的字符串信息
                //start 输入框中改变前的字符串的起始位置
                //count 输入框中改变前后的字符串改变数量一般为0
                //after 输入框中改变后的字符串与起始位置的偏移量
                Log.d("test1","2");
            }

            @Override
            public void afterTextChanged(Editable edit) {
                //edit  输入结束呈现在输入框中的信息
                  String temporaryId=editId.getText().toString();
                AutoString autoString=new AutoString("account",temporaryId);
                String params=autoString.getResult();
                NetThread nt=new NetThread(hanGetUrl,urlImagePath,params);
                nt.start();

            }
        });

        //检测自动登陆
        SharedPreferences pref=getSharedPreferences("LandedJuage", MODE_PRIVATE);
        if (pref.getBoolean("AutoLanded",false)){
            String passwordrem=pref.getString("password","");
            String idrem=pref.getString("id","");
            editId.setText(idrem);
            editPassword.setText(passwordrem);
            Log.d("test1","passwordrem"+"     "+"idrem"+"   "+passwordrem+"   "+idrem);
            //MD5加密
            EncryptMD5 encryptMD5=new EncryptMD5();
            passwordrem= encryptMD5.GetMD5(passwordrem);
            //添加要传入的参数，AutoString是自己写的方法
            AutoString autoString=new AutoString("account",idrem);
            autoString.addToResult("password",passwordrem);
            String params=autoString.getResult();
            Log.d("test1","params"+params);
            //网络连接接口
            NetThread nt=new NetThread(han,url,params);
            nt.start();
        }

        //给按钮设置监听
        buttonLogin.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {


                if (DoubleClickJuage.isFastDoubleClick()) {
                    return;
                }

                //获取两个所填Id和Password
                id=editId.getText().toString();
                password=editPassword.getText().toString();
                if(id.equals("")){
                    Toast.makeText(LoginActivity.this, "请输入你的用户名", Toast.LENGTH_SHORT).show();
                }else if(password.equals("")){
                    Toast.makeText(LoginActivity.this, "请输入你的密码", Toast.LENGTH_SHORT).show();
                }else{
                    //MD5加密
                    EncryptMD5 encryptMD5=new EncryptMD5();
                    password= encryptMD5.GetMD5(password);
                    Log.d("test","password"+".............."+password);
                    //添加要传入的参数，AutoString是自己写的方法
                    AutoString autoString=new AutoString("account",id);
                    autoString.addToResult("password",password);
                    String params=autoString.getResult();
                    Log.d("test1","params"+params);
                    //网络连接接口
                    NetThread nt=new NetThread(han,url,params);
                    nt.start();
                }

            }
        });

    }

    private void init() {
        imageViewHead=(ImageView)findViewById(R.id.image_head);
        imageViewId=(ImageView)findViewById(R.id.image_id);
        imageViewId.setAlpha(210);
        buttonLogin=(Button)findViewById(R.id.button_login);
        editId=(EditText)findViewById(R.id.edit_id);
        editPassword=(EditText)findViewById(R.id.edit_password);
//        params = new ArrayList<NameValuePair>();

        hanGetImage = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                // TODO Auto-generated method stub
                Bitmap bitmap = (Bitmap) msg.obj;
                Log.d("test1", "bitmap" + "           " + bitmap);
                //把图片变圆
//                PictureChangeToRound pictureChangeToRound=new PictureChangeToRound();
//                bitmap= pictureChangeToRound.toRoundBitmap(bitmap);
                super.handleMessage(msg);
                imageViewHead.setImageBitmap(bitmap);

            }
        };

        hanGetUrl = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                // TODO Auto-generated method stub
               String Jsmess = (String) msg.obj;

                if(Jsmess.equals("1")){
                    imageViewHead.setImageResource(R.drawable.login_head);
                    Log.d("test1","应该是这里啊！");
                }else {
                    //获取JSON对象
                    JSONObject temp = null;
                    try {
                        temp = new JSONObject(Jsmess);
                        //得到数据
                        String urlImage = temp.getString("image_path");
                        GetPicture getPicture=new GetPicture(hanGetImage,urlImage);
                        getPicture.start();
                        Log.d("test1","出现了");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                }

            }
        };

        han = new Handler(){

            @Override
            public void handleMessage(Message msg) {
                // TODO Auto-generated method stub
                String Jsmess = msg.obj.toString();
                super.handleMessage(msg);
                if(Jsmess.equals("1")){
                    Toast.makeText(LoginActivity.this, "用户名或密码错误，请重新确认", Toast.LENGTH_SHORT).show();
                } else{
                    //传数据到Application
                    getMessage(Jsmess);
                    putMessageToApplication();
                    //储存账号密码
                    SharedPreferences.Editor editor=getSharedPreferences("LandedJuage",MODE_PRIVATE).edit();
                    editor.putString("id", editId.getText().toString());
                    editor.putString("password",editPassword.getText().toString());
                    editor.commit();
                    //登陆
                    Intent intent =new Intent(LoginActivity.this,MainInterfaceActivity.class);
                    startActivity(intent);
                    finish();
                }
            }

            private void getMessage(String Jsmess) {

                try {
                    //获取JSON对象
                    JSONObject temp = new JSONObject(Jsmess);
                    //得到数据
                    managerId = temp.getString("m_id");
                    Log.d("test","managerId"+"        "+managerId);
                    managerPhone = temp.getString("phonenumber_m");
                    imagePath=temp.getString("image_path");
                    nickName=temp.getString("nickname");


                }  catch (JSONException e) {
                    e.printStackTrace();
                    Log.d("test", "哈哈哈果然是这里！！！！！");
                }
            }

            private void putMessageToApplication() {
                //获取应用程序
                managerData=(ManagerData)getApplication();
                //设置变量
                Log.d("test","第一个ID"+managerData.getManagerId());
                managerData.setManagerId(managerId);
                Log.d("test", "第二个ID" + managerData.getManagerId());
                managerData.setManagerPhone(managerPhone);
                managerData.setImagePath(imagePath);
                managerData.setNickName(nickName);

            }
        };

    }


}

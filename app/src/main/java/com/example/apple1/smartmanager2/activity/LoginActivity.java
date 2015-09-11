package com.example.apple1.smartmanager2.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;


import com.example.apple1.smartmanager2.Application.ManagerData;
import com.example.apple1.smartmanager2.R;
import com.example.apple1.smartmanager2.net.NetThread;
import com.example.apple1.smartmanager2.tools.AutoString;
import com.example.apple1.smartmanager2.tools.DoubleClickJuage;
import com.example.apple1.smartmanager2.tools.EncryptMD5;

import org.json.JSONException;
import org.json.JSONObject;


/**
 * Created by draft on 2015/7/12.
 */
public class LoginActivity extends Activity {
    private ManagerData managerData;
    private  Button buttonLogin;
    private ImageView imageViewId;
    private EditText editId,editPassword;
    private String url="http://1.smartprotecter.sinaapp.com/sm/login_m.php";
    private String id,password;
    private Handler han;
    private String managerId,managerPhone,imagePath,nickName;
//    private List<NameValuePair> params;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //初始化
        init();
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
        imageViewId=(ImageView)findViewById(R.id.image_id);
        imageViewId.setAlpha(210);
        buttonLogin=(Button)findViewById(R.id.button_login);
        editId=(EditText)findViewById(R.id.edit_id);
        editPassword=(EditText)findViewById(R.id.edit_password);
//        params = new ArrayList<NameValuePair>();
        han = new Handler(){

            @Override
            public void handleMessage(Message msg) {
                // TODO Auto-generated method stub
                String Jsmess = msg.obj.toString();
                super.handleMessage(msg);
                if(Jsmess.equals("1")){
                    Toast.makeText(LoginActivity.this, "用户名或密码错误，请重新确认", Toast.LENGTH_SHORT).show();
                } else{
                    //登入
                    getMessage(Jsmess);
                    putMessageToApplication();
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

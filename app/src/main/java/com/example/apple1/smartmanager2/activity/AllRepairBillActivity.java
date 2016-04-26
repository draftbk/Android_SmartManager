package com.example.apple1.smartmanager2.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.apple1.smartmanager2.Application.ManagerData;
import com.example.apple1.smartmanager2.R;

import com.example.apple1.smartmanager2.entity.RepairDetail;

import com.example.apple1.smartmanager2.net.GetPicture;
import com.example.apple1.smartmanager2.net.NetThread;
import com.example.apple1.smartmanager2.net.Sql_URL;
import com.example.apple1.smartmanager2.tools.AutoString;


import org.json.JSONException;
import org.json.JSONObject;


/**
 * Created by draft on 2015/7/20.
 */
public class AllRepairBillActivity extends Activity implements View.OnClickListener {
    //申明对象
    private ImageView image=null;
    private TextView textLocation=null,textPhoneNumber=null,textRepairType=null,
            textSource=null,textRepresentation=null,textRemark=null;
    private ImageButton btnCall=null,btnback=null;
    private Button btnRepair;
    private String id;
    private String url= Sql_URL.my_url+"/sm/service_id01.php";
    private String urlRepair=Sql_URL.my_url+"/sm/choose_services.php";
    private Handler han,hanGetImage,hanRepair;
    private ManagerData managerData;
    RepairDetail repairDetail=new RepairDetail();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_repair_bill);
        //得到id
        Intent intent=getIntent();
        id=intent.getStringExtra("extra_data1");
        init();
        getRepairDetail();
        btnCall.setOnClickListener(this);
        btnRepair.setOnClickListener(this);
        btnback.setOnClickListener(this);
    }






    //得到详细信息
    private void getRepairDetail() {

        AutoString autoString=new AutoString("s_id",id);
        String params=autoString.getResult();
        Log.d("test", "params" + "       " + params);
        NetThread nt=new NetThread(han,url,params);
        nt.start();


    }

    //初始化各个控件
    private void init() {
        //报修的图片
        image=(ImageView)findViewById(R.id.image);
        //各个EditText
        textLocation=(TextView)findViewById(R.id.text_location);
        textPhoneNumber=(TextView)findViewById(R.id.text_phone_number);
        textRepairType=(TextView)findViewById(R.id.text_repair_type);
        textSource=(TextView)findViewById(R.id.text_source);
        textRepresentation=(TextView)findViewById(R.id.text_representation);
        textRemark=(TextView)findViewById(R.id.text_remark);
        //打电话的ImageButton
        btnCall=(ImageButton)findViewById(R.id.btn_call);
        //查看反馈的按钮
        btnRepair=(Button)findViewById(R.id.btn_repair);
        //返回的按钮
        btnback=(ImageButton)findViewById(R.id.button_back);
        //获得manageData
        managerData= (ManagerData) getApplication();

        hanGetImage = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                // TODO Auto-generated method stub
                Bitmap bitmap = (Bitmap) msg.obj;
                Log.d("test","bitmap"+"           "+bitmap);
                super.handleMessage(msg);
                image.setImageBitmap(bitmap);

            }
        };

        hanRepair = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                // TODO Auto-generated method stub
                String r= (String) msg.obj;
                if (r.equals("0")){
                    Toast.makeText(AllRepairBillActivity.this, "添加成功", Toast.LENGTH_SHORT).show();

                }else{
                    Toast.makeText(AllRepairBillActivity.this, "添加失败，请重试", Toast.LENGTH_SHORT).show();
                }
                super.handleMessage(msg);

            }
        };

        han = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                String Jsmess =(String) msg.obj;
                Log.d("test","Jsmess"+Jsmess);
                super.handleMessage(msg);
                if (Jsmess.equals("1")){
                    Toast.makeText(AllRepairBillActivity.this, "连接失败", Toast.LENGTH_SHORT).show();
                }else {
                    parseJSONWithJSONObject(Jsmess);
                    //设置数据
                    Log.d("test","repairDetail.getImgurl()"+"             "+repairDetail.getImgurl());
                    GetPicture getPicture=new GetPicture(hanGetImage,repairDetail.getImgurl());
                    getPicture.start();
                    textLocation.setText(repairDetail.getLocation());
                    textPhoneNumber.setText(repairDetail.getPhonenumber());
                    textRepairType.setText(repairDetail.getType());
                    textSource.setText(repairDetail.getSource());
                    textRepresentation.setText(repairDetail.getRepresentation());
                    textRemark.setText(repairDetail.getRemark());
                }
            }

            private void parseJSONWithJSONObject(String Jsmess) {
                // TODO Auto-generated method stub

                try {

                    JSONObject temp = new JSONObject(Jsmess);
                    String reason = temp.getString("reason");
                    repairDetail.setRepresentation(reason);
                    String phoneNumber=temp.getString("phonenumber");
                    repairDetail.setPhonenumber(phoneNumber);
                    String location = temp.getString("address");
                    repairDetail.setLocation(location);
                    String bug=temp.getString("origin");
                    repairDetail.setSource(bug);
                    String imageUrl=temp.getString("image_path");
                    repairDetail.setImgurl(imageUrl);
                    String type=temp.getString("types");
                    if (type.equals("1")){
                        type="宿舍楼";
                    }else if (type.equals("2")){
                        type="教学楼";
                    }else if(type.equals("3")){
                        type="校园设施";
                    }
                    repairDetail.setType(type);
                    String memo=temp.getString("memo");
                    repairDetail.setRemark(memo);




                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    Log.d("test","出错了");
                    e.printStackTrace();
                }

            }

        };



    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_call:
                String number = textPhoneNumber.getText().toString();
                //用intent启动拨打电话
                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + number));
                startActivity(intent);
                break;
            case R.id.btn_repair:
                AutoString autoString=new AutoString("s_id", id);
                autoString.addToResult("m_id",managerData.getManagerId());
                String params=autoString.getResult();
                Log.d("test1", "params" + "       " + params);
                NetThread nt=new NetThread(hanRepair,urlRepair,params);
                nt.start();
                Intent intent1 = new Intent(AllRepairBillActivity.this,MainInterfaceActivity.class);
                startActivity(intent1);
                finish();
                break;
            case R.id.button_back:
                finish();
                break;
            default:
                break;
        }
    }
}

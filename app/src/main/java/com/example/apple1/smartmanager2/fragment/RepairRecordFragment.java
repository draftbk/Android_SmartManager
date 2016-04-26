package com.example.apple1.smartmanager2.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;


import com.example.apple1.smartmanager2.Application.ManagerData;
import com.example.apple1.smartmanager2.R;
import com.example.apple1.smartmanager2.activity.FinishedRepairBillActivity;
import com.example.apple1.smartmanager2.adapter.RepairAdapter;
import com.example.apple1.smartmanager2.entity.RepairRecord;
import com.example.apple1.smartmanager2.net.NetThread;
import com.example.apple1.smartmanager2.net.Sql_URL;
import com.example.apple1.smartmanager2.tools.AutoString;
import com.example.apple1.smartmanager2.tools.SlidingMenu;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by draft on 2015/7/21.
 */
public class RepairRecordFragment extends Fragment {
    /**
     * 上下文对象
     */
    public Context context;
    public Activity activity;
    private ListView listView;
    Bitmap bitmap;
    private Handler hanGetImage;
//    private RepairRecordAdapter adapter;
    private RepairAdapter adapter;
    private ArrayList<RepairRecord> repairRecordList;
    private Handler han;
    private String url= Sql_URL.my_url+"/sm/manager_id03.php";
    private ManagerData managerData;
    private ImageButton buttonBack;
    private SlidingMenu mLeftMenu;
    String sId,phoneNumber,location,bug,imageUrl;

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
        View view = inflater.inflate(R.layout.fragment_repair_record, container,
                false);
        //实例化repairRecordList
        repairRecordList=new ArrayList<RepairRecord>();
        //实例化SlidingMenu
        mLeftMenu = (SlidingMenu) activity.findViewById(R.id.menu);
        //获得manageData
        managerData= (ManagerData) getActivity().getApplication();
        //初始化Button
        buttonBack=(ImageButton)view.findViewById(R.id.button_menu);
        // 添加监听
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //转到菜单
                mLeftMenu.toggle();
            }
        });
        //初始化ListView
        listView= (ListView) view.findViewById(R.id.listview_record);
       // listView.setAdapter(adapter);
        han = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                String Jsmess =(String) msg.obj;
                Log.d("test","Jsmess"+Jsmess);
                super.handleMessage(msg);
                if (Jsmess.equals("1")){
                    Toast.makeText(context, "连接失败", Toast.LENGTH_SHORT).show();
                }else {
                    parseJSONWithJSONObject(Jsmess);
                }
            }

            private void parseJSONWithJSONObject(String Jsmess) {
                // TODO Auto-generated method stub
                JSONArray arr = null;
                try {
                    arr = new JSONArray(Jsmess);
                    Log.d("test", "arr" + arr);

                    for (int i = 0; i < arr.length(); i++) {
                        JSONObject temp = (JSONObject) arr.getJSONObject(i);

                        sId = temp.getString("s_id");
                        phoneNumber=temp.getString("phonenumber");
                        location = temp.getString("address");
                        bug=temp.getString("origin");
                        imageUrl=temp.getString("image_path");

                                RepairRecord repairRecord=new RepairRecord(sId,location,phoneNumber,bug,imageUrl);
                                Log.d("test", " repairRecord.toString()" +"               "+ repairRecord.toString());
                                //加入列表
                                Log.d("test", "repairRecordList" + repairRecordList);
                                repairRecordList.add(repairRecord);



                    }



                    Log.d("test","activity"+"           "+activity);
//                    adapter=new RepairRecordAdapter (activity,R.layout.repair_item,repairRecordList);
                    adapter=new RepairAdapter(activity,R.layout.repair_item,repairRecordList,listView);
                    listView.setAdapter(adapter);
                    //Log.d("test", "exapmple" + example);
                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id1) {
                            RepairRecord repairRecord =repairRecordList.get(position);
                            Intent intent = new Intent(activity, FinishedRepairBillActivity.class);
                            intent.putExtra("extra_data1", repairRecord.getS_id());
                            startActivity(intent);
                        }
                    });
                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

            }
        };
        Log.d("test","managerData.getManagerId()"+"       "+managerData.getManagerId());
        AutoString autoString=new AutoString("m_id",managerData.getManagerId());
        String params=autoString.getResult();
        Log.d("test","params"+"       "+params);
        NetThread nt=new NetThread(han,url,params);
        nt.start();
        return view;
    }
}
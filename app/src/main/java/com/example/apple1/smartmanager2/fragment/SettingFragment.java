package com.example.apple1.smartmanager2.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.apple1.smartmanager2.Application.ManagerData;
import com.example.apple1.smartmanager2.R;
import com.example.apple1.smartmanager2.net.GetPicture;
import com.example.apple1.smartmanager2.net.NetThread;
import com.example.apple1.smartmanager2.tools.AutoString;
import com.example.apple1.smartmanager2.tools.PictureChangeToRound;
import com.example.apple1.smartmanager2.tools.SlidingMenu;


/**
 * Created by draft on 2015/7/20.
 */
public class SettingFragment extends Fragment implements View.OnClickListener {
    /**
     * 上下文对象
     */
    public Context context;
    public Activity activity;
    public ManagerData managerData;
    private ImageButton buttonOk;
    private ImageButton buttonBack;
    private EditText editPhoneNumber;
    private ImageView imageHead;
    private EditText editNickname;
    private Handler hanGetImage;
    private Handler hanUpLoading;
    private View view;
    private SlidingMenu mLeftMenu;



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
        view = inflater.inflate(R.layout.fragment_setting, container,
                false);
        init();
        initializeTable();
        buttonBack.setOnClickListener(this);
//        buttonOk.setOnClickListener(this);
        return view;
    }


    /**
     * 初始化
     */
    private void init() {
        managerData=(ManagerData) getActivity().getApplication();
        buttonBack= (ImageButton) view.findViewById(R.id.button_menu);
        buttonOk= (ImageButton) view.findViewById(R.id.button_ok);
        editPhoneNumber= (EditText) view.findViewById(R.id.edit_phone_number);
        imageHead= (ImageView) view.findViewById(R.id.image_head);
        editPhoneNumber= (EditText) view.findViewById(R.id.edit_phone_number);
        editNickname= (EditText) view.findViewById(R.id.edit_nickname);
        mLeftMenu = (SlidingMenu) activity.findViewById(R.id.menu);
        hanGetImage = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                // TODO Auto-generated method stub
                Bitmap bitmap = (Bitmap) msg.obj;
                Log.d("test","bitmap"+"           "+bitmap);
                //把图片变圆
                PictureChangeToRound pictureChangeToRound=new PictureChangeToRound();
                bitmap= pictureChangeToRound.toRoundBitmap(bitmap);
                super.handleMessage(msg);
                imageHead.setImageBitmap(bitmap);

            }
        };

        hanUpLoading = new Handler() {

            @Override
            public void handleMessage(Message msg) {
                // TODO Auto-generated method stub
                String Jsmess = msg.obj.toString();
                super.handleMessage(msg);
                if(Jsmess.equals("1")){
                    Toast.makeText(context, "更改失败", Toast.LENGTH_SHORT).show();
                } else{
                    Toast.makeText(context, "更改成功", Toast.LENGTH_SHORT).show();
            }

            }
        };



    }

    /**l
     * 把表格原来的数据填了
     */
    private void initializeTable() {
        //设置editText中的值
        editPhoneNumber.setText(managerData.getManagerPhone());
        editNickname.setText(managerData.getNickName());
        //联网设置imageView
        Log.d("test1","managerData.getImagePath()"+"                 "+managerData.getImagePath());
        GetPicture getPicture=new GetPicture(hanGetImage,managerData.getImagePath());
        getPicture.start();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button_back:
                //切换到侧滑菜单
               mLeftMenu.toggle();
                break;
            case R.id.button_ok:
//                String newPhoneNumber=editPhoneNumber.getText().toString();
//                String newNickname=editNickname.getText().toString();
//                String newImageUrl="no picture now";
//                //添加要传入的参数，AutoString是自己写的方法
//                AutoString autoString=new AutoString("m_id",managerData.getManagerId());
//                autoString.addToResult("Nickname",newNickname);
//                autoString.addToResult("Phonenumber_m",newPhoneNumber);
//                autoString.addToResult("Icon_m",newImageUrl);
//                //要传入网络类的数据
//                String params=autoString.getResult();
//                String upLoadingUrl="http://1.smartprotecter.sinaapp.com/sm/change_m.php";
//                //开启网络
//                NetThread netThread=new NetThread(hanUpLoading,upLoadingUrl,params);
//                netThread.start();
                Toast.makeText(context, "该功能还在更新中", Toast.LENGTH_SHORT).show();
                break;
        }

    }
}

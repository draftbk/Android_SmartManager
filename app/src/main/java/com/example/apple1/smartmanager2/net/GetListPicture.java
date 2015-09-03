package com.example.apple1.smartmanager2.net;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.example.apple1.smartmanager2.tools.ChangeImagePath;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.example.apple1.smartmanager2.tools.ChangeImagePath;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by draft on 2015/7/27.
 */
public class GetListPicture extends Thread{
    private String url;
    private Handler han;
    private int position;

    public GetListPicture(Handler han,String url,int position){
        this.han=han;
        this.url=url;
        this.position=position;
    }
    @Override
    public void run() {
        // TODO Auto-generated method stub
        HttpURLConnection conn = null;
        try {


            ChangeImagePath changeImagePath=new ChangeImagePath();
            url=changeImagePath.change(url);
            Log.d("test", "url" + "       " + url);
            URL realurl = new URL(url);
            Log.d("test", "realurl" + "       " + realurl);
            conn = (HttpURLConnection) realurl.openConnection();
            Log.d("test", "conn" + "       " + conn);
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(5000);
            conn.setReadTimeout(8000);
            int code = 200;
            // code=conn.getResponseCode();
            Log.d("test", "conn.getResponseCode()" + "       " + code);
            if (code == 200) {
                InputStream inputStream = conn.getInputStream();
                Log.d("test", "inputStream" + "       " + inputStream);
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                Log.d("test", "bitmap" + "       " + bitmap);
                Message mess = new Message();
                mess.obj = bitmap;
                mess.what=position;
                han.sendMessage(mess);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (conn != null) {
                conn.disconnect();
            }


        }

    }

}
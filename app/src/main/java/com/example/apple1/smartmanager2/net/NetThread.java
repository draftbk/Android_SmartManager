package com.example.apple1.smartmanager2.net;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import org.apache.http.NameValuePair;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by draft on 2015/7/23.
 */
public class NetThread extends Thread{
    private String params;
    private String url;
    private Handler han;
    String result="";

    public NetThread(Handler han, String url, String params){
        this.han=han;
        this.url=url;
        this.params = params;
    }



    @Override
    public void run() {
        // TODO Auto-generated method stub
        HttpURLConnection connection=null;
        try {
            URL realUrl =new URL(url);
            //打开和URL之间的连接
            connection=(HttpURLConnection)realUrl.openConnection();
            //设置通用的请求属性
            connection.setRequestMethod("POST");
            connection.setConnectTimeout(8000);
            connection.setReadTimeout(8000);
            DataOutputStream out=new DataOutputStream(connection.getOutputStream());
            Log.d("test","params"+"         "+params);
            out.writeBytes(params);
            InputStream in=connection.getInputStream();
            Log.d("test","in"+"         "+in);
            //对获取的输入流进行读取
            BufferedReader reader=new BufferedReader(new InputStreamReader(in));
            Log.d("test","reader"+"         "+reader);
            StringBuilder response= new StringBuilder();
            Log.d("test","response"+"         "+response);
            String line;
            while ((line=reader.readLine())!=null)
            {
                result+=line;
            }
            Log.d("test","result"+"................."+result);

                if(result.equals("1")){
                    Message mess=new Message();
                    mess.what=1;
                    mess.obj =result;
                    han.sendMessage(mess);

                }else {
                    Message mess=new Message();
                    mess.what=0;
                    mess.obj = result;
                    han.sendMessage(mess);
                }

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }finally {
            if (connection!=null){
                connection.disconnect();
            }
        }

    }

}
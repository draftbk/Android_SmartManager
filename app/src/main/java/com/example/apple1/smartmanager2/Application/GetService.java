package com.example.apple1.smartmanager2.Application;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;

import com.example.apple1.smartmanager2.net.GetCount;

import org.json.JSONException;

/**
 * Created by Huhu on 9/23/15.
 */
public class GetService extends Service {
    private Context mContext = this;
    boolean stopThread = false;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        startThread();
        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private void startThread() {
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                while (!stopThread) {
                    new GetCount(new GetCount.SuccessCallBack() {
                        @Override
                        public void onSuccess(String result) throws JSONException {
                            Application.INT_NOW = Integer.parseInt(result);

                        }
                    });


                    try {

                        Application.INT_LAST = Application.INT_NOW;
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        t.start();
    }

}

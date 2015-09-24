package com.example.apple1.smartmanager2.Application;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.example.apple1.smartmanager2.net.GetCount;

import org.json.JSONException;

/**
 * Created by Huhu on 9/23/15.
 */
public class GetService extends Service {
    private Context mContext=this;
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        new GetCount(new GetCount.SuccessCallBack() {
            @Override
            public void onSuccess(String result) throws JSONException {
                Log.e("count", result);
            }
        });
        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}

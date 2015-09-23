package com.example.apple1.smartmanager2.net;

import org.json.JSONException;

/**
 * Created by Huhu on 8/20/15.
 */
public class GetCount {

    public GetCount( final SuccessCallBack successCallBack) {
        new NetGetConnection("http://1.smartprotecter.sinaapp.com/sp/for_update.php", new NetGetConnection.SuccessCallback() {
            @Override
            public void onSuccess(String result) throws JSONException {
                successCallBack.onSuccess(result);
            }
        }, new NetGetConnection.FailCallback() {
            @Override
            public void onFail() {

            }
        });

    }

    public static interface SuccessCallBack {
        void onSuccess(String result) throws JSONException;
    }

    public static interface FailCallBack {
        void onFail();
    }

}

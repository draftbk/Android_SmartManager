package com.example.apple1.smartmanager2.Application;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.widget.Toast;

import com.example.apple1.smartmanager2.R;
import com.example.apple1.smartmanager2.activity.MainInterfaceActivity;
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
                            int i = Application.INT_NOW - Application.INT_LAST;
                            NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                            if (i > 0) {
                                //弹出notifaction

                                Toast.makeText(mContext, "有新保修单了", Toast.LENGTH_LONG).show();
                                PendingIntent pendingIntent2 = PendingIntent.getActivity(mContext, 0, new Intent(mContext, MainInterfaceActivity.class), 0);
                                // 通过Notification.Builder来创建通知，注意API Level
                                // API11之后才支持
                                Notification notify2 = new Notification.Builder(mContext).setSmallIcon(R.drawable.icon) // 设置状态栏中的小图片，尺寸一般建议在24×24，这个图片同样也是在下拉状态栏中所显示，如果在那里需要更换更大的图片，可以使用setLargeIcon(Bitmap
                                        // icon)
                                        .setTicker("有新报修单了~")// 设置在status
                                                // bar上显示的提示文字
                                        .setContentTitle("用户提交了新报修单")// 设置在下拉status
                                                // bar后Activity，本例子中的NotififyMessage的TextView中显示的标题
                                        .setContentText("点击查看")// TextView中显示的详细内容
                                        .setContentIntent(pendingIntent2) // 关联PendingIntent
                                        .setNumber(1) // 在TextView的右方显示的数字，可放大图片看，在最右侧。这个number同时也起到一个序列号的左右，如果多个触发多个通知（同一ID），可以指定显示哪一个。

                                        .getNotification(); // 需要注意build()是在API level
                                // 16及之后增加的，在API11中可以使用getNotificatin()来代替
                                notify2.flags |= Notification.FLAG_AUTO_CANCEL;
                                notify2.defaults |= Notification.DEFAULT_SOUND;
                                manager.notify(1, notify2);

                            }
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

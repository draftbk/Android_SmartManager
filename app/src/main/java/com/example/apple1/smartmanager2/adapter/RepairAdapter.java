package com.example.apple1.smartmanager2.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;


import com.example.apple1.smartmanager2.R;
import com.example.apple1.smartmanager2.entity.RepairRecord;
import com.example.apple1.smartmanager2.net.GetListPicture;
import com.example.apple1.smartmanager2.net.GetPicture;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by draft on 2015/7/28.
 */
public class RepairAdapter extends ArrayAdapter<RepairRecord> {

    private Context context;
    private int resourceId;
    private Handler hanGetImage;
    private ListView listView;
    private ArrayList<Bitmap> bitmapArrayList=new ArrayList<Bitmap>();


    public RepairAdapter(Context context, int textViewResourceId, List<RepairRecord> objects, ListView listView){
        super(context,textViewResourceId,objects);
        this.context=context;
        resourceId=textViewResourceId;
        this.listView=listView;



    }

    @Override
    public View getView( final int position,View convertView,ViewGroup parent){
        RepairRecord repairRecord=getItem(position);
        Log.d("test","repairRecord"+repairRecord);
        //获取图片bitmap
        String imageUrl=repairRecord.getImagePath();
        Log.d("test","imageUrl"+imageUrl);
        GetListPicture getlistPicture=new GetListPicture(hanGetImage,imageUrl,position);
        getlistPicture.start();
        View view;
        final ViewHolder viewHolder;
//        if(convertView==null){
            Log.d("test",  "convertView"+convertView);
            view= LayoutInflater.from(getContext()).inflate(resourceId,null);
            viewHolder=new ViewHolder();
            viewHolder.imageRepair=(ImageView)view.findViewById(R.id.image_repair);
            viewHolder.imageRepair.setTag(position);
            viewHolder.location=(TextView)view.findViewById(R.id.text_location);
            viewHolder.phonenumber=(TextView)view.findViewById(R.id.text_phonenumber);
            viewHolder.bug=(TextView)view.findViewById(R.id.text_bug);
            view.setTag(viewHolder);
//        }else{
//            Log.d("test",  "convertViewelse"+convertView);
//            view=convertView;
//            viewHolder=(ViewHolder) view.getTag();
//        }
        viewHolder.location.setText(repairRecord.getLocation());
        viewHolder.phonenumber.setText(repairRecord.getPhonenumber());
        viewHolder.bug.setText(repairRecord.getSource());
        Log.d("test", "bitmapArrayList.size()" + "         " + bitmapArrayList.size());
        hanGetImage = new Handler() {

            @Override
            public void handleMessage(Message msg) {
                // TODO Auto-generated method stub
                Bitmap bitmap;
                bitmap = (Bitmap) msg.obj;
                super.handleMessage(msg);
                ImageView imageView=(ImageView)listView.findViewWithTag(msg.what);
                if(imageView!=null){
                    Log.d("test", "imageView.getTag()" + "         " + imageView.getTag());
                    imageView.setImageBitmap(bitmap);
                }
            }
        };
        return view;
    }


    class ViewHolder{
        ImageView imageRepair;
        TextView location,phonenumber,bug;
    }

}
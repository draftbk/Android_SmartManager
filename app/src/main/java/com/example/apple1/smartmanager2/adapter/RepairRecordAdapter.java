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
import android.widget.TextView;


import com.example.apple1.smartmanager2.R;
import com.example.apple1.smartmanager2.entity.RepairRecord;
import com.example.apple1.smartmanager2.net.GetPicture;

import java.util.List;

/**
 * Created by draft on 2015/7/28.
 */
public class RepairRecordAdapter  extends ArrayAdapter<RepairRecord> {

    private Context context;
    private int resourceId;
    private Handler hanGetImage;
    Bitmap bitmap;
    public RepairRecordAdapter(Context context,int textViewResourceId,List<RepairRecord> objects){
        super(context,textViewResourceId,objects);
        this.context=context;
        resourceId=textViewResourceId;
    }

    @Override
    public View getView( int position,View convertView,ViewGroup parent){
        RepairRecord repairRecord=getItem(position);
        Log.d("test","repairRecord"+repairRecord);
        View view;
        final ViewHolder viewHolder;
        if(convertView==null){
            view= LayoutInflater.from(getContext()).inflate(resourceId,null);
            viewHolder=new ViewHolder();
            viewHolder.imageRepair=(ImageView)view.findViewById(R.id.image_repair);
            viewHolder.location=(TextView)view.findViewById(R.id.text_location);
            viewHolder.phonenumber=(TextView)view.findViewById(R.id.text_phonenumber);
            viewHolder.bug=(TextView)view.findViewById(R.id.text_bug);
            view.setTag(viewHolder);
        }else{
            view=convertView;
            viewHolder=(ViewHolder) view.getTag();
        }
        Log.d("test","repairRecord.getLocation()"+"         "+repairRecord.getLocation());
        viewHolder.location.setText(repairRecord.getLocation());
        Log.d("test", "viewHolder.location.getText().toString()" + "         " + viewHolder.location.getText().toString());
        viewHolder.phonenumber.setText(repairRecord.getPhonenumber());
        viewHolder.bug.setText(repairRecord.getSource());
        //获取图片bitmap

        String imageUrl=repairRecord.getImagePath();
        GetPicture getPicture=new GetPicture(hanGetImage,imageUrl);
        getPicture.start();

        //设置图片

        hanGetImage = new Handler() {

            @Override
            public void handleMessage(Message msg) {
                // TODO Auto-generated method stub
                bitmap = (Bitmap) msg.obj;
                super.handleMessage(msg);
                Log.d("test", "viewHolder:" + viewHolder + "imageRepair:" + viewHolder.imageRepair + "bitmap:" + bitmap);
                viewHolder.imageRepair.setImageBitmap(bitmap);
            }
        };


        return view;
    }


    class ViewHolder{
        ImageView imageRepair;
        TextView location,phonenumber,bug;
    }

}
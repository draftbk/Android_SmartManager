package com.example.apple1.smartmanager2.entity;

import android.graphics.Bitmap;

/**
 * Created by draft on 2015/7/28.
 */
public class RepairRecord {
    private String s_id;
    private String location;
    private String phonenumber;
    private String source;
    private String imagePath;


    public RepairRecord(String s_id,String location, String phonenumber, String source, String imagePath) {
        this.s_id=s_id;
        this.location = location;
        this.phonenumber = phonenumber;
        this.source = source;
        this.imagePath = imagePath;

    }



    public String getS_id() {
        return s_id;
    }

    public void setS_id(String location) {
        this.s_id = s_id;
    }

    public String getLocation() {
        return location;
    }

    @Override
    public String toString() {
        return "RepairRecord{" +
                "s_id='" + s_id + '\'' +
                ", location='" + location + '\'' +
                ", phonenumber='" + phonenumber + '\'' +
                ", source='" + source + '\'' +
                ", imagePath='" + imagePath + '\'' +
                '}';
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }
}

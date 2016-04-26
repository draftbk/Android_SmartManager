package com.example.apple1.smartmanager2.entity;

/**
 * Created by draft on 2015/7/28.
 */
public class RepairRecordSearch {
    private String s_id;
    private String location;
    private String phonenumber;
    private String source;
    private String imagePath;
    private String states;


    public RepairRecordSearch(String s_id, String location, String phonenumber, String source, String imagePath,String states) {
        this.s_id=s_id;
        this.location = location;
        this.phonenumber = phonenumber;
        this.source = source;
        this.imagePath = imagePath;
        this.states=states;

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
        return "RepairRecordSearch{" +
                "s_id='" + s_id + '\'' +
                ", location='" + location + '\'' +
                ", phonenumber='" + phonenumber + '\'' +
                ", source='" + source + '\'' +
                ", imagePath='" + imagePath + '\'' +
                ", states='" + states + '\'' +
                '}';
    }

    public String getStates() {
        return states;
    }

    public void setStates(String states) {
        this.states = states;
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

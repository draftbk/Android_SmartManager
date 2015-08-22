package com.example.apple1.smartmanager2.Application;

import android.app.Application;

/**
 * Created by draft on 2015/7/26.
 */
public class ManagerData extends Application{
    private String managerId;
    private String managerPhone;
    private String imagePath;
    private String nickName;

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getManagerId() {
        return managerId;
    }

    public void setManagerId(String managerId) {
        this.managerId = managerId;
    }

    public String getManagerPhone() {
        return managerPhone;
    }

    public void setManagerPhone(String managerPhone) {
        this.managerPhone = managerPhone;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }


    public void onCreate(){
        super.onCreate();
        setManagerId("未能获取");
        setManagerPhone("未能获取");
        setImagePath("未能获取");
        setNickName("未能获取");

    }


}

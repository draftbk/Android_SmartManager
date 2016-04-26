package com.example.apple1.smartmanager2.tools;

/**
 * Created by slf on 16/4/22.
 */
public class WordTool {
    private String states_1[]={"没有","未","没","待"};
    private String states_2[]={"我的","我"};
    private String states_3[]={"修好","已"};
    private String about_location[]={"地点是"};
    public String getStates(String content){
        String states="";
        if (contentIn(states_1,content)){
            states="1";
        }else if (contentIn(states_2,content)){
            states="2";
        }else if (contentIn(states_3,content)){
            states="3";
        }
        return states;
    }
    public String getLocation(String content){
        String location="";
        if (contentIn(about_location,content)){
            int l1=content.indexOf("地点是");
            int l2=content.indexOf("的",l1);
            location=content.substring(l1+3,l2);
        }
        return location;
    }
    public int getLocationI1(String content){
            int l1=content.indexOf("地点是");
        return l1;
    }
    public int getLocationI2(String content){
        int l1=content.indexOf("地点是");
        int l2=content.indexOf("的",l1);
        return l2;
    }

    private Boolean contentIn(String s[],String content) {
        for (int i=0;i<s.length;i++){
            if (content.contains(s[i])){
                return true;
            }
        }
        return false;
    }
}

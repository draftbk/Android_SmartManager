package com.example.apple1.smartmanager2.tools;

/**
 * Created by draft on 2015/7/26.
 */
public class AutoString {

    private String result="";

    public AutoString(String name, String value) {
        result=result+name+"="+value;
    }

    public void addToResult(String name,String value){
        result=result+"&"+name+"="+value;
    }

    public String getResult() {
        return result;
    }
}

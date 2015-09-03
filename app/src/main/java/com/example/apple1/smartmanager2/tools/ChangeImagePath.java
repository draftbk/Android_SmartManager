package com.example.apple1.smartmanager2.tools;

/**
 * Created by apple1 on 2015/9/1.
 */
public class ChangeImagePath {


    public ChangeImagePath() {
    }

    public String change(String imagePath){
        String imagePathAfterChange = null;
        int i1=imagePath.indexOf(" ");
        int i2=imagePath.length();
        imagePathAfterChange=imagePath.substring(0,i1)+"%20"+imagePath.substring(i1+1,i2);
        return imagePathAfterChange;
    }

}
